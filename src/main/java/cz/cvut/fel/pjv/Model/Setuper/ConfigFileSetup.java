package cz.cvut.fel.pjv.Model.Setuper;

import cz.cvut.fel.pjv.Model.Entity.Entity;
import cz.cvut.fel.pjv.View.ErrorWindow;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Objects;
import java.util.Scanner;

/**
 * class used to read the configuration file and write the into variables.
 */
public class ConfigFileSetup {
    ErrorWindow err;
    Scanner scanner;


    String mapName; //first line
    int screenWidth; //second line
    int screenHeight; // third line

    public ConfigFileSetup(){
        err = new ErrorWindow();
    }

    public void getTheConfig(){
        try {
            scanner = new Scanner(new FileInputStream("./src/main/resources/config/config.txt"), StandardCharsets.UTF_8);
            try{
                mapName = scanner.nextLine();
                screenWidth = scanner.nextInt();
                screenHeight = scanner.nextInt();
                scanner.close();
            } catch (Exception e) {
                err.BadConfigFileErrorHandler("Config file", 3);
                throw new RuntimeException(e);
            }

        } catch (IOException | NullPointerException e) {
            err.IOExceptionErrorHandler("Config file", 2);
            throw new RuntimeException(e);
        }
    }

    public String getMapName() {
        return mapName;
    }

    public int getScreenWidth() {
        return screenWidth;
    }

    public int getScreenHeight() {
        return screenHeight;
    }
}
