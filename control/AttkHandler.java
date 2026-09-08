package control;

import charData.CharData;
import charData.GameChar;
import charData.stat.Stat;
import itemData.weapons.Weapon;
import worldData.statData.StatTracker;
import worldData.statData.StorageTracker;

import java.util.Map;

public abstract class AttkHandler {

    public static void handleAttk(Integer attkerID, Integer hurtID) {
        Weapon wpn = (Weapon)StorageTracker.eqSlots.get(attkerID).wpn();
        GameChar hurter = StatTracker.gameChars.get(hurtID);
        if (hurter == null) return;
        if (wpn == null) hurter.stats().damage(Stat.HP, 10.0);
        else { //TODO: Make weapons cool.
            hurter.stats().damage(Stat.HP, 10.0);
        }
    }
}
