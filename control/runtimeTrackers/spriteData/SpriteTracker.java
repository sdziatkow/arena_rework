package control.runtimeTrackers.spriteData;

import spriteData.Sprite;
import spriteData.backgroundSprite.StaticSprite;
import spriteData.behavior.boxes.*;
import spriteData.behavior.combat.Combatant;
import spriteData.weaponSprite.WeaponSprite;

import java.util.*;

import static control.runtimeTrackers.spriteData.SpriteType.*;

public abstract class SpriteTracker {
    public static Map<SpriteType, HashMap<Integer, Sprite>> all = new HashMap<>();

    public static void add(Sprite sprite) {
        if (sprite == null || sprite.getID() == null) return;
        ArrayList<SpriteType> types = new ArrayList<>();
        if (sprite instanceof Hurtable) types.add(HURTABLE);
        if (sprite instanceof Movable) types.add(MOVABLE);
        if (sprite instanceof Interactable) types.add(INTERACTABLE);
        if (sprite instanceof Combatant) types.add(COMBATANT);

        if (sprite instanceof StaticSprite) types.add(STATIC);
        if (sprite instanceof WeaponSprite) types.add(WEAPON);
        for (int t = 0; t < types.size(); ++t) {
            all.putIfAbsent(types.get(t), new HashMap<>());
            all.get(types.get(t)).put(sprite.getID(), sprite);
            if (types.get(t).equals(COMBATANT)) add(((Combatant)sprite).getWPSprite());
        }
        BoxTracker.trackBoxes(sprite);
    }

    public static void remove(SpriteType type, int arenaID) {
        validateRetrieval(type, arenaID);
        Sprite sprite = all.get(type).remove(arenaID);
        BoxTracker.removeBoxes(sprite);
    }

    public static Sprite get(SpriteType type, int arenaID) {
        validateRetrieval(type, arenaID);
        return all.get(type).get(arenaID);
    }

    public static Set<Sprite> getAll(SpriteType type) {
        validateRetrieval(type);
        return new HashSet<>(all.get(type).values());
    }

    public static Set<Integer> getAllIDs(SpriteType type) throws IllegalArgumentException {
        validateRetrieval(type);
        return all.get(type).keySet();
    }

    private static void validateRetrieval(SpriteType type) {
        if
        (
                type == null
                        || all.get(type) == null
        ) {
            throw new IllegalArgumentException(
                    "Attempting to access a Sprite that does not exist or was not properly added.");
        }
    }

    private static void validateRetrieval(SpriteType type, int arenaID) {
        validateRetrieval(type);
        if (all.get(type).get(arenaID) == null) {
            throw new IllegalArgumentException(
                    "Attempting to access a Sprite that does not exist or was not properly added.");
        }
    }
}
