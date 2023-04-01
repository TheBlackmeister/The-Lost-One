package cz.cvut.fel.pjv.Controller;

import cz.cvut.fel.pjv.obj.OBJ_chest;
import cz.cvut.fel.pjv.obj.OBJ_key;

public class AssetSetter {
    GamePanel gp;

    public AssetSetter(GamePanel gp) {
        this.gp = gp;
    }
    public void setObject() {
        gp.obj[0] = new OBJ_key();
        gp.obj[0].setPosX(1000);
        gp.obj[0].setPosY(1000);

        gp.obj[1] = new OBJ_key();
        gp.obj[1].setPosX(1100);
        gp.obj[1].setPosY(1000);

        gp.obj[2] = new OBJ_key();
        gp.obj[2].setPosX(54*gp.getTileSize());
        gp.obj[2].setPosY(54*gp.getTileSize());

        gp.obj[3] = new OBJ_chest();
        gp.obj[3].setPosX(61*gp.getTileSize());
        gp.obj[3].setPosY(48*gp.getTileSize());

        gp.obj[4] = new OBJ_chest();
        gp.obj[4].setPosX(61*gp.getTileSize());
        gp.obj[4].setPosY(66*gp.getTileSize());
    }

}
