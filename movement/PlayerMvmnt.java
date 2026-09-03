package movement;

import control.Controller;
import javafx.collections.SetChangeListener;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import spriteData.Dir;
import spriteData.MovingSprite;
import spriteData.charSprite.CombatSprite;

import static collision.ColType.CHECKBOX;

/**
 * @see MovingSprite For more information on where the sprite's speed field is coming from.
 */
public class PlayerMvmnt extends CharMvmnt {
    private static CombatSprite sprite = null;
    private static MvState state = null;

    public static void setSprite(CombatSprite s) {
        sprite = s;
        if (sprite != null) {
            state = sprite.getMvState();
        }
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
                onMove(sprite);
                break;
            case SLOWING:
                onSlowing(sprite);
                break;
            case STOPPED:
                onStopped(sprite);
                break;
            case ATTACK:
                if (sprite.isAnimRunning()) sprite.getAnim().pause(); // Pause animation if not paused.
                halt(sprite.getSpeed());
                break;
            default: return;
        }
    }

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

                sprite.getBox(CHECKBOX).checkDir(sprite.getSpeed().getMax() * 2, sprite.getDir());
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
