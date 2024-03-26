package Part2;

import javax.swing.SwingUtilities;
import Part2.MainWindow;

public class Launcher {
    public static void main(String args[]) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                MainWindow mainWindow = new MainWindow();
                mainWindow.menu();
                mainWindow.setVisible(true);
            }
        });
    }
}