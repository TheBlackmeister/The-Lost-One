package cz.cvut.fel.pjv.Model.Entity;

import cz.cvut.fel.pjv.Controller.CollisionEntityChecker;
import cz.cvut.fel.pjv.Controller.GamePanel;
import cz.cvut.fel.pjv.Model.Utils.Sound;
import cz.cvut.fel.pjv.View.ErrorWindow;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;
import java.util.logging.Logger;

public class Fountain extends Entity{
    private static final Logger logger = Logger.getLogger(Fountain.class.getName());
    CollisionEntityChecker collEntCheck;
    ErrorWindow err;
    BufferedImage fountainImage;
    GamePanel gp;
    public Fountain(int X, int Y, GamePanel gp) {
        this.actualX = X;
        this.actualY = Y;
        this.speed = 0;
        err = new ErrorWindow();
        this.gp = gp;
        collEntCheck = new CollisionEntityChecker(this.gp);
        setUpFountain();
    }
    public void setUpFountain(){
        try {
            fountainImage = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/entity/fountain/fountain.png")));
        } catch (IOException | NullPointerException e) {
            logger.severe("Fountain Image NOT FOUND!");
            err.IOExceptionErrorHandler("Fountain Image", 5);
            throw new RuntimeException(e);
        }
    }
    public void update(){
        if(collEntCheck.checkIsPlayerInsideFountain(this,gp.getPlayer())){
            Sound sound = new Sound();
            Sound applause = new Sound();
            applause.setFile(14);
            applause.play();
            sound.setFile(13);
            sound.play();
            JOptionPane.showMessageDialog(null,"Congratulations! You completed the level!");
            logger.info("Game ended, player won.");
            gp.exitGame();
        }
    }

    public void draw(Graphics2D g2d){
        g2d.drawImage(fountainImage,actualX,actualY,null);
    }
}
