package control.objectGen;

import spriteData.backgroundSprite.StorageSprite;
import java.util.HashMap;

public class StorageSpriteGen {

    public static StorageSprite genStorageSprite(String objID) {
        String pathToData = ObjectDataParser.getDataFromObjectID(ObjectType.STORAGE_SPRITE, objID);
        HashMap<String, String> data = ObjectDataParser.parseObjectData(pathToData);
        if (data.get("pathToFile") == null) throw new IllegalArgumentException("Given file is not set up correctly.");
        StorageSprite s = new StorageSprite(data.get("pathToFile"));
        s.setObjID(objID);
        return s;
    }
}
