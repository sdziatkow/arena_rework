package control.runtimeTrackers;

public enum WorldEntity {
    GAME_CHAR,
    STORAGE,
    ITEM,
    BG_SPRITE,
    EQ_SLOTS,
    DIALOGUE;
    public static final WorldEntity[] ALL = GAME_CHAR.getDeclaringClass().getEnumConstants();
}
