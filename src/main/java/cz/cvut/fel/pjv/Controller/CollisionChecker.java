package cz.cvut.fel.pjv.Controller;

import cz.cvut.fel.pjv.entity.Entity;

public class CollisionChecker {
    GamePanel gp;
    public CollisionChecker(GamePanel gp){
        this.gp = gp;
    }
    public void CheckTile (Entity entity){
        int leftWorldX = entity.getPosX() + entity.getSolidArea().x;
        int rightWorldX = entity.getPosX() + entity.getSolidArea().x + entity.getSolidArea().width;
        int topWorldY = entity.getPosY() + entity.getSolidArea().y;
        int bottomWorldY = entity.getPosY() + entity.getSolidArea().y + entity.getSolidArea().height;

        int leftCol = leftWorldX/gp.getTileSize();
        int rightCol = rightWorldX/gp.getTileSize();
        int topRow = topWorldY/gp.getTileSize();
        int bottomRow = bottomWorldY/gp.getTileSize();

        int tileNum1, tikeNum2;

        switch (entity.getDirection()){
            case "up": topRow = (topWorldY - entity.getSpeed())/gp.getTileSize();
//            tileNum1 = gp.tileM.tileNumtady e to udelane asi jinak nez ve vieud asi19:26 jsem skoncil, musel jsem
        }
    }
}
