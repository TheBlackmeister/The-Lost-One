package cz.cvut.fel.pjv.Model.Setuper;

import cz.cvut.fel.pjv.View.ErrorWindow;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class MapSetup {
    ErrorWindow err;
    File mapFile;
    Scanner scanner;
    String[] map;
    public MapSetup(){
        mapFile = new File("/map/sample_map.txt");
        err = new ErrorWindow();
        mapInit();
    }
    public void mapInit(){
        try {
            scanner = new Scanner(mapFile);
        } catch (FileNotFoundException e) {
            err.IOExceptionErrorHandler("Level map", 1);
            throw new RuntimeException(e);
        }
        StringBuilder content = new StringBuilder();
        while (scanner.hasNextLine()) {
            content.append(scanner.nextLine());
        }
        String[] array = content.toString().split(",");
        array = map;
        scanner.close();
    }

    public String[] getMap() {
        return map;
    }
}

