package spriteData.behavior.boxes;

import collision.CollisionBox;
import spriteData.Dir;

/**
 * For all Sprites who have a CheckBox to check for collision against Collidables.
 */
public interface Movable {

    /** @return CollisionBox Object of ColType.CHECKBOX associated with this Sprite. */
    CollisionBox getCheckBox();

    Dir getDir();
    void switchDir(Dir direction);
}
