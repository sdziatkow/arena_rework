package spriteData.weaponSprite;

import collision.ColType;
import collision.CollisionBox;
import spriteData.FourWaySprite;
import spriteData.behavior.boxes.Weaponry;

/**
 *
 */
public class WeaponSprite extends FourWaySprite implements Weaponry {
    private CollisionBox hitBox;

    public WeaponSprite() {
        hitBox = new CollisionBox(ColType.HITBOX);
    }

    @Override
    public CollisionBox getHitBox() { return hitBox; }
}
