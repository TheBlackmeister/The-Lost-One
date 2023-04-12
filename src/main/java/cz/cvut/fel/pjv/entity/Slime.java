package cz.cvut.fel.pjv.entity;

import cz.cvut.fel.pjv.Controller.GamePanel;
import cz.cvut.fel.pjv.Controller.KeyHandler;

/**
 * class slime represents a monster
 */
public class Slime extends Entity{
    GamePanel gp;
    KeyHandler keyHand;
    public final int screenX;
    public final int screenY;

    public Slime(GamePanel gp, KeyHandler keyHand) {
        this.gp = gp;
        this.keyHand = keyHand;
        screenX = gp.getScreenWidth()/2 - (gp.getTileSize()/2);
        screenY = gp.getScreenHeight()/2 - (gp.getTileSize()/2);
    }
}
