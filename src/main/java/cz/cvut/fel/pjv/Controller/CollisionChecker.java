package cz.cvut.fel.pjv.Controller;

import cz.cvut.fel.pjv.entity.Entity;

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
            case "up" -> { //TODO kdyz player vyjde z mapy, spadne program - podminky pro index mensi nez nula a vetsi nez mapa!
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
}
