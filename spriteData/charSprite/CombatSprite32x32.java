package spriteData.charSprite;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;

/** Character's Sprite attack frames.<br>
 * Each frame is, [width, height], [32, 32] on the spriteSheet.<br>
 */
public class CombatSprite32x32 extends CombatSprite {

    public CombatSprite32x32() {
        super();
    }

    public CombatSprite32x32(String moveSheet, String attkSheet) {
        super(moveSheet, attkSheet);
    }



}
