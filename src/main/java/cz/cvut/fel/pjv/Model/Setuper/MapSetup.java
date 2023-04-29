package cz.cvut.fel.pjv.Model.Setuper;

import cz.cvut.fel.pjv.Controller.GamePanel;
import cz.cvut.fel.pjv.View.ErrorWindow;

import java.io.*;
import java.util.Scanner;

/**
 * Class to setup the maps.
 * basic inspiration from 'ForeignGuyMike',26. 1. 2013 (https://youtu.be/FUgn-PA7yzc)
 */
public class MapSetup {
    GamePanel gp;
    ErrorWindow err;
    File mapFile;
    Scanner scanner;
    BufferedReader bReader;
    int[][] map;
    int rows;
    int cols;
    public MapSetup(GamePanel gp){
        mapFile = new File(gp.getConfig().mapName);
        err = new ErrorWindow();
        mapInit();

    }
    public void mapInit(){
        /*
        loading the map and init the scanner
         */
        try {
            bReader = new BufferedReader(new FileReader(mapFile));
        } catch (FileNotFoundException e) {
            err.IOExceptionErrorHandler("Level map", 1);
            throw new RuntimeException(e);
        }
        /*
        catching the config info from map file
         */
        try {
            rows = Integer.parseInt(bReader.readLine());
            cols = Integer.parseInt(bReader.readLine());
            map = new int[rows][cols];
            String lineTmp;
            String[] nums;
            for (int row = 0; row < rows; row++) {
                lineTmp = bReader.readLine();
                nums = lineTmp.split(" ");
                for (int col = 0; col < cols; col++) {
                    map[row][col] = Integer.parseInt(nums[col]);

                }
            }
            bReader.close();

        } catch (IOException e) {
            err.IOExceptionErrorHandler("Level map", 1);
            throw new RuntimeException(e);
        }
    }

    public int[][] getMap() {
        return map;
    }
}

