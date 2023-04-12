package cz.cvut.fel.pjv.obj;

import javax.imageio.ImageIO;
import java.io.IOException;

/**
 * class OBJ_door represens a door
 * HUGO needs a key to go through door and after HUGO uses a key, the door disapear
 */
public class OBJ_door extends SuperObject{
    public OBJ_door() {
        name = "Door";
        try {
            image = ImageIO.read(getClass().getResourceAsStream("/objects/door.png"));
        } catch(IOException e) {
            e.printStackTrace();
        }
        collision = true;
    }
}
