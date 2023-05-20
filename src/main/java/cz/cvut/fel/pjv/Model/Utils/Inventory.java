package cz.cvut.fel.pjv.Model.Utils;

import cz.cvut.fel.pjv.View.ErrorWindow;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class Inventory {
    ErrorWindow err;
    BufferedImage inventoryGUIImage;
    private int[] inv;

    public Inventory(){
        err = new ErrorWindow();
        try {
            inventoryGUIImage = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/inv/inventory_GUIWIcons.png")));
        } catch (IOException | NullPointerException e) {
            err.IOExceptionErrorHandler("Inventory Image", 5);
            throw new RuntimeException(e);
        }
        inv = new int[]{1, 1, 0}; // player has a pistol by default
    }

    public int[] getInv() {
        return inv;
    }


    public boolean addIntoInventory(int index){
        if(inv[index] >= 1) return false;
        else {
            inv[index] = 1;
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
