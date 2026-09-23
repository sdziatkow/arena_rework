package charData.stat;

import java.util.HashMap;
import javafx.beans.property.DoubleProperty;
import values.DoubleVal;
import values.ValType;

/** A representation of a game character's Stats and their values.
 * Default values are defined in enum Stat
 * @see Stat
 * @see DoubleVal
 */
public class CharStats {
    private final HashMap<Stat, DoubleVal> ALL_STATS;
    private final HashMap<Stat, Double> REGEN_VALS;

    public CharStats() {
        ALL_STATS = new HashMap<>(Stat.ALL.length);
        for (Stat s : Stat.ALL) {
            ALL_STATS.put(s, new DoubleVal());

            // Arbitrary, the max does not really matter on non-vital stats.
            if (!Stat.isVital(s)) ALL_STATS.get(s).setMax(10000.0);
        }
        REGEN_VALS = new HashMap<>();
        for (Stat s : Stat.VITALS) {
            REGEN_VALS.put(s, 0.00);
        }
    }

//SETTERS----------------------------------------------------------------------------------------------------------------

    /**
     * Must be a Stat in Stat[] Stat.VITALS.
     * @param s The vital to set the ValType.MAX of.
     * @param val The value to set the given vital's ValType.MAX of to.
     */
    public void setMaxVal(Stat s, double val) {
        if (!Stat.isVital(s)) throw new IllegalArgumentException("Can only set the ValType.MAX of vital stats.");
        ALL_STATS.get(s).set(ValType.MAX, val);
    }

    /**
     * @param s The Stat to set the ValType.VAL of.
     * @param val The value to set the given Stat's ValType.VAL to.
     */
    public void setVal(Stat s, double val) {ALL_STATS.get(s).set(ValType.VAL, val);}

    /**
     * @param s The Vital Stat to set the regen value of.
     * @param val The value to set the given Vital Stat's regen value to.
     */
    public void setRegenVal(Stat s, double val) {REGEN_VALS.replace(s, val);}

//GETTERS----------------------------------------------------------------------------------------------------------------

    public double get(Stat s, ValType v) { return ALL_STATS.get(s).get(v); }

    public DoubleProperty progressVal(Stat s) {return ALL_STATS.get(s).getProgressVal();}

//HEAL/DAMAGE------------------------------------------------------------------------------------------------------------

    /**
     * Increment the given stat's ValType.VAL by given amnt.
     * @param s The stat to heal.
     * @param amnt The amount to heal it by.
     */
    public void heal(Stat s, double amnt) {ALL_STATS.get(s).inc(amnt);}

    /**
     * Heal all stats in Stat[] Stat.VITALS by given amnt.
     * @param amnt The amount to heal all vitals by.
     */
    public void healVitals(double amnt) {for (Stat s : Stat.VITALS) {heal(s, amnt);}}

    /**
     * Heal all Vital Stats by their REGEN_VALUES.
     */
    public void regenVitals() {for (Stat s : Stat.VITALS) {heal(s, REGEN_VALS.get(s));}}

    /**
     * Decrement the given stat's ValType.VAL by given amnt.
     * @param s The stat to damage.
     * @param amnt The amount to damage it by.
     */
    public void damage(Stat s, double amnt) {ALL_STATS.get(s).dec(amnt);}

    /**
     * Damage all stats in Stat[] Stat.VITALS by given amnt.
     * @param amnt The amount to damage all vitals by.
     */
    public void damageVitals(double amnt){for (Stat s : Stat.VITALS) {damage(s, amnt);}}
}
