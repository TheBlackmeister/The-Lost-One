package cz.cvut.fel.pjv.Model.Setuper;

import cz.cvut.fel.pjv.View.ErrorWindow;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;
import java.util.logging.Logger;

/**
 * setups the tower textures so they load only once.
 */
public class TowerSetup {
    private static final Logger logger = Logger.getLogger(TowerSetup.class.getName());
    ErrorWindow err;
    BufferedImage towerImage,towerImageBullet;
    public TowerSetup(){
        err = new ErrorWindow();
    }

    public void setUpTower() {
        try {
            logger.fine("Loading the tower or new bullet images");
            towerImage = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/entity/tower/tower.png")));
            towerImageBullet = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/entity/tower_bullet/tower_bullet.png")));
        } catch (IOException | NullPointerException e) {
            logger.severe("Tower images not found!");
            err.IOExceptionErrorHandler("Tower Image", 5);
            throw new RuntimeException(e);
        }
    }



    public BufferedImage getTowerImage() {
        return towerImage;
    }

    public BufferedImage getTowerImageBullet() {
        return towerImageBullet;
    }
}
