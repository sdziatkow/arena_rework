package control.objectGen.gameEntities;

import charData.CharClass;
import charData.GameChar;
import control.objectGen.ObjectDataParser;
import control.objectGen.ObjectType;

import java.util.HashMap;

public class GameCharGen {

    public static GameChar genChar(String objID) {
        String pathToData = ObjectDataParser.getDataFromObjectID(ObjectType.GAME_CHAR, objID);
        HashMap<String, String> data = ObjectDataParser.parseObjectData(pathToData);
        GameChar c = new GameChar();
        for (String field : data.keySet()) {
            setData(field, data.get(field), c);
        }
        c.setObjID(objID);
        return c;
    }

    private static void setData(String field, String val, GameChar c)  {
        switch (field) {
            case "mvSheet":
                c.setPathToMvSheet(val);
                break;
            case "attkSheet":
                c.setPathToAttkSheet(val);
                break;
            case "dialogue":
                c.setPathToDialogue(val);
                break;
            case "name":
                c.setName(val);
                break;
            case "class":
                c.setCharClass(CharClass.valueOf(val));
                c.setInitialAttrValues();
                break;
            default: throw new IllegalArgumentException("Given file is not set up correctly.");
        }
    }
}
