package spriteData.charSprite;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import movement.MvState;
import spriteData.behavior.combat.Combatant;
import spriteData.weaponSprite.WPSprite32x32;
import spriteData.weaponSprite.WeaponSprite;

public abstract class CombatSprite extends CharSprite implements Combatant {
    private WPSprite32x32 attkSprite;
    private WPSprite32x32 wpSprite;
    private MvState state;

    public CombatSprite() {
        final String MOVE_SHEET = "file:resources/sprites/character/character_move.png";
        final String ATTK_SHEET = "file:resources/sprites/character/character_attk.png";
        setUp(MOVE_SHEET, ATTK_SHEET);
    }

    /**
     * @param moveSheet The path to the .png file that stores this Sprite's movement animation starting at [0, 0].
     * @param attkSheet The path to the .png file that stores this Sprite's attack animation starting at [0, 0].
     */
    public CombatSprite(String moveSheet, String attkSheet) {
        setUp(moveSheet, attkSheet);
    }

    private void setUp(String moveSheet, String attkSheet) {
        CharSprite.setUpSprite(this, moveSheet);
        CharSprite.setUpBoxes(this, null);

        attkSprite = new WPSprite32x32(attkSheet);
        wpSprite = new WPSprite32x32();

        getPane().getChildren().add(attkSprite.getFrame());
        getPane().getChildren().add(wpSprite.getGroup());

        attkSprite.getFrame().setVisible(false);
        wpSprite.getFrame().setVisible(false);

        EventHandler<ActionEvent> onAttkAnimEnd = new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                getFrame().setVisible(true);
                attkSprite.getFrame().setVisible(false);
                wpSprite.getFrame().setVisible(false);
            }
        };

        wpSprite.getAnim().setOnFinished(onAttkAnimEnd);

        state = MvState.MOVING;
    }

    @Override
    public void onAttk() {
        if (!attkSprite.isAnimRunning() && !wpSprite.isAnimRunning()) {
            getFrame().setVisible(false);
            setUpAttk(attkSprite);
            setUpAttk(wpSprite);
        }
    }
    private void setUpAttk(WPSprite32x32 sprite) {
        sprite.switchDir(getDir());
        sprite.idleFrame();
        sprite.getFrame().setVisible(true);
        sprite.getAnim().play();
    }

    public MvState state() { return state; }

    @Override
    public WeaponSprite getWPSprite() { return wpSprite; }

    public WeaponSprite getAttkSprite() { return attkSprite; }

//CLASS-SPECIFIC---------------------------------------------------------------------------------------------------------

}
