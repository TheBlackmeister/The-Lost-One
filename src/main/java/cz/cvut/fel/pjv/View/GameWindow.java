package cz.cvut.fel.pjv.View;

import cz.cvut.fel.pjv.Controller.GamePanel;
import cz.cvut.fel.pjv.Model.Setuper.ConfigFileSetup;


import javax.swing.*;
import java.awt.*;
import java.io.File;

public class GameWindow {
    Launcher launcher;
    JFrame gameWindow = new JFrame(); // nove okno - hlavni okno
    String mapFilePath;
    GameWindow(Launcher launcher, String filePath) {
        this.launcher = launcher;
        gameWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // to be able to close the window
        gameWindow.setResizable(false); // nemenna velikost
        gameWindow.setTitle("The Lost One"); // name of the window
        ConfigFileSetup config = new ConfigFileSetup();
        config.getTheConfig();
        if(filePath != null){
            this.mapFilePath = filePath;
        } else {
            this.mapFilePath = config.getMapName();
        }
        gameWindow.setPreferredSize(new Dimension(config.getScreenWidth(),config.getScreenHeight())); // size of the window

        GamePanel gamePanel = new GamePanel(gameWindow,this.launcher,this.mapFilePath);
        gameWindow.setUndecorated(true); // deletes the outer windows todo rework
        gameWindow.add(gamePanel);
        gameWindow.pack();
        gameWindow.setLocationRelativeTo(null); // window will be centered
        gameWindow.setVisible(true);
    }
}
