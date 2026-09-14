package charData.attr;

import java.util.HashMap;
import javafx.beans.property.DoubleProperty;
import values.IntVal;
import values.ValType;

/** A representation of a game character's Attributes and their values.
 * Default values are: min: 0; max: 100
 * @see Attr
 * @see IntVal
 */
public class CharAttr {
    private final HashMap<Attr, IntVal> ALL_ATTR;

    public CharAttr() {
        ALL_ATTR = new HashMap<>(Attr.ALL.length);
        for (Attr a : Attr.ALL) {
            ALL_ATTR.put(a, new IntVal(0, 100, 0));
        }
    }

//SETTERS----------------------------------------------------------------------------------------------------------------

    /**
     * @param a The attribute whose value will be set.
     * @param val The value to set the given attribute to.
     */
    public void setVal(Attr a, int val) {ALL_ATTR.get(a).set(val);}

//GETTERS----------------------------------------------------------------------------------------------------------------

    /**
     * @param a The attribute whose value will be returned.
     * @param t The type of value that will be returned (MIN, MAX, or VAL).
     * @return The given value of the given attribute.
     */
    public int get(Attr a, ValType t) {return ALL_ATTR.get(a).get(t);}

    /**
     * @param a The attribute whose progress value will be returned.
     * @return The progress value [0-1] of the given attribute.
     */
    public DoubleProperty progressVal(Attr a) {return ALL_ATTR.get(a).getProgressVal();}

//OPERATIONS-------------------------------------------------------------------------------------------------------------

    /**
     * @param a The attribute whose value will be incremented.
     * @param amnt The amount to increment by.
     */
    public void skillUp(Attr a, int amnt) {ALL_ATTR.get(a).inc(amnt);}

    /**
     * @param a The attribute whose value will be decremented.
     * @param amnt The amount to decrement by.
     */
    public void skillDown(Attr a, int amnt) {ALL_ATTR.get(a).dec(amnt);}
}
