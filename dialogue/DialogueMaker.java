package dialogue;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class DialogueMaker {

    public static Dialogue makeDialogue(String pathToData) {
        try { // Get the file set up.
            FileInputStream inFile = new FileInputStream(pathToData);
            Scanner scn = new Scanner(inFile);
            scn.useDelimiter("[|]|\\n");
            Dialogue d = new Dialogue();
            while (scn.hasNext()) {
                String currField = scn.next();
                String fieldVal =  scn.next();
                fieldVal = fieldVal.replaceAll("\r", "");
                setData(currField, fieldVal, d);
            }
            scn.close();
            return d;
        } catch (FileNotFoundException e) {
            throw new IllegalArgumentException("Given file path does not exist.");
        }
    }

    private static void setData(String field, String val, Dialogue d)  {
        switch (field) {
            case "name":
                d.setName(val);
                break;
            case "dialogues":
                parseDialogues(val, d);
                break;
            case "reset":
                d.toggleResetDialogue(Boolean.parseBoolean(val));
                break;
            default: throw new IllegalArgumentException("Given file is not set up correctly.");
        }
    }

    private static void parseDialogues(String d, Dialogue dia) {
        Scanner scn = new Scanner(d);
        scn.useDelimiter(";");
        while (scn.hasNext()) {
            String n = scn.next();
            dia.addDialogue(n);
        }
        scn.close();
    }
}
