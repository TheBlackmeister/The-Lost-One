package cz.cvut.fel.pjv.Model.Entity;

import cz.cvut.fel.pjv.Controller.CollisionEntityChecker;
import cz.cvut.fel.pjv.Controller.GamePanel;
import cz.cvut.fel.pjv.Model.Logic.Megamind;
import cz.cvut.fel.pjv.Model.Utils.HealthBar;
import cz.cvut.fel.pjv.Model.Utils.Sound;
import cz.cvut.fel.pjv.View.ErrorWindow;
import cz.cvut.fel.pjv.View.HealthBarTowerUI;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;
import java.util.logging.Logger;

/**
 * This class represents enemy towers throughout the map.
 */
public class Tower extends Entity{
    private static final Logger logger = Logger.getLogger(Tower.class.getName());
    Random random;
    Sound shoot;
    CollisionEntityChecker collEntCheck;
    ErrorWindow err;
    GamePanel gp;
    Megamind mgm;
    long timeOfLastShot;
    HealthBarTowerUI hbTwUI;
    public Tower(int actualX, int actualY, GamePanel gp){
        random = new Random();
        //to make a sound
        shoot = new Sound();
        //coords
        this.actualX = actualX;
        this.actualY = actualY;
        // towers don't move
        this.speed = 0;
        this.timeOfLastShot = System.nanoTime();
        //err for errors
        err = new ErrorWindow();
        this.gp = gp;
        this.mgm = new Megamind();
        // set ups textures etc.
        gp.twSetup.setUpTower();
        // healthBar set up
        this.healthBar = new HealthBar(20);
        collEntCheck = new CollisionEntityChecker(gp);
        hbTwUI = new HealthBarTowerUI(gp,this,healthBar);
        }

    /**
     * a function to check if a tower can shoot again.
     * @param timePressed time of the call of a function
     * @return boolean true if the tower can shoot.
     */
    public boolean towerCanBeShot(long timePressed){
        // 1 sec
        if((timePressed - 200_000_000_0) > timeOfLastShot) {
            timeOfLastShot = System.nanoTime();
            return true;
        }
        return false;
    }

    /**
     * updates the tower from main thread.
     */
    public void update(){
        hbTwUI.update(); // updates the UI healthBar
        //checking for projectiles
        if(collEntCheck.checkEntityCollisionTower(this) == 1){
            healthBar.decreaseHealth();
            if(healthBar.getHealth()<0){
                logger.info("A tower has been destroyed.");
                if(random.nextBoolean()) gp.objGuns.add(new ObjGun(actualX, actualY, random.nextInt(3), gp));
                Sound sound = new Sound();
                sound.setFile(15);
                sound.play();
                gp.towersToRemove.add(this);
            }
        }
        //checking for rockets
        if(collEntCheck.checkEntityCollisionTower(this) == 2){
            healthBar.decreaseHealthBy(16);
            if(healthBar.getHealth()<0){
                logger.info("A tower has been destroyed.");
                if(random.nextBoolean()) gp.objGuns.add(new ObjGun(actualX, actualY, random.nextInt(3), gp));
                Sound sound = new Sound();
                sound.setFile(15);
                sound.play();
                gp.towersToRemove.add(this);
            }
        }
        if (towerCanBeShot(System.nanoTime())){
            double directionValue = mgm.directionVector(actualX,actualY,gp.getPlayer().actualX,gp.getPlayer().actualY);
            shoot.setFile(4);
            shoot.play();
            new EnemyProjectile(actualX,actualY,directionValue,gp);

        }

    }

    /**
     * draws towers from the main thread
     * @param g2d graphics
     */
    public void draw(Graphics2D g2d){
        BufferedImage tmpImage = gp.twSetup.getTowerImage();
        hbTwUI.draw(g2d);
        g2d.drawImage(tmpImage,actualX,actualY,null);
    }
}

