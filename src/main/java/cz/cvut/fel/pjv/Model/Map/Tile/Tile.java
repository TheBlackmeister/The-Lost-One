package cz.cvut.fel.pjv.Model.Map.Tile;

import java.awt.image.BufferedImage;

/**
 * to be deleted
 */
public class Tile {
    BufferedImage image;
    private int tileX;
    private int tileY;
    private final int tileSize;
    private boolean solid;

    public Tile(BufferedImage image) {
        this.image = image;
        this.solid = false;
        tileSize = 16;
    }



    public boolean isPassable() {
        return this.solid;
    }

    public BufferedImage getImage() {
        return image;
    }

    public void setSolid(boolean solid) {
        this.solid = solid;
    }
}

