package movement;

import javafx.geometry.Bounds;
import javafx.scene.shape.Path;
import spriteData.Dir;

public class PathFinder extends CharMvmnt{

    public static final Path path = new Path();
    public static final double[] linePos = new double[2];

    public static double absDist(double x, double tx) { return Math.abs(Math.abs(x) - Math.abs(tx)); }
    public static double rawDist(double x, double tx) {return (x - tx);}
    public static double distanceTo(double[] pos1, double[] pos2) {
        return Math.sqrt(Math.pow((pos1[0] - pos2[0]), 2) + Math.pow((pos1[1] - pos2[1]), 2));
    }


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
        if (yDist < xDist) {
            if (isEast(x, tx)) best = Dir.E;
            else best = Dir.W;
        }
        else {
            if (isSouth(y, ty)) best = Dir.S;
            else best = Dir.N;
        }
        return best;
    }

    /** Will return the opposite of the direction in which the least amount of distance between the two points needs to be covered */
    public static Dir bestMoveAround(double[] pos, Bounds boxBounds) {
        double x = pos[0];
        double y = pos[1];
        double xDist;
        double yDist;
        boolean isSouth = false;
        boolean isEast = false;
        if (isSouth(y, boxBounds.getCenterY())) { // Box is south of me.
            yDist = absDist(y, boxBounds.getMaxY()); // Get dist to maxY (bottom-most), furthest from me.
            isSouth = true;
        }
        else yDist = absDist(y, boxBounds.getMinY()); // Otherwise get dist to minY(top-most), furthest from me.

        if (isEast(x, boxBounds.getCenterX())) { // Box is east of me.
            xDist = absDist(x, boxBounds.getMaxX()); // Get dist to maxX (right-most), furthest from me.
            isEast = true;
        }
        else xDist = absDist(x, boxBounds.getMinX());// Otherwise get dist to minX (left-most), furthest from me.

        if (xDist < yDist) { // Least distance to cover x-wise.
            if (isEast) return Dir.W;
            else return Dir.E;
        }
        else {
            if (isSouth) return Dir.N;
            else return Dir.S;
        }
    }
}
