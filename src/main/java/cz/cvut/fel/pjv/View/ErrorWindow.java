package cz.cvut.fel.pjv.View;


import javax.swing.*;

/**
 * error exit values:
 * 5 == texture not found
 * 1 == map not found
 */
public class ErrorWindow {

    public void IOExceptionErrorHandler(String whatFailed, int errorID){
        JOptionPane.showMessageDialog(null, whatFailed + " hasn't been found. Cannot continue with execution." + "\nError ID: " + errorID,
                "IO Exception error", JOptionPane.ERROR_MESSAGE);
        System.exit(errorID);
    }


}
