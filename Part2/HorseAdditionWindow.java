package Part2;

import javax.swing.*;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GraphicsConfiguration;
import java.awt.GridLayout;
import java.awt.event.*;
import java.util.Hashtable;

public class HorseAdditionWindow extends Window{

    private int currentHeight = 80;

    public HorseAdditionWindow(Race race) {
        super("Add Horse");

        JLabel moneyLabel = new JLabel("Money: $"+race.getPlayer().getMoney());
        moneyLabel.setBounds(50, 10, 200, 30);

        JLabel horseNameLabel = new JLabel("Horse Name:");
        horseNameLabel.setBounds(50, 50, 200, 30);
        JTextField horseNameField = new JTextField("Name");
        horseNameField.setBounds(50, 100, 200, 30);

        JLabel horseBreedLabel = new JLabel("Horse Breed:");
        horseBreedLabel.setBounds(50, 250, 200, 30);
        JComboBox horseBreedField = new JComboBox(new String[]{"Eriskay", "Haflinger", "Aegidienberger", "Connemara", "Andalusian", "Caspian", "Morgan", "Arabian"});
        horseBreedField.setBounds(50, 300, 200, 30);


        JSlider horseConfidenceSlider = new JSlider();
        horseConfidenceSlider.setMinimum(10); 
        horseConfidenceSlider.setMaximum(100);
        horseConfidenceSlider.setLabelTable(new Hashtable<Integer, JLabel>() {{
            put(10, new JLabel("0.1")); 
            put(50, new JLabel("0.5"));
            put(100, new JLabel("1.0"));
        }});
        horseConfidenceSlider.setPaintTrack(true);
        horseConfidenceSlider.setPaintLabels(true);
        horseConfidenceSlider.setPaintTicks(true);
        horseConfidenceSlider.setMajorTickSpacing(50);
        horseConfidenceSlider.setMinorTickSpacing(5);
        horseConfidenceSlider.setBounds(50, 200, 200, 50);

        JLabel horseConfidenceLabel = new JLabel("Horse Confidence: "+Math.round(horseConfidenceSlider.getValue()/10.0)/10.0);
        horseConfidenceLabel.setBounds(50, 150, 200, 30);

        JLabel horsePriceLabel = new JLabel("Horse Price: $"+Horse.getHorsePrice(Breed.valueOf(horseBreedField.getSelectedItem().toString()),
        Math.round(horseConfidenceSlider.getValue()/10.0)/10.0));
        horsePriceLabel.setBounds(50, 350, 200, 30);

        horseBreedField.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                horsePriceLabel.setText("Horse Price: $"+Horse.getHorsePrice(Breed.valueOf(horseBreedField.getSelectedItem().toString()),
                Math.round(horseConfidenceSlider.getValue()/10.0)/10.0));
            }
        });

        horseConfidenceSlider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                horseConfidenceLabel.setText("Horse Confidence: "+Math.round(horseConfidenceSlider.getValue()/10.0)/10.0);
                horsePriceLabel.setText("Horse Price: $"+Horse.getHorsePrice(Breed.valueOf(horseBreedField.getSelectedItem().toString()),
                Math.round(horseConfidenceSlider.getValue()/10.0)/10.0));
            }
        });

        DefaultButton addButton = new DefaultButton("Add Horse", 50, 380);

        DefaultButton continueButton = new DefaultButton("Continue", 50, 430);

        DefaultButton backButton = new DefaultButton("Back", 50, 480);

        JLabel horseLabel = new JLabel("Horses:");
        horseLabel.setBounds(500, 30, 200, 30);
        
        addButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (horseNameField.getText().equals("")) {
                    JOptionPane.showMessageDialog(window, "Please fill in all the fields", "Warning", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                else if(race.getPlayer().getUnlockedBreeds().contains(Breed.valueOf(horseBreedField.getSelectedItem().toString())) == false) {
                    JOptionPane.showMessageDialog(window, "You have not unlocked this breed!", "Warning", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                int confirm = JOptionPane.showConfirmDialog(window, "Are you sure you want to buy this horse for: $"+
                Horse.getHorsePrice(Breed.valueOf(horseBreedField.getSelectedItem().toString()),
                Math.round(horseConfidenceSlider.getValue()/10.0)/10.0), "Confirmation", JOptionPane.YES_NO_OPTION);

                if (confirm == JOptionPane.YES_OPTION) {
                    Double confidence = Math.round(horseConfidenceSlider.getValue()/10.0)/10.0;
                    int price = Horse.getHorsePrice(Breed.valueOf(horseBreedField.getSelectedItem().toString()), confidence);
                    if(price > race.getPlayer().getMoney()){
                        JOptionPane.showMessageDialog(window, "You don't have enough money to buy this horse!", "Warning", JOptionPane.WARNING_MESSAGE);
                        return;
                    }
                    race.getPlayer().setMoney(race.getPlayer().getMoney()-price);

                    Horse horse = new Horse(horseNameField.getText(), confidence, Breed.valueOf(horseBreedField.getSelectedItem().toString()));
                    boolean added = race.addHorse(horse);
                    if(added){
                        moneyLabel.setText("Money: $"+race.getPlayer().getMoney());
                        JLabel addedLabel = new JLabel(horse.getName()+" added! Current confidence: "+horse.getConfidence());
                        addedLabel.setBounds(500, currentHeight, 300, 30);
                        currentHeight += 50;
                        window.add(addedLabel); // Add the label to the panel
                        window.revalidate(); // Revalidate the window to update the UI
                        window.repaint();
                    }
                    else{
                        JOptionPane.showMessageDialog(window, "You can only add up to 10 horses!", "Warning", JOptionPane.WARNING_MESSAGE);
                    }
                }
            }
        });

        continueButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if(race.getHorses().size()<2){
                    JOptionPane.showMessageDialog(window, "You need at least 2 horses to start the race!", "Warning", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                window.dispose();
                RaceControlWindow raceControlWindow = new RaceControlWindow(race);
                raceControlWindow.setVisible(true);
            }
        });

        backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int confirm = JOptionPane.showConfirmDialog(window, "Are you sure? All unsaved progress will be lost.", "Confirmation", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    window.dispose();
                    MainWindow mainWindow = new MainWindow();
                    mainWindow.setVisible(true);
                }
            }
        });

        window.add(moneyLabel);
        window.add(horseNameLabel);
        window.add(horseNameField);
        window.add(horseConfidenceLabel);
        window.add(horseConfidenceSlider);
        window.add(horsePriceLabel);
        window.add(horseBreedLabel);
        window.add(horseBreedField);
        window.add(addButton);
        window.add(continueButton);
        window.add(backButton);
        window.add(horseLabel);

    }

}
