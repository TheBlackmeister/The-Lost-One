package cz.cvut.fel.pjv.Controller;

import javax.swing.*;
import java.awt.*;

/**
 * starting class (has panel)
 */
public class Main {
    public static void main(String[] args) {
        JFrame mainWindow = new JFrame(); // nove okno - hlavni okno
        mainWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // to be able to close the window
        mainWindow.setResizable(false); // nemenna velikost
        mainWindow.setTitle("Hugo Adventures"); // names of the window
        System.out.println("Helou"); // testing
        mainWindow.setPreferredSize(new Dimension(1536,864)); // size of the window
        GamePanel gamePanel = new GamePanel();
        mainWindow.add(gamePanel);
        mainWindow.pack();

        mainWindow.setIconImage(Toolkit.getDefaultToolkit().getImage(Main.class.getResource("/objects/chest.png")));
        // this adds icon to the window programme

        mainWindow.setLocationRelativeTo(null); // window will be centered
        mainWindow.setVisible(true);

        gamePanel.setupGame();
        gamePanel.startGameThread();
    }
}