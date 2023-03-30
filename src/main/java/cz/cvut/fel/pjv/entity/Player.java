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

    public Player(GamePanel gp, KeyHandler keyHand) {
        this.gp = gp;
        this.keyHand = keyHand;
        setDefaultValues(); // inicializace
        getPlayerImage(); // images postavy
    }
    public void setDefaultValues(){
        posX = 100;
        posY = 100;
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
    public void update() {
        if(keyHand.isUpPressed()) {
            direction = "up";
            posY -= speed;
        }
        else if(keyHand.isDownPressed()) {
            direction = "down";
            posY += speed;
        }
        else if(keyHand.isRightPressed()) {
            direction = "right";
            posX += speed;
        }
        else if(keyHand.isLeftPressed()) {
            direction = "left";
            posX -= speed;

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
    }
    public void draw(Graphics2D g2){
        BufferedImage image = switch (direction) {
            case "up" -> up1;
            case "down" -> down1;
            case "right" -> right1;
            case "left" -> left1;
            case "upIdle" -> upIdle;
            case "downIdle" -> downIdle;
            case "leftIdle" -> left2;
            case "rightIdle" -> right2;
            default -> down1;
        };
        g2.drawImage(image, posX, posY, gp.getTileSize(), gp.getTileSize(), null);
        //        g2.setColor(Color.white);
//
//        g2.fillRect(posX,posY,gp.getTileSize(),gp.getTileSize());
    }
}
