package worldStage.loading;

import charData.CharClass;
import charData.GameChar;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class GameCharGen {
    public static GameChar genChar(String pathToData) {
        try { // Get the file set up.
            FileInputStream inFile = new FileInputStream(pathToData);
            Scanner scn = new Scanner(inFile);
            scn.useDelimiter("[|]|\\n");
            GameChar c = new GameChar();
            while (scn.hasNext()) {
                String currField = scn.next();
                String fieldVal =  scn.next();
                fieldVal = fieldVal.replaceAll("\r", "");
                setData(currField, fieldVal, c);
            }
            scn.close();
            return c;
        } catch (FileNotFoundException e) {
            throw new IllegalArgumentException("Given file path does not exist.");
        }
    }

    private static void setData(String field, String val, GameChar c)  {
        switch (field) {
            case "mvSheet":
                c.setPathToMvSheet(val);
                break;
            case "attkSheet":
                c.setPathToAttkSheet(val);
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
