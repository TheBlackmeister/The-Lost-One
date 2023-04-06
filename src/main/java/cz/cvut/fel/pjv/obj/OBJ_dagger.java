package cz.cvut.fel.pjv.obj;

import javax.imageio.ImageIO;
import java.io.IOException;

public class OBJ_dagger extends SuperObject{
    public OBJ_dagger() {
        name = "Dagger";
        try {
            image = ImageIO.read(getClass().getResourceAsStream("/objects/weapons/dagger_normal.png"));
        } catch(IOException e) {
            e.printStackTrace();
        }
    }
}
