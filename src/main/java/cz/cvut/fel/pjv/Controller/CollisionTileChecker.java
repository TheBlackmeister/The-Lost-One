package cz.cvut.fel.pjv.Controller;

import cz.cvut.fel.pjv.Model.Entity.Player;


import java.awt.*;

public class CollisionTileChecker {
    GamePanel gp;
    public CollisionTileChecker(GamePanel gp) {
        this.gp = gp;
    }


    public boolean checkTileCollisionPlayer(Player player){
        int[][] map = gp.getRoomMover().getActualRoom().getMap();
        int tileX;
        int tileY;
        int tileWidth = 16;
        int tileHeight = 16;
        Rectangle rectP = new Rectangle(player.getActualX(),player.getActualY(),32,32);
        for (int row = 0; row < 45; row++) {
            for (int col = 0; col < 80; col++) {
                if(map[row][col]==1){ // 1 is a wall
                    tileX = col * 16;
                    tileY = row * 16;
                    Rectangle rectT = new Rectangle(tileX,tileY,tileWidth,tileHeight);
                    if(rectT.intersects(rectP)){
                        return true;
                    }
                }
            }
        }
        return false;
    }
}