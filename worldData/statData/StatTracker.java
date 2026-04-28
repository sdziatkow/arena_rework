package worldData.statData;

import charData.GameChar;

import java.util.HashMap;
import java.util.Map;

public class StatTracker {

    public final static Map<Integer, GameChar> gameChars = new HashMap<>();

    public static void trackGameChar(GameChar gameChar) {
        gameChars.put(gameChar.getID(), gameChar);
    }
}
