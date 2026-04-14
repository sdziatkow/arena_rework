package spriteData.charSprite;

import javafx.scene.image.WritableImage;
import spriteData.Dir;
import spriteData.FrameGen;

/** Character Sprite where each frame is, [width, height], [16, 32] on the spriteSheet.
 * This object's StackPane( .getPane() ) field has [width, height] of [32, 32]
 */
public class CharSprite16x32 extends CharSprite {

    public CharSprite16x32() {
        final String DEFAULT_PATH = "file:resources/sprites/character/character_move.png";
        CharSprite.setUpSprite(this, DEFAULT_PATH);
        CharSprite.setUpBoxes(this, null);
    }
    public CharSprite16x32(String pathToSheet) {
        CharSprite.setUpSprite(this, pathToSheet);
        CharSprite.setUpBoxes(this, null);
    }
}
