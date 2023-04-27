package cz.cvut.fel.pjv.Model.Setuper;

import cz.cvut.fel.pjv.View.ErrorWindow;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

/**
 * ProjectileSetup is used to setup the projectile images and implement the necessary methods.
 */
public class ProjectileSetup {

    long canBeShotInitialTime;
    ErrorWindow err;
    BufferedImage projectileImageUP,projectileImageDOWN,projectileImageLEFT,projectileImageRIGHT,projectileImageUPLEFT,projectileImageUPRIGHT,projectileImageDOWNLEFT,projectileImageDOWNRIGHT;

    public ProjectileSetup(){
        err = new ErrorWindow();
    }
    public void setUpProjectile() {
        try {
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
            err.IOExceptionErrorHandler("Projectile Image", 5);
            throw new RuntimeException(e);
        }
        canBeShotInitialTime = System.nanoTime();
    }
    public boolean canBeShot(long timePressed){
        if (canBeShotInitialTime < timePressed - 500_000_000){ // 0.5 secs
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