package cz.cvut.fel.pjv.Controller;

import cz.cvut.fel.pjv.Model.Entity.*;
import cz.cvut.fel.pjv.Model.Setuper.*;
import cz.cvut.fel.pjv.View.HealthBarPlayerUI;
import cz.cvut.fel.pjv.View.Launcher;
import cz.cvut.fel.pjv.View.MapView;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 * GamePanel is one of the main classes of the project. It updates the game status.
 */
public class GamePanel extends JPanel implements Runnable{
    Launcher launcher;
    JFrame gw;
    Thread gameThread; // main game thread
    Player player;
    ConfigFileSetup config;
    MapSetup mapsetup;
    MapView mapView;
    RoomMover roomMover;
    CollisionTileChecker collCheck;
    HealthBarPlayerUI hbUI;
    public ProjectileSetup prSetup; // nacteni textur pro bullet
    public ArrayList<Projectile> toRemove;
    public ArrayList<Projectile> projectile; // public used for manipulation from projectile class


    /**
     * tower arraylists to keep all the entities
     */
    public TowerSetup twSetup; // nacteni textur pro bullet
    public EnemySetup enSetup; // loading textures for enemies
    public ArrayList<Tower> towers;
    public ArrayList<Tower> towersToRemove;
    public ArrayList<EnemyProjectile> enemyProjectileToRemove;
    public ArrayList<EnemyProjectile> enemyProjectile; // public used for manipulation from projectile class
    public ArrayList<EnemySoldier> enemySoldiers;
    public ArrayList<EnemySoldier> enemySoldiersToRemove;
    public ArrayList<Fountain> fountains;
    public ArrayList<Fountain> fountainsToRemove;

    KeyListener keyList;

    int FPS = 90;

    public GamePanel(JFrame gw,Launcher launcher) {
        /*
        * config is used to initialize and hold all the configuration variables.
        */
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
        enSetup = new EnemySetup(); // enemysoldier texture setup
        this.addKeyListener(keyList);
        player = new Player(100,300, keyList, this);
        projectile = new ArrayList<Projectile>();
        toRemove = new ArrayList<Projectile>();
        enemyProjectile = new ArrayList<EnemyProjectile>();
        enemyProjectileToRemove = new ArrayList<EnemyProjectile>();
        towers = new ArrayList<Tower>();
        towersToRemove = new ArrayList<Tower>();
        enemySoldiers = new ArrayList<EnemySoldier>();
        enemySoldiersToRemove = new ArrayList<EnemySoldier>();
        fountains = new ArrayList<Fountain>();
        fountainsToRemove = new ArrayList<Fountain>();

        mapsetup = new MapSetup(this);

        roomMover = new RoomMover(mapsetup.getRooms(),this); // i need gp to get playerXY

        mapView = new MapView(mapsetup.getRooms().get(0),this); // zero is the first and default starting room.

        collCheck = new CollisionTileChecker(this);

        hbUI = new HealthBarPlayerUI(this,player.getHealthBar());
        startGameThread();

    } // konstruktor

    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start(); // spusti se loop
    } // starting main game thread
    public void setupGame(){

    }


    @Override
    public void run() {
    /**
     * fps counter init
     */
        double drawInterval = (float)1_000_000_000 / FPS; // 0.0166 periodickych sekund
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;

        long timer = 0; // fps counter
        int drawCount = 0; // fps counter

    //setups the game
        setupGame();


        while (gameThread != null) {
            /**
             * fps counter start
             */
            currentTime = System.nanoTime();
            delta += (currentTime - lastTime) / drawInterval;
            timer += (currentTime - lastTime); // fps counter
            lastTime = currentTime;

            if (delta >= 1) {
                update(); // updatuje napr pozice hrace
                repaint(); // vykresluje zmeny, obrazovku
                delta--;
                drawCount++; //fps counter
            }

            if (timer >= 1_000_000_000) {
                System.out.println("FPS: " + drawCount);
                drawCount = 0;
                timer = 0;
            } // FPS COUNTER
            /**
             * fps counter end
             */
            if(player.getHealthBar().getHealth()<0){
                JOptionPane.showMessageDialog(null,"You died!"); // test
                launcher.getLauncher().setVisible(true);
                this.gw.remove(this);
                this.gw.setVisible(false);
                gameThread = null;
            }
        }
    }

    public void update() {
        roomMover.update();
        mapView.update();
        player.update();
        hbUI.update();

        /**
         * updating player projectile
         */

        if(!projectile.isEmpty()){
            for (Projectile projectileTmp : projectile) {
                projectileTmp.update();
            }
            projectile.removeAll(toRemove);
            toRemove.clear();
        } //drawing player projectiles
        /**
         * updating enemy projectile (including towers)
         */

        if (!enemyProjectile.isEmpty()) {
            for (EnemyProjectile enemyProjectileTmp : enemyProjectile) {
                enemyProjectileTmp.update();
            }
            enemyProjectile.removeAll(enemyProjectileToRemove);
            enemyProjectileToRemove.clear();
            //vykresluji
        } // updating enemy projectiles


        /**
         * updating towers
         */

        if(!towers.isEmpty()){
            for (Tower towerTmp : towers) {
                towerTmp.update();
            }
            towers.removeAll(towersToRemove);
            towersToRemove.clear();
        }

        /**
         * updating enemy soldiers
         */

        if(!enemySoldiers.isEmpty()){
            for (EnemySoldier enemySoldierTmp : enemySoldiers) {
                enemySoldierTmp.update();
            }
            enemySoldiers.removeAll(enemySoldiersToRemove);
            enemySoldiersToRemove.clear();
        }

        /**
         * updating fountains
         */

        if(!fountains.isEmpty()){
            for (Fountain fountainTmp : fountains) {
                fountainTmp.update();
            }
            fountains.removeAll(fountainsToRemove);
            fountainsToRemove.clear();
        }

    }



    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        mapView.draw(g);
        player.draw(g);
        hbUI.draw(g);
        /**
         * drawing player projectile
         */

        if (!projectile.isEmpty()) {
            for (Projectile projectileTmp : projectile) {
                projectileTmp.draw(g);
            }
            projectile.removeAll(toRemove);
            toRemove.clear();


        } //drawing player projectiles
        /**
         * drawing enemy projectile (including towers)
         */

        if (!enemyProjectile.isEmpty()) {
            for (EnemyProjectile enemyProjectileTmp : enemyProjectile) {
                enemyProjectileTmp.draw(g);
            }
            enemyProjectile.removeAll(enemyProjectileToRemove);
            enemyProjectileToRemove.clear();
            //vykresluji
        } // drawing enemy projectiles

        /**
         * drawing towers
         */

        if(!towers.isEmpty()){
            for (Tower towerTmp : towers) {
                towerTmp.draw(g);
            }
            towers.removeAll(towersToRemove);
            towersToRemove.clear();
        }

        /**
         * drawing enemy soldiers
         */

        if(!enemySoldiers.isEmpty()){
            for (EnemySoldier enemySoldierTmp : enemySoldiers) {
                enemySoldierTmp.draw(g);
            }
            enemySoldiers.removeAll(enemySoldiersToRemove);
            enemySoldiersToRemove.clear();
        }

        /**
         * drawing fountains
         */

        if(!fountains.isEmpty()){
            for (Fountain fountainTmp : fountains) {
                fountainTmp.draw(g);
            }
            fountains.removeAll(fountainsToRemove);
            fountainsToRemove.clear();
        }


    }

    public Player getPlayer() {
        return player;
    }

    /**
     * getter to reach mapsetup from different class (specifically from MapView)
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
}
