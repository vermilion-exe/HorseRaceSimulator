package Part2;

import java.awt.Color;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import Part1.Lane;
import Part1.Race;

public class RaceControlWindow {
    private JFrame window;

    public RaceControlWindow(Race race) {
        window = new JFrame("Add Horse");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setSize(800, 600);
        window.setResizable(false);
        window.setLocationRelativeTo(null);
        window.setLayout(null);
        window.getContentPane().setBackground(Color.decode("#74add6"));
        
        JLabel raceLengthLabel = new JLabel("Race track length: "+race.getRaceLength());
        raceLengthLabel.setBounds(30, 30, 200, 30);
        JSlider raceLengthSlider = new JSlider(5, 30, 5);
        raceLengthSlider.setBounds(300, 30, 200, 50);
        raceLengthSlider.setPaintTrack(true);
        raceLengthSlider.setPaintTicks(true);
        raceLengthSlider.setPaintLabels(true);
        raceLengthSlider.setMajorTickSpacing(50);
        raceLengthSlider.setMinorTickSpacing(5);
        JButton startRaceButton = new JButton("Start Race!");
        startRaceButton.setBounds(300, 500, 200, 30);

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
            race.setLaneType(Lane.Dirt);
            JButton horse = new JButton(new ImageIcon(img));
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

        startRaceButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
                window.dispose();
                RaceWindow raceWindow = new RaceWindow(race);
            }
        });

        window.add(raceLengthLabel);
        window.add(raceLengthSlider);
        window.add(startRaceButton);
    }

    public void setVisible(boolean visible) {
        window.setVisible(visible);
    }
    
}
