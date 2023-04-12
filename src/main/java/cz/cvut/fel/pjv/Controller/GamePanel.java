package cz.cvut.fel.pjv.Controller;

import cz.cvut.fel.pjv.UI.Inventory;
import cz.cvut.fel.pjv.entity.Player;
import cz.cvut.fel.pjv.obj.SuperObject;
import cz.cvut.fel.pjv.tile.TileManager;
import cz.cvut.fel.pjv.utils.Sound;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel implements Runnable{
    // SCREEN SETTINGS

    final int originalTileSize = 16;
    final int scale = 3;
    final int tileSize = originalTileSize * scale; // 48x48px

    final int maxScreenCol = 32;
    final int maxScreenRow = 18;
    final int screenWidth = tileSize * maxScreenCol; // 1536
    final int screenHeight = tileSize * maxScreenRow; // 864





    //camera adjust
    final int maxWorldCol = 100;
    final int maxWorldRow = 100;

    final int worldWidth = tileSize * maxWorldCol;
    final int worldHeight = tileSize * maxWorldRow;

    int FPS = 60;

    //System initialization
    TileManager tileM = new TileManager(this); //tilemanager
    KeyHandler keyHand = new KeyHandler(); //keyhandler
    Sound sound = new Sound();
    Thread gameThread; // main game thread
    AssetSetter assetSet = new AssetSetter(this); // asset setting, such as objects
    Player player = new Player(this,keyHand); // new player
    Inventory inv = new Inventory(keyHand,this, player); // his inventory
    SuperObject[] obj = new SuperObject[20]; // array holding the objects
    protected CollisionChecker collChecker = new CollisionChecker(this); // vypocet kolizi
    // inicializace promennych konci

    public GamePanel() {
        this.setPreferredSize(new Dimension(screenWidth,screenHeight));
        this.setBackground(Color.gray);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyHand);
        this.setFocusable(true);
    } // konstruktor





    public void setupGame() {
        getAssetSet().setObject();
        playBackgroundMusic(0);
    }

    /**
     * method for playing background music with Sound class
     * @param index
     */
    public void playBackgroundMusic(int index){
        sound.setFile(index);
        sound.play();
        sound.loop();
    }
    /**
     * method for stopping the background music
     */
    public void stopMusic(){
        sound.stop();
    }
    /**
     * method for playing the sound effects
     */
    public void playSoundEffect(int index){
        sound.setFile(index);
        sound.play();
    }

    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start(); // spusti se loop
    } // starting main game thread
    @Override
    public void run() {
        double drawInterval = 1_000_000_000 /FPS; // 0.0166 periodickych sekund
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;

        long timer = 0; // fps counter
        int drawCount = 0; // fps counter
        while(gameThread != null) {
            currentTime = System.nanoTime();
            delta += (currentTime - lastTime) / drawInterval;
            timer += (currentTime - lastTime); // fps counter
            lastTime = currentTime;

            if(delta >= 1) {
                update(); // updatuje napr pozice hrace
                repaint(); // vykresluje zmeny, obrazovku
                delta--;
                drawCount++; //fps counter
            }

            if(timer >= 1_000_000_000) {
                System.out.println("FPS: " + drawCount);
                drawCount = 0;
                timer = 0;
            } // FPS COUNTER
        }
    } // HLAVNI SEKCE
    public void update() {
        player.update(); // update playera
        inv.update(); // update inventory
    }

    public void paintComponent(Graphics g){
//        BufferStrategy bufferStrategy = getB();
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D)g;

        tileM.draw(g2); // vykresleni tiles
        for(int i = 0; i < getObj().length; i++) {
            if(getObj()[i] != null) {
                getObj()[i].draw(g2, this);
            }
        }
        player.draw(g2); // vykresleni playera
        inv.draw(g2);
        g2.dispose();
    } //vykresluje


    // gettery a settery nasleduji
    public SuperObject[] getObj() {
        return obj;
    }

    public AssetSetter getAssetSet() {
        return assetSet;
    }

    public CollisionChecker getCollChecker() {
        return collChecker;
    }

    public Player getPlayer() {
        return player;
    }

    public int getTileSize() {
        return tileSize;
    }

    public int getMaxScreenCol() {
        return maxScreenCol;
    }

    public int getMaxScreenRow() {
        return maxScreenRow;
    }

    public int getScreenWidth() {
        return screenWidth;
    }

    public int getScreenHeight() {
        return screenHeight;
    }

    public int getMaxWorldCol() {
        return maxWorldCol;
    }

    public int getMaxWorldRow() {
        return maxWorldRow;
    }

    public int getWorldWidth() {
        return worldWidth;
    }

    public int getWorldHeight() {
        return worldHeight;
    }
    // konec getteru a setteru
}

