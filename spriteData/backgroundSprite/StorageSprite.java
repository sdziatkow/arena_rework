package spriteData.backgroundSprite;

import collision.BoxSizer;
import collision.CollisionBox;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.image.WritableImage;
import menus.Menus;
import spriteData.AnimSprite;
import spriteData.FrameGen;
import spriteData.behavior.boxes.Interactable;
import worldData.WorldData;
import static collision.ColType.WORLDBOX;
import static collision.ColType.INTERACTBOX;

public class StorageSprite extends AnimSprite implements Interactable {
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

        addBox(new CollisionBox(WORLDBOX));
        addBox(new CollisionBox(INTERACTBOX));

        BoxSizer.sizeBoxSmallMid(pathToFile, getBox(WORLDBOX));
        BoxSizer.sizeBoxEvenlyBiggerThan(getBox(INTERACTBOX), getBox(WORLDBOX), 2.0);

        getGroup().getChildren().add(getBox(WORLDBOX).getColBox());
        getGroup().getChildren().add(getBox(INTERACTBOX).getColBox());

        isOpen = false;
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
