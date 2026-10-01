package spriteData.behavior;

import collision.BoxSizer;
import collision.ColType;
import collision.CollisionBox;
import control.runtimeTrackers.WorldEntity;
import control.runtimeTrackers.WorldTracker;
import dialogue.Dialogue;
import movement.NPCState;
import spriteData.charSprite.CharSprite;
import spriteData.charSprite.CombatSprite;
import static collision.ColType.*;
import static control.runtimeTrackers.WorldEntity.DIALOGUE;

public class SpriteBehavior {

    public static void enableHostility(CombatSprite sprite) {
        sprite.addBox(new CollisionBox(DETECTBOX));
        sprite.getGroup().getChildren().add(sprite.getBox(DETECTBOX).getColBox());
        double factor = 10.0;
        BoxSizer.sizeBoxEvenlyBiggerThan(sprite.getBox(DETECTBOX), sprite.getBox(WORLDBOX), factor);
        sprite.getBox(DETECTBOX).getColBox().setX(-factor * 2);
        sprite.getBox(DETECTBOX).getColBox().setY(-factor * 2);
        sprite.setNPCState(NPCState.HUNTING);
        //sprite.getBox(DETECTBOX).getColBox().setOpacity(1);
    }

    public static void enableDialogue(CharSprite sprite, Dialogue d) {
        sprite.addBox(new CollisionBox(ColType.INTERACTBOX));
        BoxSizer.sizeBoxEvenlyBiggerThan(sprite.getBox(ColType.INTERACTBOX), sprite.getBox(ColType.WORLDBOX), 2.0);
        sprite.getGroup().getChildren().add(sprite.getBox(ColType.INTERACTBOX).getColBox());
        d.setID(sprite.getID());
        WorldTracker.add(DIALOGUE, d);
        //sprite.getBox(INTERACTBOX).getColBox().setOpacity(1);
    }
}
