package cz.cvut.fel.pjv.View;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;


/**
 * Launcher is a class that implements the first window with settings of the game, etc.
 */
public class Launcher implements ActionListener {
    protected boolean logOn;
    ErrorWindow err;
    JFrame launcher = new JFrame();
    JButton starter = new JButton("New Game");
    JButton logging = new JButton("Logging: OFF");
    JButton openMap = new JButton("Open a custom map");
    JButton savedGames = new JButton("Saved Games");
    JFileChooser chooseMapToOpen;
    /**
     * constructor of the launcher class
     */
    public Launcher(){
        err = new ErrorWindow();
        /*
            initialisation of the launcher buttons
         */
        starter.setFocusable(false);
        starter.addActionListener(this);
        starter.setBounds(125,100,150,50);

        logging.setFocusable(false);
        logging.addActionListener(this);
        logging.setBounds(125,200,150,50);

        openMap.setFocusable(false);
        openMap.addActionListener(this);
        openMap.setBounds(125,300,150,50);

        savedGames.setFocusable(false);
        savedGames.addActionListener(this);
        savedGames.setBounds(125,400,150,50);
        /*
            adding buttons to the window
         */
        launcher.add(starter);
        launcher.add(logging);
        launcher.add(openMap);
        launcher.add(savedGames);
        /*
            initialisation of the launcher buttons
         */
        launcher.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        launcher.setPreferredSize(new Dimension(400,600)); // size of the window
        launcher.setLocationRelativeTo(null); // window will be centered
        launcher.setLayout(null);
        launcher.setResizable(false);
        launcher.pack();    // to size the window perfectly depending on the elements
        launcher.setVisible(true);  // to be visible

    }

    /**
     * this is the action listener for the buttons.
     * @param e the event to be processed
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==starter){
            new GameWindow(this, null);
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
        if(e.getSource()==openMap) {

            chooseMapToOpen = new JFileChooser();
            chooseMapToOpen.setCurrentDirectory(new File(System.getProperty("user.dir") + "/src/main/resources/savegame"));
            FileNameExtensionFilter filter = new FileNameExtensionFilter("Text files", "txt", "text"); //adds txt filter
            chooseMapToOpen.setFileFilter(filter);
            String file = "";
            do {
                int response = chooseMapToOpen.showOpenDialog(null);
                if (response == JFileChooser.APPROVE_OPTION) {
                    file = chooseMapToOpen.getSelectedFile().getAbsolutePath();

                    if(!file.endsWith(".txt")) {
                        err.badOpenedFile("Selected file", 4);
                    } else {
                        System.out.println(file); // todo testing
                        new GameWindow(this, file); // i can just call it, dont have to assign a new variable
                    }
                }
                if (response == JFileChooser.CANCEL_OPTION) {
                    break;
                }
            }
            while (!file.endsWith(".txt"));
        }
    }

    public JFrame getLauncher() {
        return launcher;
    }
}
