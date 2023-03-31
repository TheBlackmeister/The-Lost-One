package cz.cvut.fel.pjv.obj;

import cz.cvut.fel.pjv.Controller.GamePanel;

import java.awt.*;
import java.awt.image.BufferedImage;

public class SuperObject {
    protected BufferedImage image;
    protected String name;
    protected boolean collision = false;

    protected int posX, posY;

    public int getPosX() {
        return posX;
    }

    public void setPosX(int posX) {
        this.posX = posX;
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

        if(
                posX + gp.getTileSize() > gp.getPlayer().getPosX() - gp.getPlayer().screenX &&
                posY - gp.getTileSize() < gp.getPlayer().getPosX() + gp.getPlayer().screenX &&
                posX + gp.getTileSize() > gp.getPlayer().getPosY() - gp.getPlayer().screenY &&
                posY - gp.getTileSize() < gp.getPlayer().getPosY() + gp.getPlayer().screenY
        ) //tato podminka je kvuli setreni mista pri vykreslovani mapy(aby se nevykreslila cela)
        {g2.drawImage(image, screenX, screenY, gp.getTileSize(), gp.getTileSize(), null);}
    }
}
