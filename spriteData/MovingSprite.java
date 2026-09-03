package spriteData;

import collision.ColType;
import collision.CollisionBox;
import javafx.animation.Animation;
import movement.MvState;
import spriteData.behavior.boxes.Movable;
import values.DoubleVal;

/**
 * Sprites for Animated Persons / NPCS / Mobs / Weapons etc.
 * <br> Allows for movement in FOUR directions exactly.
 * <br> Animation's cycleCount is always INDEFINITE.
 * @see AnimSprite
 * @see Dir
 */
public class MovingSprite extends FourWaySprite implements Movable {
    private DoubleVal speed;
    private MvState mvState;

    public MovingSprite() {
        getAnim().setCycleCount(Animation.INDEFINITE);
        speed = new DoubleVal();
        addBox(new CollisionBox(ColType.CHECKBOX));
    }

//SETTERS----------------------------------------------------------------------------------------------------------------

    /**
     * Overridden so that cycle count remains indefinite.
     * @param frames The total amount of frames per direction.
     */
    @Override
    public void setTotalFrames(int frames) {
        super.setTotalFrames(frames);
        getAnim().setCycleCount(Animation.INDEFINITE);
    }

    /** @param maxRate The maximum move rate of this Sprite. */
    public void setMaxSpeed(double maxRate) { speed.setMax(maxRate); }

    public void setMvState(MvState state) { mvState = state; }

//GETTERS----------------------------------------------------------------------------------------------------------------

    /** @see values.DoubleVal */
    public DoubleVal getSpeed() { return speed; }

    public MvState getMvState() { return mvState; }

//FLAGS------------------------------------------------------------------------------------------------------------------

    public boolean isMoving() {
        return (speed.get() > speed.getMin());
    }

//OPERATIONS-------------------------------------------------------------------------------------------------------------

}
