package cz.cvut.fel.pjv.Controller;

import cz.cvut.fel.pjv.Model.Entity.EnemySoldier;
import cz.cvut.fel.pjv.Model.Entity.Entity;
import cz.cvut.fel.pjv.Model.Entity.Fountain;
import cz.cvut.fel.pjv.Model.Entity.Tower;
import cz.cvut.fel.pjv.Model.Map.Room;
import cz.cvut.fel.pjv.Model.Utils.Tuple;

import java.util.ArrayList;

public class RoomMover {
    private ArrayList<Room> rooms;
    private Room actualRoom;
    private GamePanel gp;
    private int roomIndex;
    private int moveOffset;
    private int playerX;
    private int playerY;
    private ArrayList<Tuple> listOfEnemies;
    private ArrayList<Tuple> listOfTowers;
    private ArrayList<Tuple> listOfFountains;

    public RoomMover(ArrayList<Room> rooms, GamePanel gp){
        this.rooms = rooms;
        this.gp = gp;
        roomIndex = 0;
        actualRoom = rooms.get(roomIndex);
        moveOffset = 50;
        listOfEnemies = new ArrayList<>(actualRoom.getEnemies());
        listOfTowers = new ArrayList<>(actualRoom.getTowers());
        listOfFountains = new ArrayList<>(actualRoom.getFountains());
        for (Tuple enemy:listOfEnemies) {
            gp.enemySoldiers.add(new EnemySoldier(enemy.getFirst(), enemy.getSecond(), gp));
        }
        for (Tuple tower:listOfTowers) {
            gp.towers.add(new Tower(tower.getFirst(), tower.getSecond(), gp));
        }
        for (Tuple fountain:listOfFountains) {
            gp.fountains.add(new Fountain(fountain.getFirst(), fountain.getSecond(), gp));
        }
    }
    public void changeRoom(Entity.Directions direction,int newRoomIndex){ // direction is původní směr pohybu
        // update player coords
        if(direction == Entity.Directions.LEFT){
            gp.getPlayer().setActualX(gp.getConfig().getScreenWidth()-moveOffset);
        }
        if(direction == Entity.Directions.RIGHT){
            gp.getPlayer().setActualX(moveOffset);
        }
        if(direction == Entity.Directions.UP){
            gp.getPlayer().setActualY(gp.getConfig().getScreenHeight()-moveOffset);
        }
        if(direction == Entity.Directions.DOWN){
            gp.getPlayer().setActualY(moveOffset);
        }

        listOfTowers.clear();
        listOfEnemies.clear();
        ArrayList<Tuple> tmpEnemies = new ArrayList<Tuple>();
        for (EnemySoldier enemy:gp.enemySoldiers) {
            tmpEnemies.add(new Tuple(enemy.getActualX(), enemy.getActualY()));
        }
        actualRoom.setEnemies(tmpEnemies);

        ArrayList<Tuple> tmpTowers = new ArrayList<Tuple>();
        for (Tower tower:gp.towers) {
            tmpTowers.add(new Tuple(tower.getActualX(), tower.getActualY()));
        }
        actualRoom.setTowers(tmpTowers);

        ArrayList<Tuple> tmpFountains = new ArrayList<Tuple>();
        for (Fountain fountain:gp.fountains) {
            tmpFountains.add(new Tuple(fountain.getActualX(), fountain.getActualY()));
        }
        actualRoom.setFountains(tmpFountains);
        // update the room in here

        roomIndex = newRoomIndex;
        actualRoom = rooms.get(roomIndex);
        gp.enemyProjectile.clear();
        gp.projectile.clear();
        gp.towers.clear();
        gp.enemySoldiers.clear();
        gp.fountains.clear();

        // update the mapView
        gp.mapView.setRoom(actualRoom);
        for (Tuple enemy: actualRoom.getEnemies()) {
            gp.enemySoldiers.add(new EnemySoldier(enemy.getFirst(), enemy.getSecond(), gp));
        }
        for (Tuple tower: actualRoom.getTowers()) {
            gp.towers.add(new Tower(tower.getFirst(), tower.getSecond(), gp));
        }
        for (Tuple fountain: actualRoom.getFountains()) {
            gp.fountains.add(new Fountain(fountain.getFirst(), fountain.getSecond(), gp));
        }
    }

    public void update(){
        playerX = gp.getPlayer().getActualX();
        playerY = gp.getPlayer().getActualY();
        if(!actualRoom.isClosed()) {
            if (playerX < 0 && actualRoom.getLeftRoomIndex() != -1) {
                changeRoom(Entity.Directions.LEFT, actualRoom.getLeftRoomIndex());
            }
            if (playerX > gp.getConfig().getScreenWidth() && actualRoom.getRightRoomIndex() != -1) {
                changeRoom(Entity.Directions.RIGHT, actualRoom.getRightRoomIndex());
            }
            if (playerY < 0 && actualRoom.getUpRoomIndex() != -1) {
                changeRoom(Entity.Directions.UP, actualRoom.getUpRoomIndex());
            }
            if (playerY > gp.getConfig().getScreenHeight() && actualRoom.getDownRoomIndex() != -1) {
                changeRoom(Entity.Directions.DOWN, actualRoom.getDownRoomIndex());
            }
        }
        if(gp.enemySoldiers.isEmpty()&&gp.towers.isEmpty()){
            actualRoom.setClosed(false);
        }
    }
    public boolean isClosedPlayerOutOfRoomRight() {
        if(actualRoom.isClosed()) {
            return playerX > gp.getConfig().getScreenWidth()-32;
        }
        return false;
    }

    public boolean isClosedPlayerOutOfRoomLeft(){
        if(actualRoom.isClosed()) {
            return playerX < 0;
        }
        return false;
    }

    public boolean isClosedPlayerOutOfRoomDown() {
        if(actualRoom.isClosed()) {
            return playerY > gp.getConfig().getScreenHeight()-32;
        }
        return false;
    }
    public boolean isClosedPlayerOutOfRoomUp(){
        if(actualRoom.isClosed()) {
            return playerY < 0;
        }
        return false;
    }

    public Room getActualRoom() {
        return actualRoom;
    }
}
