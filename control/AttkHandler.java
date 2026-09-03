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
        StatTracker.gameChars.get(hurtID).stats().damage(Stat.HP, 10);
    }
}
