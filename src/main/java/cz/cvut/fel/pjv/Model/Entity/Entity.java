package cz.cvut.fel.pjv.Model.Entity;

import cz.cvut.fel.pjv.Model.Utils.HealthBar;

public class Entity {
    protected int actualX;
    protected int actualY;
    protected int speed;
    protected Inventory inv;
    protected HealthBar healthBar;
    public enum Directions {
        UP, DOWN, LEFT, RIGHT, UPLEFT, DOWNLEFT, UPRIGHT, DOWNRIGHT
    }

    public HealthBar getHealthBar() {
        return healthBar;
    }

    public int getActualX() {
        return actualX;
    }

    public int getActualY() {
        return actualY;
    }

    public void setActualX(int actualX) {
        this.actualX = actualX;
    }

    public void setActualY(int actualY) {
        this.actualY = actualY;
    }
}
