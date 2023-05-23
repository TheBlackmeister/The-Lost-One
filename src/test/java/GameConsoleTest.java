
import cz.cvut.fel.pjv.View.GameConsole;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class GameConsoleTest {
    @Test
    public void changeLabelTest(){
        GameConsole gameConsole = new GameConsole();
        String newText = "Test 1 2 3!";
        gameConsole.changeLabel(newText);
        Assertions.assertEquals(gameConsole.getMyOutput().getText(),newText);
    }
}
