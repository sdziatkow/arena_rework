package movement;

import javafx.scene.shape.Path;
import spriteData.Dir;

public class PathFinder extends CharMvmnt{

    public static final Path path = new Path();
    public static final double[] linePos = new double[2];

    public static double absDist(double x, double tx) { return Math.abs(Math.abs(x) - Math.abs(tx)); }
    public static double rawDist(double x, double tx) {return (x - tx);}


    /** Must give values relating to the y-axis.
     * @param y The position to check from.
     * @param ty The position to check to.
     * @return True if given (ty) appears south of given (y).
     */
    public static boolean isSouth(double y, double ty) { return rawDist(y, ty) < 0; }

    /** Must give values relating to the x-axis.
     * @param x The position to check from.
     * @param tx The position to check to.
     * @return True if given (tx) appears east of given (x).
     */
    public static boolean isEast(double x, double tx) { return rawDist(x, tx) < 0; }

    /** Will return the direction in which the most amount of distance between the two points needs to be covered */
    public static Dir bestMoveTowards(double[] pos, double[] target) {
        double x = pos[0];
        double y = pos[1];
        double tx = target[0];
        double ty = target[1];

        // Compute the absolute x distance and y distance.
        double xDist = absDist(x, tx);
        double yDist = absDist(y, ty);

        // Determine the best direction.
        Dir best;
        if (yDist <= xDist) {
            if (isEast(x, tx)) best = Dir.E;
            else best = Dir.W;
        }
        else {
            if (isSouth(y, ty)) best = Dir.S;
            else best = Dir.N;
        }
        return best;
    }

    /** Will return the direction in which the least amount of distance between the two points needs to be covered */
    public static Dir bestMoveAround(Dir dir, double[] pos, double[] boxBounds) {
        double x = pos[0];
        double y = pos[1];
        double bx = boxBounds[0];
        double by = boxBounds[1];

        // We want the largest distance from the mid-point, go that way.
        double xDist = absDist(x, bx);
        double yDist = absDist(y, by);

        if (Dir.axis(dir) == 0) { // This means coming from east or west moving towards a box.
            if (isSouth(y, by)) { // This I should go north.
                return Dir.N;
            }
            else { // Otherwise go south.
                return Dir.S;
            }
        }
        else { // This means coming from north or south moving towards a box.
            if (isEast(x, bx)) { // This means I should go west.
                return Dir.W;
            }
            else { // Otherwise go east.
                return Dir.E;
            }
        }
    }
}
