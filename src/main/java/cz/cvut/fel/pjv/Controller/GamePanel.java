package cz.cvut.fel.pjv.Controller;

import cz.cvut.fel.pjv.Model.Entity.*;
import cz.cvut.fel.pjv.Model.Map.Room;
import cz.cvut.fel.pjv.Model.Setuper.*;
import cz.cvut.fel.pjv.Model.Utils.Tuple;
import cz.cvut.fel.pjv.View.*;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Logger;

/**
 * GamePanel is one of the main classes of the project. It updates the game status.
 */
public class GamePanel extends JPanel implements Runnable{
    private static final Logger logger = Logger.getLogger(GamePanel.class.getName());
    GameConsole gameConsole;
    ErrorWindow err;
    boolean isPaused = false;
    boolean menuOpen = false;
    Launcher launcher;
    JFrame gw;
    Thread gameThread; // main game thread
    Player player;
    ConfigFileSetup config;
    MapSetup mapsetup;
    MapView mapView;
    GUIView guiView;
    RoomMover roomMover;
    CollisionTileChecker collCheck;
    HealthBarPlayerUI hbUI;
    public ProjectileSetup prSetup; // loading textures for bullets
    public ArrayList<Projectile> toRemove;
    public ArrayList<Projectile> projectile; // public used for manipulation from projectile class
    public ArrayList<ObjGun> objGuns; // used for gun drops from towers and enemies
    public ArrayList<ObjGun> objGunsToRemove;

    /**
     * tower arraylists to keep all the entities
     */
    public TowerSetup twSetup; // loading textures for bullets
    public EnemySetup enSetup; // loading textures for enemies
    public ArrayList<Tower> towers;
    public ArrayList<Tower> towersToRemove;
    public ArrayList<EnemyProjectile> enemyProjectileToRemove;
    public ArrayList<EnemyProjectile> enemyProjectile; // public used for manipulation from projectile class
    public ArrayList<Rocket> rockets;
    public ArrayList<Rocket> rocketsToRemove;
    public ArrayList<EnemySoldier> enemySoldiers;
    public ArrayList<EnemySoldier> enemySoldiersToRemove;
    public ArrayList<Fountain> fountains;
    public ArrayList<Fountain> fountainsToRemove;
    KeyListener keyList;
    int FPS = 90;
    GameMenu gameMenu;

    public GamePanel(JFrame gw, Launcher launcher, String mapFilePath) {
        /*
        * config is used to initialize and hold all the configuration variables.
        */
        err = new ErrorWindow();
        gameConsole = new GameConsole();
        this.gw = gw;
        this.launcher = launcher;
        config = new ConfigFileSetup();
        config.getTheConfig();


        this.setPreferredSize(new Dimension(config.getScreenWidth(),config.getScreenHeight()));
        this.setBackground(Color.darkGray);
        this.setDoubleBuffered(true);
        this.setFocusable(true);
        keyList = new KeyListener();
        prSetup = new ProjectileSetup(); // projectile texture setup
        twSetup = new TowerSetup(); // tower texture setup
        enSetup = new EnemySetup(); // enemySoldier texture setup
        this.addKeyListener(keyList);
        projectile = new ArrayList<>();
        toRemove = new ArrayList<>();
        enemyProjectile = new ArrayList<>();
        enemyProjectileToRemove = new ArrayList<>();
        towers = new ArrayList<>();
        towersToRemove = new ArrayList<>();
        enemySoldiers = new ArrayList<>();
        enemySoldiersToRemove = new ArrayList<>();
        fountains = new ArrayList<>();
        fountainsToRemove = new ArrayList<>();
        rockets = new ArrayList<>();
        rocketsToRemove = new ArrayList<>();
        objGuns = new ArrayList<>();
        objGunsToRemove = new ArrayList<>();
        mapsetup = new MapSetup(mapFilePath);
        player = new Player(mapsetup.getPlayerStartingCoords().getFirst(),mapsetup.getPlayerStartingCoords().getSecond(), keyList, this);
        guiView = new GUIView(player.getInv(),this);
        guiView.setMyHealth(String.valueOf(mapsetup.getPlayerStartingHP()));
        double attackSpeed = 0;
        if(player.getSelectedInventoryIndex() == 0){
            attackSpeed = 1.25;
        }
        if(player.getSelectedInventoryIndex() == 1){
            attackSpeed = 6.66;
        }
        if(player.getSelectedInventoryIndex() == 2){
            attackSpeed = 0.5;
        }
        guiView.setMySpeed(String.valueOf(player.getSpeed()));
        guiView.setMyAttackSpeed(String.valueOf(attackSpeed));
        roomMover = new RoomMover(mapsetup.getRooms(),this); // i need gp to get playerXY

        mapView = new MapView(mapsetup.getRooms().get(mapsetup.getPlayerStartingRoom()),this); // zero is the first and default starting room.

        collCheck = new CollisionTileChecker(this);

        hbUI = new HealthBarPlayerUI(this,player.getHealthBar());
        gameMenu = new GameMenu(this,keyList);
        startGameThread();

    } // constructor

    public void startGameThread() {
        gameThread = new Thread(this);
        logger.info("Started game thread");
        gameThread.start(); // starting loop
    } // starting main game thread
    public void setupGame(){

    }

    @Override
    public void run() {
    /*
     * fps counter init
     */

        double drawInterval = (float)1_000_000_000 / FPS; // 0.0166 periodic seconds
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;

        long timer = 0; // fps counter
        int drawCount = 0; // fps counter

    //setups the game
        setupGame();


        while (gameThread != null) {
            /*
             * fps counter start
             */
            currentTime = System.nanoTime();
            delta += (currentTime - lastTime) / drawInterval;
            timer += (currentTime - lastTime); // fps counter
            lastTime = currentTime;

            if (delta >= 1) {
                update(); // updates
                repaint(); // draws
                delta--;
                drawCount++; //fps counter
            }

            if (timer >= 1_000_000_000) {
                System.out.println("FPS: " + drawCount);
                gameConsole.changeLabelFPS("FPS: " + drawCount);
                drawCount = 0;
                timer = 0;
                logger.finest("FPS:" + drawCount);
            } // FPS COUNTER
            /*
             * fps counter end
             */
            if(player.getHealthBar().getHealth()<0){
                JOptionPane.showMessageDialog(null,"You died!");
                logger.info("Player has died, his/her health is below zero.");
                exitGame();
            }
        }
    }
    public void saveGame() {
        logger.info("Started saving the game");
        String filePath;
        StringBuilder savedString = new StringBuilder();
        logger.info("Made new StringBuilder");
        savedString.append(getConfig().getScreenHeight() / 16).append("\n");
        savedString.append(getConfig().getScreenWidth() / 16).append("\n");
        savedString.append(roomMover.getRooms().size()).append("\n");
        savedString.append("(").append(player.getActualX()).append(",").append(player.getActualY()).append(")\n");
        savedString.append(roomMover.getRooms().indexOf(roomMover.getActualRoom())).append("\n");
        savedString.append(player.getHealthBar().getHealth()).append("\n");
        savedString.append(mapsetup.getFirstInvIndex()).append("\n");
        savedString.append(mapsetup.getSecondInvIndex()).append("\n");
        savedString.append(mapsetup.getThirdInvIndex()).append("\n");
        logger.info("Saved player");
        for (int roomIndex = 0; roomIndex < roomMover.getRooms().size(); roomIndex++) {
            logger.info("Started saving rooms");
            if(roomIndex == roomMover.getRooms().indexOf(roomMover.getActualRoom())){
                savedString.append(roomMover.getActualRoom().getUpRoomIndex()).append("\n");
                savedString.append(roomMover.getActualRoom().getRightRoomIndex()).append("\n");
                savedString.append(roomMover.getActualRoom().getDownRoomIndex()).append("\n");
                savedString.append(roomMover.getActualRoom().getLeftRoomIndex()).append("\n");
                savedString.append(roomMover.getActualRoom().isClosed()).append("\n");
                savedString.append(enemySoldiers.size()).append("\n");
                savedString.append(towers.size()).append("\n");
                savedString.append(fountains.size()).append("\n");
                logger.info("Started saving entities from active room");
                for (EnemySoldier enemy : enemySoldiers) {
                    savedString.append("(").append(enemy.getActualX()).append(",").append(enemy.getActualY()).append(")\n");
                }
                for (Tower tower : towers) {
                    savedString.append("(").append(tower.getActualX()).append(",").append(tower.getActualY()).append(")\n");
                }
                for (Fountain fountain : fountains) {
                    savedString.append("(").append(fountain.getActualX()).append(",").append(fountain.getActualY()).append(")\n");
                }

            } else{
                logger.info("Saving room " + roomIndex);
                Room room = roomMover.getRooms().get(roomIndex);
                savedString.append(room.getUpRoomIndex()).append("\n");
                savedString.append(room.getRightRoomIndex()).append("\n");
                savedString.append(room.getDownRoomIndex()).append("\n");
                savedString.append(room.getLeftRoomIndex()).append("\n");
                savedString.append(room.isClosed()).append("\n");
                savedString.append(room.getEnemies().size()).append("\n");
                savedString.append(room.getTowers().size()).append("\n");
                savedString.append(room.getFountains().size()).append("\n");
                logger.info("Saving entities from room " + roomIndex);
                for (Tuple enemy : room.getEnemies()) {
                    savedString.append("(").append(enemy.getFirst()).append(",").append(enemy.getSecond()).append(")\n");
                }
                for (Tuple tower : room.getTowers()) {
                    savedString.append("(").append(tower.getFirst()).append(",").append(tower.getSecond()).append(")\n");
                }
                for (Tuple fountain : room.getFountains()) {
                    savedString.append("(").append(fountain.getFirst()).append(",").append(fountain.getSecond()).append(")\n");
                }
            }
            logger.info("Saving the map of the room " + roomIndex);
            int[][] map = roomMover.getRooms().get(roomIndex).getMap();
            for (int row = 0; row < getConfig().getScreenHeight() / 16; row++) {
                if (row != 0) {
                    savedString.append("\n");
                }
                for (int col = 0; col < getConfig().getScreenWidth() / 16; col++) {
                    savedString.append(map[row][col]).append(" ");
                }
            }
            savedString.append("\n");
        }
            logger.info("opening file chooser");
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setCurrentDirectory(new File(System.getProperty("user.dir") + "/src/main/resources/savegame"));
            FileNameExtensionFilter filter = new FileNameExtensionFilter("Text files", "txt", "text"); //adds txt filter
            fileChooser.setFileFilter(filter);
            int userSelection = fileChooser.showSaveDialog(null);
            if (userSelection == JFileChooser.APPROVE_OPTION) {
                logger.info("User selected the save directory and approved save");
                // User chooses a file
                try {
                    filePath = fileChooser.getSelectedFile().getAbsolutePath();
                    logger.info("The path will be: " + filePath);
                    // Create a BufferedWriter to write the string to the file
                    BufferedWriter writer = new BufferedWriter(new FileWriter(filePath));
                    logger.info("Writing to the specified file");
                    // Write your string to the file
                    writer.write(String.valueOf(savedString));

                    // Close the writer
                    writer.close();

                    // Display a success message
                    logger.info("String saved to file: " + filePath);
                    JOptionPane.showMessageDialog(null,"The game has been saved into \""+ filePath +"\".");
                } catch (IOException e) {
                    // Handle any errors that may occur during file writing
                    err.IOExceptionErrorHandler("Saving the map",6);
                    logger.severe("Error while saving the level: " + e);
                }
            }

    }



    public void exitGame(){
        logger.info("Exiting game");
        launcher.getLauncher().setVisible(true);
        this.gw.remove(this);
        this.gw.setVisible(false);
        gameThread = null;
        logger.warning("Game thread has been shut down.");
        gameMenu.setGuiThread(null);
        logger.warning("GUI thread has been shut down.");
    }

    public void update() {
        if(!isPaused){
            roomMover.update();
            mapView.update();
            player.update();
            hbUI.update();

            /*
             * updating player projectile
             */

            if (!projectile.isEmpty()) {
                for (Projectile projectileTmp : projectile) {
                    projectileTmp.update();
                }
                projectile.removeAll(toRemove);
                toRemove.clear();
            } //drawing player projectiles
            /*
             * updating enemy projectile (including towers)
             */

            if (!enemyProjectile.isEmpty()) {
                for (EnemyProjectile enemyProjectileTmp : enemyProjectile) {
                    enemyProjectileTmp.update();
                }
                enemyProjectile.removeAll(enemyProjectileToRemove);
                enemyProjectileToRemove.clear();
                //drawing
            } // updating enemy projectiles


            /*
             * updating towers
             */

            if (!towers.isEmpty()) {
                for (Tower towerTmp : towers) {
                    towerTmp.update();
                }
                towers.removeAll(towersToRemove);
                towersToRemove.clear();
            }

            /*
             * updating enemy soldiers
             */

            if (!enemySoldiers.isEmpty()) {
                for (EnemySoldier enemySoldierTmp : enemySoldiers) {
                        enemySoldierTmp.update();
                }
                enemySoldiers.removeAll(enemySoldiersToRemove);
                enemySoldiersToRemove.clear();
            }

            /*
             * updating fountains
             */

            if (!fountains.isEmpty()) {
                for (Fountain fountainTmp : fountains) {
                    fountainTmp.update();
                }
                fountains.removeAll(fountainsToRemove);
                fountainsToRemove.clear();
            }

            /*
             * updating rockets
             */

            if(!rockets.isEmpty()){
                for (Rocket rocketTmp : rockets) {
                    rocketTmp.update();
                }
                rockets.removeAll(rocketsToRemove);
                rocketsToRemove.clear();
            }

            /*
             * drawing gun drops
             */

            if(!objGuns.isEmpty()){
                for (ObjGun gun : objGuns) {
                    gun.update();
                }
                objGuns.removeAll(objGunsToRemove);
                objGunsToRemove.clear();
            }
        }
        if(menuOpen){
            gameMenu.update();
        }
    }



    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d;
        g2d = (Graphics2D)g;
        mapView.draw(g2d);
        player.draw(g2d);
        hbUI.draw(g2d);

        /*
         * drawing player projectile
         */

        if (!projectile.isEmpty()) {
            for (Projectile projectileTmp : projectile) {
                projectileTmp.draw(g2d);
            }
            projectile.removeAll(toRemove);
            toRemove.clear();


        } //drawing player projectiles
        /*
         * drawing enemy projectile (including towers)
         */

        if (!enemyProjectile.isEmpty()) {
            for (EnemyProjectile enemyProjectileTmp : enemyProjectile) {
                enemyProjectileTmp.draw(g2d);
            }
            enemyProjectile.removeAll(enemyProjectileToRemove);
            enemyProjectileToRemove.clear();
            //drawing
        } // drawing enemy projectiles

        /*
         * drawing towers
         */

        if(!towers.isEmpty()){
            for (Tower towerTmp : towers) {
                towerTmp.draw(g2d);
            }
            towers.removeAll(towersToRemove);
            towersToRemove.clear();
        }

        /*
         * drawing enemy soldiers
         */

        if(!enemySoldiers.isEmpty()){
            for (EnemySoldier enemySoldierTmp : enemySoldiers) {
                enemySoldierTmp.draw(g2d);
            }
            enemySoldiers.removeAll(enemySoldiersToRemove);
            enemySoldiersToRemove.clear();
        }

        /*
         * drawing fountains
         */

        if(!fountains.isEmpty()){
            for (Fountain fountainTmp : fountains) {
                fountainTmp.draw(g2d);
            }
            fountains.removeAll(fountainsToRemove);
            fountainsToRemove.clear();

        }

        /*
         * drawing rockets
         */

        if(!rockets.isEmpty()){
            for (Rocket rocketTmp : rockets) {
                rocketTmp.draw(g2d);
            }
            rockets.removeAll(rocketsToRemove);
            rocketsToRemove.clear();

        }

        /*
         * drawing gun drops
         */

        if(!objGuns.isEmpty()){
            for (ObjGun gun : objGuns) {
                gun.draw(g2d);
            }
            objGuns.removeAll(objGunsToRemove);
            objGunsToRemove.clear();

        }



        guiView.draw(g2d);
        gameConsole.draw(g2d);
        if(menuOpen){
            gameMenu.draw(g2d);
        }

    }

    public Player getPlayer() {
        return player;
    }

    /**
     * getter to reach mapSetup from different class (specifically from MapView)
     * @return MapSetup
     */
    public MapSetup getMapsetup() {
        return mapsetup;
    }

    /**
     * Getter to reach configuration object from other classes
     * @return configuration object
     */
    public ConfigFileSetup getConfig() {
        return config;
    }

    /**
     * Getter to reach coll checker specifically from player
     * @return collChecker object
     */
    public CollisionTileChecker getCollCheck() {
        return collCheck;
    }

    public RoomMover getRoomMover() {
        return roomMover;
    }

    public boolean isPaused() {
        return isPaused;
    }

    public void setPaused(boolean paused) {
        isPaused = paused;
    }

    public void setMenuOpen(boolean menuOpen) {
        this.menuOpen = menuOpen;
    }

    public Launcher getLauncher() {
        return launcher;
    }

    public GameConsole getGameConsole() {
        return gameConsole;
    }

    public GUIView getGuiView() {
        return guiView;
    }
}
