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

        JButton continueButton = new JButton("Continue");
        continueButton.setBounds(190, 150, 200, 30);
        try {
            GameSaveController.validateSaveFile();
            BufferedReader reader = new BufferedReader(new FileReader("Part2/save.txt"));
            if(reader.readLine() == null){
                continueButton.setEnabled(false);
            }
            reader.close();
        }
        catch(IOException e){
            e.printStackTrace();
        }

        continueButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                game(false);
            }
        });

        JButton newGameButton = new JButton("New Game");
        newGameButton.setBounds(410, 150, 200, 30);
         newGameButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                game(true);
            }
        });

        JButton exitButton = new JButton("Exit");
        exitButton.setBounds(300, 200, 200, 30);
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
