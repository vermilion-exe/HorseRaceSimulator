package Part2;

import javax.swing.*;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GraphicsConfiguration;
import java.awt.GridLayout;
import java.awt.event.*;

public class HorseAdditionWindow extends Window{

    private int currentHeight = 80;

    public HorseAdditionWindow(Race race) {
        super("Add Horse");

        JLabel horseNameLabel = new JLabel("Horse Name:");
        horseNameLabel.setBounds(50, 30, 200, 30);
        JTextField horseNameField = new JTextField("Name");
        horseNameField.setBounds(50, 80, 200, 30);

        JLabel horseConfidenceLabel = new JLabel("Horse Confidence:");
        horseConfidenceLabel.setBounds(50, 130, 200, 30);
        JTextField horseConfidenceField = new JTextField("Confidence");
        horseConfidenceField.setBounds(50, 180, 200, 30);

        JLabel horseBreedLabel = new JLabel("Horse Breed:");
        horseBreedLabel.setBounds(50, 230, 200, 30);
        JComboBox horseBreedField = new JComboBox(new String[]{"Eriskay", "Haflinger", "Aegidienberger", "Connemara", "Andalusian", "Caspian", "Morgan", "Arabian"});
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
                if(race.getPlayer().getUnlockedBreeds().contains(Breed.valueOf(horseBreedField.getSelectedItem().toString())) == false) {
                    JOptionPane.showMessageDialog(window, "You have not unlocked this breed!", "Warning", JOptionPane.WARNING_MESSAGE);
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
        window.add(horseConfidenceLabel);
        window.add(horseConfidenceField);
        window.add(horseBreedLabel);
        window.add(horseBreedField);
        window.add(addButton);
        window.add(continueButton);
        window.add(horseLabel);

    }

}
