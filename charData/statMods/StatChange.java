package charData.statMods;

public enum StatChange {
    PLUS, MINUS, MULT;
    public static final StatChange[] ALL = PLUS.getDeclaringClass().getEnumConstants();
}
