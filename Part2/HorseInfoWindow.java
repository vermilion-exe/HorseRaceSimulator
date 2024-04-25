package Part2;

import java.awt.Image;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.util.Hashtable;

public class HorseInfoWindow extends Window{
    
    public HorseInfoWindow(Race race, int horseIndex) {
        super("Horse Info");

        window.setSize(550, 400);

        Horse horse = race.getHorses().get(horseIndex);

        Image img = null;
        try {
            String imageLink = horse.getImageLinks()[0];
            img = ImageIO.read(getClass().getResource(imageLink));
        } catch (Exception e) {
            e.printStackTrace();
        }
        JLabel horseIcon = new JLabel(new ImageIcon(img));
        horseIcon.setBounds(30, 30, 200, 200);

        JLabel horseName = new JLabel("Name: " + horse.getName());
        horseName.setBounds(350, 10, 200, 30);

        JLabel horseBreed = new JLabel("Breed: " + horse.getBreed());
        horseBreed.setBounds(350, 50, 200, 30);

        JLabel horseRacesWon = new JLabel("Races won: " + horse.getRacesWon());
        horseRacesWon.setBounds(350, 90, 200, 30);

        JLabel horseSpeed = new JLabel("Speed: " + horse.getSpeed());
        horseSpeed.setBounds(350, 130, 200, 30);

        JLabel horseConfidence = new JLabel("Confidence: " + String.format("%.1f", horse.getConfidence()));
        horseConfidence.setBounds(350, 170, 200, 30);

        DefaultButton closeButton = new DefaultButton("Close", 380, 320, 140, 30);
        closeButton.addActionListener(e -> {
            window.dispose();
            RaceControlWindow raceWindow = new RaceControlWindow(race);
            raceWindow.setVisible(true);
        });

        JLabel betLabel = new JLabel("Bet amount: $"+horse.getBet());
        betLabel.setBounds(350, 210, 150, 30);
        JSlider betSlider = new JSlider(0, race.getPlayer().getMoney());
        betSlider.setBounds(10, 220, 200, 50);
        betSlider.setSnapToTicks(true);
        betSlider.setPaintTrack(true);
        betSlider.setPaintTicks(true);
        betSlider.setPaintLabels(true);
        betSlider.setMajorTickSpacing(50);
        betSlider.setLabelTable(new Hashtable<Integer, JLabel>() {{
            put(0, new JLabel("$0")); 
            put(race.getPlayer().getMoney()/4, new JLabel("$"+race.getPlayer().getMoney()/4));            
            put(race.getPlayer().getMoney()/2, new JLabel("$"+race.getPlayer().getMoney()/2));
            put(race.getPlayer().getMoney()*3/4, new JLabel("$"+race.getPlayer().getMoney()*3/4));
            put(race.getPlayer().getMoney(), new JLabel("$"+race.getPlayer().getMoney()));
        }});

        DefaultButton betButton = new DefaultButton("Bet", 10, 280, 140, 30);
        betButton.addActionListener(e -> {
            int bet = betSlider.getValue();
            if(bet == 0) {
                JOptionPane.showMessageDialog(window, "Please place a bet", "Warning", JOptionPane.WARNING_MESSAGE);
                return;
            }
            else {
                race.getPlayer().setMoney(race.getPlayer().getMoney() - bet);
                horse.setBet(horse.getBet() + bet);
                betLabel.setText("Bet amount: $"+horse.getBet());
                betSlider.setMaximum(race.getPlayer().getMoney());
                betSlider.setLabelTable(new Hashtable<Integer, JLabel>() {{
                    put(0, new JLabel("$0")); 
                    put(race.getPlayer().getMoney()/4, new JLabel("$"+race.getPlayer().getMoney()/4));            
                    put(race.getPlayer().getMoney()/2, new JLabel("$"+race.getPlayer().getMoney()/2));
                    put(race.getPlayer().getMoney()*3/4, new JLabel("$"+race.getPlayer().getMoney()*3/4));
                    put(race.getPlayer().getMoney(), new JLabel("$"+race.getPlayer().getMoney()));
                }});
            }
        });

        DefaultButton removeBetButton = new DefaultButton("Remove bet", 10, 320, 140, 30);
        removeBetButton.addActionListener(e -> {
            int bet = horse.getBet();
            race.getPlayer().setMoney(race.getPlayer().getMoney()+bet);
            horse.setBet(0);
            betLabel.setText("Bet amount: $"+horse.getBet());
            betSlider.setMaximum(race.getPlayer().getMoney());
            betSlider.setLabelTable(new Hashtable<Integer, JLabel>() {{
                put(0, new JLabel("$0")); 
                put(race.getPlayer().getMoney()/4, new JLabel("$"+race.getPlayer().getMoney()/4));            
                put(race.getPlayer().getMoney()/2, new JLabel("$"+race.getPlayer().getMoney()/2));
                put(race.getPlayer().getMoney()*3/4, new JLabel("$"+race.getPlayer().getMoney()*3/4));
                put(race.getPlayer().getMoney(), new JLabel("$"+race.getPlayer().getMoney()));
            }});
        });

        DefaultButton deleteButton = new DefaultButton("Delete", 220, 320, 140, 30);
        deleteButton.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(window, "Are you sure you want to delete this horse?", "Confirmation", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                race.getHorses().remove(horseIndex);
                window.dispose();
                if(race.getHorses().size() < 2) {
                    HorseAdditionWindow horseAdditionWindow = new HorseAdditionWindow(race);
                    horseAdditionWindow.setVisible(true);
                }
                else{
                    RaceControlWindow raceWindow = new RaceControlWindow(race);
                    raceWindow.setVisible(true);
                }
            }
        });

        window.add(horseIcon);
        window.add(horseName);
        window.add(horseBreed);
        window.add(horseRacesWon);
        window.add(horseSpeed);
        window.add(horseConfidence);
        window.add(closeButton);
        window.add(betSlider);
        window.add(betButton);
        window.add(removeBetButton);
        window.add(betLabel);
        window.add(deleteButton);
    }
}
