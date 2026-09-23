package control.objectGen;

public enum ObjectType {
    GAME_CHAR("char_data.txt"),
    ITEM("item_data.txt"),
    STATIC_SPRITE("static_sprite_data.txt"),
    STORAGE_SPRITE("storage_sprite_data.txt");

    private final String path = "resources/object_data/ids/";
    private String pathToIDs = path;
    ObjectType(String pathToIDs) {
        this.pathToIDs += pathToIDs;
    }
    String getPathToIDs() {return pathToIDs;}
}
