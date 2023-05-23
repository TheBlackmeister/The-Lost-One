package cz.cvut.fel.pjv.Controller;

import cz.cvut.fel.pjv.View.Launcher;

import java.util.logging.LogManager;
import java.util.logging.Logger;

/**
 * this is the main class.
 */
public class Main {
    private static final Logger logger = Logger.getLogger(Main.class.getName());
    public static void main(String[] args) {
        if(args.length != 0 && args[0].equals("OFF")){
            LogManager.getLogManager().reset();
        }
        logger.info("Starting launcher");
        new Launcher();
    }
}