package spriteData.charSprite;

import collision.ColType;
import collision.CollisionBox;
import javafx.scene.image.WritableImage;
import spriteData.Dir;
import spriteData.FrameGen;
import spriteData.MovingSprite;
import spriteData.behavior.boxes.Collidable;
import spriteData.behavior.boxes.Hurtable;

import static collision.ColType.*;

public abstract class CharSprite extends MovingSprite implements Collidable, Hurtable {
    private CollisionBox worldBox;
    private CollisionBox hurtBox;

    public CharSprite() {
        worldBox = new CollisionBox();
        hurtBox = new CollisionBox(HURTBOX);
    }

    @Override
    public CollisionBox getWorldBox() {
        return worldBox;
    }

    @Override
    public CollisionBox getHurtBox() {
        return hurtBox;
    }

    public static void setUpSprite(CharSprite sprite, String pathToSheet) {
        final int TOTAL_FRAMES = 4; // For each individual animation.
        final int TOTAL_DIRECTIONS = 4; // Total animations. 1 per direction. 4 Total directions.

        sprite.setSheet(pathToSheet);
        sprite.setTotalFrames(TOTAL_FRAMES);

        final WritableImage[][] ALL_FRAMES = FrameGen.genFrames( // Generate frames from spriteSheet.
                TOTAL_DIRECTIONS, TOTAL_FRAMES,
                sprite.getSheet(),
                0, 0, 16, 32
        );

        sprite.setAllFrames(Dir.N, ALL_FRAMES[0]);
        sprite.setAllFrames(Dir.S, ALL_FRAMES[1]);
        sprite.setAllFrames(Dir.E, ALL_FRAMES[2]);
        sprite.setAllFrames(Dir.W, ALL_FRAMES[3]);
        sprite.switchDir(Dir.S);
        sprite.idleFrame();

        sprite.getPane().getChildren().add(sprite.getFrame());
        sprite.getPane().setPrefHeight(32);
        sprite.getPane().setPrefWidth(32);
        sprite.getGroup().getChildren().add(sprite.getPane());
    }

    /**
     * Will set the bounds of boxes of given types and add them to this Sprite's Group.
     * @param types If given null, will set up WORLDBOX, CHECKBOX, and HURTBOX by default.
     */
    public static void setUpBoxes(CharSprite sprite, ColType[] types) {
        final double[] WORLD_BOX_BOUNDS = new double[]{8.0, 8.0, 16.0, 20.0};
        final double[] CHECK_BOX_BOUNDS = new double[]{12.0, 12.0, 10.0, 12.0};
        final double[] HURT_BOX_BOUNDS = new double[]{11.5, 12.0, 10.0, 12.0};

        if (types == null) types = new ColType[]{WORLDBOX, CHECKBOX, HURTBOX};

        for (int t = 0; t < types.length; ++t) {
            switch (types[t]) {
                case WORLDBOX:
                    sprite.getWorldBox().setBounds(WORLD_BOX_BOUNDS);
                    sprite.getGroup().getChildren().add(sprite.getWorldBox().getColBox());
                    break;
                case CHECKBOX:
                    sprite.getCheckBox().setBaseBounds(CHECK_BOX_BOUNDS);
                    sprite.getCheckBox().contract();
                    sprite.getGroup().getChildren().add(sprite.getCheckBox().getColBox());
                    break;
                case HURTBOX:
                    sprite.getHurtBox().setBounds(HURT_BOX_BOUNDS);
                    sprite.getGroup().getChildren().add(sprite.getHurtBox().getColBox());
                    break;
                default:
                    break;
            }
        }
    }
}
