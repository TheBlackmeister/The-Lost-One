package cz.cvut.fel.pjv.Model.Setuper;

import cz.cvut.fel.pjv.Model.Map.Room;
import cz.cvut.fel.pjv.Model.Utils.Tuple;
import cz.cvut.fel.pjv.View.ErrorWindow;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;
import java.util.Objects;
import java.util.logging.Logger;

/**
 * Class to set up the maps and tiles.
 * basic inspiration from 'ForeignGuyMike',26. 1. 2013 (https://youtu.be/FUgn-PA7yzc), yet it is already very different.
 */
public class MapSetup {
    private static final Logger logger = Logger.getLogger(MapSetup.class.getName());
    private final ArrayList<Room> rooms;
    private final ErrorWindow err;
    private final File mapFile;
    private BufferedImage tile0, tile1;
    private Tuple playerStartingCoords;
    private int playerStartingRoom, playerStartingHP;
    private int firstInvIndex, secondInvIndex, thirdInvIndex;

    /**
     * constructor
     * @param mapFilePath selects the level file to be opened
     */
    public MapSetup(String mapFilePath){
        mapFile = new File(mapFilePath);
        err = new ErrorWindow();
        rooms = new ArrayList<>();
        mapInit();
        tileInit();
    }

    /**
     * loading the map
     */
    public void mapInit(){
        /*
        loading the map and init the scanner
         */
        BufferedReader bReader;
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
            int rows = Integer.parseInt(bReader.readLine()); // *16 == image height
            int cols = Integer.parseInt(bReader.readLine()); // *16 == image width
            int numberOfMaps = Integer.parseInt(bReader.readLine());

            String coordsTMP = bReader.readLine();
            coordsTMP = coordsTMP.substring(1,coordsTMP.length()-1);
            String[] partsCoordsPlayer = coordsTMP.split(",");
            int coordPlayerX = Integer.parseInt(partsCoordsPlayer[0].trim()); // parse the first part as an coordX and trim any whitespace
            int coordPlayerY = Integer.parseInt(partsCoordsPlayer[1].trim()); // parse the second part as an coordY and trim any whitespace
            playerStartingCoords = new Tuple(coordPlayerX,coordPlayerY);
            playerStartingRoom = Integer.parseInt(bReader.readLine()); // what is the first room's index
            playerStartingHP = Integer.parseInt(bReader.readLine()); // how many hp
            if (playerStartingHP > 100) playerStartingHP = 100; // player cannot start with more than 100 hp
            firstInvIndex = Integer.parseInt(bReader.readLine()); // starting inventory
            secondInvIndex = Integer.parseInt(bReader.readLine()); // starting inventory
            thirdInvIndex = Integer.parseInt(bReader.readLine()); // starting inventory

            for (int mapIndex = 0; mapIndex < numberOfMaps; mapIndex++) {
                int upRoomIndex = Integer.parseInt(bReader.readLine()); // does the Room have room above?
                int rightRoomIndex = Integer.parseInt(bReader.readLine()); // does the Room have room next to it?
                int downRoomIndex = Integer.parseInt(bReader.readLine()); // does the Room have room under?
                int leftRoomIndex = Integer.parseInt(bReader.readLine()); // does the Room have room next to it?
                boolean closed = Boolean.parseBoolean(bReader.readLine()); // is it closed?
                int numberOfEnemies = Integer.parseInt(bReader.readLine()); // how many enemies are in the room?
                int numberOfTowers = Integer.parseInt(bReader.readLine()); // how many towers are in the room?
                int numberOfFountains = Integer.parseInt(bReader.readLine()); // how many fountains are in the room?

                ArrayList<Tuple> listOfEnemies = new ArrayList<>();
                ArrayList<Tuple> listOfTowers = new ArrayList<>();
                ArrayList<Tuple> listOfFountains = new ArrayList<>();

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


                int[][] map = new int[rows][cols];
                String lineTmp;
                String[] nums;
                try {
                    for (int row = 0; row < rows; row++) {
                        lineTmp = bReader.readLine();
                        nums = lineTmp.split(" ");
                        for (int col = 0; col < cols; col++) {
                            map[row][col] = Integer.parseInt(nums[col]);
                            if (map[row][col] > 1) {
                                logger.severe("Incorrectly written or corrupted map!");
                                err.IOExceptionErrorHandler("Map", 8);
                                throw new RuntimeException(new Exception("Incorrectly written or corrupted map!"));
                            }

                        }
                    }
                }catch (Exception e){
                    logger.severe("Incorrectly written or corrupted map!");
                    err.IOExceptionErrorHandler("Map", 8);
                    throw new RuntimeException(new Exception("Incorrectly written or corrupted map!"));
                }

                Room newRoom = new Room(new ArrayList<>(listOfEnemies),new ArrayList<>(listOfTowers), new ArrayList<>(listOfFountains), map, upRoomIndex, rightRoomIndex, downRoomIndex, leftRoomIndex, closed);
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
            err.BadConfigFileErrorHandler("Selected map",8);

        }
    }

    /**
     * This loads the tiles.
     */
    public void tileInit(){
        try {
            tile0 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/tiles/0_passable.png")));
            tile1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/tiles/1_notpassable.png")));
        } catch (IOException | NullPointerException e) {
            logger.severe("Tile Images not found!");
            err.IOExceptionErrorHandler("Tile images", 5);
            throw new RuntimeException(e);
        }
    }
    public int getFirstInvIndex() {
        return firstInvIndex;
    }

    public int getSecondInvIndex() {
        return secondInvIndex;
    }

    public int getThirdInvIndex() {
        return thirdInvIndex;
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

