package spriteData.behavior;

import collision.BoxSizer;
import collision.CollisionBox;
import movement.NPCState;
import spriteData.charSprite.CombatSprite;

import static collision.ColType.*;

public class SpriteBehavior {

    public static void enableHostility(CombatSprite sprite) {
        sprite.addBox(new CollisionBox(DETECTBOX));
        sprite.getGroup().getChildren().add(sprite.getBox(DETECTBOX).getColBox());
        double factor = 10.0;
        BoxSizer.sizeBoxEvenlyBiggerThan(sprite.getBox(DETECTBOX), sprite.getBox(WORLDBOX), factor);
        sprite.getBox(DETECTBOX).getColBox().setX(-factor * 2);
        sprite.getBox(DETECTBOX).getColBox().setY(-factor * 2);
        sprite.setNPCState(NPCState.HUNTING);
        sprite.getBox(DETECTBOX).getColBox().setOpacity(1);
    }
}
