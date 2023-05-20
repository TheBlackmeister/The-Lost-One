package cz.cvut.fel.pjv.View;

import cz.cvut.fel.pjv.Controller.GamePanel;
import cz.cvut.fel.pjv.Controller.KeyListener;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class GameMenu implements Runnable {
    ErrorWindow err;
    GamePanel gp;
    KeyListener keyListener;
    Thread guiThread;
    long canBePressed, menuCanBePressed = 0;
    String[] menu;
    int actualMenuItem = 0;
    BufferedImage backMenu,arrow;

    public GameMenu(GamePanel gp, KeyListener keyList) {
        err = new ErrorWindow();
        this.gp = gp;
        this.keyListener = keyList;
        menu = new String[]{"Save the game","Reset the game","Quit the Game"};
        guiThread = new Thread(this);
        guiThread.start();

        try {
            backMenu = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/gui/backgroundMenu.png")));
            arrow = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/gui/arrow.png")));
        } catch (IOException | NullPointerException e) {
            err.IOExceptionErrorHandler("GameMenu", 5);
            throw new RuntimeException(e);
        }

    }

    @Override
    public void run() {

        double drawInterval = (float) 1_000_000_000 / 30; // refresh rate
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;

        long timer = 0; // fps counter
        int drawCount = 0; // fps counter


        while (guiThread != null) {
            /*
             * fps counter start
             */
            currentTime = System.nanoTime();
            delta += (currentTime - lastTime) / drawInterval;
            timer += (currentTime - lastTime); // fps counter
            lastTime = currentTime;

            if (delta >= 1) {

                if (keyListener.isEscPressed()) {
                    if (canBePressedAgain(System.nanoTime())) {
                        if (gp.isPaused()) {
                            gp.setPaused(false);
                            gp.setMenuOpen(false);
                            System.out.println("Thread runs");
                        } else {
                            gp.setMenuOpen(true);
                            gp.setPaused(true);
                            System.out.println("Thread waits");
                        }
                    }
                }
                delta--;
                drawCount++;
            }

            if (timer >= 1_000_000_000) {
                System.out.println("GameMenu thread FPS: " + drawCount);
                drawCount = 0;
                timer = 0;
            } // FPS COUNTER
        }
    }

    public boolean canBePressedAgain(long rightNow) {
        if(canBePressed < rightNow - 500_000_000){
            canBePressed = rightNow;
            return true;
        }
        return false;
    }
    public boolean menuCanBePressedAgain(long rightNow) {
        if(menuCanBePressed < rightNow - 300_000_000){
            menuCanBePressed = rightNow;
            return true;
        }
        return false;
    }


    public void resetGame(){
        JButton starter = gp.getLauncher().starter;
        gp.exitGame();
        starter.doClick();
        guiThread = null;
    }


    public void update(){
        if(keyListener.isDownPressed()){
            if(menuCanBePressedAgain(System.nanoTime())){
                if(actualMenuItem == menu.length-1){
                    actualMenuItem = 0;
                } else{
                    actualMenuItem++;
                }
            }
        }

        if(keyListener.isUpPressed()){
            if(menuCanBePressedAgain(System.nanoTime())) {
                if (actualMenuItem == 0) {
                    actualMenuItem = menu.length-1;
                } else {
                    actualMenuItem--;
                }
            }
        }
        if(keyListener.isEnterPressed()){
            if(menuCanBePressedAgain(System.nanoTime())) {
                switch (actualMenuItem){
                    case 0 -> {
                        gp.saveGame();
                        gp.exitGame();
                    }
                    case 1 -> resetGame();
                    case 2 -> gp.exitGame();
                }
            }
        }
    }


    public void draw(Graphics g) {
        Graphics2D g2d;
        g2d = (Graphics2D) g;
        g2d.setColor(Color.red);
        g2d.drawImage(backMenu, 0, 0, gp.getConfig().getScreenWidth(), gp.getConfig().getScreenHeight(), null);

        for (int i = 0; i < menu.length; i++) {
            if(actualMenuItem == i){
                g2d.drawImage(arrow,gp.getConfig().getScreenWidth() / 2 -150,(gp.getConfig().getScreenHeight()*(i+1)/menu.length)-150, (int) (161*0.3), (int) (248*0.3),null);
            }
            g2d.setFont(new Font("TimesRoman",Font.PLAIN,30));
            g2d.drawString(menu[i], gp.getConfig().getScreenWidth() / 2 -100, (gp.getConfig().getScreenHeight()*(i+1)/menu.length)-100);
        }
    }
    public void setGuiThread(Thread guiThread) {
        this.guiThread = guiThread;
    }
}

