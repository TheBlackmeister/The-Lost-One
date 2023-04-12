package cz.cvut.fel.pjv.Controller;

import cz.cvut.fel.pjv.entity.Entity;

/**
 * class colisionchecker
 */
public class CollisionChecker {
    GamePanel gp;
    public CollisionChecker(GamePanel gp) {
        this.gp = gp;
    }

    public void checkTile(Entity entity) {
        int entityLeftWorldX = entity.getPosX() + entity.getCollisionBox().x;
        int entityRightWorldX = entity.getPosX() + entity.getCollisionBox().x + entity.getCollisionBox().width;
        int entityTopWorldY = entity.getPosY() + entity.getCollisionBox().y;
        int entityBottomWorldY = entity.getPosY() + entity.getCollisionBox().y + entity.getCollisionBox().height;

        int entityLeftCol = entityLeftWorldX/gp.tileSize;
        int entityRightCol = entityRightWorldX/gp.tileSize;
        int entityTopRow = entityTopWorldY/gp.tileSize;
        int entityBottomRow = entityBottomWorldY/gp.tileSize;

        int tileNumber1, tileNumber2;

        switch (entity.getDirection()) {
            case "up" -> {
                entityTopRow = (entityTopWorldY - entity.getSpeed()) / gp.tileSize;
                tileNumber1 = gp.tileM.getMapTileNum()[entityLeftCol][entityTopRow];
                tileNumber2 = gp.tileM.getMapTileNum()[entityRightCol][entityTopRow];
                if (gp.tileM.getTile()[tileNumber1].collision || gp.tileM.getTile()[tileNumber2].collision) {
                    entity.setCollisionOn(true);
                }
            }
            case "down" -> {
                entityBottomRow = (entityBottomWorldY + entity.getSpeed()) / gp.tileSize;
                tileNumber1 = gp.tileM.getMapTileNum()[entityLeftCol][entityBottomRow];
                tileNumber2 = gp.tileM.getMapTileNum()[entityRightCol][entityBottomRow];
                if (gp.tileM.getTile()[tileNumber1].collision || gp.tileM.getTile()[tileNumber2].collision) {
                    entity.setCollisionOn(true);
                }
            }
            case "left" -> {
                entityLeftCol = (entityLeftWorldX - entity.getSpeed()) / gp.tileSize;
                tileNumber1 = gp.tileM.getMapTileNum()[entityLeftCol][entityTopRow];
                tileNumber2 = gp.tileM.getMapTileNum()[entityLeftCol][entityBottomRow];
                if (gp.tileM.getTile()[tileNumber1].collision || gp.tileM.getTile()[tileNumber2].collision) {
                    entity.setCollisionOn(true);
                }
            }
            case "right" -> {
                entityRightCol = (entityRightWorldX + entity.getSpeed()) / gp.tileSize;
                tileNumber1 = gp.tileM.getMapTileNum()[entityRightCol][entityTopRow];
                tileNumber2 = gp.tileM.getMapTileNum()[entityRightCol][entityBottomRow];
                if (gp.tileM.getTile()[tileNumber1].collision || gp.tileM.getTile()[tileNumber2].collision) {
                    entity.setCollisionOn(true);
                }
            }
        }
    }

    public int checkObject(Entity entity, boolean player) {
        int objectID = -1;
        for (int i=0;i<gp.getObj().length;i++) {
            if(gp.getObj()[i] != null) {
                // zjistujeme collision X Y entity
                entity.getCollisionBox().x = entity.getPosX() + entity.getCollisionBox().x;
                entity.getCollisionBox().y = entity.getPosY() + entity.getCollisionBox().y;
                // zjistujeme collision X Y objektu
                gp.obj[i].collisionBox.x = gp.obj[i].getPosX() + gp.obj[i].getCollisionBox().x;
                gp.obj[i].collisionBox.y = gp.obj[i].getPosY() + gp.obj[i].getCollisionBox().y;
                switch (entity.getDirection()) {
                    case "up":
                        entity.getCollisionBox().y -= entity.getSpeed();
                        if (entity.getCollisionBox().intersects(gp.obj[i].getCollisionBox())){
                            if(gp.obj[i].isCollision()){
                                entity.setCollisionOn(true);
                            }
                            if(player){
                                objectID = i;
                            }
                        }
                        break;
                    case "down":
                        entity.getCollisionBox().y += entity.getSpeed();
                        if (entity.getCollisionBox().intersects(gp.obj[i].getCollisionBox())){
                            if(gp.obj[i].isCollision()){
                                entity.setCollisionOn(true);
                            }
                            if(player){
                                objectID = i;
                            }
                        }
                        break;
                    case "right":
                        entity.getCollisionBox().x += entity.getSpeed();
                        if (entity.getCollisionBox().intersects(gp.obj[i].getCollisionBox())){
                            if(gp.obj[i].isCollision()){
                                entity.setCollisionOn(true);
                            }
                            if(player){
                                objectID = i;
                            }
                        }
                        break;
                    case "left":
                        entity.getCollisionBox().x -= entity.getSpeed();
                        if (entity.getCollisionBox().intersects(gp.obj[i].getCollisionBox())){
                            if(gp.obj[i].isCollision()){
                                entity.setCollisionOn(true);
                            }
                            if(player){
                                objectID = i;
                            }
                        }
                        break;
                }
                entity.getCollisionBox().x = entity.getcollisionBoxDefaultX();
                entity.getCollisionBox().y = entity.getcollisionBoxDefaultY();
                gp.obj[i].getCollisionBox().x = gp.obj[i].getCollisionBoxDefaultX();
                gp.obj[i].getCollisionBox().y = gp.obj[i].getCollisionBoxDefaultY();
            }
        }


        return objectID;
    }
}
