package charData.stat;
import charData.attr.Attr;
import charData.attr.CharAttr;
import values.ValType;
import java.util.HashMap;

import static values.ValType.MAX;
import static values.ValType.VAL;

/** Defines all Stats for a game character.
 * <br> HP  - Health  (Points)
 * <br> MP  - Mana    (Points)
 * <br> SP  - Stamina (Points)
 * <br> PHYSDEF - Physical Defense
 * <br> MAGDEF - Magic Defense
 * <br> SPEED - Speed
 * <br> ----
 * <br> Stats are generated from Attr values. They do not have default values, they have Attribute Scalings.
 */
public enum Stat {
    HP, MP, SP, PHYSDEF, MAGDEF, ACCURACY, DODGE, CRIT, SPEED;
    public static final Stat[] ALL = HP.getDeclaringClass().getEnumConstants();
    public static final Stat[] VITALS = new Stat[]{HP, MP, SP};
    public static boolean isVital(Stat s) {return (s == HP || s == MP || s == SP);}

    /**
     * This method will set each Stat in given stats based on the Attr values in attrVals.
     * This ONLY accounts for the given attrVals.
     * @param stats CharStat Object to be generated.
     * @param attrVals CharAttr Object to generate the CharStat values.
     */
    public static void genStatsFromAttr(CharStats stats, CharAttr attrVals) {
        for (Stat s : Stat.ALL) {
            HashMap<Attr, Double> attrScaling = getAttrScalings(s);
            double statVal = 0.0;
            for (Attr a : attrScaling.keySet()) { // Compute each scaling and add it to statVal.
                double scale = attrScaling.get(a);
                double attrVal = attrVals.get(a, VAL);
                double attrMax = attrVals.get(a, MAX);
                statVal += computeScaling(scale, attrVal, attrMax);
            }
            if (s.equals(CRIT) || s.equals(DODGE)) {statVal /= 500;}
            if (isVital(s)) stats.setMaxVal(s, statVal);
            else stats.setVal(s, statVal);
        }
    }

    /**
     * @param scale The percent value [0-1] of attrVal that will be applied to this stat.
     * @param attrVal The value that the stat is based off of.
     * @param attrMax The max value of attrVal.
     * @return The value of a stat based on the given values.
     */
    private static double computeScaling(double scale, double attrVal, double attrMax) {
        double val;
        val = scale * attrVal;
        val += attrVal + (attrMax * scale);
        return val;
    }

    /**
     * Stat -> Attr -> Scaling.
     * Scaling should be applied as followed: statVal = attrVal * Scaling.
     * @param s The stat whose scalings will be returned.
     * @return A HashMap describing the given Stat's Attribute Scalings.
     */
    private static HashMap<Attr, Double> getAttrScalings(Stat s) {
        HashMap<Attr, Double> scalings = new HashMap<>();
        switch (s) {
            case HP:
                scalings.put(Attr.VIGOR, 0.827);
                break;
            case MP:
                scalings.put(Attr.INTELLIGENCE, 0.887);
                break;
            case SP:
                scalings.put(Attr.ENDURANCE, 0.847);
                break;
            case PHYSDEF:
                scalings.put(Attr.STRENGTH, 0.149);
                break;
            case MAGDEF:
                scalings.put(Attr.WILLPOWER, 0.127);
                break;
            case ACCURACY:
                scalings.put(Attr.DEXTERITY, 0.5);
                break;
            case DODGE:
                scalings.put(Attr.AGILITY, 0.057);
                break;
            case CRIT:
                scalings.put(Attr.DEXTERITY, 0.00027);
                break;
            case SPEED:
                scalings.put(Attr.AGILITY, 0.43);
                break;
            default: break;
        }
        return scalings;
    }
}
