package control.handlers;


public enum EQAction {
    EQUIP, UN_EQUIP;
    private static final String[] dispInfo = new String[] {"Equip", "Un-Equip"};

    /** @return [EQUIP, UN_EQUIP] as proper Strings. */
    public static String dispInfo(EQAction action) {
        return dispInfo[action.ordinal()];
    }
}
