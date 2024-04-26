package Part2;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import javax.swing.*;

import java.awt.Font;
import java.awt.event.*;

public class MainWindow extends Window {

    public MainWindow(){
        super("Horse Race Simulator");

        JLabel title = new JLabel("Horse Races Simulator");
        title.setFont(new Font("Serif", Font.PLAIN, 50));
        title.setBounds(175, 30, 500, 70);

        DefaultButton continueButton = new DefaultButton("Continue", 190, 150);
        if(!GameSaveController.hasData()) {
            continueButton.setEnabled(false);
        }

        continueButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                game(false);
            }
        });

        DefaultButton newGameButton = new DefaultButton("New Game", 410, 150);
         newGameButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if(GameSaveController.hasData()){
                    int dialogResult = JOptionPane.showConfirmDialog(window, "Are you sure you want to start a new game? The current game will be lost.", "Warning", JOptionPane.YES_NO_OPTION);
                    if(dialogResult == JOptionPane.YES_OPTION){
                        game(true);
                    }
                }
                else{
                    game(true);
                }
            }
        });

        DefaultButton exitButton = new DefaultButton("Exit", 300, 200);
        exitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        window.add(title);
        window.add(continueButton);
        window.add(newGameButton);
        window.add(exitButton);
    }


    public void game(boolean isNewGame){
        if(isNewGame){
            GameSaveController.deleteGame();
        }
        Race race = GameSaveController.loadGame();
        if(race.getHorses().size()==10){
            window.dispose();
            RaceControlWindow raceControlWindow = new RaceControlWindow(race);
            raceControlWindow.setVisible(true);
        }
        else{
            window.dispose();
            HorseAdditionWindow horseAdditionWindow = new HorseAdditionWindow(race);
            horseAdditionWindow.setVisible(true);
        }
    }
}
