package collision;

import javafx.geometry.Bounds;
import spriteData.behavior.boxes.*;

import java.util.Map;
import java.util.Objects;
import java.util.Stack;

public class ColChecker {

    /**
     *
     * @param sprite A Movable Sprite (one with a checkBox).
     * @param allSprites All sprites needing to check against (ones with a worldBox).
     * @return A Stack of all Collidable Sprite's IDs that this Moveable Sprite's checkBox is colliding with.
     * @see Movable
     * @see Collidable
     */
    public static Stack<Integer> isColliding(Movable sprite, Map<Integer, Collidable> allSprites) {
        Stack<Integer> collidingWith = new Stack<>();
        Bounds spriteBounds = sprite.getCheckBox().getBounds();
        allSprites.forEach((id, other) -> {
            if (!(sprite.getCheckBox().getID() == id)) {
                if (other.getWorldBox().getBounds().intersects(spriteBounds)) {
                    collidingWith.push(id);
                }
            }
        });
        return collidingWith;
    }

    /**
     *
     * @param sprite A Movable Sprite (one with a checkBox).
     * @param allSprites All sprites needing to check against (ones with an interactBox).
     * @return A Stack of all Interactable Sprite's IDs that this Moveable Sprite's checkBox is colliding with.
     * @see Movable
     * @see Interactable
     */
    public static Stack<Integer> isInteracting(Movable sprite, Map<Integer, Interactable> allSprites) {
        Stack<Integer> collidingWith = new Stack<>();
        Bounds spriteBounds = sprite.getCheckBox().getBounds();
        allSprites.forEach((id, other) -> {
            if (!(sprite.getCheckBox().getID() == id)) {
                if (other.getInteractBox().getBounds().intersects(spriteBounds)) {
                    collidingWith.push(id);
                }
            }
        });
        return collidingWith;
    }

    /**
     * @param avoidID The ID of the hurtBox to avoid checking for (don't hit yourself).
     * @param wpn A Weaponry Sprite (one with a hitBox).
     * @param allSprites All sprites needing to check against (ones with a hurtBox).
     * @return A Stack of all Hurtable Sprite's IDs that this Weaponry Sprite's hitBox is colliding with.
     * @see Weaponry
     * @see Hurtable
     */
    public static Stack<Integer> isHitting(Integer avoidID, Weaponry wpn, Map<Integer, Hurtable> allSprites) {
        Stack<Integer> ids = new Stack<>();
        Bounds wpnBounds = wpn.getHitBox().getBounds();
        allSprites.forEach((id, other) -> {
            if (!Objects.equals(avoidID, other.getHurtBox().getID())) {
                if (other.getHurtBox().getBounds().intersects(wpnBounds)) {
                    ids.push(id);
                }
            }
        });
        return ids;
    }
}
