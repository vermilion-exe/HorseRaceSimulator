package Part2;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.*;

public class RoundWindow extends Window {

    public RoundWindow(Race race, Round round) {
        super("Round #"+round.getRoundNumber());

        int windowHeight = 200*((round.getHorseBets().keySet().size()-1)/4)+500;
        window.setSize(1350, windowHeight);
        window.setLocation(0, 0);
        
        JLabel roundLabel = new JLabel("Round #"+round.getRoundNumber());
        roundLabel.setFont(new Font("Dialog", Font.PLAIN, 36));
        roundLabel.setBounds(600, 50, 200, 30);
        window.add(roundLabel);

        JLabel laneTypeLabel = new JLabel("Lane type: "+round.getLaneType());
        laneTypeLabel.setFont(new Font("Dialog", Font.PLAIN, 24));
        laneTypeLabel.setBounds(20, 100, 300, 30);
        JLabel raceLengthLabel = new JLabel("Race track length: "+round.getRaceLength());
        raceLengthLabel.setFont(new Font("Dialog", Font.PLAIN, 24));
        raceLengthLabel.setBounds(20, 140, 300, 30);
        JLabel raceFinishedLabel = new JLabel("Race status: "+(round.wasRaceFinished()?"Finished":"Unfinished"));
        raceFinishedLabel.setFont(new Font("Dialog", Font.PLAIN, 24));
        raceFinishedLabel.setBounds(20, 180, 300, 30);
        window.add(laneTypeLabel);
        window.add(raceLengthLabel);
        window.add(raceFinishedLabel);

        int horseY = 270;
        int horseX = 10;
        List<Horse> horses = new ArrayList<>(round.getHorseBets().keySet());
        for(int i = 0; i<round.getHorseBets().keySet().size(); i++) {
            if(i%4 == 0 && i != 0) {
                horseY += 180;
                horseX = 20;
            }
            Image img = null;
            try {
                String imageLink = horses.get(i).getImageLinks()[0];
                img = ImageIO.read(getClass().getResource(imageLink));
            } catch (Exception e) {
                e.printStackTrace();
            }
            JLabel horse = new JLabel(new ImageIcon(img));
            // final int horseIndex = i;
            // horse.addActionListener(e -> {
            //     window.dispose();
            //     HorseInfoWindow horseInfoWindow = new HorseInfoWindow(race, horseIndex);
            //     horseInfoWindow.setVisible(true);
            // });
            if(round.getWinner() == horses.get(i)) {
                JLabel winnerLabel = new JLabel("Winner!");
                winnerLabel.setBounds(horseX+120, horseY-30, 100, 30);
                winnerLabel.setForeground(Color.yellow);
                window.add(winnerLabel);
            }
            JLabel horseNameLabel = new JLabel(horses.get(i).getName());
            double chanceOfWinning = horses.get(i).getChanceOfWinning();
            JLabel horseChanceLabel = new JLabel("Chance of winning: " + String.format("%.2f", chanceOfWinning)+"%");
            String confidence = "Confidence: " + String.format("%.1f", horses.get(i).getConfidence())+
            ((horses.get(i).hasFallen()&&horses.get(i).getConfidence()>0.2)?
             " (Decreased to "+(String.format("%.1f", (horses.get(i).getConfidence()-0.1)))+")":"");
            JLabel horseConfidenceLabel = new JLabel(confidence);
            int bet = round.getHorseBets().get(horses.get(i));
            String betString = "Bet amount: $"+bet;
            if(round.getWinner() != horses.get(i) && bet != 0) {
                betString += " (lost)";
            }
            else if(round.getWinner() == horses.get(i) && bet != 0) {
                betString += " (gained $"+round.getTotalProfit()+")";
            }
            JLabel horseBetLabel = new JLabel(betString);
            horse.setBounds(horseX, horseY, 100, 100);
            horseNameLabel.setBounds(horseX+120, horseY, 100, 30);
            horseChanceLabel.setBounds(horseX+120, horseY+30, 200, 30);
            horseConfidenceLabel.setBounds(horseX+120, horseY+60, 200, 30);
            horseBetLabel.setBounds(horseX+120, horseY+90, 200, 30);
            horseX += 320;
            window.add(horse);
            window.add(horseNameLabel);
            window.add(horseChanceLabel);
            window.add(horseConfidenceLabel);
            window.add(horseBetLabel);
        }

        DefaultButton backButton = new DefaultButton("Back", 600, windowHeight-80);
        backButton.addActionListener(e -> {
            window.dispose();
            PreviousRoundsWindow previousRoundsWindow = new PreviousRoundsWindow(race);
            previousRoundsWindow.setVisible(true);
        });
        window.add(backButton);
    }
    
}
