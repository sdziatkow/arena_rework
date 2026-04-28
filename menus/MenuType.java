package menus;

public enum MenuType {
    CHARACTER, STATS, ATTRIBUTES, BACKPACK, EQUIPPED, STORAGE;
    public static MenuType[] all() {return CHARACTER.getDeclaringClass().getEnumConstants();}
}
