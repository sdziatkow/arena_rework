package spriteData.charSprite;

import collision.BoxSizer;
import collision.ColType;
import collision.CollisionBox;
import movement.NPCState;
import spriteData.MovingSprite;
import spriteData.behavior.boxes.Collidable;
import spriteData.behavior.boxes.Hurtable;
import values.IntVal;

import static collision.ColType.*;

public class CharSprite extends MovingSprite implements Collidable, Hurtable {
    private CollisionBox worldBox;
    private CollisionBox hurtBox;
    private NPCState npcState;
    public IntVal dirCount;

    public CharSprite() {
        final String DEFAULT_PATH = "file:resources/sprites/character/move_4x4_16x32.png";
        setUp(DEFAULT_PATH);
    }
    public CharSprite(String pathToSheet) {
        setUp(pathToSheet);
    }

    private void setUp(String pathToSheet) {
        dirCount = new IntVal();
        dirCount.setMax(20);
        worldBox = new CollisionBox();
        hurtBox = new CollisionBox(HURTBOX);

        setUpSprite(this, pathToSheet, new int[]{32, 32});
        BoxSizer.sizeBoxSmallMid(pathToSheet, worldBox);
        getGroup().getChildren().add(getWorldBox().getColBox());

        BoxSizer.sizeBoxBigMid(pathToSheet, hurtBox);
        getGroup().getChildren().add(hurtBox.getColBox());

        getCheckBox().setBaseBounds(worldBox.getBaseBounds());
        getCheckBox().contract();
        getGroup().getChildren().add(getCheckBox().getColBox());
//        setUpBoxes(this, null);
    }

    @Override
    public void setID(Integer ID) {
        super.setID(ID);
        worldBox.setID(ID);
        hurtBox.setID(ID);
    }

    @Override
    public CollisionBox getWorldBox() {
        return worldBox;
    }

    @Override
    public CollisionBox getHurtBox() {
        return hurtBox;
    }

    public NPCState getNPCState() { return npcState; }
    public void setNPCState(NPCState s) { npcState = s; }

    /**
     * Will set the bounds of boxes of given types and add them to this Sprite's Group.
     * @param types If given null, will set up WORLDBOX, CHECKBOX, and HURTBOX by default.
     */
    public static void setUpBoxes(CharSprite sprite, ColType[] types) {
        final double[] WORLD_BOX_BOUNDS = new double[]{12.5, 12.5, 6.0, 8.0};
        final double[] CHECK_BOX_BOUNDS = new double[]{10.0, 10.0, 12.0, 14.0};

        if (types == null) types = new ColType[]{WORLDBOX, CHECKBOX, HURTBOX};

        for (int t = 0; t < types.length; ++t) {
            switch (types[t]) {
                case WORLDBOX:
                    sprite.getWorldBox().setBounds(WORLD_BOX_BOUNDS);
                    sprite.getGroup().getChildren().add(sprite.getWorldBox().getColBox());
                    break;
                case CHECKBOX:
                    sprite.getCheckBox().setBaseBounds(WORLD_BOX_BOUNDS);
                    sprite.getCheckBox().contract();
                    sprite.getGroup().getChildren().add(sprite.getCheckBox().getColBox());
                    break;
                case HURTBOX:
                    sprite.getHurtBox().setBounds(WORLD_BOX_BOUNDS);
                    sprite.getGroup().getChildren().add(sprite.getHurtBox().getColBox());
                    break;
                default:
                    break;
            }
        }
    }
}
