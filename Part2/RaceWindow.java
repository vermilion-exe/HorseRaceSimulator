package Part2;

import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.*;

public class RaceWindow {
    private JFrame window;
    
    public RaceWindow(Race race) {
        window = new JFrame("Race");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        int windowHeight = 60*race.getHorses().size()+150;
        window.setSize(800, windowHeight);
        window.setResizable(false);
        window.setLocationRelativeTo(null);
        window.setLayout(null);
        window.getContentPane().setBackground(Color.decode("#74add6"));

        JButton backButton = new JButton("Back");
        backButton.setBounds(300, 15, 200, 30);
        backButton.addActionListener(e -> {
            window.dispose();
            RaceControlWindow raceControlWindow = new RaceControlWindow(race);
            raceControlWindow.setVisible(true);
        });


        JLabel[] horses = new JLabel[race.getHorses().size()]; 

        prepareRace(race, horses);

        startRace(race, horses, backButton);
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

    private void startRace(Race race, JLabel[] horses, JButton backButton){
        for(Horse horse : race.getHorses())
        {
            horse.goBackToStart();
        }

        Timer timer = new Timer(100, null);

        timer.addActionListener(e -> {
            if(race.allHorsesFallen())
            {
                race.setRaceFinished(true);
                JOptionPane.showMessageDialog(window, "All horses have fallen. No one won.", "Message", JOptionPane.INFORMATION_MESSAGE);
            }

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
                    JOptionPane.showMessageDialog(window, horse.getName()+" won!", "Message", JOptionPane.INFORMATION_MESSAGE);
                    break;
                }
            }

            if(race.getRaceFinished()){
                window.add(backButton);
                ((Timer)e.getSource()).stop();
            }
            else{
                race.iterateRace();
                for(int i = 0; i < horses.length; i++) {
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

            window.revalidate(); // Revalidate the window to update the UI
            window.repaint();
        });

        timer.start();
        race.setRaceFinished(false);
    }

    private double getPosition(double input, double inputMin, double inputMax, double outputMin, double outputMax) {
        return ((input - inputMin) / (inputMax - inputMin)) * (outputMax - outputMin) + outputMin;
    }

    public void setVisible(boolean visible) {
        window.setVisible(visible);
    }

}