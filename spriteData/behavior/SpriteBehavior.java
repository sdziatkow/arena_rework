package spriteData.behavior;

import collision.BoxSizer;
import collision.CollisionBox;
import movement.NPCState;
import spriteData.charSprite.CombatSprite;

import static collision.ColType.DETECTBOX;
import static collision.ColType.WORLDBOX;

public class SpriteBehavior {

    public static void enableHostility(CombatSprite sprite) {
        sprite.addBox(new CollisionBox(DETECTBOX));
        sprite.getGroup().getChildren().add(sprite.getBox(DETECTBOX).getColBox());
        BoxSizer.sizeBoxEvenlyBiggerThan(sprite.getBox(DETECTBOX), sprite.getBox(WORLDBOX), 7.0);
        sprite.setNPCState(NPCState.HUNTING);
        sprite.getBox(DETECTBOX).getColBox().setOpacity(1);
    }
}
