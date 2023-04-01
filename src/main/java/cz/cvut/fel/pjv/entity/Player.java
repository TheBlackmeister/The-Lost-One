package cz.cvut.fel.pjv.entity;

import cz.cvut.fel.pjv.Controller.GamePanel;
import cz.cvut.fel.pjv.Controller.KeyHandler;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Player extends Entity{
    GamePanel gp;
    KeyHandler keyHand;
    public final int screenX;
    public final int screenY;
    public int keys;

    public Player(GamePanel gp, KeyHandler keyHand) {
        this.gp = gp;
        this.keyHand = keyHand;
        collisionBox = new Rectangle(); // COLLISIONBOX
        collisionBox.x = 8;
        collisionBox.y = 16;
        collisionBox.width = 32;
        collisionBox.height = 32;
        setDefaultValues(); // inicializace
        getPlayerImage(); // images postavy
        screenX = gp.getScreenWidth()/2 - (gp.getTileSize()/2);
        screenY = gp.getScreenHeight()/2 - (gp.getTileSize()/2);
        keys = 0;
    }
    public void setDefaultValues(){ //spawnpoint
        posX = gp.getWorldWidth()/2;
        posY = gp.getWorldHeight()/2;
        speed = 4;
        direction = "down";
    }

    public void getPlayerImage() {
        try {
            up1 = ImageIO.read(getClass().getResourceAsStream("/player/Hugo_Running_back_1.png"));
            up2 = ImageIO.read(getClass().getResourceAsStream("/player/Hugo_Running_back_2.png"));
            upIdle = ImageIO.read(getClass().getResourceAsStream("/player/Hugo_Idle_back.png"));
            down1 = ImageIO.read(getClass().getResourceAsStream("/player/Hugo_Running_front_1.png"));
            down2 = ImageIO.read(getClass().getResourceAsStream("/player/Hugo_Running_front_2.png"));
            downIdle = ImageIO.read(getClass().getResourceAsStream("/player/Hugo_Idle_front.png"));
            left1 = ImageIO.read(getClass().getResourceAsStream("/player/Hugo_running_leftside.png"));
            left2 = ImageIO.read(getClass().getResourceAsStream("/player/Hugo_Idle_leftside.png"));
            right1 = ImageIO.read(getClass().getResourceAsStream("/player/Hugo_running_rightside.png"));
            right2 = ImageIO.read(getClass().getResourceAsStream("/player/Hugo_Idle_rightside.png"));
        }
        catch(IOException e) {
            e.printStackTrace();
        }
    }
    public void pickUpObject(int objectID) {
        if(objectID!=-1){
            String objectName = gp.getObj()[objectID].getName();

            switch (objectName) {
                case "Chest":
                    break;
                case "Key":
                    keys++; // mam o klic vice!
                    gp.getObj()[objectID] = null;
                    break;
                case "Door":
                    if (keys > 0) {
                        keys--;
                        gp.getObj()[objectID] = null;
                    }
                    else {
                        System.out.println("nemas klice!");
                    }
            }
        }
        else{

        }
    }
    public void update() {
        if(keyHand.isUpPressed()) {
            direction = "up";
//            posY -= speed; //presmerovano do collision - pod timto switchem je collcheck
        }
        else if(keyHand.isDownPressed()) {
            direction = "down";
//            posY += speed; //presmerovano do collision - pod timto switchem je collcheck
        }
        else if(keyHand.isRightPressed()) {
            direction = "right";
//            posX += speed; //presmerovano do collision - pod timto switchem je collcheck
        }
        else if(keyHand.isLeftPressed()) {
            direction = "left";
//            posX -= speed; //presmerovano do collision - pod timto switchem je collcheck

        } // zaznamenani WASD
        else if(!keyHand.isRightPressed() && direction.equals("right")){
            direction = "rightIdle";
        }
        else if(!keyHand.isLeftPressed() && direction.equals("left")){
            direction = "leftIdle";
        }
        else if(!keyHand.isUpPressed() && direction.equals("up")){
            direction = "upIdle";
        }
        else if(!keyHand.isDownPressed() && direction.equals("down")){
            direction = "downIdle";
        }
        // tile collision check
        collisionOn = false;
        gp.getCollChecker().checkTile(this); // this protoze player o sobe je entita

        int objectID = gp.getCollChecker().checkObject(this, true); // object coll check
        pickUpObject(objectID);

        // if collision je false, player se muze tim smerem hybat
        if(!isCollisionOn()) {
            switch (direction) {
                case "up" -> posY -= speed;
                case "down" -> posY += speed;
                case "right" -> posX += speed;
                case "left" -> posX -= speed;
                default -> {}
            }
        }

        spriteCounter++; // animace hrace
        if(spriteCounter > 10) {
            if(spriteNum == 1)  {
                spriteNum = 2;
            }
            else if(spriteNum == 2) {
                spriteNum = 1;
            }
            spriteCounter = 0;
        } // konec animace hrace
    }
    public void draw(Graphics2D g2){
        BufferedImage image = null;
         switch (direction) {
            case "up":
                if(spriteNum == 1){
                    image = up1;
                }
                if(spriteNum == 2) {
                    image = up2;
                }
                break;
             case "down":
                 if(spriteNum == 1){
                     image = down1;
                 }
                 if(spriteNum == 2){
                     image  = down2;
                 }
                 break;
             case "right":
                 if(spriteNum == 1){
                     image = right1;
                 }
                 if(spriteNum == 2){
                     image  = right2;
                 }
                 break;
             case "left":
                 if(spriteNum == 1){
                     image = left1;
                 }
                 if(spriteNum == 2){
                     image  = left2;
                 }
                 break;
             case "upIdle":
                 image = upIdle;
                 break;
             case "downIdle":
                 image = downIdle;
                 break;
             case "leftIdle":
                 image = left2;
                 break;
             case "rightIdle":
                 image = right2;
                 break;
             default:
                 image = down1;
                 break;
        }
        g2.drawImage(image, screenX, screenY, gp.getTileSize(), gp.getTileSize(), null);
        //        g2.setColor(Color.white);
//
//        g2.fillRect(posX,posY,gp.getTileSize(),gp.getTileSize());
    }
}
