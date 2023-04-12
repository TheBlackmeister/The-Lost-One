package cz.cvut.fel.pjv.obj;

import javax.imageio.ImageIO;
import java.io.IOException;

/**
 * class OBJ_chest represents a chest, HUGO can find sword, health or coins in it
 * chest can be found inside a building
 */
public class OBJ_chest extends SuperObject{
    public OBJ_chest() {
        name = "Chest";
        try {
            image = ImageIO.read(getClass().getResourceAsStream("/objects/chest.png"));
        } catch(IOException e) {
            e.printStackTrace();
        }
        collision = true;
    }
}
