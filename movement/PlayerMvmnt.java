package movement;

import control.Controller;
import javafx.collections.SetChangeListener;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import spriteData.Dir;
import spriteData.MovingSprite;
import spriteData.charSprite.CombatSprite32x32;
import values.DoubleVal;

/**
 *
 * @see MovingSprite For more information on where the sprite's speed field is coming from.
 */
public class PlayerMvmnt {
    private static CombatSprite32x32 sprite = null;
    public static double acceleration = 0.24;
    public static double friction = 0.12;
    private static MvState state = null;

    public static void setSprite(CombatSprite32x32 s) {
        sprite = s;
        if (sprite != null) state = sprite.state();
    }

    private static void setState() {
        if (sprite.getWPSprite().isAnimRunning()) state = MvState.ATTACK;
        else if (!Controller.mvmntKeysDown.isEmpty()) state = MvState.MOVING;
        else if (!sprite.isMoving()) state = MvState.STOPPED;
        else state = MvState.SLOWING;
    }

    public static void forceState(MvState s) {
        state = s;
        move();
    }

    public static void runMvmnt() {
        setState();
        move();
    }

    private static void move() {
        switch (state) {
            case MOVING:
                accel(sprite.getSpeed());
                if (!sprite.isAnimRunning()) sprite.getAnim().play(); // Play animation if it is not playing.
                translate();
                slow(sprite.getSpeed());
                break;
            case SLOWING:
                translate();
                hardSlow(sprite.getSpeed());
                break;
            case STOPPED:
                sprite.idleFrame(); // Switch to idle frame
                if (sprite.isAnimRunning()) sprite.getAnim().pause(); // Pause animation if not paused.
                break;
            case ATTACK:
                if (sprite.isAnimRunning()) sprite.getAnim().pause(); // Pause animation if not paused.
                halt(sprite.getSpeed());
                break;
            default: return;
        }
    }

    public static void translate() {
        double[] currPos = new double[]{sprite.getPos(0).doubleValue(), sprite.getPos(1).doubleValue()};
        double[] nextPos = nextPos(sprite.getSpeed(), sprite.getDir(), currPos);
        sprite.setPos(nextPos[0], nextPos[1]);
    }
    private static double[] nextPos(DoubleVal speed, Dir direction, double[] currPos) {
        switch (direction) {
            case N:
                currPos[1] -= speed.get();
                break;
            case S:
                currPos[1] += speed.get();
                break;
            case E:
                currPos[0] += speed.get();
                break;
            case W:
                currPos[0] -= speed.get();
                break;
            default: return currPos;
        }
        return currPos;
    }

    private static void accel(DoubleVal speed) { speed.inc(acceleration); }
    private static void slow(DoubleVal speed) { speed.dec(friction); }
    private static void hardSlow(DoubleVal speed) {speed.dec(friction * 2.7);}
    private static void halt(DoubleVal speed) { speed.set(speed.getMin()); }

    public static void cntrlSetUp() {
        Controller.mvmntKeysDown.addListener(new SetChangeListener<KeyCode>() {
            @Override
            public void onChanged(Change<? extends KeyCode> change) {
                KeyCode wasAdded = change.getElementAdded();
                if (wasAdded != null) {
                    switch (wasAdded) {
                        case W:
                            sprite.switchDir(Dir.N);
                            break;
                        case S:
                            sprite.switchDir(Dir.S);
                            break;
                        case D:
                            sprite.switchDir(Dir.E);
                            break;
                        case A:
                            sprite.switchDir(Dir.W);
                            break;
                        default:
                            return;
                    }

                }
                KeyCode wasRemoved = change.getElementRemoved();
                if (wasRemoved != null) {
                    boolean w = change.getSet().contains(KeyCode.W);
                    boolean s = change.getSet().contains(KeyCode.S);
                    boolean d = change.getSet().contains(KeyCode.D);
                    boolean a = change.getSet().contains(KeyCode.A);
                    if (w) sprite.switchDir(Dir.N);
                    else if (s) sprite.switchDir(Dir.S);
                    else if (d) sprite.switchDir(Dir.E);
                    else if (a) sprite.switchDir(Dir.W);
                }

                sprite.getCheckBox().expand(sprite.getSpeed().getMax() * 2, sprite.getDir());
            }
        });

        Controller.mouseBtnsDown.addListener(new SetChangeListener<MouseButton>() {
            @Override
            public void onChanged(Change<? extends MouseButton> change) {
                MouseButton wasAdded = change.getElementAdded();
                if (wasAdded == null) return;
                if (wasAdded.equals(MouseButton.PRIMARY)) {
                    sprite.onAttk();
                }
            }
        });
    }
}
