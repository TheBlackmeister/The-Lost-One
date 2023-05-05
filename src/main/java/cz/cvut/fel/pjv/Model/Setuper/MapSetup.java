package cz.cvut.fel.pjv.Model.Setuper;

import cz.cvut.fel.pjv.Controller.GamePanel;
import cz.cvut.fel.pjv.Model.Entity.EnemySoldier;
import cz.cvut.fel.pjv.Model.Entity.Entity;
import cz.cvut.fel.pjv.Model.Entity.Tower;
import cz.cvut.fel.pjv.Model.Map.Room;
import cz.cvut.fel.pjv.Model.Utils.Tuple;
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
    private int upRoomIndex,rightRoomIndex,downRoomIndex,leftRoomIndex, numberOfEnemies, numberOfTowers;
    private boolean closed;
    private int widthOfBoundaries;
    int[][] map;
    int rows;
    int cols;
    int numberOfMaps;
    public MapSetup(GamePanel gp){
        mapFile = new File(gp.getConfig().mapName);
        err = new ErrorWindow();
        rooms = new ArrayList<Room>();
        this.gp = gp;
        mapInit();
        tileInit();
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
                numberOfEnemies = Integer.parseInt(bReader.readLine()); // how many enemies are in the room?
                numberOfTowers = Integer.parseInt(bReader.readLine()); // how many towers are in the room?

                ArrayList<Tuple> listOfEnemies = new ArrayList<Tuple>();
                ArrayList<Tuple> listOfTowers = new ArrayList<Tuple>();

                for (int enemy = 0; enemy < numberOfEnemies; enemy++) {
                    String enemyTmp = bReader.readLine();
                    enemyTmp = enemyTmp.substring(1,enemyTmp.length()-1);
                    String[] parts = enemyTmp.split(",");
                    int coordX = Integer.parseInt(parts[0].trim()); // parse the first part as an coordX and trim any whitespace
                    int coordY = Integer.parseInt(parts[1].trim()); // parse the second part as an coordY and trim any whitespace
                    listOfEnemies.add(new Tuple(coordX,coordY));
                }
                for (int tower = 0; tower < numberOfTowers; tower++) {
                    String towerTmp = bReader.readLine();
                    towerTmp = towerTmp.substring(1,towerTmp.length()-1);
                    String[] parts = towerTmp.split(",");
                    int coordX = Integer.parseInt(parts[0].trim()); // parse the first part as an coordX and trim any whitespace
                    int coordY = Integer.parseInt(parts[1].trim()); // parse the second part as an coordY and trim any whitespace
                    listOfTowers.add(new Tuple(coordX,coordY));
                }
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

                Room newRoom = new Room(new ArrayList<>(listOfEnemies),new ArrayList<>(listOfTowers),map,upRoomIndex,rightRoomIndex,downRoomIndex,leftRoomIndex,closed);
                rooms.add(newRoom);
                listOfEnemies.clear();
                listOfTowers.clear();
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

