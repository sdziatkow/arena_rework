package control.objectGen;

import spriteData.backgroundSprite.StaticSprite;
import spriteData.backgroundSprite.StorageSprite;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Scanner;

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
