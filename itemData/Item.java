package itemData;

import charData.attr.Attr;
import charData.stat.Stat;
import values.IntVal;

import java.util.ArrayList;
import java.util.HashMap;

/** Basic class for all item data.
 * <br> name: A descriptive name of this item.
 * <br> amount: The amount of this item that a storage has.
 * <br> value: The gold value for each individual item.
 * <br> attrChanges: The Attributes that are changed by this item ON EQUIP or ON USE.
 * <br> statChanges: The Stats that are changed by this item ON EQUIP or ON USE.
 */
public class Item extends ItemData implements Comparable<Item> {
    private String name;
    private IntVal amount;
    private IntVal value;
    private HashMap<Attr, Integer> attrChanges;
    private HashMap<Stat, Double> statChanges;

    public Item() {
        name = null;
        setUp();
    }
    public Item(String n) {
        name = n;
        setUp();
    }

    private void setUp() {
        final int DEFAULT_MAX_AMNT = 10000; // Arbitrary, just make it big.
        final int DEFAULT_MAX_VAL  = 10000; // Arbitrary, just make it big.
        amount = new IntVal();
        value = new IntVal();
        amount.setMax(DEFAULT_MAX_AMNT);
        value.setMax(DEFAULT_MAX_VAL);
        attrChanges = new HashMap<>();
        statChanges = new HashMap<>();
    }

//SETTERS----------------------------------------------------------------------------------------------------------------

    public void setName(String n) {name = n;}

//GETTERS----------------------------------------------------------------------------------------------------------------

    public String getName() {return name;}
    public IntVal amnt() {return amount;}
    public IntVal val() {return value;}
    public HashMap<Attr, Integer> getAttrChanges() {return attrChanges;}
    public HashMap<Stat, Double> getStatChanges() {return statChanges;}

    public int getTotalValue() {return (amount.get() * value.get()); }

    /** This is not an Object field, SHOULD BE STORED IN LOCAL VARIABLE FOR USE. */
    public ArrayList<String> dispInfo() {
        ArrayList<String> dispInfo = new ArrayList<>();
        dispInfo.add("Name");
        dispInfo.add(name);

        dispInfo.add("Value");
        dispInfo.add(String.valueOf(value.get()) + "g");

        dispInfo.add("Amount");
        dispInfo.add(String.valueOf(amount.get()));

        attrChanges.forEach((Attr a, Integer val) -> {
            dispInfo.add("+[" + a.toString() + "]");
            dispInfo.add(String.valueOf(val));
        });
        statChanges.forEach((Stat s, Double val) -> {
            dispInfo.add("+[" + s.toString() + "]");
            dispInfo.add(String.valueOf(val));
        });
        return dispInfo;
    }

//OPERATIONS-------------------------------------------------------------------------------------------------------------

    /** Default ordering; compares this Item's name field to the other's using String.compareTo(). */
    @Override
    public int compareTo(Item other) { return this.name.compareTo(other.name); }

    public void addAttrChange(Attr a, int change) {
        attrChanges.putIfAbsent(a, change);
        attrChanges.put(a, change);
    }

    public void addStatChange(Stat s, double change) {
        statChanges.putIfAbsent(s, change);
        statChanges.put(s, change);
    }
}
