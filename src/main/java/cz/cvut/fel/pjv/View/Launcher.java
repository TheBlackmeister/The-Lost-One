package cz.cvut.fel.pjv.View;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 * Launcher is a class that implements the first window with settings of the game, etc.
 */
public class Launcher implements ActionListener {
    private static final Logger logger = Logger.getLogger(Launcher.class.getName());
    ErrorWindow err;
    JFrame launcher = new JFrame();
    JButton starter = new JButton("Start a new demo");
    JButton openMap = new JButton("Open a custom or saved level");
    JFileChooser chooseMapToOpen;

    /**
     * constructor of the launcher class
     */
    public Launcher(){

        logger.setLevel(Level.INFO);
        logger.setUseParentHandlers(true);

        err = new ErrorWindow();
        /*
            initialisation of the launcher buttons
         */
        logger.info("Initialisation of the launcher buttons");
        starter.setFocusable(false);
        starter.addActionListener(this);
        starter.setBounds(50,50,300,50);

        openMap.setFocusable(false);
        openMap.addActionListener(this);
        openMap.setBounds(50,150,300,50);
        /*
            adding buttons to the window
         */
        logger.info("Adding the launcher buttons to the window");
        launcher.add(starter);
        launcher.add(openMap);
        /*
            initialisation of the launcher buttons
         */
        launcher.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        launcher.setPreferredSize(new Dimension(415,280)); // size of the window
        launcher.setLocationRelativeTo(null); // window will be centered
        launcher.setLayout(null);
        launcher.setResizable(false);
        launcher.pack();    // to size the window perfectly depending on the elements
        launcher.setVisible(true);  // to be visible
        logger.info("Launcher launched");
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
        if(e.getSource()==openMap) {
            logger.info("Choosing map.");
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
                        logger.warning("File doesn't end with .txt, trying again.");
                        err.badOpenedFile("Selected file", 4);
                    } else {
                        logger.info("File opened: " + file);
                        new GameWindow(this, file); // I can just call it, don't have to assign a new variable
                    }
                }
                if (response == JFileChooser.CANCEL_OPTION) {
                    logger.info("User cancelled loading from a file.");
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
