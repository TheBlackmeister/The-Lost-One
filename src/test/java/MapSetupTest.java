import cz.cvut.fel.pjv.Controller.GamePanel;
import cz.cvut.fel.pjv.View.GameWindow;
import cz.cvut.fel.pjv.View.Launcher;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import javax.swing.*;


public class MapSetupTest {
    GameWindow gameWindow = new GameWindow(new Launcher(),null);
    GamePanel gp = new GamePanel(new JFrame(),new Launcher(),gameWindow.getMapFilePath());
    @Test
    public void towersGenerationTest() {
        int towersLength = 1;

        Assertions.assertEquals(gp.getMapsetup().getRooms().get(0).getTowers().size(),towersLength);
    }

    @Test
    public void enemyGenerationTest(){
        int enemyLength = 0;

        Assertions.assertEquals(gp.getMapsetup().getRooms().get(0).getEnemies().size(),enemyLength);
    }

    @Test
    public void fountainGenerationTest(){
        int fountainLength = 0;

        Assertions.assertEquals(gp.getMapsetup().getRooms().get(0).getFountains().size(),fountainLength);
    }
    @Test
    public void checkPlayerCoords(){
        int coordX = 100;
        int coordY = 400;
        Assertions.assertEquals(gp.getPlayer().getActualX(),coordX);
        Assertions.assertEquals(gp.getPlayer().getActualY(),coordY);
    }
}
