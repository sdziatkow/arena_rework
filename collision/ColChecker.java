package collision;

import javafx.geometry.Bounds;
import javafx.scene.shape.Path;
import spriteData.behavior.boxes.*;

import java.util.Map;
import java.util.Objects;
import java.util.Stack;

public class ColChecker {

    public static Stack<Integer> isPathColliding(int avoidID, Path p, Map<Integer, CollisionBox> allSprites) {
        Stack<Integer> collidingWith = new Stack<>();
        Bounds pathBounds = p.getBoundsInLocal();
        allSprites.forEach((id, other) -> {
            if (id != avoidID) {
                if (other.getBounds().intersects(pathBounds)) {
                    collidingWith.push(id);
                }
            }
        });
        return collidingWith;
    }

    /**
     *
     * @param checkFor box to check for.
     * @param checkAgainst All sprites needing to check against.
     * @return A Stack of all Collidable Sprite's IDs that this Moveable Sprite's checkBox is colliding with.
     * @see Movable
     */
    public static Stack<Integer> isColliding(CollisionBox checkFor, Map<Integer, CollisionBox> checkAgainst) {
        Stack<Integer> collidingWith = new Stack<>();
        checkAgainst.forEach((id, box) -> {
            if (checkFor.getID() != id) {
                if (checkFor.getBounds().intersects(box.getBounds())) {
                    collidingWith.push(id);
                }
            }
        });
        return collidingWith;
    }

    /**
     * @param avoidID An ID different from the given checkFor's ID that should not be checked.
     * @param checkFor box to check for.
     * @param checkAgainst All sprites needing to check against.
     * @return A Stack of all Collidable Sprite's IDs that this Moveable Sprite's checkBox is colliding with.
     * @see Movable
     */
    public static Stack<Integer> isColliding(
            int avoidID,
            CollisionBox checkFor,
            Map<Integer, CollisionBox> checkAgainst
    ) {
        Stack<Integer> collidingWith = new Stack<>();
        checkAgainst.forEach((id, box) -> {
            if (checkFor.getID() != id && avoidID != id) {
                if (checkFor.getBounds().intersects(box.getBounds())) {
                    collidingWith.push(id);
                }
            }
        });
        return collidingWith;
    }
}
