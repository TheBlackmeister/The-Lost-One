package cz.cvut.fel.pjv.View;

import cz.cvut.fel.pjv.Model.Entity.EnemySoldier;
import cz.cvut.fel.pjv.Model.Entity.Tower;
import cz.cvut.fel.pjv.Model.Map.Room;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class MapView {
    public enum RoomType {
        CLOSED,UP,LEFT,RIGHT,DOWN
    }
    private ArrayList<EnemySoldier> enemies;
    private ArrayList<Tower> towers;
    private RoomType roomType;
    private int[][] map;

    public MapView(ArrayList<EnemySoldier> enemies, ArrayList<Tower> towers, RoomType roomType, int[][] map) {
        this.enemies = enemies;
        this.towers = towers;
        this.roomType = roomType;
        this.map = map;
    }
    public void setMap(int[][] map) {
        this.map = map;
    }

    public void update(){

    }
    public void draw(Graphics g){
        Graphics2D g2d;
        g2d = (Graphics2D)g;
        BufferedImage PlayerImage = null;

        for (int row = 0; row < 45; row++) {
            for (int col = 0; col < 80; col++) {

                int typeOfTile = map[row][col];

                if(typeOfTile == 0){
                    g2d.setColor(Color.green);
                }
                if(typeOfTile == 1){
                    g2d.setColor(Color.black);
                }
                g2d.fillRect(col*16,row*16,16,16);
            }
        }
    }
}
