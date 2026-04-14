package spriteData.backgroundSprite;

import collision.CollisionBox;
import spriteData.Sprite;
import spriteData.behavior.boxes.Collidable;

public class BGSprite extends Sprite implements Collidable {
    private CollisionBox worldBox;

    public BGSprite() { worldBox = new CollisionBox(); }

    @Override
    public CollisionBox getWorldBox() { return worldBox; }
}
