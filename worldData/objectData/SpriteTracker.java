package worldData.objectData;

import spriteData.Sprite;
import spriteData.backgroundSprite.BGSprite;
import spriteData.backgroundSprite.PickableSprite;
import spriteData.behavior.boxes.*;
import spriteData.charSprite.CharSprite;
import spriteData.charSprite.CombatSprite;
import spriteData.weaponSprite.WeaponSprite;
import java.util.HashMap;
import java.util.Map;

public abstract class SpriteTracker {

    public static int playerID;
    public static Map<Integer, Sprite> allSprites = new HashMap<>();

    public static Map<Integer, Interactable> interactables = new HashMap<>();
    public static Map<Integer, Collidable> collidables = new HashMap<>();
    public static Map<Integer, BGSprite> bgSprites = new HashMap<>();
    public static Map<Integer, BGSprite> pickables = new HashMap<>();

    public static Map<Integer, Hurtable> hurtables = new HashMap<>();
    public static Map<Integer, Movable> movables = new HashMap<>();
    public static Map<Integer, CharSprite> charSprites = new HashMap<>();
    public static Map<Integer, CombatSprite> combatSprites = new HashMap<>();

    public static Map<Integer, Weaponry> weapons = new HashMap<>();
    public static Map<Integer, WeaponSprite> weaponSprites = new HashMap<>();

    public static void addSprite(Sprite sprite) {
        allSprites.putIfAbsent(sprite.getID(), sprite);
        if (sprite instanceof CombatSprite c) {
            addSprite(c.getWPSprite());
            combatSprites.put(c.getID(), c);
        }
        if (sprite instanceof CharSprite c) {
            charSprites.put(c.getID(), c);
        }
        if (sprite instanceof WeaponSprite w) {
            weaponSprites.put(w.getID(), w);
        }
        if (sprite instanceof BGSprite b) {
            bgSprites.put(b.getID(), b);
        }
        if (sprite instanceof PickableSprite p) {
            pickables.put(p.getID(), p);
        }

        if (sprite instanceof Collidable c) {
            collidables.put(c.getWorldBox().getID(), c);
        }
        if (sprite instanceof Hurtable h) {
            hurtables.put(h.getHurtBox().getID(), h);
        }
        if (sprite instanceof Movable m) {
            movables.put(m.getCheckBox().getID(), m);
        }
        if (sprite instanceof Weaponry w) {
            weapons.put(w.getHitBox().getID(), w);
        }
        if (sprite instanceof Interactable i) {
            interactables.put(i.getInteractBox().getID(), i);
        }
    }

    public static void removeSprite(Sprite sprite) {
        allSprites.remove(sprite.getID(), sprite);
        if (sprite instanceof CombatSprite c) {
            addSprite(c.getWPSprite());
            combatSprites.remove(c.getID(), c);
        }
        if (sprite instanceof CharSprite c) {
            charSprites.remove(c.getID(), c);
        }
        if (sprite instanceof WeaponSprite w) {
            weaponSprites.remove(w.getID(), w);
        }
        if (sprite instanceof BGSprite b) {
            bgSprites.remove(b.getID(), b);
        }

        if (sprite instanceof Collidable c) {
            collidables.remove(c.getWorldBox().getID(), c);
        }
        if (sprite instanceof Hurtable h) {
            hurtables.remove(h.getHurtBox().getID(), h);
        }
        if (sprite instanceof Movable m) {
            movables.remove(m.getCheckBox().getID(), m);
        }
        if (sprite instanceof Weaponry w) {
            weapons.remove(w.getHitBox().getID(), w);
        }
        if (sprite instanceof Interactable i) {
            interactables.remove(i.getInteractBox().getID(), i);
        }
    }

}
