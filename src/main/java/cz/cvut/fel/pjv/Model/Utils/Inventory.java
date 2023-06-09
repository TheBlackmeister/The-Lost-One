package cz.cvut.fel.pjv.Model.Utils;

import cz.cvut.fel.pjv.Controller.GamePanel;
import cz.cvut.fel.pjv.View.ErrorWindow;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;
import java.util.logging.Logger;

/**
 * Holds the inventory of the player.
 * Uses 3 indexes to hold 3 different guns.
 * 0 == pistol
 * 1 == machine gun
 * 2 == rocket launcher
 */
public class Inventory {
    private static final Logger logger = Logger.getLogger(Inventory.class.getName());
    ErrorWindow err;
    BufferedImage inventoryGUIImage;
    private int[] inv;
    GamePanel gp;

    public Inventory(GamePanel gp){
        this.gp = gp;
        err = new ErrorWindow();
        try {
            logger.info("Loading inventory image");
            inventoryGUIImage = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/inv/inventory_GUIWIcons.png")));
        } catch (IOException | NullPointerException e) {
            logger.severe("Inventory images not found!");
            err.IOExceptionErrorHandler("Inventory Image", 5);
            throw new RuntimeException(e);
        }
        inv = new int[]{gp.getMapsetup().getFirstInvIndex(), gp.getMapsetup().getSecondInvIndex(), gp.getMapsetup().getThirdInvIndex()}; // player has a pistol by default
    }

    public int[] getInv() {
        return inv;
    }


    public boolean addIntoInventory(int index){

        if(inv[index] >= 1) {
            logger.warning("Player already has the item!");
            return false;
        }
        else {
            if(inv[0] == 0 && inv[1]  == 0 && inv[2] == 0) gp.getPlayer().setSelectedInventoryIndex(index);
            inv[index] = 1;
            logger.info("Item added to the inventory.");
            return true;
        }
    }

    /**
     * removes gun from inventory
     * @param index gun to be dropped
     * @return true if player had the gun, else returns false
     */
    public boolean dropTheGun(int index){
      if(inv[index] == 1){
          inv[index] = 0;
          return true;
      } return false;
    }



}
