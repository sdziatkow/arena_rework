package spriteData.behavior.boxes;

import collision.CollisionBox;

/**
 * For all Sprites that have a hitBox.
 */
public interface Weaponry {

    /** @return CollisionBox Object of ColType.HITBOX associated with this Sprite. */
    CollisionBox getHitBox();
}
