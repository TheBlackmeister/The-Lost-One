package cz.cvut.fel.pjv.obj;

import cz.cvut.fel.pjv.Controller.GamePanel;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * class SuperObject is a parent of all other objects
 */

public class SuperObject {
    protected BufferedImage image;
    protected String name;
    protected boolean collision = false;
    public Rectangle collisionBox = new Rectangle(0,0,48,48);
    protected int collisionBoxDefaultX = 0;
    protected int collisionBoxDefaultY = 0;
    protected int posX, posY;

    public String getName() {
        return name;
    }

    public boolean isCollision() {
        return collision;
    }

    public int getPosX() {
        return posX;
    }

    public void setPosX(int posX) {
        this.posX = posX;
    }

    public Rectangle getCollisionBox() {
        return collisionBox;
    }

    public int getCollisionBoxDefaultX() {
        return collisionBoxDefaultX;
    }

    public int getCollisionBoxDefaultY() {
        return collisionBoxDefaultY;
    }

    public int getPosY() {
        return posY;
    }

    public void setPosY(int posY) {
        this.posY = posY;
    }



    //KONEC KONSTRUKTORU
    public void draw(Graphics2D g2, GamePanel gp){

        int screenX = posX - gp.getPlayer().getPosX() + gp.getPlayer().screenX;
        int screenY = posY - gp.getPlayer().getPosY() + gp.getPlayer().screenY;

        if(((posX + gp.getTileSize()) > (gp.getPlayer().getPosX() - gp.getPlayer().screenX)) &&
                ((posX - gp.getTileSize()) < (gp.getPlayer().getPosX() + gp.getPlayer().screenX)) &&
                ((posY + gp.getTileSize()) > (gp.getPlayer().getPosY() - gp.getPlayer().screenY)) &&
                ((posY - gp.getTileSize()) < (gp.getPlayer().getPosY() + gp.getPlayer().screenY))
        ) //tato podminka je kvuli setreni mista pri vykreslovani mapy(aby se nevykreslila cela) // optimalisation
        {g2.drawImage(image, screenX, screenY, gp.getTileSize(), gp.getTileSize(), null);}
    }
}
