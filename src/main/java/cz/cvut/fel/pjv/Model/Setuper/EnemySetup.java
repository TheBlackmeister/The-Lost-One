package cz.cvut.fel.pjv.Model.Setuper;

import cz.cvut.fel.pjv.View.ErrorWindow;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class EnemySetup {
    ErrorWindow err;
    BufferedImage enemyImage,enemyImageBullet;

    public EnemySetup() {
        this.err = new ErrorWindow();
    }
    public void setUpEnemy(){
        try {
            enemyImage = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/entity/player/redsoldier.png")));
            enemyImageBullet = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/entity/tower_bullet/tower_bullet.png")));
        } catch (IOException | NullPointerException e) {
            err.IOExceptionErrorHandler("Enemy Soldier Image", 5);
            throw new RuntimeException(e);
        }
    }

    public BufferedImage getEnemyImage() {
        return enemyImage;
    }

    public BufferedImage getEnemyImageBullet() {
        return enemyImageBullet;
    }
}
