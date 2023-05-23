package cz.cvut.fel.pjv.Model.Entity;

import cz.cvut.fel.pjv.Controller.GamePanel;
import cz.cvut.fel.pjv.View.ErrorWindow;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;
import java.util.logging.Logger;

public class ObjGun extends Entity{
    private static final Logger logger = Logger.getLogger(ObjGun.class.getName());
    GamePanel gp;
    int type;
    BufferedImage mg,rl,pistol;
    ErrorWindow err;
    public ObjGun(int X, int Y, int type, GamePanel gp) {
        this.gp = gp;
        actualX = X;
        actualY = Y;
        err = new ErrorWindow();
        this.type = type;

        try {
            logger.info("Loading Gun images");
            mg = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/obj/scar.png")));
            pistol = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/obj/pistol.png")));
            rl = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/obj/rpg45.png")));
        } catch (IOException | NullPointerException e) {
            logger.info("Gun images not found!");
            err.IOExceptionErrorHandler("Gun Images", 5);
            throw new RuntimeException(e);
        }
    }
    public void update(){
        if(gp.getPlayer().keyList.isiPressed() && type != -1 && new Rectangle(actualX,actualY,32,32).intersects(new Rectangle(gp.getPlayer().actualX,gp.getPlayer().actualY,32,32))){
            if(gp.getPlayer().getInv().addIntoInventory(type)) {
                type = -1;
                gp.objGunsToRemove.add(this);
            }
        }
    }
    public void draw(Graphics2D g2d){
        switch (type){
            case 0 -> g2d.drawImage(pistol,actualX,actualY,32,25, null);
            case 1 -> g2d.drawImage(mg,actualX,actualY,32,15, null);
            case 2 -> g2d.drawImage(rl,actualX,actualY,32,32, null);
            default -> {}
        }
    }
}
