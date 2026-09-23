package control.objectGen;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Scanner;

public class ObjectDataParser {

    public static HashMap<String, String> parseObjectData(String pathToData) {
        HashMap<String, String> data = new HashMap<>();
        try { // Get the file set up.
            FileInputStream inFile = new FileInputStream(pathToData);
            Scanner scn = new Scanner(inFile);
            scn.useDelimiter("[|]|\\n");
            while (scn.hasNext()) {
                String currField = scn.next();
                String fieldVal =  scn.next();
                fieldVal = fieldVal.replaceAll("\r", "");
                data.put(currField, fieldVal);
            }
            scn.close();
            return data;
        } catch (FileNotFoundException e) {
            throw new IllegalArgumentException("Given file path does not exist.");
        }
    }

    public static String getDataFromObjectID(ObjectType t, String objID) {
        try { // Get the file set up.
            FileInputStream inFile = new FileInputStream(t.getPathToIDs());
            Scanner scn = new Scanner(inFile);
            scn.useDelimiter("[|]|\\n");
            while (scn.hasNext()) {
                String currField = scn.next();
                String fieldVal =  scn.next();
                fieldVal = fieldVal.replaceAll("\r", "");
                if (currField.equals(objID)) return fieldVal;
            }
            scn.close();
            return null;
        } catch (FileNotFoundException e) {
            throw new IllegalArgumentException("Given file path does not exist.");
        }
    }
}
