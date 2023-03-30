package cz.cvut.fel.pjv.entity;

import cz.cvut.fel.pjv.Controller.GamePanel;
import cz.cvut.fel.pjv.Controller.KeyHandler;

import java.awt.*;

public class Player extends Entity{
    GamePanel gp;
    KeyHandler keyHand;

    public Player(GamePanel gp, KeyHandler keyHand) {
        this.gp = gp;
        this.keyHand = keyHand;

        setDefaultValues(); // inicializace
    }
    public void setDefaultValues(){
        posX = 100;
        posY = 100;
        speed = 4;
    }
    public void update() {
        if(keyHand.isUpPressed()) {
            posY -= speed;
        }
        else if(keyHand.isDownPressed()) {
            posY += speed;
        }
        else if(keyHand.isRightPressed()) {
            posX += speed;
        }
        else if(keyHand.isLeftPressed()) {
            posX -= speed;
        } // zaznamenani WASD
    }
    public void draw(Graphics2D g2){
        g2.setColor(Color.white);

        g2.fillRect(posX,posY,gp.getTileSize(),gp.getTileSize());
    }
}
