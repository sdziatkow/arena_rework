package spriteData.behavior.boxes;

import collision.CheckBox;

/**
 * For all Sprites who have a CheckBox to check for collision against Collidables.
 */
public interface Movable {

    /** @return CollisionBox Object of ColType.CHECKBOX associated with this Sprite. */
    CheckBox getCheckBox();
}
