package charData.stat;

/** Defines all Stats for a game character.
 * HP  - Health  (Points)
 * SP  - Stamina (Points)
 * DEF - Defense
 * SPD - Speed
 */
public enum Stat {
    HP, SP, DEF, SPEED;

    public static Stat[] getStats() {
        return HP.getDeclaringClass().getEnumConstants();
    }

    /** Defines the default values for all stats.
     * @return double array with length of three. [min, max, val]
     */
    public static double[] getDefaultVals(Stat s) {
        if (s == null) return new double[]{0.0, 1.0, 0.0};
        double min;
        double max;
        double val;

        switch (s) {
            case HP:
                min = 0.0;
                max = 100.0;
                val = 100.0;
                break;
            case SP:
                min = 0.0;
                max = 100.0;
                val = 50.0;
                break;
            case DEF:
                min = 0.0;
                max = 100.0;
                val = 0.0;
                break;
            case SPEED:
                min = 0.0;
                max = 100.0;
                val = 5.0;
                break;
            default:
                min = 0.0;
                max = 1.0;
                val = 0.0;
        }
        return new double[]{min, max, val};
    }
}
