package cz.cvut.fel.pjv.Controller;

import cz.cvut.fel.pjv.Model.Entity.Entity;
import cz.cvut.fel.pjv.Model.Map.Room;

import java.util.ArrayList;

public class RoomMover {
    private ArrayList<Room> rooms;
    private Room actualRoom;
    private GamePanel gp;
    private int roomIndex;
    private int moveOffset;
    private int playerX;
    private int playerY;
    public RoomMover(ArrayList<Room> rooms, GamePanel gp){
        this.rooms = rooms;
        this.gp = gp;
        roomIndex = 0;
        actualRoom = rooms.get(roomIndex);
        moveOffset = 50;

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
        // update the room in here
        roomIndex = newRoomIndex;
        actualRoom = rooms.get(roomIndex);
        gp.enemyProjectile.clear();
        gp.projectile.clear();
        // update the mapView
        gp.mapView.setRoom(actualRoom);
    }

    public void update(){
        playerX = gp.getPlayer().getActualX();
        playerY = gp.getPlayer().getActualY();
        if(playerX < 0 && playerY > 288 & playerY < 432 && actualRoom.getLeftRoomIndex()!=-1){ // todo add podminka on Y within this (doors)
            changeRoom(Entity.Directions.LEFT,actualRoom.getLeftRoomIndex());
        }
        if(playerX > gp.getConfig().getScreenWidth() && playerY > 288 & playerY < 432 && actualRoom.getRightRoomIndex()!=-1){ // todo add podminka on Y within this (doors)
            changeRoom(Entity.Directions.RIGHT,actualRoom.getRightRoomIndex());
        }
    }

}
