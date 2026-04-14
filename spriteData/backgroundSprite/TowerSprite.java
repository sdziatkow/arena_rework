package spriteData.backgroundSprite;

import spriteData.Sprite;

public class TowerSprite extends BGSprite {

    public TowerSprite() {
        final String PATH_TO_SHEET      = "file:resources/sprites/overworld.png";
        final int[] FRAME_BOUNDS        = new int[]{0, 336, 48, 128};
        final double[] WORLD_BOX_BOUNDS = new double[]{6, 70, 34, 26};

        setSheet(PATH_TO_SHEET);
        setFrame(FRAME_BOUNDS);

        getPane().getChildren().add(getFrame());
        getPane().setPrefWidth(FRAME_BOUNDS[2]);
        getPane().setPrefHeight(FRAME_BOUNDS[3]);

        getWorldBox().setBounds(WORLD_BOX_BOUNDS);

        getGroup().getChildren().add(getPane());
        getGroup().getChildren().add(getWorldBox().getColBox());
    }
}
