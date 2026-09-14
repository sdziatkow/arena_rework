package control.handlers;

import charData.GameChar;
import charData.stat.Stat;
import itemData.weapons.Weapon;
import control.runtimeTrackers.worldData.StatTracker;
import control.runtimeTrackers.worldData.StorageTracker;

import java.util.Random;

import static charData.stat.Stat.*;
import static values.ValType.VAL;

public abstract class AttkHandler {
    private final static double MAX_ACCURACY = 200.0;

    public static void handleAttk(Integer attkerID, Integer hurtID) {
        Weapon wpn = (Weapon)StorageTracker.eqSlots.get(attkerID).wpn();
        GameChar attker = StatTracker.gameChars.get(attkerID);
        GameChar hurter = StatTracker.gameChars.get(hurtID);
        if (hurter == null) return;
        if (wpn == null) {
            hurter.stats().damage(HP, 1.0);
            return;
        }

        if (attker.stats().get(ACCURACY, VAL) >= MAX_ACCURACY) {
            System.out.println("|>PERFECT ACCURACY");
        }
        else if (!rollAcc(attker.stats().get(ACCURACY, VAL))) {
            System.out.println("|>ATTACK MISS");
            return;
        }
        if (rollPrcntVal(hurter.stats().get(DODGE, VAL))) {
            System.out.println("|>DEFENDER DODGE");
            return;
        }
        Double hurtDef;
        switch (wpn.getDmgType()) {
            case PHYS: hurtDef = hurter.stats().get(PHYSDEF, VAL); break;
            case MAG: hurtDef = hurter.stats().get(MAGDEF, VAL); break;
            default: hurtDef = null; break;
        }
        if (hurtDef == null) return;
        wpn.computeDmgWithScalings(attker.attr()).forEach((Stat s, Double dmg) -> {
            if (rollPrcntVal(attker.stats().get(CRIT, VAL))){
                System.out.println("|>>>>>CRIT>>>>>>");
                dmg *= 2.0;
            }
            double dOT = (dmg / 4.0) - (hurtDef / 4.0);
            if (dOT < 0.0000) dOT = 0.0;
            System.out.println("|-|-|DAMAGING " + s.toString());
            System.out.println("|>Attacker Damage One Tick: " + (dmg / 4.0) + "|>Hurter Defense One Tick: " + (hurtDef / 4.0));
            System.out.println("|>Final Damage One Tick: " + dOT);
            System.out.println("|> Four Total Ticks, Final Damage Estimate ((+/-)dmgRoll && Crit): " + (dOT * 4.0));
            System.out.println("|");
            hurter.stats().damage(s, dOT);
        });
        System.out.println("-------------------------");
    }

    private static boolean rollAcc(double acc) {
        Random r = new Random();
        int missChance;
        if (acc >= MAX_ACCURACY) return true;
        if (acc < 100) return ((int)acc > r.nextInt(100));
        missChance = 10;
        return (acc > r.nextInt(missChance) + acc);
    }

    private static boolean rollPrcntVal(double chance) {
        Random r = new Random();
        return ((int)(chance * 100.0) > r.nextInt(100));
    }

}
