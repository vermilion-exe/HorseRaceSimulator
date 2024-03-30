package Part2;

import java.awt.Color;
import java.awt.Image;

import javax.imageio.ImageIO;
import javax.swing.*;

public class HorseInfoWindow {
    private JFrame window;
    
    public HorseInfoWindow(Race race, int horseIndex) {
        window = new JFrame("Horse Info");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setSize(500, 300);
        window.setResizable(false);
        window.setLocationRelativeTo(null);
        window.setLayout(null);
        window.getContentPane().setBackground(Color.decode("#74add6"));

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
        horseName.setBounds(300, 10, 200, 30);

        JLabel horseBreed = new JLabel("Breed: " + horse.getBreed());
        horseBreed.setBounds(300, 50, 200, 30);

        JLabel horseRacesWon = new JLabel("Races won: " + horse.getRacesWon());
        horseRacesWon.setBounds(300, 90, 200, 30);

        JLabel horseSpeed = new JLabel("Speed: " + horse.getSpeed());
        horseSpeed.setBounds(300, 130, 200, 30);

        JLabel horseConfidence = new JLabel("Confidence: " + horse.getConfidence());
        horseConfidence.setBounds(300, 170, 200, 30);

        JButton closeButton = new JButton("Close");
        closeButton.setBounds(40, 220, 200, 30);
        closeButton.addActionListener(e -> {
            window.dispose();
            RaceControlWindow raceWindow = new RaceControlWindow(race);
            raceWindow.setVisible(true);
        });

        JButton deleteButton = new JButton("Delete");
        deleteButton.setBounds(260, 220, 200, 30);
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
        window.add(deleteButton);
    }

    public void setVisible(boolean visible) {
        window.setVisible(visible);
    }
}
