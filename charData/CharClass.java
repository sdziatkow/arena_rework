package charData;

/**
 * BRUTE -
 * BARBARIAN -
 * DRIFTER -
 * RANGER -
 * SCOUT -
 * MONK -
 */
public enum CharClass {
    BRUTE, BARBARIAN, DRIFTER, RANGER, SCOUT, MONK;

    public static CharClass[] getClasses() {
        return CharClass.BRUTE.getDeclaringClass().getEnumConstants();
    }
}
