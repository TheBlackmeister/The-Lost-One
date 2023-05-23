package cz.cvut.fel.pjv.Model.Setuper;

import cz.cvut.fel.pjv.View.ErrorWindow;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;
import java.util.logging.Logger;

/**
 * ProjectileSetup is used to set up the projectile images and implement the necessary methods.
 */
public class ProjectileSetup {
    private static final Logger logger = Logger.getLogger(ProjectileSetup.class.getName());
    long canBeShotInitialTime, canBeShotInitialTimeRL;
    ErrorWindow err;
    BufferedImage projectileImageUP,projectileImageDOWN,projectileImageLEFT,projectileImageRIGHT,projectileImageUPLEFT,projectileImageUPRIGHT,projectileImageDOWNLEFT,projectileImageDOWNRIGHT;

    public ProjectileSetup(){
        err = new ErrorWindow();
    }
    public void setUpProjectile() {
        try {
            logger.fine("Loading projectile images");
            projectileImageUP = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/entity/projectile/projectiles_up.gif")));
            projectileImageDOWN = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/entity/projectile/projectiles_down.gif")));
            projectileImageLEFT = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/entity/projectile/projectiles_left.gif")));
            projectileImageRIGHT = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/entity/projectile/projectiles_right.gif")));
            projectileImageUPLEFT = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/entity/projectile/projectiles_upleft.gif")));
            projectileImageUPRIGHT = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/entity/projectile/projectiles_upright.gif")));
            projectileImageDOWNLEFT = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/entity/projectile/projectiles_downleft.gif")));
            projectileImageDOWNRIGHT = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/entity/projectile/projectiles_downright.gif")));
            // green player texture is from here https://www.deviantart.com/friendlyfirefox/art/Pimp-My-Sprite-Top-down-Soldier-467311032
            // Other colors are colored by me.

        } catch (IOException | NullPointerException e) {
            logger.severe("Projectile images not found!");
            err.IOExceptionErrorHandler("Projectile Image", 5);
            throw new RuntimeException(e);
        }
        canBeShotInitialTime = System.nanoTime();
    }
    public boolean canBeShot(long timePressed){
        // 0.8 secs
        return canBeShotInitialTime < timePressed - 800_000_000;
    }

    public boolean canBeShotMG(long timePressed){
        // 0.2 secs
        return canBeShotInitialTime < timePressed - 200_000_000;
    }

    public boolean canBeShotRL(long timePressed){
        // 2 secs
        if (canBeShotInitialTimeRL < timePressed - 2_000_000_000){
            canBeShotInitialTimeRL = timePressed;
            return true;
        }
        return false;
    }



    public BufferedImage getProjectileImageUP() {
        return projectileImageUP;
    }

    public BufferedImage getProjectileImageDOWN() {
        return projectileImageDOWN;
    }

    public BufferedImage getProjectileImageLEFT() {
        return projectileImageLEFT;
    }

    public BufferedImage getProjectileImageRIGHT() {
        return projectileImageRIGHT;
    }

    public BufferedImage getProjectileImageUPLEFT() {
        return projectileImageUPLEFT;
    }

    public BufferedImage getProjectileImageUPRIGHT() {
        return projectileImageUPRIGHT;
    }

    public BufferedImage getProjectileImageDOWNLEFT() {
        return projectileImageDOWNLEFT;
    }

    public BufferedImage getProjectileImageDOWNRIGHT() {
        return projectileImageDOWNRIGHT;
    }
}
