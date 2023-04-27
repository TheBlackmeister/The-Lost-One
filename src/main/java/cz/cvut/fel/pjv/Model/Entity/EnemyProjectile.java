package cz.cvut.fel.pjv.Model.Entity;

import cz.cvut.fel.pjv.Controller.GamePanel;
import cz.cvut.fel.pjv.View.ErrorWindow;

import java.awt.*;
import java.awt.image.BufferedImage;

public class EnemyProjectile extends Entity{
    GamePanel gp;
    ErrorWindow err;
    double vx,vy;
    public EnemyProjectile(int actualX, int actualY, double vx,double vy, GamePanel gp) {
        this.actualX = actualX;
        this.actualY = actualY;
        this.vx = vx;
        this.vy = vy;
        this.speed = 2; //todo test
        this.gp = gp;
        err = new ErrorWindow();
        gp.twSetup.setUpTower();
        gp.enemyProjectile.add(this);
    }
    public void update(){
        actualX += vx;
        actualY += vy;
        if(actualX < -100 || actualY < -100 || actualX > 900 || actualY > 700){
            gp.enemyProjectileToRemove.add(this); //this adds the projectile to the arraylist used to remove it from main arraylist
        }
    }
    public void draw(Graphics g){
        Graphics2D g2d;
        g2d = (Graphics2D)g;
        BufferedImage PlayerImage = gp.twSetup.getTowerImageBullet();

        g2d.drawImage(PlayerImage,actualX,actualY,16,16,null);
    }

}
