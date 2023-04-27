package cz.cvut.fel.pjv.Model.Logic;
import java.lang.Math.*;
public class Megamind {
    // calculating the direction of a tower shot to be towards player
    // Calculate vector components
    public Megamind(){

    }

    public double[] directionVector(double x1,double x2,double y1,double y2){ //y-x = y1-x1; y2-x2
        double dx = y1-x1;
        double dy = y2-x2;
        // Calculate vector magnitude
//        double mag = Math.sqrt(dx*dx + dy*dy);
//        // Calculate normalized vector components
//        double vx = dx / mag;
//        double vy = dy / mag;
        double polar = Math.atan2(dy,dx);
        return polar;
        return new double[]{vx,vy};
    }

}
