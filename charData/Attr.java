package charData;

/**
 * VIGOR - HP
 * ENDURANCE - SP
 * WILLPOWER - DEF
 * STRENGTH - DMG
 * AGILITY - SPEED
 * DEXTERITY - CRIT
 */
public enum Attr {
    VIGOR, ENDURANCE, WILLPOWER, STRENGTH, AGILITY, DEXTERITY;

    public static Attr[] getAttr() {
        return VIGOR.getDeclaringClass().getEnumConstants();
    }
}
