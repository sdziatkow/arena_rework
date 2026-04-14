package spriteData;

import collision.CheckBox;
import javafx.animation.Animation;
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
    private CheckBox checkBox;
    private DoubleVal speed;

    public MovingSprite() {
        getAnim().setCycleCount(Animation.INDEFINITE);
        checkBox = new CheckBox();
        speed = new DoubleVal();
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

//GETTERS----------------------------------------------------------------------------------------------------------------

    /** @see CheckBox */
    public CheckBox getCheckBox(){ return checkBox; }

    /** @see values.DoubleVal */
    public DoubleVal getSpeed() { return speed; }

//FLAGS------------------------------------------------------------------------------------------------------------------

    public boolean isMoving() {
        return (speed.get() > speed.getMin());
    }

//OPERATIONS-------------------------------------------------------------------------------------------------------------

}
