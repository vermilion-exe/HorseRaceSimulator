package Part2;

import javax.swing.*;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.*;

import Part1.Horse;
import Part1.Race;

public class HorseAdditionWindow {

    private JFrame window;
    private int currentHeight = 50;

    public HorseAdditionWindow(Race race) {
        window = new JFrame("Add Horse");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setSize(800, 600);
        window.setLocationRelativeTo(null);
        window.setLayout(null);


        // JPanel panel = new JPanel();
        // panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
        // panel.setPreferredSize(new Dimension(200, 30));

        JLabel horseNameLabel = new JLabel("Horse Name:");
        horseNameLabel.setBounds(50, 100, 200, 30);
        JTextField horseNameField = new JTextField("Name");
        horseNameField.setBounds(50, 150, 200, 30);

        JLabel horseSymbolLabel = new JLabel("Horse Symbol:");
        horseSymbolLabel.setBounds(50, 200, 200, 30);
        JTextField horseSymbolField = new JTextField("Symbol");
        horseSymbolField.setBounds(50, 250, 200, 30);

        JLabel horseConfidenceLabel = new JLabel("Horse Confidence:");
        horseConfidenceLabel.setBounds(50, 300, 200, 30);
        JTextField horseConfidenceField = new JTextField("Confidence");
        horseConfidenceField.setBounds(50, 350, 200, 30);

        JButton addButton = new JButton("Add Horse");
        addButton.setBounds(50, 400, 200, 30);

        JButton continueButton = new JButton("Continue");
        continueButton.setBounds(50, 450, 200, 30);

        addButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (horseNameField.getText().equals("") || horseSymbolField.getText().equals("") || horseConfidenceField.getText().equals("")) {
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
                if(confidence < 0 || confidence > 1) {
                    JOptionPane.showMessageDialog(window, "Confidence should be a number between 0 and 1!", "Warning", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                Horse horse = new Horse(horseSymbolField.getText().charAt(0), horseNameField.getText(), confidence);
                race.addHorse(horse);
                JLabel addedLabel = new JLabel(horse.getName()+" added! Current confidence: "+horse.getConfidence());
                addedLabel.setBounds(600, currentHeight, 200, 30);
                currentHeight += 50;
                window.add(addedLabel); // Add the label to the panel
                window.revalidate(); // Revalidate the window to update the UI
                window.repaint();
            }
        });

        window.add(horseNameLabel);
        window.add(horseNameField);
        window.add(horseSymbolLabel);
        window.add(horseSymbolField);
        window.add(horseConfidenceLabel);
        window.add(horseConfidenceField);
        window.add(addButton);
        window.add(continueButton);

    }
    
    public void setVisible(boolean visible) {
        window.setVisible(visible);
    }

}
