package cz.cvut.fel.pjv.Model.Map;

import cz.cvut.fel.pjv.Model.Entity.EnemySoldier;
import cz.cvut.fel.pjv.Model.Entity.Tower;
import cz.cvut.fel.pjv.Model.Setuper.MapSetup;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

/**
 * this class represents each room.
 */
public class Room {
    enum RoomType {
        CLOSED,UP,LEFT,RIGHT,DOWN
    }
    private final int tileSize = 16;
    private ArrayList<EnemySoldier> enemies;
    private ArrayList<Tower> towers;
    private RoomType roomType;
    private int[][] map;
    private boolean opened;

    public Room(ArrayList<EnemySoldier> enemies, ArrayList<Tower> towers,RoomType roomType,int[][] map){
        this.map = map;
        this.roomType = roomType;
        this.enemies = enemies;
        this.towers = towers;
        this.opened = false;
    }

    public boolean isOpened() {
        return opened;
    }

//    public void setOpened(boolean opened) {
//        this.opened = opened;
//    }
//
//    public void setMap(int[][] map) {
//        this.map = map;
//    }
//
//    public void update(){
//
//    }
//    public void draw(Graphics g){
//        Graphics2D g2d;
//        g2d = (Graphics2D)g;
//        BufferedImage PlayerImage = null;
//
//        for (int row = 0; row < 45; row++) {
//            for (int col = 0; col < 80; col++) {
//
//                int typeOfTile = map[row][col];
//
//                if(typeOfTile == 0){
//                    g2d.setColor(Color.green);
//                }
//                if(typeOfTile == 1){
//                    g2d.setColor(Color.black);
//                }
//                g2d.fillRect(col*16,row*16,16,16);
//            }
//        }
//    }

}
