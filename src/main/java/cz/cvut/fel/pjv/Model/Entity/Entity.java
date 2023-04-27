package cz.cvut.fel.pjv.Model.Entity;

public class Entity {
    protected int actualX;
    protected int actualY;
    protected int speed;
    protected Inventory inv;
    public enum Directions {
        UP, DOWN, LEFT, RIGHT, UPLEFT, DOWNLEFT, UPRIGHT, DOWNRIGHT
    }
}
