package cz.cvut.fel.pjv.Model.Entity;

import cz.cvut.fel.pjv.View.ErrorWindow;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class Projectile extends Entity {

    ProjectileDirections direction;
    public Projectile(int actualX, int actualY,ProjectileDirections direction) {
        this.actualX = actualX;
        this.actualY = actualY;
        this.direction = direction;
        this.speed = 30;
    }
    public enum ProjectileDirections {
        UP, DOWN, LEFT, RIGHT, UPLEFT, DOWNLEFT, UPRIGHT, DOWNRIGHT
    }
    ErrorWindow err;
    BufferedImage projectileImageUP,projectileImageDOWN,projectileImageLEFT,projectileImageRIGHT,projectileImageUPLEFT,projectileImageUPRIGHT,projectileImageDOWNLEFT,projectileImageDOWNRIGHT;


    public void setUpProjectile() {
        try {
            projectileImageUP = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/entity/projectile/projectiles_up.gif")));
            projectileImageDOWN = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/entity/player/projectiles_down.gif")));
            projectileImageLEFT = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/entity/player/projectiles_left.gif")));
            projectileImageRIGHT = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/entity/player/projectiles_right.gif")));
            projectileImageUPLEFT = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/entity/player/projectiles_upleft.gif")));
            projectileImageUPRIGHT = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/entity/player/projectiles_upright.gif")));
            projectileImageDOWNLEFT = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/entity/player/projectiles_downleft.gif")));
            projectileImageDOWNRIGHT = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/entity/player/projectiles_downright.gif")));
            // green player texture is from here https://www.deviantart.com/friendlyfirefox/art/Pimp-My-Sprite-Top-down-Soldier-467311032
            // Other colors are colored by me.
            direction = Projectile.ProjectileDirections.DOWN;
        } catch (IOException | NullPointerException e) {
            err.IOExceptionErrorHandler("Projectile Image", 5);
            throw new RuntimeException(e);
        }
    }
//    public void newProjectile(int actualX, int actualY, ProjectileDirections direction){
//
//    }
    public void update(){
        System.out.println("test");
        switch (direction){
            case DOWN -> actualY += speed;
            case UP -> actualY -= speed;
            case LEFT -> actualX -= speed;
            case RIGHT -> actualX += speed;
            case UPLEFT -> {actualX -= speed/2; actualY -= speed/2;}
            case UPRIGHT -> {actualX += speed/2; actualY -= speed/2;}
            case DOWNLEFT -> {actualX -= speed/2; actualY += speed/2;}
            case DOWNRIGHT -> {actualX += speed/2; actualY += speed/2;}

        }

    }
    public void draw(Graphics g){
        Graphics2D g2d;
        g2d = (Graphics2D)g;
        BufferedImage PlayerImage = null;

        switch (direction){
            case DOWN -> PlayerImage = projectileImageDOWN;
            case UP -> PlayerImage = projectileImageUP;
            case LEFT -> PlayerImage = projectileImageLEFT;
            case RIGHT -> PlayerImage = projectileImageRIGHT;
            case UPLEFT -> PlayerImage = projectileImageUPLEFT;
            case DOWNLEFT -> PlayerImage = projectileImageDOWNLEFT;
            case UPRIGHT -> PlayerImage = projectileImageUPRIGHT;
            case DOWNRIGHT -> PlayerImage = projectileImageDOWNRIGHT;
        }

        g2d.drawImage(PlayerImage,actualX,actualY,null);

    }

}
