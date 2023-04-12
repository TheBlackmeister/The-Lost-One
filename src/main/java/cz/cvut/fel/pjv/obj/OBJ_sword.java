package cz.cvut.fel.pjv.obj;

import javax.imageio.ImageIO;
import java.io.IOException;
/**
 * class OBJ_sword represents a weapon
 * weapons can be found randomly, in a chest, after kill of a monster or can be traded for money
 */
public class OBJ_sword extends SuperObject{
    public OBJ_sword() {
        name = "Sword";
        try {
            image = ImageIO.read(getClass().getResourceAsStream("/objects/weapons/sword.png"));
        } catch(IOException e) {
            e.printStackTrace();
        }
    }
}
