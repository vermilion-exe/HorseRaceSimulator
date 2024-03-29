package Part2;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import javax.swing.*;

import Part1.Race;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.*;

public class MainWindow {

    private JFrame window;

    public MainWindow() {
        window = new JFrame("Horse Race Simulator");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setSize(800, 600);
        window.setResizable(false);
        window.setLocationRelativeTo(null);
        window.setLayout(null);
        window.getContentPane().setBackground(Color.decode("#74add6"));
    }

    public void menu() {
        //Creating the Frame
        // JFrame menu = new JFrame("Menu Frame");
        // menu.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // menu.setSize(400, 400);

        JLabel title = new JLabel("Horse Races Simulator");
        title.setFont(new Font("Serif", Font.PLAIN, 50));
        title.setBounds(175, 30, 500, 70);

        JButton continueButton = new JButton("Continue");
        continueButton.setBounds(190, 150, 200, 30);
        try {
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

        window.add(title);
        window.add(continueButton);
        window.add(newGameButton);

        //Adding Components to the frame.
        window.setVisible(true);
     }

     public void setVisible(boolean visible){
        window.setVisible(visible);
     }

     public void game(boolean isNewGame){
        if(isNewGame){
            GameSaveController.deleteGame();
        }
        Race race = GameSaveController.loadGame();
        if(race.getHorses().size()==10){
            RaceControlWindow raceControlWindow = new RaceControlWindow(race);
            raceControlWindow.setVisible(true);
        }
        else{
            HorseAdditionWindow horseAdditionWindow = new HorseAdditionWindow(race);
            horseAdditionWindow.setVisible(true);
        }
     }
}
