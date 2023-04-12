package cz.cvut.fel.pjv.obj;

import javax.imageio.ImageIO;
import java.io.IOException;
/**
 * class OBJ_coin represents coins, HUGO can buy stuff for coins
 * obj coin can be found randomly, in a chest or after kill of a monster
 */
public class OBJ_coin extends SuperObject {
    public OBJ_coin() {
        name = "Coin";
        try {
            image = ImageIO.read(getClass().getResourceAsStream("/objects/gold.gif"));
        } catch(IOException e) {
            e.printStackTrace();
        }
    }
}
