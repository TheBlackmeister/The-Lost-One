package cz.cvut.fel.pjv.Model.Entity;

import cz.cvut.fel.pjv.Controller.GamePanel;
import cz.cvut.fel.pjv.Model.Utils.Sound;
import cz.cvut.fel.pjv.View.ErrorWindow;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;
import java.util.logging.Logger;

/**
 * Rocket fired from rocket launcher by the player.
 * The rocket is programmed so that it explodes on contact with enemy soldier or tower or after a time delay.
 * the rocket then deals damage in the explosion area for 0.1 secs.
 */
public class Rocket extends Entity{
    private static final Logger logger = Logger.getLogger(Rocket.class.getName());
    Sound explosion;
    GamePanel gp;
    ErrorWindow err;
    DirectionsEnum.Directions direction;
    long waitingTillExplosion;
    long afterExplosion;
    BufferedImage rocket,rocketBlast;
    boolean deleted;

    public Rocket(int X, int Y, GamePanel gp, DirectionsEnum.Directions direction) {
        this.err = new ErrorWindow();
        // 1 second until explosion
        waitingTillExplosion = System.nanoTime() + 1_000_000_000;
        explosion = new Sound();
        afterExplosion = 0;
        deleted = false;
        this.gp = gp;
        this.direction = direction;
        this.speed = 5;
        this.actualX = X;
        this.actualY = Y;
        try {
            logger.info("Loading rocket textures");
            rocket = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/entity/tower_bullet/tower_bullet.png")));
            rocketBlast = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/entity/projectile/rocket/rocketBlast.png")));
        } catch (IOException | NullPointerException e) {
            logger.severe("Rocket images NOT FOUND! Error: " + e);
            err.IOExceptionErrorHandler("Tower Image", 5);
            throw new RuntimeException(e);
        }
        gp.rockets.add(this);
    }

    /**
     * @return interval in which the rocket is exploding
     */
    public long getAfterExplosion() {
        return afterExplosion;
    }

    public boolean isDeleted() {
        return deleted;
    }

    /**
     * It plays explosion sound, then sets the afterExplosion time - 0.1 sec
     * that is the time for which the rocket deals damage in the explosion area.
     */
    void kaboom(){
        explosion.setFile(11);
        explosion.play();
        afterExplosion = System.nanoTime() + 100_000_000; //0.1 sec
        logger.info("rocket exploded");

    }
    public void update(){
        if(afterExplosion == 0) {
            if (System.nanoTime() > waitingTillExplosion) {
                kaboom();
            }
            Rectangle rectRocket = new Rectangle(actualX,actualY,16,16);
            for(Tower tower:gp.towers){
                Rectangle rectT = new Rectangle(tower.getActualX(),tower.getActualY(),32,32);
                if(rectRocket.intersects(rectT)) kaboom();
            }
            for(EnemySoldier enemySoldier:gp.enemySoldiers){
                Rectangle rectP = new Rectangle(enemySoldier.getActualX(),enemySoldier.getActualY(),32,32);
                if(rectRocket.intersects(rectP)) kaboom();
            }

            if (direction == DirectionsEnum.Directions.UP) {
                actualY -= speed;
            }
            if (direction == DirectionsEnum.Directions.RIGHT) {
                actualX += speed;
            }
            if (direction == DirectionsEnum.Directions.DOWN) {
                actualY += speed;
            }
            if (direction == DirectionsEnum.Directions.LEFT) {
                actualX -= speed;
            }
            if (direction == DirectionsEnum.Directions.DOWNLEFT) {
                actualY += speed;
                actualX -= speed;
            }
            if (direction == DirectionsEnum.Directions.UPLEFT) {
                actualY -= speed;
                actualX -= speed;
            }
            if (direction == DirectionsEnum.Directions.UPRIGHT) {
                actualY -= speed;
                actualX += speed;
            }
            if (direction == DirectionsEnum.Directions.DOWNRIGHT) {
                actualY += speed;
                actualX += speed;
            }
        }
        //rocket exploded and dealt damage for 0.1 sec after explosion.
        if(afterExplosion != 0 && afterExplosion < System.nanoTime() && !deleted) {
            gp.rocketsToRemove.add(this);
            deleted = true;
        }
    }
    public void draw(Graphics2D g2d){
        if(afterExplosion == 0) g2d.drawImage(rocket,actualX,actualY,16,16,null);
        if(afterExplosion != 0 && afterExplosion > System.nanoTime()) g2d.drawImage(rocketBlast,actualX-50,actualY-50,100,100,null);
    }
}
