package spriteData.weaponSprite;

import javafx.scene.image.WritableImage;
import spriteData.Dir;
import spriteData.FrameGen;

/** WeaponSprite where each frame is 32x32.
 * Intended to be used with CharSprite16x32.
 */
public class WPSprite32x32 extends WeaponSprite{

    public WPSprite32x32() {
        final String DEFAULT_PATH = "file:resources/sprites/sword.png";
        setUp(DEFAULT_PATH);
    }

    public WPSprite32x32(String pathToFile) {
        setUp(pathToFile);
    }

    private void setUp(String pathToFile) {
        final int TOTAL_FRAMES = 4; // For each individual animation.
        final int TOTAL_DIRECTIONS = 4; // Total animations. 1 per direction. 4 Total directions.
        final double[] HIT_BOX_BOUNDS = new double[] {0, 0, 32, 32};

        setSheet(pathToFile);
        setTotalFrames(TOTAL_FRAMES);

        final WritableImage[][] ALL_FRAMES = FrameGen.genFrames(
                TOTAL_DIRECTIONS, TOTAL_FRAMES,
                getSheet(),
                0, 0, 32, 32
        );

        setAllFrames(Dir.N, ALL_FRAMES[0]);
        setAllFrames(Dir.S, ALL_FRAMES[1]);
        setAllFrames(Dir.E, ALL_FRAMES[2]);
        setAllFrames(Dir.W, ALL_FRAMES[3]);
        switchDir(Dir.S);
        idleFrame();

        getPane().getChildren().add(getFrame());
        getPane().setPrefWidth(32);
        getPane().setPrefHeight(32);
        getGroup().getChildren().add(getPane());

        getHitBox().setBounds(HIT_BOX_BOUNDS);
        getGroup().getChildren().add(getHitBox().getColBox());
    }
}
