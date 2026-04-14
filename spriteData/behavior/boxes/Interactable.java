package spriteData.behavior.boxes;

import collision.CollisionBox;

public interface Interactable {

    /** @return CollisionBox Object of ColType.INTERACTBOX associated with this Sprite. */
    CollisionBox getInteractBox();
}
