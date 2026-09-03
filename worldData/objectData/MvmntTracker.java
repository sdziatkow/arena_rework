package worldData.objectData;

import movement.npcMvmnt.NPCMvmnt;
import spriteData.charSprite.CharSprite;

import java.util.HashMap;
import java.util.Map;

public class MvmntTracker {

    public static final Map<Integer, NPCMvmnt> allNPCMvmnts = new HashMap<>();

    public static void trackMvmnt(CharSprite sprite) {
        allNPCMvmnts.putIfAbsent(sprite.getID(), new NPCMvmnt(sprite));
    }
}
