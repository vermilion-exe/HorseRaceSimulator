package Part2;

import java.awt.Color;

import javax.swing.JButton;

public class DefaultButton extends JButton {

    public DefaultButton(String text){
        super(text);
        setBackground(Color.decode("#086788"));
        setForeground(Color.WHITE);
    }

    public DefaultButton(String text, int x, int y) {
        super(text);
        setBounds(x, y, 200, 30);
        setBackground(Color.decode("#086788"));
        setForeground(Color.WHITE);
    }

    public DefaultButton(String text, int x, int y, int width, int height) {
        super(text);
        setBounds(x, y, width, height);
        setBackground(Color.decode("#086788"));
        setForeground(Color.WHITE);
    }
    
}
