package cz.cvut.fel.pjv.Model.Entity;

import cz.cvut.fel.pjv.Controller.GamePanel;
import cz.cvut.fel.pjv.View.ErrorWindow;

import java.awt.*;
import java.awt.image.BufferedImage;

import static java.lang.Math.*;

public class EnemyProjectile extends Entity{
    GamePanel gp;
    ErrorWindow err;
    double polar;
    double tmpX,tmpY;

    public EnemyProjectile(int actualX, int actualY, double polar, GamePanel gp) {
        this.actualX = actualX;
        this.actualY = actualY;
        this.polar = polar;
        tmpX = actualX; //tmp variable because it has to be double
        tmpY = actualY; //tmp variable because it has to be double
        this.speed = 5; //todo test
        this.gp = gp;
        err = new ErrorWindow();
        gp.twSetup.setUpTower();
        gp.enemyProjectile.add(this);
    }
    public void update(){
        tmpX += cos(polar) * speed;
        tmpY += sin(polar)  * speed;
        if(actualX < -100 || actualY < -100 || actualX > gp.getConfig().getScreenWidth()+100 || actualY > gp.getConfig().getScreenHeight()+100){
            gp.enemyProjectileToRemove.add(this); //this adds the projectile to the arraylist used to remove it from main arraylist
        }
    }
    public void draw(Graphics g){
        Graphics2D g2d;
        g2d = (Graphics2D)g;
        BufferedImage PlayerImage = gp.twSetup.getTowerImageBullet();

        g2d.drawImage(PlayerImage,(int) tmpX,(int)tmpY,16,16,null);
    }

    public double getTmpX() {
        return tmpX;
    }

    public double getTmpY() {
        return tmpY;
    }

}
