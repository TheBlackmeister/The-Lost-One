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

/**
 * GamePanel is one of the main classes of the project. It updates the game status.
 */
public class GamePanel extends JPanel implements Runnable{
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
        gameConsole = new GameConsole(this);
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
        mapsetup = new MapSetup(this,mapFilePath);
        player = new Player(mapsetup.getPlayerStartingCoords().getFirst(),mapsetup.getPlayerStartingCoords().getSecond(), keyList, this);
        roomMover = new RoomMover(mapsetup.getRooms(),this); // i need gp to get playerXY
        guiView = new GUIView(player.getInv(),this);
        mapView = new MapView(mapsetup.getRooms().get(mapsetup.getPlayerStartingRoom()),this); // zero is the first and default starting room.

        collCheck = new CollisionTileChecker(this);

        hbUI = new HealthBarPlayerUI(this,player.getHealthBar());
        gameMenu = new GameMenu(this,keyList);
        startGameThread();

    } // constructor

    public void startGameThread() {
        gameThread = new Thread(this);
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
            } // FPS COUNTER
            /*
             * fps counter end
             */
            if(player.getHealthBar().getHealth()<0){
                JOptionPane.showMessageDialog(null,"You died!");
                exitGame();
            }
        }
    }
    public void saveGame() {
        String filePath;
        StringBuilder savedString = new StringBuilder();
        savedString.append(getConfig().getScreenHeight() / 16).append("\n");
        savedString.append(getConfig().getScreenWidth() / 16).append("\n");
        savedString.append(roomMover.getRooms().size()).append("\n");
        savedString.append("(").append(player.getActualX()).append(",").append(player.getActualY()).append(")\n");
        savedString.append(roomMover.getRooms().indexOf(roomMover.getActualRoom())).append("\n");
        savedString.append(player.getHealthBar().getHealth()).append("\n");
        savedString.append(mapsetup.getFirstInvIndex()).append("\n");
        savedString.append(mapsetup.getSecondInvIndex()).append("\n");
        savedString.append(mapsetup.getThirdInvIndex()).append("\n");

        for (int roomIndex = 0; roomIndex < roomMover.getRooms().size(); roomIndex++) {
            if(roomIndex == roomMover.getRooms().indexOf(roomMover.getActualRoom())){
                savedString.append(roomMover.getActualRoom().getUpRoomIndex()).append("\n");
                savedString.append(roomMover.getActualRoom().getRightRoomIndex()).append("\n");
                savedString.append(roomMover.getActualRoom().getDownRoomIndex()).append("\n");
                savedString.append(roomMover.getActualRoom().getLeftRoomIndex()).append("\n");
                savedString.append(roomMover.getActualRoom().isClosed()).append("\n");
                savedString.append(enemySoldiers.size()).append("\n");
                savedString.append(towers.size()).append("\n");
                savedString.append(fountains.size()).append("\n");

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
                Room room = roomMover.getRooms().get(roomIndex);
                savedString.append(room.getUpRoomIndex()).append("\n");
                savedString.append(room.getRightRoomIndex()).append("\n");
                savedString.append(room.getDownRoomIndex()).append("\n");
                savedString.append(room.getLeftRoomIndex()).append("\n");
                savedString.append(room.isClosed()).append("\n");
                savedString.append(room.getEnemies().size()).append("\n");
                savedString.append(room.getTowers().size()).append("\n");
                savedString.append(room.getFountains().size()).append("\n");

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
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setCurrentDirectory(new File(System.getProperty("user.dir") + "/src/main/resources/savegame"));
            FileNameExtensionFilter filter = new FileNameExtensionFilter("Text files", "txt", "text"); //adds txt filter
            fileChooser.setFileFilter(filter);
            int userSelection = fileChooser.showSaveDialog(null);
            if (userSelection == JFileChooser.APPROVE_OPTION) {
                // User chooses a file
                try {
                    filePath = fileChooser.getSelectedFile().getAbsolutePath();

                    // Create a BufferedWriter to write the string to the file
                    BufferedWriter writer = new BufferedWriter(new FileWriter(filePath));

                    // Write your string to the file
                    writer.write(String.valueOf(savedString));

                    // Close the writer
                    writer.close();

                    // Display a success message
                    System.out.println("String saved to file: " + filePath);
                    JOptionPane.showMessageDialog(null,"The game has been saved into \""+ filePath +"\".");
                } catch (IOException e) {
                    // Handle any errors that may occur during file writing
                    err.IOExceptionErrorHandler("Saving the map",6);
                }
            }

    }



    public void exitGame(){
        launcher.getLauncher().setVisible(true);
        this.gw.remove(this);
        this.gw.setVisible(false);
        gameThread = null;
        gameMenu.setGuiThread(null);
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

        mapView.draw(g);
        player.draw(g);
        hbUI.draw(g);

        /*
         * drawing player projectile
         */

        if (!projectile.isEmpty()) {
            for (Projectile projectileTmp : projectile) {
                projectileTmp.draw(g);
            }
            projectile.removeAll(toRemove);
            toRemove.clear();


        } //drawing player projectiles
        /*
         * drawing enemy projectile (including towers)
         */

        if (!enemyProjectile.isEmpty()) {
            for (EnemyProjectile enemyProjectileTmp : enemyProjectile) {
                enemyProjectileTmp.draw(g);
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
                towerTmp.draw(g);
            }
            towers.removeAll(towersToRemove);
            towersToRemove.clear();
        }

        /*
         * drawing enemy soldiers
         */

        if(!enemySoldiers.isEmpty()){
            for (EnemySoldier enemySoldierTmp : enemySoldiers) {
                enemySoldierTmp.draw(g);
            }
            enemySoldiers.removeAll(enemySoldiersToRemove);
            enemySoldiersToRemove.clear();
        }

        /*
         * drawing fountains
         */

        if(!fountains.isEmpty()){
            for (Fountain fountainTmp : fountains) {
                fountainTmp.draw(g);
            }
            fountains.removeAll(fountainsToRemove);
            fountainsToRemove.clear();

        }

        /*
         * drawing rockets
         */

        if(!rockets.isEmpty()){
            for (Rocket rocketTmp : rockets) {
                rocketTmp.draw(g);
            }
            rockets.removeAll(rocketsToRemove);
            rocketsToRemove.clear();

        }

        /*
         * drawing gun drops
         */

        if(!objGuns.isEmpty()){
            for (ObjGun gun : objGuns) {
                gun.draw(g);
            }
            objGuns.removeAll(objGunsToRemove);
            objGunsToRemove.clear();

        }



        guiView.draw(g);
        gameConsole.draw(g);
        if(menuOpen){
            gameMenu.draw(g);
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
}
