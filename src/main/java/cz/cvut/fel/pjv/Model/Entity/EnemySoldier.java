package cz.cvut.fel.pjv.Model.Entity;

import cz.cvut.fel.pjv.Controller.*;
import cz.cvut.fel.pjv.Model.Logic.Megamind;
import cz.cvut.fel.pjv.Model.Utils.HealthBar;
import cz.cvut.fel.pjv.Model.Utils.Sound;
import cz.cvut.fel.pjv.View.ErrorWindow;
import cz.cvut.fel.pjv.View.HealthBarEnemyUI;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.util.Random;
import java.util.logging.Logger;

import static java.lang.Math.cos;
import static java.lang.Math.sin;

/**
 * EnemySoldier entity.
 */
public class EnemySoldier extends Entity{
    private static final Logger logger = Logger.getLogger(EnemySoldier.class.getName());
    Sound shoot = new Sound();
    Random random;
    CollisionTileChecker collTileCheck;
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
        random = new Random();
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
        collTileCheck = new CollisionTileChecker(gp);
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
        //checking for projectiles
        if(collEntCheck.checkEntityCollisionEnemy(this) == 1){
            healthBar.decreaseHealth();
            if(healthBar.getHealth()<0){
                logger.info("Soldier has died");
                if(random.nextBoolean()) gp.objGuns.add(new ObjGun(actualX, actualY, random.nextInt(3), gp));
                Sound sound = new Sound();
                sound.setFile(random.nextInt(4) + 16);
                sound.play();
                gp.enemySoldiersToRemove.add(this);
            }
        }
        //checking for rockets
        if(collEntCheck.checkEntityCollisionEnemy(this) == 2){
            healthBar.decreaseHealthBy(16);
            if(healthBar.getHealth()<0){
                logger.info("Soldier has died");
                if(random.nextBoolean()) gp.objGuns.add(new ObjGun(actualX, actualY, random.nextInt(3), gp));
                Sound sound = new Sound();
                sound.setFile(random.nextInt(4) + 16);
                sound.play();
                gp.enemySoldiersToRemove.add(this);
            }
        }
        directionValue = mgm.directionVector(actualX,actualY,gp.getPlayer().actualX,gp.getPlayer().actualY);
        if (enemyCanBeShot(System.nanoTime())){
            shoot.setFile(1);
            shoot.play();
            new EnemyProjectile(actualX,actualY,directionValue,gp);
        }
        tmpX += cos(directionValue) * speed;
        tmpY += sin(directionValue) * speed;
        actualX = (int) tmpX;
        actualY = (int) tmpY;

        if(collTileCheck.checkTileCollisionEnemy(this)){
            tmpX -= cos(directionValue) * speed;
            tmpY -= sin(directionValue) * speed;
            actualX = (int) tmpX;
            actualY = (int) tmpY;
        }
    }

    public void draw(Graphics2D g2d){
        BufferedImage origImage = gp.enSetup.getEnemyImage();
        int cx = origImage.getWidth() / 2;
        int cy = origImage.getHeight() / 2;
        // Create a transform that translates to the center and then rotates
        AffineTransform rotationTransform = new AffineTransform();
        rotationTransform.translate(tmpX, tmpY);
        rotationTransform.rotate(directionValue, cx, cy);

        hbEnUI.draw(g2d);
        g2d.drawImage(origImage,rotationTransform,null);
    }
}
