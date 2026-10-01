package control.objectGen.gameObjects;

import control.objectGen.ObjectDataParser;
import dialogue.Dialogue;

import java.util.HashMap;
import java.util.Scanner;

public class DialogueMaker {

    public static Dialogue makeDialogue(String pathToData) {
        if (pathToData == null) return null;
        HashMap<String, String> data = ObjectDataParser.parseObjectData(pathToData);
        Dialogue d = new Dialogue();
        for (String field : data.keySet()) {
            setData(field, data.get(field), d);
        }
        return d;
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
