package cz.cvut.fel.pjv.Model.Entity;

import cz.cvut.fel.pjv.Controller.CollisionEntityChecker;
import cz.cvut.fel.pjv.Controller.GamePanel;
import cz.cvut.fel.pjv.Model.Logic.Megamind;
import cz.cvut.fel.pjv.Model.Utils.HealthBar;
import cz.cvut.fel.pjv.View.ErrorWindow;
import cz.cvut.fel.pjv.View.HealthBarTowerUI;
import java.awt.*;
import java.awt.image.BufferedImage;

public class Tower extends Entity{
    CollisionEntityChecker collEntCheck;
    ErrorWindow err;
    GamePanel gp;
    Megamind mgm;
    long timeOfLastShot;
    HealthBarTowerUI hbTwUI;
    public Tower(int actualX, int actualY, GamePanel gp){
        this.actualX = actualX;
        this.actualY = actualY;
        this.speed = 0;
        this.timeOfLastShot = System.nanoTime();
        err = new ErrorWindow();
        this.gp = gp;
        this.mgm = new Megamind();
        gp.twSetup.setUpTower();
        this.healthBar = new HealthBar(20);
        collEntCheck = new CollisionEntityChecker(gp);
        hbTwUI = new HealthBarTowerUI(gp,this,healthBar);
        }

    public boolean towerCanBeShot(long timePressed){
        // 1 sec
        if((timePressed - 200_000_000_0) > timeOfLastShot) {
            timeOfLastShot = System.nanoTime();
            return true;
        }
        return false;
    }
    public void update(){
        hbTwUI.update(); // updates the UI healthBar
        //checking for projectiles
        if(collEntCheck.checkEntityCollisionTower(this) == 1){
            healthBar.decreaseHealth();
            if(healthBar.getHealth()<0){
                gp.towersToRemove.add(this);
            }
        }
        //checking for rockets
        if(collEntCheck.checkEntityCollisionTower(this) == 2){
            healthBar.decreaseHealthBy(16);
            if(healthBar.getHealth()<0){
                gp.towersToRemove.add(this);
            }
        }
        if (towerCanBeShot(System.nanoTime())){
            double directionValue = mgm.directionVector(actualX,actualY,gp.getPlayer().actualX,gp.getPlayer().actualY);
            EnemyProjectile newTowerProjectile = new EnemyProjectile(actualX,actualY,directionValue,gp);
        }

    }

    public void draw(Graphics g){
        Graphics2D g2d;
        g2d = (Graphics2D)g;
        BufferedImage tmpImage = gp.twSetup.getTowerImage();
        hbTwUI.draw(g);
        g2d.drawImage(tmpImage,actualX,actualY,null);

    }

    }

