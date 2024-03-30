package Part2;

import java.awt.Color;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class RaceControlWindow {
    private JFrame window;

    public RaceControlWindow(Race race) {
        window = new JFrame("Race Control");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setSize(800, 600);
        window.setResizable(false);
        window.setLocationRelativeTo(null);
        window.setLayout(null);
        window.getContentPane().setBackground(Color.decode("#74add6"));
        
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

        JButton saveButton = new JButton("Save");
        saveButton.setBounds(650, 30, 100, 100);
        saveButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                GameSaveController.saveGame(race);
            }
        });

        JButton backButton = new JButton("Back");
        backButton.setBounds(190, 500, 200, 30);
        JButton startRaceButton = new JButton("Start Race!");
        startRaceButton.setBounds(410, 500, 200, 30);

        Image shopImage = null;
        try {
            shopImage = ImageIO.read(getClass().getResource("resources/shop.png"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        JButton shopButton = new JButton(new ImageIcon(shopImage));
        shopButton.setBounds(650, 500, 100, 100);
        shopButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                window.dispose();
                ShopWindow shopWindow = new ShopWindow();
                shopWindow.setVisible(true);
            }
        });


        int horseY = 150;
        int horseX = 30;
        for(int i = 0; i<race.getHorses().size(); i++) {
            if(i%5 == 0 && i != 0) {
                horseY += 150;
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
            horse.setBounds(horseX, horseY, 100, 100);
            horseNameLabel.setBounds(horseX, horseY+100, 100, 30);
            horseX += 155;
            window.add(horse);
            window.add(horseNameLabel);
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
        window.add(saveButton);
        window.add(backButton);
        window.add(startRaceButton);
    }

    public void setVisible(boolean visible) {
        window.setVisible(visible);
    }
    
}
