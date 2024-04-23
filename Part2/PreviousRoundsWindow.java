package Part2;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
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
        roundPanel.setLayout(new BoxLayout(roundPanel, BoxLayout.Y_AXIS));

        for(Round round : race.getRounds()) {
            JButton roundInfo = new JButton("Round #"+round.getRoundNumber());
            roundInfo.setMaximumSize(new Dimension(800, 100));
            
            // try{
            //     Image img = ImageIO.read(getClass().getResource("resources/race-sprites/" + round.getLaneType() + "_0.png"));
            //     roundInfo.setIcon(new ImageIcon(img));
            // }
            // catch (Exception e) {
            //     e.printStackTrace();
            // }
            
            roundInfo.addActionListener(e -> {
                window.dispose();
                RoundWindow roundWindow = new RoundWindow(round);
                roundWindow.setVisible(true);
            });

            roundPanel.add(roundInfo);
        }
        
        JScrollPane scrollPane = new JScrollPane(roundPanel);
        window.add(scrollPane);
    }
}
