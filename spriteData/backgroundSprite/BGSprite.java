package spriteData.backgroundSprite;

import collision.CollisionBox;
import spriteData.Sprite;
import spriteData.behavior.boxes.Collidable;

public abstract class BGSprite extends Sprite implements Collidable {
    private CollisionBox worldBox;

    public BGSprite() {
        worldBox = new CollisionBox();
    }

    public void setUpWorldBox() {
        getGroup().getChildren().add(worldBox.getColBox());
    }

    @Override
    public CollisionBox getWorldBox() { return worldBox; }

    @Override
    public void setID(Integer id) {
        super.setID(id);
        worldBox.setID(id);
    }
}
