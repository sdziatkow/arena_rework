package control.objectGen.gameObjects;

import control.objectGen.ObjectDataParser;
import tileSet.TileSet;

import java.util.HashMap;

public class TileSetMaker {

    public static TileSet makeTileSet(String pathToData) {
        HashMap<String, String> data = ObjectDataParser.parseObjectData(pathToData);
        TileSet t = new TileSet();
        for (String field : data.keySet()) {
            setData(field, data.get(field), t);
        }
        t.setUp();
        return t;
    }

    private static void setData(String field, String val, TileSet t)  {
        switch (field) {
            case "pathToFloor":
                t.setPathToFloor(val);
                break;
            case "pathToBorder":
                t.setPathToBorder(val);
                break;
            case "width":
                t.setTileWidth(Integer.parseInt(val));
                break;
            case "height":
                t.setTileHeight(Integer.parseInt(val));
                break;
            case "offset":
                t.setBorderOffset(Double.parseDouble(val));
                break;
            default: throw new IllegalArgumentException("Given file is not set up correctly.");
        }
    }
}
