package cz.cvut.fel.pjv.Controller;


import cz.cvut.fel.pjv.Model.Entity.EnemySoldier;
import cz.cvut.fel.pjv.Model.Entity.Player;


import java.awt.*;

/**
 * this class is used to check whether is the player or the enemy colliding with the non-passable tile.
 */
public class CollisionTileChecker {
    GamePanel gp;
    public CollisionTileChecker(GamePanel gp) {
        this.gp = gp;
    }

    /**
     * This function takes player and checks if he/she is clipping with a non-passable tile.
     * @param player given player
     * @return true == collision detected, false == collision not detected.
     */
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
    /**
     * This function takes enemy soldier and checks if it is clipping with a non-passable tile.
     * int[][] map gets map from the roomMover via GamePanel
     * it creates two rectangles and checks if they are colliding for tile.
     * @param enemySoldier given player
     * @return true == collision detected, false == collision not detected.
     */
    public boolean checkTileCollisionEnemy(EnemySoldier enemySoldier){
        int[][] map = gp.getRoomMover().getActualRoom().getMap();
        int tileX;
        int tileY;
        int tileWidth = 16;
        int tileHeight = 16;
        Rectangle rectE = new Rectangle(enemySoldier.getActualX(),enemySoldier.getActualY(),32,32);
        for (int row = 0; row < 45; row++) {
            for (int col = 0; col < 80; col++) {
                if(map[row][col]==1){ // 1 is a wall
                    tileX = col * 16;
                    tileY = row * 16;
                    Rectangle rectT = new Rectangle(tileX,tileY,tileWidth,tileHeight);
                    if(rectT.intersects(rectE)){

                        return true;
                    }
                }
            }
        }
        return false;
    }
}