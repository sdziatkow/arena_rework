package itemData;

public enum ItemType {
    ALL, WEAPON, ARMOR, USABLE;

    public static ItemType[] getTypes() {
        return ALL.getDeclaringClass().getEnumConstants();
    }
}
