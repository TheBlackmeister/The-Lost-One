package cz.cvut.fel.pjv.View;

import cz.cvut.fel.pjv.Controller.GamePanel;

import javax.swing.*;
import java.awt.*;

public class GameWindow {
    JFrame gameWindow = new JFrame(); // nove okno - hlavni okno

    GameWindow() {
        gameWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // to be able to close the window
        gameWindow.setResizable(false); // nemenna velikost
        gameWindow.setTitle("The Lost One"); // name of the window
        gameWindow.setPreferredSize(new Dimension(800,600)); // size of the window

        GamePanel gamePanel = new GamePanel();

        gameWindow.add(gamePanel);
        gameWindow.pack();
        gameWindow.setLocationRelativeTo(null); // window will be centered
        gameWindow.setVisible(true);
    }
}
