package collision;

import javafx.geometry.Bounds;
import spriteData.behavior.boxes.Collidable;
import spriteData.behavior.boxes.Hurtable;
import spriteData.behavior.boxes.Movable;
import spriteData.behavior.boxes.Weaponry;

import java.util.ArrayList;

public class ColChecker {

    public static boolean isColliding(Movable sprite, ArrayList<Collidable> allSprites) {
        Collidable currSprite;
        Bounds currBounds;
        Bounds spriteBounds = sprite.getCheckBox().getBounds();
        for (int s = 0; s < allSprites.size(); ++s) {
            currSprite = allSprites.get(s);
            currBounds = currSprite.getWorldBox().getBounds();
            if (sprite != currSprite && currBounds.intersects(spriteBounds)) {
                return true;
            }
        }
        return false;
    }

    public static boolean isHitting(Weaponry sprite, ArrayList<Hurtable> allSprites) {
        Hurtable currSprite;
        Bounds currBounds;
        Bounds spriteBounds = sprite.getHitBox().getBounds();
        for (int s = 0; s < allSprites.size(); ++s) {
            currSprite = allSprites.get(s);
            currBounds = currSprite.getHurtBox().getBounds();
            if (currBounds.intersects(spriteBounds)) {
                return true;
            }
        }
        return false;
    }
}
