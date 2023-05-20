package cz.cvut.fel.pjv.View;

import cz.cvut.fel.pjv.Controller.GamePanel;
import cz.cvut.fel.pjv.Model.Utils.Inventory;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class GUIView {
    ErrorWindow err;
    BufferedImage inventoryGUI,arrowDown, mg, pistol, rocketLauncher;
    Inventory inventory;
    GamePanel gp;
    public GUIView(Inventory inventory, GamePanel gp) {
        err = new ErrorWindow();
        this.inventory = inventory;
        this.gp = gp;
        try {
            inventoryGUI = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/inv/inventory_GUIWIcons.png")));
            arrowDown = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/gui/arrowDown.png")));
            mg = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/obj/scar.png")));
            pistol = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/obj/pistol.png")));
            rocketLauncher = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/obj/rpg45.png")));
        } catch (IOException | NullPointerException e) {
            err.IOExceptionErrorHandler("Inventory Images", 5);
            throw new RuntimeException(e);
        }
    }

    public void draw(Graphics g){
        Graphics2D g2d;
        g2d = (Graphics2D)g;
        g2d.drawImage(inventoryGUI,5, (int) (gp.getConfig().getScreenHeight()-(114*0.8)-5), (int) (400*0.8), (int) (114*0.8),null);

        int[] inv = inventory.getInv();
        if(inv[0] == 1) g2d.drawImage(pistol, 60,(int) (gp.getConfig().getScreenHeight()-(90*0.8)) , (int) (182*0.35), (int) (145*0.35), null);
        if (inv[1] == 1) g2d.drawImage(mg, 148,(int) (gp.getConfig().getScreenHeight()-(90*0.8)+5) , (int) (420*0.18), (int) (203*0.18), null);
        if (inv[2] == 1) g2d.drawImage(rocketLauncher, 240,(int) (gp.getConfig().getScreenHeight()-(115*0.8)) , (int) (368*0.24), (int) (368*0.24), null);

        g2d.drawImage(arrowDown,78+94*gp.getPlayer().getSelectedInventoryIndex(),597, (int) (arrowDown.getWidth()*0.1), (int) (arrowDown.getHeight()*0.1),null);
    }
}
