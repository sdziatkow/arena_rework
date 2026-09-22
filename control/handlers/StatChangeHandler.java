package control.handlers;

import charData.GameChar;
import charData.attr.Attr;
import charData.attr.CharAttr;
import charData.stat.CharStats;
import charData.stat.Stat;
import charData.statMods.StatChange;
import charData.statMods.StatMod;
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

public class StatChangeHandler {

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

    public static void applyStatMod(Integer gameCharID, StatMod mod) {
        GameChar g = StatTracker.gameChars.get(gameCharID);
        if (g == null) return;
        applyStatChanges(g, mod);
        updateStatsFromAttr(gameCharID);
    }

//APPLY-CHANGES----------------------------------------------------------------------------------------------------------

    private static void applyStatChanges(GameChar g, StatMod m) {
        for (StatChange change : StatChange.ALL) {
            for (ValType type : ValType.ALL) {
                for (Attr a : Attr.ALL) {
                    int val = g.attr().get(a, type);
                    Integer mod = m.getChange(change, type, a);
                    if (mod != null) g.attr().setVal(a, (int)computeChange(change, val, mod));
                }
                for (Stat s : Stat.ALL) {
                    double val = g.stats().get(s, type);
                    Double mod = m.getChange(change, type, s);
                    if (mod != null) g.stats().setVal(s, computeChange(change, val, mod));
                }
            }
        }
    }

    private static double computeChange(StatChange change, double val, double mod) {
        switch (change) {
            case PLUS: return (val + mod);
            case MINUS: return (val - mod);
            case MULT: return (val * mod);
            default: return val;
        }
    }
}
