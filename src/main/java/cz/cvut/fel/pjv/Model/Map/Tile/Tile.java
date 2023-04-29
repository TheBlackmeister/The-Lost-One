package cz.cvut.fel.pjv.Model.Map.Tile;

public class Tile {
    private int tileX;
    private int tileY;
    private int tileSize;
    private boolean solid;

    public Tile(int x, int y) {
        this.tileX = x;
        this.tileY = y;
        this.solid = false;
        tileSize = 16;
    }

    public int getX() {
        return this.tileX;
    }

    public int getY() {
        return this.tileY;
    }

    public boolean isPassable() {
        return this.solid;
    }

    public void setPassable(boolean solid) {
        this.solid = solid;
    }
}
