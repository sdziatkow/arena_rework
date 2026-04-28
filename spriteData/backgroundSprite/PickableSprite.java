package spriteData.backgroundSprite;

import collision.BoxSizer;
import collision.ColType;
import collision.CollisionBox;
import spriteData.behavior.boxes.Interactable;
import worldData.WorldData;
import worldData.statData.StorageTracker;

/** For sprites that can be picked up and are only one frame. */
public class PickableSprite extends StaticSprite implements Interactable {
    CollisionBox interactBox;

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
        interactBox = new CollisionBox(ColType.INTERACTBOX);
        getGroup().getChildren().add(interactBox.getColBox());

        BoxSizer.sizeBoxSmallMid(pathToFile, getWorldBox());
        BoxSizer.sizeBoxBigMid(pathToFile, interactBox);
    }

    @Override
    public void setID(Integer id) {
        super.setID(id);
        interactBox.setID(id);
    }

    @Override
    public CollisionBox getInteractBox() {return interactBox;}

    @Override
    public void onInteract(int interactorID) {
        WorldData.onItemPickedUp(interactorID, getID());
        WorldData.removeSprite(getID());
        System.out.println(StorageTracker.storages.get(interactorID).all().toString());
    }

}
