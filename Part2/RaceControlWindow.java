package Part2;

import java.awt.Color;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class RaceControlWindow extends Window{

    public RaceControlWindow(Race race) {
        super("Race Control");

        window.setSize(1050, 800);
        
        JLabel raceLengthLabel = new JLabel("Race track length: "+race.getRaceLength());
        raceLengthLabel.setBounds(30, 30, 150, 30);
        JSlider raceLengthSlider = new JSlider(5, 30, 5);
        raceLengthSlider.setBounds(170, 30, 200, 50);
        raceLengthSlider.setPaintTrack(true);
        raceLengthSlider.setPaintTicks(true);
        raceLengthSlider.setPaintLabels(true);
        raceLengthSlider.setMajorTickSpacing(50);
        raceLengthSlider.setMinorTickSpacing(5);
        raceLengthSlider.setValue(race.getRaceLength());

        JLabel laneTypeLabel = new JLabel("Lane type:");
        laneTypeLabel.setBounds(410, 20, 200, 30);
        JComboBox laneTypeField = new JComboBox(new String[]{"Dirt", "Grass"});
        laneTypeField.setBounds(400, 50, 200, 30);
        laneTypeField.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                race.setLaneType(Lane.valueOf((laneTypeField.getSelectedItem().toString())));
            }
        });

        JButton previousRoundsButton = new JButton("Previous rounds");
        previousRoundsButton.setBounds(660, 50, 180, 50);
        previousRoundsButton.setEnabled(race.getRounds().size() > 0);
        previousRoundsButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                window.dispose();
                PreviousRoundsWindow previousRoundsWindow = new PreviousRoundsWindow(race);
                previousRoundsWindow.setVisible(true);
            }
        });

        JButton saveButton = new JButton("Save");
        saveButton.setBounds(900, 30, 100, 100);
        saveButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                GameSaveController.saveGame(race);
            }
        });

        JButton backButton = new JButton("Back");
        backButton.setBounds(290, 680, 200, 30);
        JButton startRaceButton = new JButton("Start race!");
        startRaceButton.setBounds(510, 680, 200, 30);

        Image shopImage = null;
        try {
            shopImage = ImageIO.read(getClass().getResource("resources/shop.png"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        JButton shopButton = new JButton(new ImageIcon(shopImage.getScaledInstance(100, 100, Image.SCALE_DEFAULT)));
        shopButton.setBounds(750, 630, 100, 100);
        shopButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                window.dispose();
                ShopWindow shopWindow = new ShopWindow(race);
                shopWindow.setVisible(true);
            }
        });

        race.calculateOdds();

        int horseY = 200;
        int horseX = 30;
        for(int i = 0; i<race.getHorses().size(); i++) {
            if(i%5 == 0 && i != 0) {
                horseY += 200;
                horseX = 30;
            }
            Image img = null;
            try {
                String imageLink = race.getHorses().get(i).getImageLinks()[0];
                img = ImageIO.read(getClass().getResource(imageLink));
            } catch (Exception e) {
                e.printStackTrace();
            }
            JButton horse = new JButton(new ImageIcon(img));
            final int horseIndex = i;
            horse.addActionListener(e -> {
                window.dispose();
                HorseInfoWindow horseInfoWindow = new HorseInfoWindow(race, horseIndex);
                horseInfoWindow.setVisible(true);
            });
            JLabel horseNameLabel = new JLabel(race.getHorses().get(i).getName());
            double chanceOfWinning = race.getHorses().get(i).getChanceOfWinning();
            JLabel horseChanceLabel = new JLabel("Chance of winning: " + String.format("%.2f", chanceOfWinning)+"%");
            JLabel horseBetLabel = new JLabel("Current bet: $"+race.getHorses().get(i).getBet());
            horse.setBounds(horseX, horseY, 100, 100);
            horseNameLabel.setBounds(horseX, horseY+100, 100, 30);
            horseChanceLabel.setBounds(horseX, horseY+130, 200, 30);
            horseBetLabel.setBounds(horseX, horseY+160, 200, 30);
            horseX += 200;
            window.add(horse);
            window.add(horseNameLabel);
            window.add(horseChanceLabel);
            window.add(horseBetLabel);
        }

        raceLengthSlider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                race.setRaceLength(raceLengthSlider.getValue());
                raceLengthLabel.setText("Race track length: "+race.getRaceLength());
            }
        });

        backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
                window.dispose();
                HorseAdditionWindow horseAdditionWindow = new HorseAdditionWindow(race);
                horseAdditionWindow.setVisible(true);
            }
        });

        startRaceButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
                window.dispose();
                RaceWindow raceWindow = new RaceWindow(race);
            }
        });

        window.add(raceLengthLabel);
        window.add(raceLengthSlider);
        window.add(laneTypeLabel);
        window.add(laneTypeField);
        window.add(shopButton);
        window.add(previousRoundsButton);
        window.add(saveButton);
        window.add(backButton);
        window.add(startRaceButton);
    }
    
}
