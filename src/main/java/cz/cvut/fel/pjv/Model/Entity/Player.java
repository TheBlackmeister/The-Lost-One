package cz.cvut.fel.pjv.Model.Entity;
import cz.cvut.fel.pjv.Controller.CollisionEntityChecker;
import cz.cvut.fel.pjv.Controller.GamePanel;
import cz.cvut.fel.pjv.Controller.KeyListener;
import cz.cvut.fel.pjv.Model.Utils.HealthBar;
import cz.cvut.fel.pjv.Model.Utils.Inventory;
import cz.cvut.fel.pjv.Model.Utils.Sound;
import cz.cvut.fel.pjv.View.ErrorWindow;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;
import java.util.Random;

/**
 * This is the Player class. it
 */
public class Player extends Entity{
    int selectedInventoryIndex = 0;
    CollisionEntityChecker collEntCheck;
    GamePanel gp;
    ErrorWindow err;
    KeyListener keyList;
    BufferedImage playerImageUP,playerImageDOWN,playerImageLEFT,playerImageRIGHT, playerImageUPLEFT, playerImageUPRIGHT,playerImageDOWNLEFT,playerImageDOWNRIGHT;
    DirectionsEnum.Directions direction;
    Sound gunShot,walkingSound;
    long walkingTimer = 0;
    long switchTimer = 0;
    Random random;
    public Player(int actualX, int actualY, KeyListener keyList, GamePanel gp) {

        this.actualX = actualX;
        this.actualY = actualY;
        this.speed = 3;
        this.keyList = keyList;
        err = new ErrorWindow();
        setUpPlayer();
        direction = DirectionsEnum.Directions.DOWN;
        this.gp = gp;
        this.healthBar = new HealthBar(gp.getMapsetup().getPlayerStartingHP());
        collEntCheck = new CollisionEntityChecker(gp);
        this.inv = new Inventory();
         random = new Random();
        //setting the sounds
        gunShot = new Sound();
        walkingSound = new Sound();
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
            direction = DirectionsEnum.Directions.DOWN;
        } catch (IOException | NullPointerException e) {
            err.IOExceptionErrorHandler("Player Image", 5);
            throw new RuntimeException(e);
        }
    }

    private void playGunSound(int index){
        gunShot.setFile(index);
        gunShot.play();
    }

    public void canBeWalkedAndWalk(long timePressed){
        if (walkingTimer < timePressed - 450_000_000){ // 0.45 secs
            walkingSound.setFile(random.nextInt(5)+5);
            walkingSound.play();
            walkingTimer = timePressed;

        }
    }

    public void canBeSwitchedThenSwitch(long timePressed){
        if (switchTimer < timePressed - 250_000_000){ // 0.25 secs
            walkingSound.setFile(12);
            walkingSound.play();
            selectedInventoryIndex++;
            if(selectedInventoryIndex > 2) selectedInventoryIndex = 0;
            while(inv.getInv()[selectedInventoryIndex]==0){
                if(selectedInventoryIndex > 2) selectedInventoryIndex = 0;
                selectedInventoryIndex++;
                if(selectedInventoryIndex > 2) selectedInventoryIndex = 0;
            }
            switchTimer = timePressed;
        }
    }

    public void update(){
        if(collEntCheck.checkEntityCollisionPlayer(this)){
            healthBar.decreaseHealth();
        }
        if(keyList.isnPressed()) {
            canBeSwitchedThenSwitch(System.nanoTime());
        }
        if(keyList.ismPressed()) {
            if (selectedInventoryIndex == 1 && gp.prSetup.canBeShotMG(System.nanoTime())) { // player has MG
                playGunSound(3);
                new Projectile(actualX, actualY, direction, gp);

            } else if (selectedInventoryIndex == 2 && gp.prSetup.canBeShotRL(System.nanoTime())) { // player has an RPG
                new Rocket(actualX, actualY, gp, direction);
                playGunSound(10);
            } else if (selectedInventoryIndex == 0 && gp.prSetup.canBeShot(System.nanoTime())) { // player has pistol
                playGunSound(2);
                new Projectile(actualX, actualY, direction, gp);
            }
        }

        if(keyList.isDownPressed()) {
            // set direction
            direction = DirectionsEnum.Directions.DOWN;
            // move player
            actualY += speed;
            //play a sound
            canBeWalkedAndWalk(System.nanoTime());
            // if player collides, return to original place
            if(gp.getCollCheck().checkTileCollisionPlayer(this)){
                actualY -= speed;
            }
            if(gp.getRoomMover().isClosedPlayerOutOfRoomDown()){
                actualY -= speed;
                System.out.println("closed!");
            }
        }

        if(keyList.isUpPressed()) {
            // set direction
            direction = DirectionsEnum.Directions.UP;
            // move player
            actualY -= speed;
            //play a sound
            canBeWalkedAndWalk(System.nanoTime());
            // if player collides, return to original place
            if(gp.getCollCheck().checkTileCollisionPlayer(this)){
                actualY += speed;
            }
            if(gp.getRoomMover().isClosedPlayerOutOfRoomUp()){
                actualY += speed;
                System.out.println("closed!");
            }
        }

        if(keyList.isRightPressed()) {
            // set direction
            direction = DirectionsEnum.Directions.RIGHT;
            // move player
            actualX += speed;
            //play a sound
            canBeWalkedAndWalk(System.nanoTime());
            // if player collides, return to original place
            if(gp.getCollCheck().checkTileCollisionPlayer(this)){
                actualX -= speed;
            }
            if(gp.getRoomMover().isClosedPlayerOutOfRoomRight()){
                actualX -= speed;
                System.out.println("closed!");
            }
        }

        if(keyList.isLeftPressed()) {
            // set direction
            direction = DirectionsEnum.Directions.LEFT;
            // move player
            actualX -= speed;
            //play a sound
            canBeWalkedAndWalk(System.nanoTime());
            // if player collides, return to original place
            if(gp.getCollCheck().checkTileCollisionPlayer(this)){
                actualX += speed;
            }
            if(gp.getRoomMover().isClosedPlayerOutOfRoomLeft()){
                actualX += speed;
                System.out.println("closed!");
            }
        }
        if(keyList.isDownPressed() && keyList.isLeftPressed()){
            direction = DirectionsEnum.Directions.DOWNLEFT;
        }
        if(keyList.isUpPressed() && keyList.isLeftPressed()){
            direction = DirectionsEnum.Directions.UPLEFT;
        }
        if(keyList.isDownPressed() && keyList.isRightPressed()){
            direction = DirectionsEnum.Directions.DOWNRIGHT;
        }
        if(keyList.isUpPressed() && keyList.isRightPressed()){
            direction = DirectionsEnum.Directions.UPRIGHT;
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
            case UPLEFT -> PlayerImage = playerImageUPLEFT;
            case DOWNLEFT -> PlayerImage = playerImageDOWNLEFT;
            case UPRIGHT -> PlayerImage = playerImageUPRIGHT;
            case DOWNRIGHT -> PlayerImage = playerImageDOWNRIGHT;
        }

        g2d.drawImage(PlayerImage,actualX,actualY,null);

    }

    public int getSelectedInventoryIndex() {
        return selectedInventoryIndex;
    }
}
