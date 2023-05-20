package cz.cvut.fel.pjv.Model.Setuper;

import cz.cvut.fel.pjv.Controller.GamePanel;
import cz.cvut.fel.pjv.Model.Map.Room;
import cz.cvut.fel.pjv.Model.Utils.Tuple;
import cz.cvut.fel.pjv.View.ErrorWindow;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;
import java.util.Objects;

/**
 * Class to set up the maps and tiles.
 * basic inspiration from 'ForeignGuyMike',26. 1. 2013 (https://youtu.be/FUgn-PA7yzc), yet it is already very different.
 */
public class MapSetup {
    private ArrayList<Room> rooms;
    private GamePanel gp;
    private ErrorWindow err;
    private File mapFile;
    private BufferedReader bReader;
    private BufferedImage tile0, tile1;
    private int upRoomIndex,rightRoomIndex,downRoomIndex,leftRoomIndex, numberOfEnemies, numberOfTowers, numberOfFountains;
    private boolean closed;
    private int[][] map;
    private int rows;
    private int cols;
    private int numberOfMaps;
    private Tuple playerStartingCoords;
    private int playerStartingRoom, playerStartingHP;
    public MapSetup(GamePanel gp, String mapFilePath){
        mapFile = new File(mapFilePath);
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

            String coordsTMP = bReader.readLine();
            coordsTMP = coordsTMP.substring(1,coordsTMP.length()-1);
            String[] partsCoordsPlayer = coordsTMP.split(",");
            int coordPlayerX = Integer.parseInt(partsCoordsPlayer[0].trim()); // parse the first part as an coordX and trim any whitespace
            int coordPlayerY = Integer.parseInt(partsCoordsPlayer[1].trim()); // parse the second part as an coordY and trim any whitespace
            playerStartingCoords = new Tuple(coordPlayerX,coordPlayerY);
            playerStartingRoom = Integer.parseInt(bReader.readLine()); // what is the first room's index
            playerStartingHP = Integer.parseInt(bReader.readLine()); // how many hp
            if (playerStartingHP > 100) playerStartingHP = 100;
            for (int mapIndex = 0; mapIndex < numberOfMaps; mapIndex++) {
                upRoomIndex = Integer.parseInt(bReader.readLine()); // does the Room have room above?
                rightRoomIndex = Integer.parseInt(bReader.readLine()); // does the Room have room next to it?
                downRoomIndex = Integer.parseInt(bReader.readLine()); // does the Room have room under?
                leftRoomIndex = Integer.parseInt(bReader.readLine()); // does the Room have room next to it?
                closed = Boolean.parseBoolean(bReader.readLine()); // is it closed?
                numberOfEnemies = Integer.parseInt(bReader.readLine()); // how many enemies are in the room?
                numberOfTowers = Integer.parseInt(bReader.readLine()); // how many towers are in the room?
                numberOfFountains  = Integer.parseInt(bReader.readLine()); // how many fountains are in the room?

                ArrayList<Tuple> listOfEnemies = new ArrayList<Tuple>();
                ArrayList<Tuple> listOfTowers = new ArrayList<Tuple>();
                ArrayList<Tuple> listOfFountains = new ArrayList<Tuple>();

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
                for (int fountain = 0; fountain < numberOfFountains; fountain++) {
                    String fountainTMP = bReader.readLine();
                    fountainTMP = fountainTMP.substring(1,fountainTMP.length()-1);
                    String[] parts = fountainTMP.split(",");
                    int coordX = Integer.parseInt(parts[0].trim()); // parse the first part as an coordX and trim any whitespace
                    int coordY = Integer.parseInt(parts[1].trim()); // parse the second part as an coordY and trim any whitespace
                    listOfFountains.add(new Tuple(coordX,coordY));
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

                Room newRoom = new Room(new ArrayList<>(listOfEnemies),new ArrayList<>(listOfTowers), new ArrayList<>(listOfFountains),map,upRoomIndex,rightRoomIndex,downRoomIndex,leftRoomIndex,closed);
                rooms.add(newRoom);
                listOfEnemies.clear();
                listOfTowers.clear();
                listOfFountains.clear();
            }
            bReader.close();


        } catch (IOException e) {
            err.IOExceptionErrorHandler("Level map", 1);
            throw new RuntimeException(e);
        }
        catch (Exception e) {
            err.BadConfigFileErrorHandler("Selected map",4);

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

    public Tuple getPlayerStartingCoords() {
        return playerStartingCoords;
    }

    public int getPlayerStartingRoom() {
        return playerStartingRoom;
    }

    public int getPlayerStartingHP() {
        return playerStartingHP;
    }
}

