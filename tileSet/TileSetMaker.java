package tileSet;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class TileSetMaker {

    public static TileSet makeTileSet(String pathToData) {
        try { // Get the file set up.
            FileInputStream inFile = new FileInputStream(pathToData);
            Scanner scn = new Scanner(inFile);
            scn.useDelimiter("[|]|\\n");
            TileSet t = new TileSet();
            while (scn.hasNext()) {
                String currField = scn.next();
                String fieldVal =  scn.next();
                fieldVal = fieldVal.replaceAll("\r", "");
                setData(currField, fieldVal, t);
            }
            scn.close();
            t.setUp();
            return t;
        } catch (FileNotFoundException e) {
            throw new IllegalArgumentException("Given file path does not exist.");
        }
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
