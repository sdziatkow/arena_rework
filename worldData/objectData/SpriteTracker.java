package worldData.objectData;

import spriteData.Sprite;
import spriteData.backgroundSprite.PickableSprite;
import spriteData.backgroundSprite.StaticSprite;
import spriteData.behavior.boxes.*;
import spriteData.charSprite.CharSprite;
import spriteData.charSprite.CombatSprite;
import spriteData.weaponSprite.WeaponSprite;

import java.util.HashMap;
import java.util.Map;

public abstract class SpriteTracker {

    public static int playerID;
    public static Map<Integer, Sprite> allSprites = new HashMap<>();

    //BG-SPRITES.
    public static Map<Integer, StaticSprite> staticSprites = new HashMap<>();
    public static Map<Integer, PickableSprite> pickables = new HashMap<>();

    // GAME-CHARS.
    public static Map<Integer, CharSprite> charSprites = new HashMap<>();
    public static Map<Integer, CombatSprite> combatSprites = new HashMap<>();
    public static Map<Integer, WeaponSprite> weaponSprites = new HashMap<>();

    // BEHAVIORS.
    public static Map<Integer, Interactable> interactables = new HashMap<>();
    public static Map<Integer, Hurtable> hurtables = new HashMap<>();
    public static Map<Integer, Movable> movables = new HashMap<>();

    public static void trackSprite(Sprite sprite) {
        allSprites.putIfAbsent(sprite.getID(), sprite);
        if (sprite instanceof CombatSprite c) {
            trackSprite(c.getWPSprite());
            combatSprites.put(c.getID(), c);
        }
        if (sprite instanceof CharSprite c) {
            charSprites.put(c.getID(), c);
        }
        if (sprite instanceof WeaponSprite w) {
            weaponSprites.put(w.getID(), w);
        }
        if (sprite instanceof StaticSprite s) {
            staticSprites.put(s.getID(), s);
        }
        if (sprite instanceof PickableSprite p) {
            pickables.put(p.getID(), p);
        }

        if (sprite instanceof Interactable i) {
            interactables.put(sprite.getID(), i);
        }
        if (sprite instanceof Hurtable h) {
            hurtables.put(sprite.getID(), h);
        }
        if (sprite instanceof Movable m) {
            movables.put(sprite.getID(), m);
        }
        BoxTracker.trackBoxes(sprite);
    }

    public static void removeSprite(Sprite sprite) {
        allSprites.remove(sprite.getID(), sprite);
        if (sprite instanceof CombatSprite c) {
            removeSprite(c.getWPSprite());
            combatSprites.remove(c.getID(), c);
        }
        if (sprite instanceof CharSprite c) {
            charSprites.remove(c.getID(), c);
        }
        if (sprite instanceof WeaponSprite w) {
            weaponSprites.remove(w.getID(), w);
        }
        if (sprite instanceof StaticSprite s) {
            staticSprites.remove(s.getID(), s);
        }
        if (sprite instanceof PickableSprite p) {
            pickables.remove(p.getID(), p);
        }

        if (sprite instanceof Interactable i) {
            interactables.remove(sprite.getID(), i);
        }
        if (sprite instanceof Hurtable h) {
            hurtables.remove(sprite.getID(), h);
        }
        if (sprite instanceof Movable m) {
            movables.remove(sprite.getID(), m);
        }
        BoxTracker.removeBoxes(sprite);
    }

}
