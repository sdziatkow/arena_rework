package movement;

import javafx.scene.Node;
import javafx.scene.ParallelCamera;
import spriteData.Dir;
import spriteData.charSprite.CharSprite;
import values.DoubleVal;

/** For moving Sprites of class CharSprite; a visual representation of a game character.
 * <br> There two main implementations
 * @see PlayerMvmnt
 * @see PathFinder
 * @see MvState
 * @see NPCState
 */
public abstract class CharMvmnt {
    public static double acceleration = 1.05;
    public static double friction = 0.22;

    public static void onMove(CharSprite sprite) {
        accel(sprite.getSpeed());
        if (!sprite.isAnimRunning()) sprite.getAnim().play(); // Play animation if it is not playing.
        translate(sprite, sprite.getDir());
        slow(sprite.getSpeed());
    }

    public static void onSlowing(CharSprite sprite) {
        translate(sprite, sprite.getDir());
        hardSlow(sprite.getSpeed());
    }

    public static void onStopped(CharSprite sprite) {
        sprite.idleFrame(); // Switch to idle frame
        if (sprite.isAnimRunning()) sprite.getAnim().pause(); // Pause animation if not paused.
    }

    public static void translate(CharSprite sprite, Dir dir) {
        double[] currPos = new double[]{sprite.getPos(0).doubleValue(), sprite.getPos(1).doubleValue()};
        double[] nextPos = nextPos(sprite.getSpeed(), dir, currPos);
        sprite.setPos(nextPos[0], nextPos[1]);
    }
    private static double[] nextPos(DoubleVal speed, Dir direction, double[] currPos) {
        currPos[Dir.axis(direction)] += Dir.sign(direction) * speed.get();
        return currPos;
    }

    public static void accel(DoubleVal speed) { speed.inc(acceleration); }
    public static void slow(DoubleVal speed) { speed.dec(friction); }
    public static void hardSlow(DoubleVal speed) {speed.dec(friction * 2.7);}
    public static void halt(DoubleVal speed) { speed.set(speed.getMin()); }

    public static void bindNode(CharSprite sprite, Node n) {
        n.translateXProperty().bind(sprite.getGroup().translateXProperty());
        n.translateYProperty().bind(sprite.getGroup().translateYProperty());
    }
}
