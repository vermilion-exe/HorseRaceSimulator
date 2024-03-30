package Part2;

import java.awt.Color;

import javax.swing.JFrame;

public class Window {

    protected JFrame window;

    public Window(String title) {
        window = new JFrame();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setSize(800, 600);
        window.setResizable(false);
        window.setLocationRelativeTo(null);
        window.setLayout(null);
        window.getContentPane().setBackground(Color.decode("#74add6"));
    }

    public void setVisible(boolean visible) {
        window.setVisible(visible);
    }
    
}
