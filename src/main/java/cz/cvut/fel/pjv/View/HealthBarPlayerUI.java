package cz.cvut.fel.pjv.View;

import cz.cvut.fel.pjv.Controller.GamePanel;
import cz.cvut.fel.pjv.Model.Utils.HealthBar;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;
import java.util.logging.Logger;

public class HealthBarPlayerUI {
    private static final Logger logger = Logger.getLogger(HealthBarPlayerUI.class.getName());
    GamePanel gp;
    HealthBar hb;
    ErrorWindow err;
    int maxHealth;
    float currentHealth;
    float percentage;
    BufferedImage healthBar,heart;
    public HealthBarPlayerUI(GamePanel gp, HealthBar hb) {
        this.gp = gp;
        this.hb = hb;
        err = new ErrorWindow();
        maxHealth = hb.getMaxHealth();
        currentHealth = hb.getHealth();
        percentage = currentHealth / maxHealth;
        try {
            logger.info("Loading Healthbar images");
            healthBar = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/gui/healthBar.png")));
            heart = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/gui/heart.png")));
        } catch (IOException | NullPointerException e) {
            logger.info("HealthBar images NOT FOUND!");
            err.IOExceptionErrorHandler("GUI Images", 5);
            throw new RuntimeException(e);
        }
    }
    public void update(){
        currentHealth = gp.getPlayer().getHealthBar().getHealth();
        percentage = currentHealth/maxHealth;

    }
    public void draw(Graphics g){
        g.setColor(Color.green);
        g.fillRect(32,16,(int) (percentage*100),16);
        g.drawImage(healthBar,32,16,null);
        g.drawImage(heart,8,14,null);

    }
}
