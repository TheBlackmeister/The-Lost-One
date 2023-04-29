package cz.cvut.fel.pjv.Controller;

import cz.cvut.fel.pjv.Model.Entity.*;
import cz.cvut.fel.pjv.Model.Map.Room;
import cz.cvut.fel.pjv.Model.Setuper.ConfigFileSetup;
import cz.cvut.fel.pjv.Model.Setuper.MapSetup;
import cz.cvut.fel.pjv.Model.Setuper.ProjectileSetup;
import cz.cvut.fel.pjv.Model.Setuper.TowerSetup;
import cz.cvut.fel.pjv.View.MapView;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 * GamePanel is one of the main classes of the project. It updates the game status.
 */
public class GamePanel extends JPanel implements Runnable{
    Thread gameThread; // main game thread
    Player player;
    ArrayList<Room> map;
    ConfigFileSetup config;
    MapSetup mapsetup;
    MapView mapView;
    int[][][] worldMap;
    public ProjectileSetup prSetup; // nacteni textur pro bullet
    public ArrayList<Projectile> toRemove;
    public ArrayList<Projectile> projectile; // public used for manipulation from projectile class


    /**
     * tower arraylists to keep all the entities
     */
    public TowerSetup twSetup; // nacteni textur pro bullet
    public ArrayList<Tower> towers;
    public ArrayList<Tower> towersToRemove;
    public ArrayList<EnemyProjectile> enemyProjectileToRemove;
    public ArrayList<EnemyProjectile> enemyProjectile; // public used for manipulation from projectile class
    public ArrayList<EnemySoldier> enemySoldiers;

    KeyListener keyList;

    int FPS = 90;

    public GamePanel() {
        /*
        * config is used to initialize and hold all the configuration variables.
        */
        config = new ConfigFileSetup();
        config.getTheConfig();

        mapsetup = new MapSetup(this);
        mapsetup.mapInit();
        this.setPreferredSize(new Dimension(config.getScreenWidth(),config.getScreenHeight()));
        this.setBackground(Color.darkGray);
        this.setDoubleBuffered(true);
        this.setFocusable(true);
        keyList = new KeyListener();
        prSetup = new ProjectileSetup(); // projectile texture setup
        twSetup = new TowerSetup(); // tower texture setup
        this.addKeyListener(keyList);
        player = new Player(100,300, keyList, this);
        projectile = new ArrayList<Projectile>();
        toRemove = new ArrayList<Projectile>();
        enemyProjectile = new ArrayList<EnemyProjectile>();
        enemyProjectileToRemove = new ArrayList<EnemyProjectile>();
        towers = new ArrayList<Tower>();
        towersToRemove = new ArrayList<Tower>();
        enemySoldiers = new ArrayList<EnemySoldier>();

        mapView = new MapView(enemySoldiers,towers,MapView.RoomType.CLOSED, mapsetup.getMap());
        startGameThread();

    } // konstruktor

    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start(); // spusti se loop
    } // starting main game thread
    public void setupGame(){
        JOptionPane.showMessageDialog(null,"Funguje"); // test
        //todo test
    }


    @Override
    public void run() {
/**
 * fps counter init
 */
        double drawInterval = 1_000_000_000 / FPS; // 0.0166 periodickych sekund
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
        }
    }

    public void update() {
        mapView.update();
        player.update();
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


    }



    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        mapView.draw(g);
        player.draw(g);
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


    }

    public Player getPlayer() {
        return player;
    }

    /**
     * Getter to reach configuration object from other classes
     * @return configuration object
     */
    public ConfigFileSetup getConfig() {
        return config;
    }
}
