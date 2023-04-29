package cz.cvut.fel.pjv.Model.Logic;

public class Megamind {
    // calculating the direction of a tower shot to be towards player
    // Calculate vector components
    public Megamind(){

    }

    /**
     * a function used to calculate the direction from one point to another. (ex. Tower to player)
     * @param x1 is the X coordinate of the origin point.
     * @param x2 is the Y coordinate of the origin point.
     * @param y1 is the X coordinate of the target point.
     * @param y2 is the Y coordinate of the target point.
     * @return direction in Radians (double)
     */
    public double directionVector(double x1,double x2,double y1,double y2){ //y-x = y1-x1; y2-x2 // todo could be static
        double dx = y1-x1;
        double dy = y2-x2;

        return Math.atan2(dy,dx);
    }

}
