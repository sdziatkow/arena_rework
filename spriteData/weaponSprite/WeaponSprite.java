package spriteData.weaponSprite;

import collision.ColType;
import collision.CollisionBox;
import control.ArenaObject;
import spriteData.FourWaySprite;
import spriteData.behavior.boxes.Weaponry;

/** Basic class for all weapon sprites.
 *
 */
public class WeaponSprite extends FourWaySprite implements Weaponry {
    private CollisionBox hitBox;

    public WeaponSprite() {
        final String DEFAULT_PATH = "file:resources/sprites/wpns/sword/attk_4x4_32x32.png";
        setUp(DEFAULT_PATH);
    }

    public WeaponSprite(String pathToFile) {
        setUp(pathToFile);
    }

    private void setUp(String pathToFile) {
        setUpSprite(this, pathToFile);

        hitBox = new CollisionBox(ColType.HITBOX);

        final double[] HIT_BOX_BOUNDS = new double[] {10.0, 10.0, 12.0, 14.0};
        hitBox.setBounds(HIT_BOX_BOUNDS);
        hitBox.setBaseBounds(HIT_BOX_BOUNDS);
        getGroup().getChildren().add(hitBox.getColBox());
    }

    @Override
    public void setID(Integer ID) {
        super.setID(ID);
        hitBox.setID(ID);
    }

    @Override
    public CollisionBox getHitBox() { return hitBox; }
}
