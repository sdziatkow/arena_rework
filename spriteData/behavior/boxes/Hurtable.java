package spriteData.behavior.boxes;

import collision.CollisionBox;

/**
 * For all Sprites that have a hurtBox
 */
public interface Hurtable {

    /** @return CollisionBox Object of ColType.HURTBOX associated with this Sprite. */
    CollisionBox getHurtBox();
}
