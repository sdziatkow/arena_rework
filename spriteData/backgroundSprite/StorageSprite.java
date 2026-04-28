package spriteData.backgroundSprite;

import collision.BoxSizer;
import collision.ColType;
import collision.CollisionBox;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.image.WritableImage;
import menus.Menus;
import spriteData.AnimSprite;
import spriteData.FrameGen;
import spriteData.behavior.boxes.Collidable;
import spriteData.behavior.boxes.Interactable;
import worldData.WorldData;

public class StorageSprite extends AnimSprite implements Collidable, Interactable {
    private CollisionBox worldBox;
    private CollisionBox interactBox;
    private boolean isOpen;

    public StorageSprite() {
        final String DEFAULT_PATH = "file:resources/sprites/bg_sprites/chest/open_1x3_20x20.png";
        setUp(DEFAULT_PATH);
    }

    public StorageSprite(String pathToFile) {
        setUp(pathToFile);
    }

    private void setUp(String pathToFile) {
        WritableImage[] frameSet = FrameGen.genFrames(pathToFile)[0];
        setTotalFrames(frameSet.length);
        setFrameSet(frameSet);
        setFrame(frameSet[0]);
        getPane().getChildren().add(getFrame());
        getGroup().getChildren().add(getPane());

        getAnim().setOnFinished(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                reverseFrames();
            }
        });

        worldBox = new CollisionBox();
        interactBox = new CollisionBox(ColType.INTERACTBOX);

        BoxSizer.sizeBoxSmallMid(pathToFile, worldBox);
        BoxSizer.sizeBoxEvenlyBiggerThan(interactBox, worldBox);

        getGroup().getChildren().add(worldBox.getColBox());
        getGroup().getChildren().add(interactBox.getColBox());
        isOpen = false;
    }

    @Override
    public CollisionBox getWorldBox() {
        return worldBox;
    }

    @Override
    public CollisionBox getInteractBox() {
        return interactBox;
    }

    @Override
    public void setID(Integer id) {
        super.setID(id);
        interactBox.setID(id);
    }

    @Override
    public void onInteract(int interactorID) {
        if (!isAnimRunning()) {
            getAnim().play();
            isOpen = !isOpen;
        }
        if (isOpen) {
            WorldData.openStorageInteraction(interactorID, getID());
        } else Menus.clearMenus();

    }
}
