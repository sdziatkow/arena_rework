package spriteData.backgroundSprite;

import collision.BoxSizer;
import collision.CollisionBox;
import javafx.scene.image.WritableImage;
import spriteData.FrameGen;
import spriteData.Sprite;
import static collision.ColType.WORLDBOX;

/** For sprites with only one frame. */
public class StaticSprite extends Sprite {

    public StaticSprite() {
        final String DEFAULT_SPRITE = "file:resources/sprites/bg_sprites/stone_tower/idle_1x1_48x128.png";
        setUp(DEFAULT_SPRITE);
    }

    public StaticSprite(String pathToFile) {
        setUp(pathToFile);
    }

    private void setUp(String pathToFile) {
        addBox(new CollisionBox(WORLDBOX));
        WritableImage frame = FrameGen.genOneFrame(pathToFile);
        setFrame(frame);
        BoxSizer.sizeBoxWideBottom(pathToFile, getBox(WORLDBOX));
        getPane().getChildren().add(getFrame());
        getGroup().getChildren().add(getPane());
        getGroup().getChildren().add(getBox(WORLDBOX).getColBox());
    }

}
