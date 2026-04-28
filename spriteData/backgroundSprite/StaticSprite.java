package spriteData.backgroundSprite;

import collision.BoxSizer;
import javafx.scene.image.WritableImage;
import spriteData.FrameGen;

/** For sprites with only one frame. */
public class StaticSprite extends BGSprite {

    public StaticSprite() {
        final String DEFAULT_SPRITE = "file:resources/sprites/bg_sprites/stone_tower/idle_1x1_48x128.png";
        setUp(DEFAULT_SPRITE);
    }

    public StaticSprite(String pathToFile) {
        setUp(pathToFile);
    }

    private void setUp(String pathToFile) {
        WritableImage frame = FrameGen.genOneFrame(pathToFile);
        setFrame(frame);
        BoxSizer.sizeBoxWideBottom(pathToFile, getWorldBox());
        getPane().getChildren().add(getFrame());
        getGroup().getChildren().add(getPane());
        super.setUpWorldBox();
    }

}
