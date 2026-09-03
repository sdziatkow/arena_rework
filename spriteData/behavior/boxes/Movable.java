package spriteData.behavior.boxes;

import collision.CollisionBox;
import spriteData.Dir;

/**
 * For all Sprites who have a CheckBox to check for collision against Collidables.
 */
public interface Movable {
    Dir getDir();
    void switchDir(Dir direction);
}
