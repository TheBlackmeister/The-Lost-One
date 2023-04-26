package cz.cvut.fel.pjv.Model.Entity;
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

    public enum PlayerDirections {
        UP, DOWN, LEFT, RIGHT, UPLEFT, DOWNLEFT, UPRIGHT, LEFTRIGHT
    }
    ErrorWindow err;
    BufferedImage playerImageUP,playerImageDOWN,playerImageLEFT,playerImageRIGHT;
    PlayerDirections direction;
    public Player(int actualX, int actualY) {
        this.actualX = actualX;
        this.actualY = actualY;
        this.speed = 3;
        err = new ErrorWindow();
        setUpPlayer();
        direction = PlayerDirections.DOWN;
    }
    public void setUpPlayer() {
        try {
            playerImageUP = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/entity/player/greensoldier_UP.png")));
            playerImageDOWN = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/entity/player/greensoldier_DOWN.png")));
            playerImageLEFT = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/entity/player/greensoldier_LEFT.png")));
            playerImageRIGHT = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/entity/player/greensoldier_RIGHT.png")));
            // green player texture is from here https://www.deviantart.com/friendlyfirefox/art/Pimp-My-Sprite-Top-down-Soldier-467311032
            // Other colors are colored by me.
            direction = PlayerDirections.DOWN;
        } catch (IOException | NullPointerException e) {
            err.IOExceptionErrorHandler("Player Image", 5);
            throw new RuntimeException(e);
        }
    }
    public void update(){

        switch (direction){
            case DOWN -> actualY += speed;
            case UP -> actualY -= speed;
            case LEFT -> actualX -= speed;
            case RIGHT -> actualX += speed;
        }

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
            case UPLEFT -> {
            }
            case DOWNLEFT -> {
            }
            case UPRIGHT -> {
            }
            case LEFTRIGHT -> {
            }
        }
        g2d.drawImage(PlayerImage,actualX,actualY,null);

    }
}
