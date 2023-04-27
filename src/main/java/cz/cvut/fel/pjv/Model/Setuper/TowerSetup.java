package cz.cvut.fel.pjv.Model.Setuper;

import cz.cvut.fel.pjv.View.ErrorWindow;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class TowerSetup {
    long canBeShotInitialTime;
    ErrorWindow err;
    BufferedImage towerImage,towerImageBullet;
    public TowerSetup(){
        err = new ErrorWindow();
    }

    public void setUpTower() {
        try {
            towerImage = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/entity/tower/tower.png")));
            towerImageBullet = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/entity/tower_bullet/tower_bullet.png")));
        } catch (IOException | NullPointerException e) {
            err.IOExceptionErrorHandler("Tower Image", 5);
            throw new RuntimeException(e);
        }
        canBeShotInitialTime = System.nanoTime();
    }
    public boolean towerCanBeShot(long timePressed){
        // 1 sec

        return canBeShotInitialTime < (timePressed - 1_000_000_000);
    }


    public BufferedImage getTowerImage() {
        return towerImage;
    }

    public BufferedImage getTowerImageBullet() {
        return towerImageBullet;
    }
}
