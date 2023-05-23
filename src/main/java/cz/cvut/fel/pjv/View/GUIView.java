package cz.cvut.fel.pjv.View;

import cz.cvut.fel.pjv.Controller.GamePanel;
import cz.cvut.fel.pjv.Model.Utils.Inventory;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;
import java.util.logging.Logger;

public class GUIView {
    private static final Logger logger = Logger.getLogger(GUIView.class.getName());
    ErrorWindow err;
    BufferedImage inventoryGUI,arrowDown, mg, pistol, rocketLauncher;
    Inventory inventory;
    GamePanel gp;
    JTextField myHealth, mySpeed, myAttackSpeed;

    public GUIView(Inventory inventory, GamePanel gp) {
        this.myHealth = new JTextField(10);
        this.mySpeed = new JTextField(10);
        this.myAttackSpeed = new JTextField(10);
        err = new ErrorWindow();
        this.inventory = inventory;
        this.gp = gp;
        try {
            logger.info("Loading GUIView images");
            inventoryGUI = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/inv/inventory_GUIWIcons.png")));
            arrowDown = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/gui/arrowDown.png")));
            mg = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/obj/scar.png")));
            pistol = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/obj/pistol.png")));
            rocketLauncher = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/obj/rpg45.png")));
        } catch (IOException | NullPointerException e) {
            logger.info("GUIView images NOT FOUND");
            err.IOExceptionErrorHandler("Inventory Images", 5);
            throw new RuntimeException(e);
        }
    }

    public void setMyHealth(String myHealth) {
        this.myHealth.setText(myHealth);
    }

    public void setMySpeed(String mySpeed) {
        this.mySpeed.setText(mySpeed);
    }

    public void setMyAttackSpeed(String myAttackSpeed) {
        this.myAttackSpeed.setText(myAttackSpeed);
    }

    public void draw(Graphics2D g2d){

        g2d.drawImage(inventoryGUI,5, (int) (gp.getConfig().getScreenHeight()-(114*0.8)-5), (int) (400*0.8), (int) (114*0.8),null);
        g2d.setColor(Color.white);
        g2d.drawString(myHealth.getText(),23,640);
        g2d.drawString(myAttackSpeed.getText(),23,663);
        g2d.drawString(mySpeed.getText(),23,686);
        int[] inv = inventory.getInv();

        if(inv[0] == 1) g2d.drawImage(pistol, 60,(int) (gp.getConfig().getScreenHeight()-(90*0.8)) , (int) (182*0.35), (int) (145*0.35), null);
        if (inv[1] == 1) g2d.drawImage(mg, 148,(int) (gp.getConfig().getScreenHeight()-(90*0.8)+5) , (int) (420*0.18), (int) (203*0.18), null);
        if (inv[2] == 1) g2d.drawImage(rocketLauncher, 240,(int) (gp.getConfig().getScreenHeight()-(115*0.8)) , (int) (368*0.24), (int) (368*0.24), null);

        g2d.drawImage(arrowDown,78+94*gp.getPlayer().getSelectedInventoryIndex(),597, (int) (arrowDown.getWidth()*0.1), (int) (arrowDown.getHeight()*0.1),null);
    }
}
