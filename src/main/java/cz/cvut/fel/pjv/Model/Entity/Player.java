package cz.cvut.fel.pjv.Model.Entity;

import cz.cvut.fel.pjv.Controller.*;
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
import java.util.logging.Logger;

/**
 * This is the Player class. it
 */
public class Player extends Entity{
    private static final Logger logger = Logger.getLogger(Player.class.getName());
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
        this.inv = new Inventory(gp);
         random = new Random();
        //setting the sounds
        gunShot = new Sound();
        walkingSound = new Sound();
    }
    public void setUpPlayer() {
        try {
            logger.info("Loading player images");
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
            logger.severe("Error loading player images!");
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
            if(inv.getInv()[0] == 0 && inv.getInv()[1] == 0 && inv.getInv()[2] == 0){
                switchTimer = timePressed;
                return;
            }
            selectedInventoryIndex++;
            if(selectedInventoryIndex > 2) selectedInventoryIndex = 0;
            while(inv.getInv()[selectedInventoryIndex]==0){
                if(selectedInventoryIndex > 2) selectedInventoryIndex = 0;
                selectedInventoryIndex++;
                if(selectedInventoryIndex > 2) selectedInventoryIndex = 0;
            }
            switchTimer = timePressed;
            double attackSpeed = 0;
            if(selectedInventoryIndex == 0){
                attackSpeed = 1.25;
            }
            else if(selectedInventoryIndex == 1){
                attackSpeed = 6.66;
            }
            else if(selectedInventoryIndex == 2){
                attackSpeed = 0.5;
            }
            gp.getGuiView().setMyAttackSpeed(String.valueOf(attackSpeed));
        }
    }

    public void update(){
        if(collEntCheck.checkEntityCollisionPlayer(this)){
            healthBar.decreaseHealth();
            gp.getGuiView().setMyHealth(String.valueOf(healthBar.getHealth()));
        }
        if(keyList.isoPressed()){

            if(inv.dropTheGun(selectedInventoryIndex)) {
                gp.objGuns.add(new ObjGun(actualX,actualY,selectedInventoryIndex,gp));
                logger.info("Gun dropped!");
            }
        }
        if(keyList.isnPressed()) {
            canBeSwitchedThenSwitch(System.nanoTime());
        }
        if(keyList.ismPressed()) {
            if(inv.getInv()[0]==0&&inv.getInv()[1]==0&&inv.getInv()[2]==0) {
                gp.getGameConsole().changeLabel("You don't have any weapons equipped. Cannot shoot!");
            } else if (selectedInventoryIndex == 1 && gp.prSetup.canBeShotMG(System.nanoTime())) { // player has MG
                if(inv.getInv()[1]==0){
                    gp.getGameConsole().changeLabel("You dropped this gun. Press N to change equipped slot!");
                } else {
                    playGunSound(3);
                    new Projectile(actualX, actualY, direction, gp);
                }
            } else if (selectedInventoryIndex == 2 && gp.prSetup.canBeShotRL(System.nanoTime())) { // player has an RPG
                if(inv.getInv()[2]==0){
                    gp.getGameConsole().changeLabel("You dropped this gun. Press N to change equipped slot!");
                } else {
                    new Rocket(actualX, actualY, gp, direction);
                    playGunSound(10);
                }
            } else if (selectedInventoryIndex == 0 && gp.prSetup.canBeShot(System.nanoTime())) { // player has pistol
                if(inv.getInv()[0]==0){
                    gp.getGameConsole().changeLabel("You dropped this gun. Press N to change equipped slot!");
                } else {
                    playGunSound(2);
                    new Projectile(actualX, actualY, direction, gp);
                }
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
                gp.getGameConsole().changeLabel("Door is closed. Kill all enemies!");
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
                gp.getGameConsole().changeLabel("Door is closed. Kill all enemies!");
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
                gp.getGameConsole().changeLabel("Door is closed. Kill all enemies!");
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
                gp.getGameConsole().changeLabel("Door is closed. Kill all enemies!");
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
    public void draw(Graphics2D g2d){
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

    public void setSelectedInventoryIndex(int selectedInventoryIndex) {
        this.selectedInventoryIndex = selectedInventoryIndex;
    }
}
