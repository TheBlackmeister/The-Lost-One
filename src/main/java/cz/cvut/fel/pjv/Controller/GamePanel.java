package cz.cvut.fel.pjv.Controller;

import cz.cvut.fel.pjv.Model.Entity.EnemyProjectile;
import cz.cvut.fel.pjv.Model.Entity.Player;
import cz.cvut.fel.pjv.Model.Entity.Projectile;
import cz.cvut.fel.pjv.Model.Entity.Tower;
import cz.cvut.fel.pjv.Model.Setuper.ProjectileSetup;
import cz.cvut.fel.pjv.Model.Setuper.TowerSetup;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 * GamePanel is one of the main classes of the project. It updates the game status.
 */
public class GamePanel extends JPanel implements Runnable{
    Thread gameThread; // main game thread
    Player player;

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
    KeyListener keyList;
//    MapSetup map;
    int FPS = 90;

    public GamePanel() {
        this.setPreferredSize(new Dimension(800,600));
        this.setBackground(Color.darkGray);
        this.setDoubleBuffered(true);
//        this.addKeyListener(keyHand);
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
//        map = new MapSetup();
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
        /*
         * FPS COUNTER INIT
         * */
        double drawInterval = 1_000_000_000 / FPS; // 0.0166 periodickych sekund
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;

        long timer = 0; // fps counter
        int drawCount = 0; // fps counter


        setupGame();


        while (gameThread != null) {
            /*
             * FPS COUNTER START
             * */
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
            /*
             * FPS COUNTER END
             * */
        }
    }

    public void update() {
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

}
