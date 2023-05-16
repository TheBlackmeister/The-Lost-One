package cz.cvut.fel.pjv.Model.Entity;

import cz.cvut.fel.pjv.Controller.CollisionEntityChecker;
import cz.cvut.fel.pjv.Controller.GamePanel;
import cz.cvut.fel.pjv.View.ErrorWindow;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class Fountain extends Entity{
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
            err.IOExceptionErrorHandler("Fountain Image", 5);
            throw new RuntimeException(e);
        }
    }
    public void update(){
        if(collEntCheck.checkIsPlayerInsideFountain(this,gp.getPlayer())){
            System.out.println("konececececec");
            //todo ending
        }
    }

    public void draw(Graphics g){
        Graphics2D g2d;
        g2d = (Graphics2D)g;
        g2d.drawImage(fountainImage,actualX,actualY,null);
    }
}
