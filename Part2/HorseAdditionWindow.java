package Part2;

import javax.swing.*;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GraphicsConfiguration;
import java.awt.GridLayout;
import java.awt.event.*;

import Part1.Breed;
import Part1.Horse;
import Part1.Race;

public class HorseAdditionWindow {

    private JFrame window;
    private int currentHeight = 80;

    public HorseAdditionWindow(Race race) {
        window = new JFrame("Add Horse");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setSize(800, 600);
        window.setLocationRelativeTo(null);
        window.setLayout(null);
        window.getContentPane().setBackground(Color.decode("#74add6"));


        // JPanel panel = new JPanel();
        // panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
        // panel.setPreferredSize(new Dimension(200, 30));

        JLabel horseNameLabel = new JLabel("Horse Name:");
        horseNameLabel.setBounds(50, 30, 200, 30);
        JTextField horseNameField = new JTextField("Name");
        horseNameField.setBounds(50, 80, 200, 30);

        // JLabel horseSymbolLabel = new JLabel("Horse Symbol:");
        // horseSymbolLabel.setBounds(50, 130, 200, 30);
        // JTextField horseSymbolField = new JTextField("Symbol");
        // horseSymbolField.setBounds(50, 180, 200, 30);

        JLabel horseConfidenceLabel = new JLabel("Horse Confidence:");
        horseConfidenceLabel.setBounds(50, 130, 200, 30);
        JTextField horseConfidenceField = new JTextField("Confidence");
        horseConfidenceField.setBounds(50, 180, 200, 30);

        JLabel horseBreedLabel = new JLabel("Horse Breed:");
        horseBreedLabel.setBounds(50, 230, 200, 30);
        JComboBox horseBreedField = new JComboBox(new String[]{"Haflinger", "Andalusian", "Eriskay", "Caspian", "Arabian", "Aegidienberger", "Connemara", "Morgan"});
        horseBreedField.setBounds(50, 280, 200, 30);

        JButton addButton = new JButton("Add Horse");
        addButton.setBounds(50, 330, 200, 30);

        JButton continueButton = new JButton("Continue");
        continueButton.setBounds(50, 380, 200, 30);

        JLabel horseLabel = new JLabel("Horses:");
        horseLabel.setBounds(500, 30, 200, 30);

        addButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (horseNameField.getText().equals("") || horseConfidenceField.getText().equals("")) {
                    JOptionPane.showMessageDialog(window, "Please fill in all the fields", "Warning", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                Double confidence = 0.0;
                try {
                    confidence = Double.parseDouble(horseConfidenceField.getText());
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(window, "Confidence should be a number between 0 and 1!", "Warning", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                if(confidence < 0.1 || confidence > 1) {
                    JOptionPane.showMessageDialog(window, "Confidence should be a number between 0.1 and 1!", "Warning", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                Horse horse = new Horse(horseNameField.getText(), confidence, Breed.valueOf(horseBreedField.getSelectedItem().toString()));
                race.addHorse(horse);
                JLabel addedLabel = new JLabel(horse.getName()+" added! Current confidence: "+horse.getConfidence());
                addedLabel.setBounds(500, currentHeight, 300, 30);
                currentHeight += 50;
                window.add(addedLabel); // Add the label to the panel
                window.revalidate(); // Revalidate the window to update the UI
                window.repaint();
            }
        });

        continueButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if(race.getHorses().size()<2){
                    JOptionPane.showMessageDialog(window, "You need at least 2 horses to start the race!", "Warning", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                window.setVisible(false);
                RaceControlWindow raceControlWindow = new RaceControlWindow(race);
                raceControlWindow.setVisible(true);
            }
        });

        window.add(horseNameLabel);
        window.add(horseNameField);
        // window.add(horseSymbolLabel);
        // window.add(horseSymbolField);
        window.add(horseConfidenceLabel);
        window.add(horseConfidenceField);
        window.add(horseBreedLabel);
        window.add(horseBreedField);
        window.add(addButton);
        window.add(continueButton);
        window.add(horseLabel);

    }
    
    public void setVisible(boolean visible) {
        window.setVisible(visible);
    }

}
