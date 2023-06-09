package cz.cvut.fel.pjv.View;

import cz.cvut.fel.pjv.Controller.GamePanel;
import cz.cvut.fel.pjv.Model.Entity.EnemySoldier;
import cz.cvut.fel.pjv.Model.Entity.Tower;
import cz.cvut.fel.pjv.Model.Map.Room;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

/**
 * class that draws the tiles, map.
 */
public class MapView {
    private int[][] map;
    GamePanel gp; // to access the tile textures and load them only once.

    public MapView(Room room, GamePanel gp) {
        this.map = room.getMap();
        this.gp = gp;
    }
    public void setRoom(Room room) {
        this.map = room.getMap();
    }

    public void update(){

    }
    public void draw(Graphics2D g2d){
        BufferedImage tileImage = null;

        for (int row = 0; row < 45; row++) {
            for (int col = 0; col < 80; col++) {

                int typeOfTile = map[row][col];

                if(typeOfTile == 0){
                    tileImage = gp.getMapsetup().getTile0();
                }
                if(typeOfTile == 1){
                    tileImage = gp.getMapsetup().getTile1();
                }
                g2d.drawImage(tileImage,col*16,row*16,16,16,null);
            }
        }
    }
}
