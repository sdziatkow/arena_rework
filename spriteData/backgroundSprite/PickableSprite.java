package spriteData.backgroundSprite;

import collision.BoxSizer;
import collision.CollisionBox;
import spriteData.behavior.boxes.Interactable;
import worldData.WorldData;

import static collision.ColType.WORLDBOX;
import static collision.ColType.INTERACTBOX;

/** For sprites that can be picked up and are only one frame. */
public class PickableSprite extends StaticSprite implements Interactable {

    public PickableSprite() {
        final String DEFAULT_FILE = "file:resources/sprites/wpns/sword/idle_1x1_16x16.png";
        super(DEFAULT_FILE);
        setUp(DEFAULT_FILE);
    }

    public PickableSprite(String pathToFile) {
        super(pathToFile);
        setUp(pathToFile);
    }

    private void setUp(String pathToFile) {
        addBox(new CollisionBox(INTERACTBOX));
        getGroup().getChildren().add(getBox(INTERACTBOX).getColBox());

        BoxSizer.sizeBoxSmallMid(pathToFile, getBox(WORLDBOX));
        BoxSizer.sizeBoxEvenlyBiggerThan(getBox(INTERACTBOX), getBox(WORLDBOX), 2.0);
    }

    @Override
    public void onInteract(int interactorID) {
        WorldData.onItemPickedUp(interactorID, getID());
        WorldData.removeSprite(getID());
    }

}
