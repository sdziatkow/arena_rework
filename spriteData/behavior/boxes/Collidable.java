package spriteData.behavior.boxes;

import collision.CollisionBox;

/**
 * For all Sprites that are able to be collided with by Movable Sprites.
 */
public interface Collidable {

    /** @return CollisionBox Object of ColType.WORLDBOX associated with this Sprite. */
    CollisionBox getWorldBox();
}
