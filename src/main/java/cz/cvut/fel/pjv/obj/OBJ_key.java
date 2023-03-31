package cz.cvut.fel.pjv.obj;

import javax.imageio.ImageIO;
import java.io.IOException;

public class OBJ_key extends SuperObject{
    public OBJ_key() {
        name = "Key";
        try {
            image = ImageIO.read(getClass().getResourceAsStream("/objects/key.png"));
        } catch(IOException e) {
            e.printStackTrace();
        }
    }
}
