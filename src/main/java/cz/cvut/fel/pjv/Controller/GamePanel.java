package cz.cvut.fel.pjv.Controller;

import cz.cvut.fel.pjv.Model.Entity.Player;

import javax.swing.*;
import java.awt.*;

/**
 * GamePanel is one of the main classes of the project. It updates the game status.
 */
public class GamePanel extends JPanel implements Runnable{
    Thread gameThread; // main game thread
    Player player;
    int FPS = 90;
    public GamePanel() {
        this.setPreferredSize(new Dimension(800,600));
        this.setBackground(Color.darkGray);
        this.setDoubleBuffered(true);
//        this.addKeyListener(keyHand);
        this.setFocusable(true);
        player = new Player(100,100);
        startGameThread();

    } // konstruktor

    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start(); // spusti se loop
    } // starting main game thread
    public void setupGame(){

        JOptionPane.showMessageDialog(null,"Funguje"); // test
    }


    @Override
    public void run() {
        double drawInterval = 1_000_000_000 / FPS; // 0.0166 periodickych sekund
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;

        long timer = 0; // fps counter
        int drawCount = 0; // fps counter
        setupGame();
        while (gameThread != null) {
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
        }
    }

    public void update() {
        player.update();
        System.out.println("update");
    }
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D)g;
        player.draw(g);
        System.out.println("drawing");
        //vykresluji
    }
}
