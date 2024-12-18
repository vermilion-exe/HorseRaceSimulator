package Part2;

import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.swing.*;

public class RaceWindow extends Window {
    
    public RaceWindow(Race race) {
        super("Race");

        int windowHeight = 60*race.getHorses().size()+150;
        window.setSize(800, windowHeight);

        DefaultButton backButton = new DefaultButton("Back", 300, 15);
        backButton.addActionListener(e -> {
            window.dispose();
            RaceControlWindow raceControlWindow = new RaceControlWindow(race);
            raceControlWindow.setVisible(true);
        });

        JLabel[] horses = new JLabel[race.getHorses().size()]; 

        prepareRace(race, horses);

        startRaceGUI(race, horses, backButton);
    }

    private void prepareRace(Race race, JLabel[] horses){
        int lineY = 115;

        for(int i = 0; i < race.getHorses().size(); i++) {
            BufferedImage tile = null;
            try {
                tile = ImageIO.read(getClass().getResource("resources/race-sprites/Tile.png"));
            }
            catch (Exception e) {
                e.printStackTrace();
            }
            JLabel startLabel = new JLabel(new ImageIcon(tile));
            startLabel.setBounds(0, lineY, 60, 60);
            
            BufferedImage lane = null;
            try{
                String laneType = race.getLaneType().toString()+"_"+(i%2);            
                lane = ImageIO.read(getClass().getResource("resources/race-sprites/" + laneType + ".png"));
            }
            catch (Exception e) {
                e.printStackTrace();
            }
            JLabel laneLabel = new JLabel(new ImageIcon(lane));
            laneLabel.setBounds(60, lineY, 670, 60);

            JLabel finishLabel = new JLabel(new ImageIcon(tile));
            finishLabel.setBounds(730, lineY, 60, 60);

            BufferedImage horseIcon = null;
            try{
                String imageLink = race.getHorses().get(i).getImageLinks()[0];
                horseIcon = ImageIO.read(getClass().getResource(imageLink));
            }
            catch (Exception e) {
                e.printStackTrace();
            }
            horses[i] = new JLabel(new ImageIcon(horseIcon));
            horses[i].setBounds(0, lineY-10, 60, 50);

            window.add(startLabel);
            window.add(laneLabel);
            window.add(finishLabel);
            window.add(horses[i], 0);
            

            lineY += 60;
        }

        setVisible(true);
    }

    private void startRaceGUI(Race race, JLabel[] horses, JButton backButton){
        Map<Horse, Double> horseConfidenceMap = new HashMap<>();
        for(Horse horse : race.getHorses())
        {
            horseConfidenceMap.put(horse, horse.getConfidence());
            horse.goBackToStart();
        }

        Timer timer = new Timer(100, null);

        timer.addActionListener(e -> {
            for(Horse horse : race.getHorses())
            {
                if(race.raceWonBy(horse))
                {
                    race.setRaceFinished(true);
                    horse.countVictory();
                    for(int i = 0; i < horses.length; i++) {
                        BufferedImage horseIcon = null;
                        try{
                            String imageLink = race.getHorses().get(i).getImageLinks()[0];
                            horseIcon = ImageIO.read(getClass().getResource(imageLink));
                        }
                        catch (Exception exc) {
                        }
                        horses[i].setIcon(new ImageIcon(horseIcon));
                    }
                    int totalProfit = race.finishRound(horseConfidenceMap);
                    JOptionPane.showMessageDialog(window, horse.getName()+" won!", "Message", JOptionPane.INFORMATION_MESSAGE);
                    JOptionPane.showMessageDialog(window, "You have earned $"+totalProfit+"!"+"\n You now own $"+race.getPlayer().getMoney(), "Message", JOptionPane.INFORMATION_MESSAGE);
                    break;
                }
            }
            
            if(race.allHorsesFallen()&&!race.getRaceFinished())
            {
                race.setRaceFinished(true);
                race.finishRound(horseConfidenceMap);
                JOptionPane.showMessageDialog(window, "All horses have fallen. No one won.", "Message", JOptionPane.INFORMATION_MESSAGE);
            }

            if(race.getRaceFinished()){
                window.add(backButton);
                ((Timer)e.getSource()).stop();
            }
            else{
                race.iterateRace();
                for(int i = 0; i < horses.length; i++) {
                    if(race.getHorses().get(i).hasFallen()){
                        BufferedImage horseFallIcon = null;
                        try{
                            String imageLink = race.getHorses().get(i).getImageLinks()[5];
                            horseFallIcon = ImageIO.read(getClass().getResource(imageLink));
                        }
                        catch (Exception exc) {
                        }
                        horses[i].setIcon(new ImageIcon(horseFallIcon));
                    }
                    else{
                        int xPosition = (int)getPosition(race.getHorses().get(i).getDistanceTravelled(), 0, race.getRaceLength(), 0, 730);
                        xPosition = xPosition > 730 ? 730 : xPosition;
                        horses[i].setBounds(xPosition, horses[i].getY(), 60, 60);
                        BufferedImage horseIcon = null;
                        try{
                            String imageLink = race.getHorses().get(i).getImageLinks()[(horses[i].getX()/50)%4+1];
                            horseIcon = ImageIO.read(getClass().getResource(imageLink));
                        }
                        catch (Exception exc) {
                        }
                        horses[i].setIcon(new ImageIcon(horseIcon));
                    }
                }
            }

            window.revalidate(); // Revalidate the window to update the UI
            window.repaint();
        });

        timer.start();
        race.setRaceFinished(false);
    }

    private double getPosition(double input, double inputMin, double inputMax, double outputMin, double outputMax) {
        return ((input - inputMin) / (inputMax - inputMin)) * (outputMax - outputMin) + outputMin;
    }

}