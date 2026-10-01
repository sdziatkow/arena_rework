package charData;
import control.ArenaObject;

public abstract class CharData extends ArenaObject {

    private String pathToMvSheet;
    private String pathToAttkSheet;
    private String pathToDialogue;
    private Integer storageID;
    private String name;

    public CharData() {
        pathToMvSheet = "file:resources/sprites/character/move_4x4_16x32.png";
        pathToAttkSheet = null;
        pathToDialogue = null;
        storageID = null;
        name = null;
    }

    public void setPathToMvSheet(String n) { pathToMvSheet = n; }
    public void setPathToAttkSheet(String n) { pathToAttkSheet = n; }
    public void setPathToDialogue(String n) { pathToDialogue = n; }
    public void setStorageID(int i) {storageID = i;}
    public void setName(String n) { name = n; }

    public String getPathToMvSheet() { return pathToMvSheet; }
    public String getPathToAttkSheet() { return pathToAttkSheet; }
    public String getPathToDialogue() { return pathToDialogue; }
    public Integer getStorageID() {return storageID;}
    public String getName() { return name; }

}
