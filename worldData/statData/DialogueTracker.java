package worldData.statData;

import dialogue.Dialogue;

import java.util.HashMap;

public class DialogueTracker {

    public static HashMap<Integer, Dialogue> dialogues = new HashMap<>();

    public static void addDialogue(Dialogue d) {
        dialogues.put(d.getID(), d);
    }

    public static void removeDialogue(Integer id) {
        dialogues.remove(id);
    }
}
