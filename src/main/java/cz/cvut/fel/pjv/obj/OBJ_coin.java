package cz.cvut.fel.pjv.obj;

import javax.imageio.ImageIO;
import java.io.IOException;

public class OBJ_coin extends SuperObject {
    public OBJ_coin() {
        name = "Coin";
        try {
            image = ImageIO.read(getClass().getResourceAsStream("/objects/coin_animation.gif"));
        } catch(IOException e) {
            e.printStackTrace();
        }
    }
}
