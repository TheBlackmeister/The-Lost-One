package cz.cvut.fel.pjv.obj;

import javax.imageio.ImageIO;
import java.io.IOException;

public class OBJ_chest extends SuperObject{
    public OBJ_chest() {
        name = "Chest";
        try {
            image = ImageIO.read(getClass().getResourceAsStream("/objects/chest.png"));
        } catch(IOException e) {
            e.printStackTrace();
        }
    }
}
