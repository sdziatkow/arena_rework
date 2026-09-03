package spriteData.weaponSprite;

import static collision.ColType.HITBOX;
import collision.CollisionBox;
import spriteData.FourWaySprite;

/** Basic class for all weapon sprites.
 *
 */
public class WeaponSprite extends FourWaySprite {
    public WeaponSprite() {
        final String DEFAULT_PATH = "file:resources/sprites/wpns/sword/attk_4x4_32x32.png";
        setUp(DEFAULT_PATH);
    }

    public WeaponSprite(String pathToFile) {
        setUp(pathToFile);
    }

    private void setUp(String pathToFile) {
        setUpSprite(this, pathToFile);

        final double[] HIT_BOX_BOUNDS = new double[] {10.0, 10.0, 12.0, 14.0};
        addBox(new CollisionBox(HITBOX));

        getBox(HITBOX).setBounds(HIT_BOX_BOUNDS);
        getBox(HITBOX).setBaseBounds(HIT_BOX_BOUNDS);
        getGroup().getChildren().add(getBox(HITBOX).getColBox());
    }
}
