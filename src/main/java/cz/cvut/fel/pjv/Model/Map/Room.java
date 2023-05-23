package cz.cvut.fel.pjv.Model.Map;

import cz.cvut.fel.pjv.Model.Utils.Tuple;
import java.util.ArrayList;

/**
 * Represents each room.
 */
public class Room {
    private ArrayList<Tuple> enemies;
    private ArrayList<Tuple> towers;
    private ArrayList<Tuple> fountains;
    private int[][] map;
    private boolean closed;
    private int upRoomIndex;
    private int rightRoomIndex;
    private int downRoomIndex;
    private int leftRoomIndex;

    public Room(ArrayList<Tuple> enemies, ArrayList<Tuple> towers, ArrayList<Tuple> fountains, int[][] map, int upRoomIndex, int rightRoomIndex, int downRoomIndex, int leftRoomIndex, boolean closed){
        this.map = map;
        this.enemies = enemies;
        this.towers = towers;
        this.fountains = fountains;
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

    public void setFountains(ArrayList<Tuple> fountains) {
        this.fountains = fountains;
    }

    public ArrayList<Tuple> getEnemies() {
        return enemies;
    }

    public ArrayList<Tuple> getTowers() {
        return towers;
    }

    public ArrayList<Tuple> getFountains() {
        return fountains;
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

}
