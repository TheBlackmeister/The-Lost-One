package cz.cvut.fel.pjv.Model.Entity;

public class Entity {
    protected int actualX;
    protected int actualY;
    protected int speed;
    protected Inventory inv;
    public enum Directions {
        UP, DOWN, LEFT, RIGHT, UPLEFT, DOWNLEFT, UPRIGHT, DOWNRIGHT
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
