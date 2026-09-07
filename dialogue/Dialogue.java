package dialogue;

import control.ArenaObject;
import java.util.ArrayList;

public class Dialogue extends ArenaObject {
    private String name;
    private final ArrayList<String> dialogues;
    private int idx;
    private boolean reset;

    public Dialogue() {
        name = null;
        dialogues = new ArrayList<>();
        idx = 0;
        reset = false;
    }

    public void setName(String n) {name = n;}
    public void addDialogue(String d) {dialogues.add(d);}
    public void toggleResetDialogue(boolean b) {reset = b;}

    public String getName() {return name;}
    public ArrayList<String> getDialogues() {return dialogues;}

    public String speak() {
        if (idx >= dialogues.size()) {
            if (reset) idx = 0;
            else return null;
        }
        String d = dialogues.get(idx);
        idx++;
        return d;
    }
}
