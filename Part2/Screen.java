package Part2;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import javax.swing.*;

import java.awt.CardLayout;
import java.awt.event.*;

public class Screen {
    public static void main(String args[]) throws IOException{
        //Creating the Frame
        JFrame menu = new JFrame("Menu Frame");
        menu.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        menu.setSize(400, 400);

        //Creating the MenuBar and adding components
        JMenuBar mb = new JMenuBar();
        JMenu m1 = new JMenu("FILE");
        JMenu m2 = new JMenu("Help");
        mb.add(m1);
        mb.add(m2);
        JMenuItem m11 = new JMenuItem("Open");
        JMenuItem m22 = new JMenuItem("Save as");
        m1.add(m11);
        m1.add(m22);

        //Creating the panel at bottom and adding components
        JPanel panel = new JPanel(); // the panel is not visible in output
        //panel.add(menu, "Menu");
        JButton continueButton = new JButton("Continue");
        BufferedReader reader = new BufferedReader(new FileReader("Part2/save.txt"));
        if(reader.readLine() == null){
            continueButton.setEnabled(false);
        }
        reader.close();
        JButton newGameButton = new JButton("New Game");
         newGameButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                game();
            }
        });
        panel.add(continueButton);
        panel.add(newGameButton);

        // Text Area at the Center
        JTextArea ta = new JTextArea();

        //Adding Components to the frame.
        menu.getContentPane().add("Center", panel);
        menu.getContentPane().add("North", mb);
        menu.setVisible(true);
     }

     public static void game(){
        
     }
}
