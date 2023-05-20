package cz.cvut.fel.pjv.View;


import javax.swing.*;

/**
 * error exit values:
 * 1 == map not found
 * 2 == config file not found
 * 3 == config file corrupted or has incorrectly written lines
 * 4 == opened unsupported file
 * 5 == texture not found
 * 6 == saving the game problems
 * 7 == sound not found or corrupted
 */
public class ErrorWindow {

    public void IOExceptionErrorHandler(String whatFailed, int errorID){
        JOptionPane.showMessageDialog(null, whatFailed + " hasn't been found. Cannot continue with execution." + "\nError ID: " + errorID,
                "IO Exception error", JOptionPane.ERROR_MESSAGE);
        System.exit(errorID);
    }

    public void BadConfigFileErrorHandler(String whatFailed, int errorID){
        JOptionPane.showMessageDialog(null, whatFailed + " is probably corrupted or has incorrectly written lines. Cannot continue with execution." + "\nError ID: " + errorID,
                "IO Exception error", JOptionPane.ERROR_MESSAGE);
        System.exit(errorID);
    }

    public void badOpenedFile(String whatFailed, int errorID){
        JOptionPane.showMessageDialog(null, whatFailed + " is either not a .txt file or is corrupted.\nTry choosing different file.\nError ID: " + errorID,
                "Error while opening the file", JOptionPane.ERROR_MESSAGE);
    }

    public void badOpenedSoundFile(String whatFailed, int errorID){
        JOptionPane.showMessageDialog(null, whatFailed + " is either not a .wav file or is corrupted.\nTry reinstalling the game.\nError ID: " + errorID,
                "Error while opening the sound file", JOptionPane.ERROR_MESSAGE);
    }


}
