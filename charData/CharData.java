package charData;
import control.ArenaObject;

public abstract class CharData extends ArenaObject {

    private String pathToMvSheet;
    private String pathToAttkSheet;
    private String name;

    public CharData() {
        pathToMvSheet = "file:resources/sprites/character/move_4x4_16x32.png";
        pathToAttkSheet = null;
        name = "ERROR:NOT-SET";
    }

    public CharData(String mvSheet, String attkSheet, String n) {
        pathToMvSheet = mvSheet;
        pathToAttkSheet = attkSheet;
        name = n;
    }

    public void setPathToMvSheet(String n) { pathToMvSheet = n; }
    public void setPathToAttkSheet(String n) { pathToAttkSheet = n; }
    public void setName(String n) { name = n; }

    public String getPathToMvSheet() { return pathToMvSheet; }
    public String getPathToAttkSheet() { return pathToAttkSheet; }
    public String getName() { return name; }

}
