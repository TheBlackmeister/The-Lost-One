package cz.cvut.fel.pjv.obj;

import javax.imageio.ImageIO;
import java.io.IOException;
/**
 * class OBJ_dagger represents a weapon
 * weapons can be found randomly, in a chest, after kill of a monster or can be traded for money
 */
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
