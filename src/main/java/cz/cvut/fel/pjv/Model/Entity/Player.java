package cz.cvut.fel.pjv.Model.Entity;
import cz.cvut.fel.pjv.Controller.GamePanel;
import cz.cvut.fel.pjv.Controller.KeyListener;
import cz.cvut.fel.pjv.View.ErrorWindow;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;
/**
 * This is the Player class. it
 */
public class Player extends Entity{
    GamePanel gp;
    ErrorWindow err;
    KeyListener keyList;
    BufferedImage playerImageUP,playerImageDOWN,playerImageLEFT,playerImageRIGHT, playerImageUPLEFT, playerImageUPRIGHT,playerImageDOWNLEFT,playerImageDOWNRIGHT;
    Directions direction;
    public Player(int actualX, int actualY, KeyListener keyList, GamePanel gp) {
        this.actualX = actualX;
        this.actualY = actualY;
        this.speed = 3;
        this.keyList = keyList;
        err = new ErrorWindow();
        setUpPlayer();
        direction = Directions.DOWN;
        this.gp = gp;
    }
    public void setUpPlayer() {
        try {
            playerImageUP = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/entity/player/greensoldier_UP.png")));
            playerImageDOWN = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/entity/player/greensoldier_DOWN.png")));
            playerImageLEFT = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/entity/player/greensoldier_LEFT.png")));
            playerImageRIGHT = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/entity/player/greensoldier_RIGHT.png")));
            playerImageUPLEFT = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/entity/player/greensoldier_UP_LEFT.png")));
            playerImageUPRIGHT = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/entity/player/greensoldier_UP_RIGHT.png")));
            playerImageDOWNLEFT = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/entity/player/greensoldier_DOWN_LEFT.png")));
            playerImageDOWNRIGHT = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/entity/player/greensoldier_DOWN_RIGHT.png")));
            // green player texture is from here https://www.deviantart.com/friendlyfirefox/art/Pimp-My-Sprite-Top-down-Soldier-467311032
            // Other colors are colored by me.
            direction = Directions.DOWN;
        } catch (IOException | NullPointerException e) {
            err.IOExceptionErrorHandler("Player Image", 5);
            throw new RuntimeException(e);
        }
    }
    public void update(){
        if(keyList.ismPressed()) {
            if (gp.prSetup.canBeShot(System.nanoTime())){
                Projectile projectile = new Projectile(actualX,actualY,direction,gp); //todo
                System.out.println("M pressed"); //testing
            }
        }

        //todo test
        if (keyList.isEscPressed()) {
            if (gp.twSetup.towerCanBeShot(System.nanoTime())){
                Tower tower = new Tower(225,289,gp); //todo test
                System.out.println("esc pressed"); //testing
            }
        }

        if(keyList.isDownPressed()) {
            direction = Directions.DOWN;
            actualY += speed;
        }

        if(keyList.isUpPressed()) {
            direction = Directions.UP;
            actualY -= speed;
        }

        if(keyList.isRightPressed()) {
            direction = Directions.RIGHT;
            actualX += speed;
        }

        if(keyList.isLeftPressed()) {
            direction = Directions.LEFT;
            actualX -= speed;
        }
        if(keyList.isDownPressed() && keyList.isLeftPressed()){
            direction = Directions.DOWNLEFT;
        }
        if(keyList.isUpPressed() && keyList.isLeftPressed()){
            direction = Directions.UPLEFT;
        }
        if(keyList.isDownPressed() && keyList.isRightPressed()){
            direction = Directions.DOWNRIGHT;
        }
        if(keyList.isUpPressed() && keyList.isRightPressed()){
            direction = Directions.UPRIGHT;
        }
//        switch (direction){
//            case DOWN -> actualY += speed;
//            case UP -> actualY -= speed;
//            case LEFT -> actualX -= speed;
//            case RIGHT -> actualX += speed;
//        }

    }
    public void draw(Graphics g){
        Graphics2D g2d;
        g2d = (Graphics2D)g;
        BufferedImage PlayerImage = null;

        switch (direction){
            case DOWN -> PlayerImage = playerImageDOWN;
            case UP -> PlayerImage = playerImageUP;
            case LEFT -> PlayerImage = playerImageLEFT;
            case RIGHT -> PlayerImage = playerImageRIGHT;
            case UPLEFT -> PlayerImage = playerImageUPLEFT;
            case DOWNLEFT -> PlayerImage = playerImageDOWNLEFT;
            case UPRIGHT -> PlayerImage = playerImageUPRIGHT;
            case DOWNRIGHT -> PlayerImage = playerImageDOWNRIGHT;
        }

        g2d.drawImage(PlayerImage,actualX,actualY,null);

    }
}
