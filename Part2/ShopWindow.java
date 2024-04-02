package Part2;

import java.awt.Color;
import java.awt.Image;

import javax.imageio.ImageIO;
import javax.swing.*;

public class ShopWindow extends Window {

    public ShopWindow(Race race){
        super("Shop");
        window.setSize(680, 700);

        JLabel moneyLabel = new JLabel();
        moneyLabel.setBounds(20, 20, 100, 30);
        moneyLabel.setText("Money: $"+race.getPlayer().getMoney());
        moneyLabel.setBackground(Color.lightGray);
        moneyLabel.setOpaque(true);

        JLabel horseDisplay = new JLabel();
        horseDisplay.setBounds(290, 100, 200, 200);

        Image barnImg = null;
        try {
            String imageLink = "resources/Barn.png";
            barnImg = ImageIO.read(getClass().getResource(imageLink));
        } catch (Exception e) {
            e.printStackTrace();
        }
        JLabel barn = new JLabel(new ImageIcon(barnImg));
        barn.setBounds(0, 0, 680, 270);

        JButton backButton = new JButton("Back");
        backButton.setBounds(290, 620, 100, 30);
        backButton.addActionListener( event -> {
            window.dispose();
            RaceControlWindow raceWindow = new RaceControlWindow(race);
            raceWindow.setVisible(true);
        });

        Breed[] breeds = Breed.values();

        int horseY = 290;
        int horseX = 20;
        for(int i = 0; i<8; i++) {
            if(i%4 == 0 && i != 0) {
                horseY += 150;
                horseX = 20;
            }
            Image img = null;
            try {
                String imageLink = "resources/horse-sprites/" + breeds[i].toString() + "_0.png";
                img = ImageIO.read(getClass().getResource(imageLink));
            } catch (Exception e) {
                e.printStackTrace();
            }
            JButton horse = new JButton(new ImageIcon(img));
            JLabel breedLabel = new JLabel(breeds[i].toString()+(race.getPlayer().getUnlockedBreeds().contains(breeds[i])?"":" - $"+Horse.getBreedPrice(breeds[i])));
            final Image imgDisplay = img;
            final int breedIndex = i;
            horse.addActionListener(e -> {
                if(race.getPlayer().getUnlockedBreeds().contains(breeds[breedIndex])){
                    horseDisplay.setIcon(new ImageIcon(imgDisplay.getScaledInstance(100, 100, Image.SCALE_DEFAULT)));
                }
                else{
                    int confirm = JOptionPane.showConfirmDialog(window, "Are you sure you want to buy this breed?", "Confirmation", JOptionPane.YES_NO_OPTION);
                    if (confirm == JOptionPane.YES_OPTION) {
                       boolean bought = race.getPlayer().buy(breeds[breedIndex]);
                       if(bought){
                           JOptionPane.showMessageDialog(window, "Breed bought successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                           horseDisplay.setIcon(new ImageIcon(imgDisplay.getScaledInstance(100, 100, Image.SCALE_DEFAULT)));
                           breedLabel.setText(breeds[breedIndex].toString());
                           moneyLabel.setText("Money: $"+race.getPlayer().getMoney());
                       }
                       else{
                           JOptionPane.showMessageDialog(window, "You don't have enough money to buy this breed!", "Warning", JOptionPane.WARNING_MESSAGE);
                       }
                    }
                }
            });
            horse.setBounds(horseX, horseY, 100, 100);
            breedLabel.setBounds(horseX, horseY+100, 150, 30);
            horseX += 170;
            window.add(horse);
            window.add(breedLabel);
        }

        window.add(horseDisplay);
        window.add(moneyLabel);
        window.add(barn);
        window.add(backButton);
    }

}
