package itemData;
import charData.attr.Attr;
import charData.stat.Stat;
import charData.statMods.StatChange;
import charData.statMods.StatMod;
import values.IntVal;
import values.ValType;

import java.util.ArrayList;

/** Basic class for all item data.
 * <br> name: A descriptive name of this item.
 * <br> value: The gold value for each individual item.
 */
public class Item extends ItemData implements Comparable<Item> {
    private String name;
    private IntVal value;
    private StatMod statMod;

    public Item() {
        name = null;
        setUp();
    }
    public Item(String n) {
        name = n;
        setUp();
    }

    private void setUp() {
        final int DEFAULT_MAX_VAL  = 10000; // Arbitrary, just make it big.
        value = new IntVal();
        value.setMax(DEFAULT_MAX_VAL);
        statMod = new StatMod();
    }

//SETTERS----------------------------------------------------------------------------------------------------------------

    @Override
    public void setStorageID(Integer id) {
        super.setStorageID(id);
        statMod.setGameCharID(id);
    }

    public void setName(String n) {name = n;}

//GETTERS----------------------------------------------------------------------------------------------------------------

    public String getName() {return name;}
    public IntVal val() {return value;}
    public StatMod statMod() {return statMod;}

    /** This is not an Object field, SHOULD BE STORED IN LOCAL VARIABLE FOR USE. */
    public ArrayList<String> dispInfo() {
        ArrayList<String> dispInfo = new ArrayList<>();
        dispInfo.add("Name");
        dispInfo.add(name);

        dispInfo.add("Value");
        dispInfo.add(String.valueOf(value.get()) + "g");

        for (StatChange c : StatChange.ALL) {
            for (ValType v : ValType.ALL) {
                for (Attr a : Attr.ALL) {
                    Integer mod = statMod.getChange(c, v, a);
                    if (mod != null) {
                        dispInfo.add("+[" + a.toString() + "]");
                        dispInfo.add(String.valueOf(mod));
                    }
                }
                for (Stat s : Stat.ALL) {
                    Double mod = statMod.getChange(c, v, s);
                    if (mod != null) {
                        dispInfo.add("+[" + s.toString() + "]");
                        dispInfo.add(String.format("%.2f", mod));
                    }
                }
            }
        }
        return dispInfo;
    }

//OPERATIONS-------------------------------------------------------------------------------------------------------------

    /** Default ordering; compares this Item's name field to the other's using String.compareTo(). */
    @Override
    public int compareTo(Item other) { return this.name.compareTo(other.name); }
}
