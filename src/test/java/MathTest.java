import cz.cvut.fel.pjv.Model.Logic.Megamind;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class MathTest {
    @Test
    public void calculateDirectionVector(){
        //initialize
        Megamind megamind = new Megamind();
        // first point
        int x1 = 5;
        int y1 = 6;
        // second point
        int x2 = 0;
        int y2 = 1;
        //expected result in polar coordinates (in radians)
        double res = -2.356194490192345;
        //calculate dir. vector and assert
        Assertions.assertEquals(megamind.directionVector(x1,y1,x2,y2),res);
    }
}
