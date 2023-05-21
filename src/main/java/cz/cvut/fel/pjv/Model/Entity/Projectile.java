package cz.cvut.fel.pjv.Model.Entity;

import cz.cvut.fel.pjv.Controller.GamePanel;
import cz.cvut.fel.pjv.View.ErrorWindow;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Projectile extends Entity {
    GamePanel gp;
    ErrorWindow err;
    DirectionsEnum.Directions direction;
    public Projectile(int actualX, int actualY, DirectionsEnum.Directions direction, GamePanel gp) {
        this.actualX = actualX;
        this.actualY = actualY;
        this.direction = direction;
        this.speed = 10;
        this.gp = gp;
        err = new ErrorWindow();
        gp.prSetup.setUpProjectile();
        gp.projectile.add(this);
    }

    public void update(){
        switch (direction){
            case DOWN -> actualY += speed;
            case UP -> actualY -= speed;
            case LEFT -> actualX -= speed;
            case RIGHT -> actualX += speed;
            case UPLEFT -> {actualX -= speed/2; actualY -= speed/2;}
            case UPRIGHT -> {actualX += speed/2; actualY -= speed/2;}
            case DOWNLEFT -> {actualX -= speed/2; actualY += speed/2;}
            case DOWNRIGHT -> {actualX += speed/2; actualY += speed/2;}
        }

        if(actualX < -100 || actualY < -100 || actualX > gp.getConfig().getScreenWidth()+100 || actualY > gp.getConfig().getScreenHeight()+100){
            gp.toRemove.add(this); //this adds the projectile to the arraylist used to remove it from main arraylist
        }


    }
    public void draw(Graphics g){
        Graphics2D g2d;
        g2d = (Graphics2D)g;
        BufferedImage PlayerImage = null;

        switch (direction){
            case DOWN -> PlayerImage = gp.prSetup.getProjectileImageDOWN();
            case UP -> PlayerImage = gp.prSetup.getProjectileImageUP();
            case LEFT -> PlayerImage = gp.prSetup.getProjectileImageLEFT();
            case RIGHT -> PlayerImage = gp.prSetup.getProjectileImageRIGHT();
            case UPLEFT -> PlayerImage = gp.prSetup.getProjectileImageUPLEFT();
            case DOWNLEFT -> PlayerImage = gp.prSetup.getProjectileImageDOWNLEFT();
            case UPRIGHT -> PlayerImage = gp.prSetup.getProjectileImageUPRIGHT();
            case DOWNRIGHT -> PlayerImage = gp.prSetup.getProjectileImageDOWNRIGHT();
        }

        g2d.drawImage(PlayerImage,actualX,actualY,null);

    }

}
