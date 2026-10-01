package control.objectGen.gameEntities;

import control.objectGen.ObjectDataParser;
import control.objectGen.ObjectType;
import spriteData.backgroundSprite.StaticSprite;

import java.util.HashMap;

public class StaticSpriteGen {

    public static StaticSprite genStaticSprite(String objID) {
        String pathToData = ObjectDataParser.getDataFromObjectID(ObjectType.STATIC_SPRITE, objID);
        HashMap<String, String> data = ObjectDataParser.parseObjectData(pathToData);
        if (data.get("pathToFile") == null) throw new IllegalArgumentException("Given file is not set up correctly.");
        StaticSprite s = new StaticSprite(data.get("pathToFile"));
        s.setObjID(objID);
        return new StaticSprite(data.get("pathToFile"));
    }
}
