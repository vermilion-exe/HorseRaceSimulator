package Part2;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;

import javax.imageio.ImageIO;
import javax.swing.*;

public class PreviousRoundsWindow extends Window {
    
    public PreviousRoundsWindow(Race race) {
        super("Previous Rounds");
        window.setLayout(new BorderLayout());
        window.setSize(800, 600);
        
        JPanel roundPanel = new JPanel();
        roundPanel.setBackground(Color.decode("#74add6"));
        roundPanel.setLayout(new GridBagLayout());
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        for(Round round : race.getRounds()) {  
            ImageIcon icon = null;     
            try{
                Image img = ImageIO.read(getClass().getResource("resources/RoundBackground.jpg"));
                icon = new ImageIcon(img.getScaledInstance(730, 100, Image.SCALE_SMOOTH));
            }
            catch (Exception e) {
                e.printStackTrace();
            }

            JButton roundInfo = new JButton("Round #"+round.getRoundNumber(), icon);
            roundInfo.setPreferredSize(new Dimension(700, 100));
            JLabel spacing = new JLabel(" ");

            roundInfo.setHorizontalTextPosition(SwingConstants.CENTER);
            roundInfo.setFont(new Font("Arial", Font.PLAIN, 24));
        
            roundInfo.addActionListener(e -> {
                window.dispose();
                RoundWindow roundWindow = new RoundWindow(race, round);
                roundWindow.setVisible(true);
            });
        
            roundPanel.add(roundInfo, gbc);
            roundPanel.add(spacing, gbc);
        }
        
        JScrollPane scrollPane = new JScrollPane(roundPanel);
        window.add(scrollPane);

        JButton backButton = new JButton("Back");
        backButton.addActionListener(e -> {
            window.dispose();
            RaceControlWindow raceControlWindow = new RaceControlWindow(race);
            raceControlWindow.setVisible(true);
        });
        window.add(backButton, BorderLayout.SOUTH);
    }
}
