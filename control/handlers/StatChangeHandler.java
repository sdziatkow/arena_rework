package control.handlers;

import charData.GameChar;
import charData.attr.Attr;
import charData.attr.CharAttr;
import charData.stat.CharStats;
import charData.stat.Stat;
import control.runtimeTrackers.spriteData.SpriteTracker;
import control.runtimeTrackers.worldData.StatTracker;
import control.runtimeTrackers.worldData.StorageTracker;
import itemData.Item;
import spriteData.behavior.boxes.Movable;
import spriteData.charSprite.CombatSprite;
import values.ValType;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

/**
 VALID_SIGNS: '+', '-', '*'
 */
public class StatChangeHandler {
    private static final Set<Character> VALID_SIGNS = new HashSet<>();
    private static boolean isValidSign(char sign) {return VALID_SIGNS.contains(sign);}
    public static void setUp() {
        VALID_SIGNS.add('+');
        VALID_SIGNS.add('-');
        VALID_SIGNS.add('*');
        VALID_SIGNS.add('=');
    }

    public static void updateStatsFromAttr(Integer gameCharID) {
        GameChar g = StatTracker.gameChars.get(gameCharID);
        if (g == null) return;
        Stat.genStatsFromAttr(g.stats(), g.attr());
        updateAttkSpeed(gameCharID);
        updateMoveSpeed(gameCharID);
    }

    public static void updateAttkSpeed(int gameCharID) {
        double gSpeed = StatTracker.gameChars.get(gameCharID).stats().get(Stat.SPEED, ValType.VAL);
        CombatSprite sprite = SpriteTracker.combatSprites.get(gameCharID);
        if (sprite == null) return;
        double attkSpeed = ((gSpeed * 1.5) / (100.0 + (gSpeed / 2.0))); //TODO: needs more testing for how much it effects it.
        sprite.setAttkSpeed(attkSpeed);
        //System.out.println("|> New Attack Speed: " + sprite.getAttkSpeed());
    }

    public static void updateMoveSpeed(int gameCharID) {
        double gSpeed = StatTracker.gameChars.get(gameCharID).stats().get(Stat.SPEED, ValType.VAL);
        Movable sprite = SpriteTracker.movables.get(gameCharID);
        if (sprite == null) return;
        double mvSpeed = 0.5 + (gSpeed / (100.0 + (gSpeed / 8)));
        sprite.setMaxSpeed(mvSpeed);
    }

    public static void applyItemChanges(Integer gameCharID, Integer itemID, ValType t, char sign) {
        if (!isValidSign(sign)) return;
        GameChar gameChar = StatTracker.gameChars.get(gameCharID);
        Item item = StorageTracker.items.get(itemID);
        if (gameChar == null || item == null || t == null) return;
        applyAttrChanges(gameChar.attr(), item.getAttrChanges(), sign);
        applyStatChanges(gameChar.stats(), item.getStatChanges(), t, sign);
        updateStatsFromAttr(gameCharID);
    }

//APPLY-CHANGES----------------------------------------------------------------------------------------------------------

    private static void applyAttrChanges(CharAttr attrVals, HashMap<Attr, Integer> changes, char sign) {
        if (!isValidSign(sign)) return;
        for (Attr a : changes.keySet()) {
            double val = attrVals.get(a, ValType.VAL);
            double change = changes.get(a);
            switch (sign) {
                case '+': val += change; break;
                case '-': val -= change; break;
                case '*': val *= change; break;
                default: break;
            }
            attrVals.setVal(a, (int)val);
        }
    }

    public static void applyStatChanges(CharStats statVals, HashMap<Stat, Double> changes, ValType t, char sign) {
        if (!isValidSign(sign)) return;
        for (Stat s : changes.keySet()) {
            double val = statVals.get(s, ValType.VAL);
            double change = changes.get(s);
            switch (sign) {
                case '+': val += change; break;
                case '-': val -= change; break;
                case '*': val *= change; break;
                default: break;
            }
            switch (t) {
                case MAX:
                    if (Stat.isVital(s)) statVals.setMaxVal(s, val);
                    break;
                case VAL:
                    statVals.setVal(s, val);
                    break;
                default: break;
            }
        }
    }
}
