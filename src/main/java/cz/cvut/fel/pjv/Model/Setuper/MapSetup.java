package cz.cvut.fel.pjv.Model.Setuper;

import cz.cvut.fel.pjv.Controller.GamePanel;
import cz.cvut.fel.pjv.Model.Entity.EnemySoldier;
import cz.cvut.fel.pjv.Model.Entity.Entity;
import cz.cvut.fel.pjv.Model.Entity.Tower;
import cz.cvut.fel.pjv.Model.Map.Room;
import cz.cvut.fel.pjv.View.ErrorWindow;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner;

/**
 * Class to set up the maps and tiles.
 * basic inspiration from 'ForeignGuyMike',26. 1. 2013 (https://youtu.be/FUgn-PA7yzc)
 */
public class MapSetup {
    ArrayList<Room> rooms;
    GamePanel gp;
    ErrorWindow err;
    File mapFile;
    BufferedReader bReader;
    BufferedImage tile0, tile1;
    private int upRoomIndex,rightRoomIndex,downRoomIndex,leftRoomIndex;
    private boolean closed;
    int[][] map;
    int rows;
    int cols;
    int numberOfMaps;
    public MapSetup(GamePanel gp){
        mapFile = new File(gp.getConfig().mapName);
        err = new ErrorWindow();
        rooms = new ArrayList<Room>();
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
            rows = Integer.parseInt(bReader.readLine()); // *16 == image height
            cols = Integer.parseInt(bReader.readLine()); // *16 == image width
            numberOfMaps = Integer.parseInt(bReader.readLine());
            for (int mapIndex = 0; mapIndex < numberOfMaps; mapIndex++) {
                upRoomIndex = Integer.parseInt(bReader.readLine()); // does the Room have room above?
                rightRoomIndex = Integer.parseInt(bReader.readLine()); // does the Room have room next to it?
                downRoomIndex = Integer.parseInt(bReader.readLine()); // does the Room have room under?
                leftRoomIndex = Integer.parseInt(bReader.readLine()); // does the Room have room next to it?
                closed = Boolean.parseBoolean(bReader.readLine()); // is it closed?
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
                Room newRoom = new Room(new ArrayList<EnemySoldier>(),new ArrayList<Tower>(),map,upRoomIndex,rightRoomIndex,downRoomIndex,leftRoomIndex,closed); //todo
                rooms.add(newRoom);
            }
            bReader.close();


        } catch (IOException e) {
            err.IOExceptionErrorHandler("Level map", 1);
            throw new RuntimeException(e);
        }
    }
    public void tileInit(){
        try {
            tile0 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/tiles/0_passable.png")));
            tile1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/tiles/1_notpassable.png")));
        } catch (IOException | NullPointerException e) {
            err.IOExceptionErrorHandler("Player Image", 5);
            throw new RuntimeException(e);
        }
    }

    public ArrayList<Room> getRooms() {
        return rooms;
    }

    public BufferedImage getTile0() {
        return tile0;
    }

    public BufferedImage getTile1() {
        return tile1;
    }

    public int[][] getMap() {
        return map;
    }
}

