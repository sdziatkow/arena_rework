package worldData.objectData;

import collision.ColType;
import collision.CollisionBox;
import spriteData.Sprite;

import java.util.HashMap;
import java.util.Map;

public class BoxTracker {
    private static final Map<ColType, Map<Integer, CollisionBox>> boxes = new HashMap<>(ColType.all().length);

    public static void trackBoxes(Sprite sprite) {
        for (ColType t : ColType.all()) {
            if (sprite.hasBox(t)) {
                boxes.putIfAbsent(t, new HashMap<>(10));
                boxes.get(t).put(sprite.getID(), sprite.getBox(t));
            }
        }
    }

    public static void removeBoxes(Sprite sprite) {
        for (ColType t : ColType.all()) {
            if (sprite.hasBox(t)) {
                boxes.get(t).remove(sprite.getID(), sprite.getBox(t));
            }
        }
    }

    public static Map<Integer, CollisionBox> getBoxes(ColType t) {return boxes.get(t);}
    public static CollisionBox getBox(ColType t, int id) {return boxes.get(t).get(id);}

}
