package cz.cvut.fel.pjv.Model.Entity;

import cz.cvut.fel.pjv.Controller.GamePanel;
import cz.cvut.fel.pjv.Model.Logic.Megamind;
import cz.cvut.fel.pjv.View.ErrorWindow;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.Objects;

public class Tower extends Entity{
    ErrorWindow err;
    GamePanel gp;
    Megamind mgm;
    Tower(int actualX, int actualY,GamePanel gp){
        this.actualX = actualX;
        this.actualY = actualY;
        this.speed = 0;
        err = new ErrorWindow();
        this.gp = gp;
        this.mgm = new Megamind();
        gp.twSetup.setUpTower();
        gp.towers.add(this);
        }


    public void update(){

            if (gp.twSetup.towerCanBeShot(System.nanoTime())){
                double[] directionValues = mgm.directionVector(actualX,actualY,gp.getPlayer().actualX,gp.getPlayer().actualY);
                EnemyProjectile newTowerProjectile = new EnemyProjectile(actualX,actualY,directionValues[0],directionValues[1],gp); //todo
                 //todo
//                System.out.println(""); //testing
            }


    }
    public void draw(Graphics g){
        Graphics2D g2d;
        g2d = (Graphics2D)g;
        BufferedImage tmpImage = gp.twSetup.getTowerImage();

        g2d.drawImage(tmpImage,actualX,actualY,null);

    }

    }

