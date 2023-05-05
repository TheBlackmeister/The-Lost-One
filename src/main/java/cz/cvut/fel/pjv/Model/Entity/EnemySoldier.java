package cz.cvut.fel.pjv.Model.Entity;

import cz.cvut.fel.pjv.Controller.CollisionEntityChecker;
import cz.cvut.fel.pjv.Controller.GamePanel;
import cz.cvut.fel.pjv.Model.Logic.Megamind;
import cz.cvut.fel.pjv.Model.Utils.HealthBar;
import cz.cvut.fel.pjv.View.ErrorWindow;
import cz.cvut.fel.pjv.View.HealthBarEnemyUI;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

import static java.lang.Math.cos;
import static java.lang.Math.sin;

public class EnemySoldier extends Entity{
    CollisionEntityChecker collEntCheck;
    ErrorWindow err;
    GamePanel gp;
    Megamind mgm;
    long timeOfLastShot;
    HealthBarEnemyUI hbEnUI;
    double tmpX;
    double tmpY;
    double directionValue;
    public EnemySoldier(int actualX, int actualY, GamePanel gp){
        this.actualX = actualX;
        this.actualY = actualY;
        tmpX = actualX;
        tmpY = actualY;
        this.speed = 1;
        this.timeOfLastShot = System.nanoTime();
        err = new ErrorWindow();
        this.gp = gp;
        this.mgm = new Megamind();
        gp.enSetup.setUpEnemy();

        this.healthBar = new HealthBar(10);
        collEntCheck = new CollisionEntityChecker(gp);
        hbEnUI = new HealthBarEnemyUI(gp,this,healthBar);
    }

    public boolean enemyCanBeShot(long timePressed){
        // 1 sec
        if((timePressed - 100_000_000_0) > timeOfLastShot) {
            timeOfLastShot = System.nanoTime();
            return true;
        }
        return false;
    }

    public void update(){
        hbEnUI.update(); // updates the UI healthBar
        if(collEntCheck.checkEntityCollisionEnemy(this)){
            healthBar.decreaseHealth();
            if(healthBar.getHealth()<0){
                gp.enemySoldiersToRemove.add(this);
            }
        }
        directionValue = mgm.directionVector(actualX,actualY,gp.getPlayer().actualX,gp.getPlayer().actualY);
        if (enemyCanBeShot(System.nanoTime())){
            EnemyProjectile newEnemyProjectile = new EnemyProjectile(actualX,actualY,directionValue,gp);
        }
        tmpX += cos(directionValue) * speed;
        tmpY += sin(directionValue) * speed;
        actualX = (int) tmpX;
        actualY = (int) tmpY;
    }

    public void draw(Graphics g){
        Graphics2D g2d;
        g2d = (Graphics2D)g;
        BufferedImage origImage = gp.enSetup.getEnemyImage();
        int cx = origImage.getWidth() / 2;
        int cy = origImage.getHeight() / 2;
        // Create a transform that translates to the center and then rotates
        AffineTransform rotationTransform = new AffineTransform();
        rotationTransform.translate(tmpX, tmpY);
        rotationTransform.rotate(directionValue, cx, cy);

        hbEnUI.draw(g);
        g2d.drawImage(origImage,rotationTransform,null);
    }
}
