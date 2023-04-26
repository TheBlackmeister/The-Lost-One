package cz.cvut.fel.pjv.View;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Launcher is a class that implements the first window with settings of the game, etc.
 */
public class Launcher implements ActionListener {
    protected boolean logOn;
    JFrame launcher = new JFrame();
    JButton starter = new JButton("Start the Game");
    JButton logging = new JButton("Logging: OFF");

    /**
     * constructor of the launcher class
     */
    public Launcher(){
        /*
            initialisation of the launcher buttons
         */
        starter.setFocusable(false);
        starter.addActionListener(this);
        starter.setBounds(125,100,150,50);

        logging.setFocusable(false);
        logging.addActionListener(this);
        logging.setBounds(125,200,150,50);
        /*
            adding buttons to the window
         */
        launcher.add(starter);
        launcher.add(logging);
        /*
            initialisation of the launcher buttons
         */
        launcher.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        launcher.setPreferredSize(new Dimension(400,400)); // size of the window
        launcher.setLocationRelativeTo(null); // window will be centered
        launcher.setLayout(null);
        launcher.setResizable(false);
        launcher.pack();    // to size the window perfectly depending on the
        launcher.setVisible(true);  // to be visible

    }

    /**
     * this is the action listener for the buttons.
     * @param e the event to be processed
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==starter){
            GameWindow mainWindow = new GameWindow();
            launcher.setVisible(false); // 'temporarily closes' the launcher after the mainwindow is opened
        }
        if(e.getSource()==logging){
            if(!logOn){
                logOn = true;
                logging.setText("Logging: ON");
                JOptionPane.showMessageDialog(null,"Logging is turned on.");
            } else {
                logOn = false;
                logging.setText("Logging: OFF");
                JOptionPane.showMessageDialog(null,"Logging is turned off.");
            }
        }
    }
}
