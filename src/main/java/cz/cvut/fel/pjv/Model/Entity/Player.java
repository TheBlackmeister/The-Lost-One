package cz.cvut.fel.pjv.Model.Entity;
import cz.cvut.fel.pjv.Controller.CollisionEntityChecker;
import cz.cvut.fel.pjv.Controller.GamePanel;
import cz.cvut.fel.pjv.Controller.KeyListener;
import cz.cvut.fel.pjv.Model.Utils.HealthBar;
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
    CollisionEntityChecker collEntCheck;
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
        this.healthBar = new HealthBar(gp.getMapsetup().getPlayerStartingHP());
        collEntCheck = new CollisionEntityChecker(gp);
        this.inv = new Inventory();
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
        if(collEntCheck.checkEntityCollisionPlayer(this)){
            healthBar.decreaseHealth();
        }
        if(keyList.ismPressed()) {
            if (inv.getInv()[1] == 1 && gp.prSetup.canBeShotMG(System.nanoTime())) { // player has MG
                Projectile projectile = new Projectile(actualX, actualY, direction, gp);

            } else if (inv.getInv()[2] == 1 && gp.prSetup.canBeShotRL(System.nanoTime())) { // player has MiniGun
                Projectile projectile = new Projectile(actualX, actualY, direction, gp);

            } else if (inv.getInv()[0] == 1 && gp.prSetup.canBeShot(System.nanoTime())) { // player has pistol
                Projectile projectile = new Projectile(actualX, actualY, direction, gp);
            }
        }

        if(keyList.isDownPressed()) {
            direction = Directions.DOWN;
            actualY += speed;
            if(gp.getCollCheck().checkTileCollisionPlayer(this)){
                actualY -= speed;
                // todo collision sound could be played here
            }
            if(gp.getRoomMover().isClosedPlayerOutOfRoomDown()){
                actualY -= speed;
                System.out.println("closed!");
            }
        }

        if(keyList.isUpPressed()) {
            direction = Directions.UP;
            actualY -= speed;
            if(gp.getCollCheck().checkTileCollisionPlayer(this)){
                actualY += speed;
                // todo collision sound could be played here
            }
            if(gp.getRoomMover().isClosedPlayerOutOfRoomUp()){
                actualY += speed;
                System.out.println("closed!");
            }
        }

        if(keyList.isRightPressed()) {
            direction = Directions.RIGHT;
            actualX += speed;
            if(gp.getCollCheck().checkTileCollisionPlayer(this)){
                actualX -= speed;
                // todo collision sound could be played here
            }
            if(gp.getRoomMover().isClosedPlayerOutOfRoomRight()){
                actualX -= speed;
                System.out.println("closed!");
            }
        }

        if(keyList.isLeftPressed()) {
            direction = Directions.LEFT;
            actualX -= speed;
            if(gp.getCollCheck().checkTileCollisionPlayer(this)){
                actualX += speed;
                // todo collision sound could be played here
            }
            if(gp.getRoomMover().isClosedPlayerOutOfRoomLeft()){
                actualX += speed;
                System.out.println("closed!");
            }
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
