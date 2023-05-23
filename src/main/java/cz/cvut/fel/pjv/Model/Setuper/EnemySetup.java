package cz.cvut.fel.pjv.Model.Setuper;

import cz.cvut.fel.pjv.View.ErrorWindow;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;
import java.util.logging.Logger;

/**
 * setups the enemy textures so they load only once.
 */
public class EnemySetup {
    private static final Logger logger = Logger.getLogger(EnemySetup.class.getName());
    ErrorWindow err;
    BufferedImage enemyImage,enemyImageBullet;

    public EnemySetup() {
        this.err = new ErrorWindow();
    }
    public void setUpEnemy(){
        try {
            logger.info("Loading enemy soldier images");
            enemyImage = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/entity/player/redsoldier.png")));
            enemyImageBullet = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/entity/tower_bullet/tower_bullet.png")));
        } catch (IOException | NullPointerException e) {
            logger.severe("Enemy soldier image not found!");
            err.IOExceptionErrorHandler("Enemy Soldier Image", 5);
            throw new RuntimeException(e);
        }
    }

    public BufferedImage getEnemyImage() {
        return enemyImage;
    }
}
