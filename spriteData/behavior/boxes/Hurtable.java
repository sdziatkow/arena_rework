package spriteData.behavior.boxes;

import collision.CollisionBox;

/**
 * For all Sprites that have a hurtBox
 */
public interface Hurtable {
    void onHurt(int attackerID);
}
