package control.objectGen.gameEntities;

import control.objectGen.ObjectDataParser;
import control.objectGen.ObjectType;
import storageData.Storage;

import java.util.HashMap;

public class StorageGen {

    public static Storage genStorage(String objID) {
        String pathToData = ObjectDataParser.getDataFromObjectID(ObjectType.STORAGE_SPRITE, objID);
        HashMap<String, String> data = ObjectDataParser.parseObjectData(pathToData);
        Storage s = new Storage();
        for (String field : data.keySet()) {
            setData(field, data.get(field), s);
        }
        s.setObjID(objID);
        return s;
    }

    public static void setData(String field, String val, Storage s) {
        switch (field) {
            case "pathToAnimSprite":
                s.setPathToAnimSprite(val);
                break;
            default: throw new IllegalArgumentException("Given file is not set up correctly.");
        }
    }
}
