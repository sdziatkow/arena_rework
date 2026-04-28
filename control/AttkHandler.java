package control;

import charData.CharData;
import charData.GameChar;
import charData.stat.Stat;
import java.util.Map;

public abstract class AttkHandler {

    static int i = 0;
    public static void handleAttk(Integer attkerID, Integer hurtID, Map<Integer, GameChar> data) {
        data.get(hurtID).stats().damage(Stat.HP, 10.0);
        System.out.println("HIT" + ++i);
    }
}
