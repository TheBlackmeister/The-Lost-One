package cz.cvut.fel.pjv.Model.Map;

import cz.cvut.fel.pjv.Model.Entity.EnemySoldier;
import cz.cvut.fel.pjv.Model.Entity.Tower;
import cz.cvut.fel.pjv.Model.Utils.Tuple;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

/**
 * this class represents each room.
 */
public class Room {
    private final int tileSize = 16;
    private ArrayList<Tuple> enemies;
    private ArrayList<Tuple> towers;
    private int[][] map;
    private boolean closed;
    private int upRoomIndex;
    private int rightRoomIndex;
    private int downRoomIndex;
    private int leftRoomIndex;

    public Room(ArrayList<Tuple> enemies, ArrayList<Tuple> towers, int[][] map, int upRoomIndex, int rightRoomIndex, int downRoomIndex, int leftRoomIndex, boolean closed){
        this.map = map;
        this.enemies = enemies;
        this.towers = towers;
        this.closed = closed;
        this.upRoomIndex = upRoomIndex;
        this.rightRoomIndex = rightRoomIndex;
        this.leftRoomIndex = leftRoomIndex;
        this.downRoomIndex = downRoomIndex;
    }

    public void setEnemies(ArrayList<Tuple> enemies) {
        this.enemies = enemies;
    }

    public void setTowers(ArrayList<Tuple> towers) {
        this.towers = towers;
    }

    public ArrayList<Tuple> getEnemies() {
        return enemies;
    }

    public ArrayList<Tuple> getTowers() {
        return towers;
    }

    public int[][] getMap() {
        return map;
    }

    public int getUpRoomIndex() {
        return upRoomIndex;
    }

    public int getDownRoomIndex() {
        return downRoomIndex;
    }

    public int getLeftRoomIndex() {
        return leftRoomIndex;
    }

    public int getRightRoomIndex() {
        return rightRoomIndex;
    }

    public boolean isClosed() {
        return closed;
    }

    public void setClosed(boolean closed) {
        this.closed = closed;
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
