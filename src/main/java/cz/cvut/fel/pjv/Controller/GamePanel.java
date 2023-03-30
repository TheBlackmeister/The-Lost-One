package cz.cvut.fel.pjv.Controller;
import cz.cvut.fel.pjv.entity.Player;

import java.awt.*;
import javax.swing.*;

public class GamePanel extends JPanel implements Runnable{
    // SCREEN SETTINGS

    final int originalTileSize = 16;
    final int scale = 3;
    final int tileSize = originalTileSize * scale; // 48x48px

    final int maxScreenCol = 32;
    final int maxScreenRow = 18;
    final int screenWidth = tileSize * maxScreenCol; // 1536
    final int screenHeight = tileSize * maxScreenRow; // 864

    public int getTileSize() {
        return tileSize;
    }

    int FPS = 60;
    KeyHandler keyHand = new KeyHandler();
    Thread gameThread;
    Player player = new Player(this,keyHand);

    // defaultni pozice hrace

    // konec promennych

    public GamePanel() {
        this.setPreferredSize(new Dimension(screenWidth,screenHeight));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyHand);
        this.setFocusable(true);
    } // konstruktor

    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start(); // spusti se loop
    }
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
    }
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D)g;

        player.draw(g2); // vykresleni playera

        g2.dispose();
    }
}

