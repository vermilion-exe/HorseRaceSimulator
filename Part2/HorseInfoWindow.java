package Part2;

import java.awt.Color;
import java.awt.Image;

import javax.imageio.ImageIO;
import javax.swing.*;

import Part1.Horse;

public class HorseInfoWindow {
    private JFrame window;
    
    public HorseInfoWindow(Horse horse) {
        window = new JFrame("Horse Info");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setSize(500, 300);
        window.setResizable(false);
        window.setLocationRelativeTo(null);
        window.setLayout(null);
        window.getContentPane().setBackground(Color.decode("#74add6"));

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
        horseName.setBounds(300, 30, 200, 30);

        JLabel horseBreed = new JLabel("Breed: " + horse.getBreed());
        horseBreed.setBounds(300, 70, 200, 30);

        JLabel horseRacesWon = new JLabel("Races won: " + horse.getRacesWon());
        horseRacesWon.setBounds(300, 110, 200, 30);

        JLabel horseConfidence = new JLabel("Confidence: " + horse.getConfidence());
        horseConfidence.setBounds(300, 150, 200, 30);

        JButton closeButton = new JButton("Close");
        closeButton.setBounds(90, 220, 200, 30);
        closeButton.addActionListener(e -> {
            window.dispose();
        });

        window.add(horseIcon);
        window.add(horseName);
        window.add(horseBreed);
        window.add(horseRacesWon);
        window.add(horseConfidence);
        window.add(closeButton);
    }

    public void setVisible(boolean visible) {
        window.setVisible(visible);
    }
}
