package charData.statMods;

public enum StatChange {
    PLUS, MINUS, MULT, DIV;
    public static final StatChange[] ALL = PLUS.getDeclaringClass().getEnumConstants();
}
