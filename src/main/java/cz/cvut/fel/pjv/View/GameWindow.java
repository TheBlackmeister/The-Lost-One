package cz.cvut.fel.pjv.View;

import cz.cvut.fel.pjv.Controller.GamePanel;
import cz.cvut.fel.pjv.Model.Setuper.ConfigFileSetup;


import javax.swing.*;
import java.awt.*;

public class GameWindow {
    Launcher launcher;
    JFrame gameWindow = new JFrame(); // nove okno - hlavni okno

    GameWindow(Launcher launcher) {
        this.launcher = launcher;
        gameWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // to be able to close the window
        gameWindow.setResizable(false); // nemenna velikost
        gameWindow.setTitle("The Lost One"); // name of the window
        ConfigFileSetup config = new ConfigFileSetup();
        config.getTheConfig();

        gameWindow.setPreferredSize(new Dimension(config.getScreenWidth(),config.getScreenHeight())); // size of the window

        GamePanel gamePanel = new GamePanel(gameWindow,this.launcher);
        gameWindow.setUndecorated(true); // deletes the outer windows todo rework
        gameWindow.add(gamePanel);
        gameWindow.pack();
        gameWindow.setLocationRelativeTo(null); // window will be centered
        gameWindow.setVisible(true);
    }
}
