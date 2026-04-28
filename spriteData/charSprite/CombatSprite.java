package spriteData.charSprite;

import javafx.animation.KeyFrame;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.util.Duration;
import spriteData.Dir;
import spriteData.FourWaySprite;
import spriteData.behavior.combat.Combatant;
import spriteData.weaponSprite.WeaponSprite;
import worldData.WorldData;

public class CombatSprite extends CharSprite implements Combatant {
    private FourWaySprite attkSprite;
    private WeaponSprite wpSprite;

    public CombatSprite() {
        final String DEFAULT_ATTK_SHEET = "file:resources/sprites/character/attk_4x4_32x32.png";
        super();
        setUp(DEFAULT_ATTK_SHEET);
    }

    /**
     * @param moveSheet The path to the .png file that stores this Sprite's movement animation starting at [0, 0].
     * @param attkSheet The path to the .png file that stores this Sprite's attack animation starting at [0, 0].
     */
    public CombatSprite(String moveSheet, String attkSheet) {
        super(moveSheet);
        setUp(attkSheet);
    }

    private void setUp(String attkSheet) {
        attkSprite = new WeaponSprite(attkSheet);
        wpSprite = new WeaponSprite("file:resources/sprites/wpns/nothing_4x4_32x32.png");

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
                wpSprite.getHitBox().contract();
            }
        };

        EventHandler<ActionEvent> onAnimFrameFinish = new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e) {
                wpSprite.nextFrame();
                WorldData.triggerAttk(getID(), wpSprite.getID());
            }
        };

        wpSprite.setAnimEvent(new KeyFrame(new Duration(BASE_FRAME_RATE), onAnimFrameFinish));
        wpSprite.getAnim().getKeyFrames().clear();
        wpSprite.getAnim().getKeyFrames().add(wpSprite.getAnimEvent());
        wpSprite.getAnim().setOnFinished(onAttkAnimEnd);
    }

    @Override
    public void onAttk() {
        double hitBoxMvAmnt = getWorldBox().getBounds().getWidth() / 2.0;
        if (getDir().equals(Dir.N) || getDir().equals(Dir.W)) hitBoxMvAmnt += (0.25 * hitBoxMvAmnt);
        wpSprite.getHitBox().checkDir(hitBoxMvAmnt, getDir());


        if (!attkSprite.isAnimRunning() && !wpSprite.isAnimRunning()) {
            getFrame().setVisible(false);
            setUpAttk(attkSprite);
            setUpAttk(wpSprite);
        }
    }
    private void setUpAttk(FourWaySprite sprite) {
        sprite.switchDir(getDir());
        sprite.idleFrame();
        sprite.getFrame().setVisible(true);
        sprite.getAnim().play();
    }

    @Override
    public void setWPSprite(WeaponSprite s) { wpSprite = s;}

    @Override
    public WeaponSprite getWPSprite() { return wpSprite; }

//CLASS-SPECIFIC---------------------------------------------------------------------------------------------------------

    @Override
    public void setID(Integer ID) {
        super.setID(ID);
        wpSprite.setID(ID);
    }

}
