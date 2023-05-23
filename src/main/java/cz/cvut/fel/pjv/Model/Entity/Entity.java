package cz.cvut.fel.pjv.Model.Entity;

import cz.cvut.fel.pjv.Model.Utils.HealthBar;
import cz.cvut.fel.pjv.Model.Utils.Inventory;

/**
 * Parent class of every entity.
 */
public class Entity {
    protected int actualX;
    protected int actualY;
    protected int speed;
    protected Inventory inv;
    protected HealthBar healthBar;

    public HealthBar getHealthBar() {
        return healthBar;
    }

    public int getActualX() {
        return actualX;
    }

    public int getActualY() {
        return actualY;
    }

    public Inventory getInv() {
        return inv;
    }

    public void setActualX(int actualX) {
        this.actualX = actualX;
    }

    public void setActualY(int actualY) {
        this.actualY = actualY;
    }

    public int getSpeed() {
        return this.speed;
    }
}
