package itemData.weapons;

import charData.attr.Attr;
import charData.attr.CharAttr;
import charData.stat.Stat;
import itemData.DmgType;
import itemData.Item;
import values.DoubleVal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import static values.ValType.VAL;

/**
 * <br>statMults: Associated with DmgType.
 *                The damage multiplier of this weapon's damage when being applied to a specific Stat.
 *                EX: If this Weapon's statMult for HP = 1.0, when damaging HP, it will do (dmg * 1.0) dmg to HP.
 * <br>attrScalings: The Attributes that this Weapon's damage will scale with.
 *                   EX: 0.5 Vigor scaling will add (vigor * 0.5 * statMult) to this Weapon's damage.
 */
public class Weapon extends Item {
    private String pathToWpnSprite;
    private HashMap<Stat, Double> statMults;
    private HashMap<Attr, Double> attrScalings;
    private DoubleVal dmg;
    private DmgType dmgType;

    public Weapon() {
        super();
        pathToWpnSprite = null;
        dmg = new DoubleVal();
        attrScalings = new HashMap<>();
    }

    public Weapon(String name, String pathToPickableSprite, String pathToWpnSprite) {
        super(name);
        setPathToPickableSprite(pathToPickableSprite);
        this.pathToWpnSprite = pathToWpnSprite;
        dmg = new DoubleVal();
        attrScalings = new HashMap<>();
    }

//SETTERS----------------------------------------------------------------------------------------------------------------

    /** @param path The path to the image file containing this Weapon's sprite. */
    public void setPathToWpnSprite(String path) {pathToWpnSprite = path;}

    private void setStatMults(HashMap<Stat, Double> mults) {statMults = mults;}

    /**
     * Will set this Weapon's DmgType and set its associated Stat Mults.
     * @param type The type of damage this Weapon will do.
     */
    public void setDmgType(DmgType type) {
        dmgType = type;
        setStatMults(DmgType.statMults(dmgType));
    }

    /** @param val The minimum damage this Weapon will do */
    public void setMinDmg(double val) {dmg.setMin(val);}

    /** @param val The maximum damage this Weapon wll do. */
    public void setMaxDmg(double val) {dmg.setMax(val);}

//GETTERS----------------------------------------------------------------------------------------------------------------

    /** @return The path to the image file containing this Weapon's sprite. */
    public String getPathToWpnSprite() {return pathToWpnSprite;}

    /** @return An enum constant describing what type of damage this Weapon does. */
    public DmgType getDmgType() {return dmgType;}

    @Override
    public ArrayList<String> dispInfo() {
        ArrayList<String> out = super.dispInfo();
        out.add("Damage Type");
        out.add(dmgType.toString());
        out.add("Damage");
        out.add(dmg.getMin() + " - " + dmg.getMax());
        out.add("-----");
        out.add("");
        out.add("Scales With:");
        out.add("");
        attrScalings.forEach((Attr a, Double d) -> {
            out.add("[" + a.toString() + "]");
            out.add(String.format("%.0f%%", d * 100));
        });
        return out;
    }

//OPERATIONS-------------------------------------------------------------------------------------------------------------

    /**
     * @param s The stat that this weapon will damage.
     * @param dmg The percent of total damage that will be applied to the stat. [0-1]
     */
    public void addStatMult(Stat s, double dmg) {
        statMults.putIfAbsent(s, dmg);
        statMults.put(s, dmg);
    }

    /**
     * @param a The Attribute that this Weapon Object will scale with.
     * @param scale The percent of the given Attribute's value that will be added to this Weapon Object's damage. [0-1]
     */
    public void addAttrScaling(Attr a, double scale) {
        attrScalings.putIfAbsent(a, scale);
        attrScalings.put(a, scale);
    }

    /** @return A random double value between this Weapon's min and max damage. */
    private double rollDmg() {
        Random gen = new Random();
        double d = gen.nextInt((int)(dmg.getMax() - dmg.getMin())) + dmg.getMin() + (dmg.getMax() - (int)dmg.getMax());
        return d;
    }

    /**
     * One damage roll between this Weapon's min and max dmg.
     * @return A HashMap describing which Stats are damaged and how much damage is applied to the Stat.
     */
    public HashMap<Stat, Double> computeDmg() {
        HashMap<Stat, Double> allDmg = new HashMap<>();
        double baseDmg = rollDmg();
        for (Stat s : statMults.keySet()) {
            double mult = statMults.get(s);
            allDmg.put(s, (baseDmg * mult));
        }
        return allDmg;
    }

    /**
     * One damage roll between this Weapon's min and max dmg with attrScalings applied.
     * @param attr The GameChar's CharAttr in which this Weapon's attrScalings will be applied.
     * @return A HashMap describing which Stats are damaged and how much damage is applied to the Stat.
     */
    public HashMap<Stat, Double> computeDmgWithScalings(CharAttr attr) {
        HashMap<Stat, Double> allDmg = new HashMap<>();
        double totalScalingDmg = 0.0;
        for (Attr a : attrScalings.keySet()) {
            double scale = attrScalings.get(a);
            double attrVal = attr.get(a, VAL);
            totalScalingDmg += attrVal * scale;
        }

        double baseDmg = rollDmg();
        for (Stat s : statMults.keySet()) {
            double mult = statMults.get(s);
            allDmg.put(s, (baseDmg * mult) + (totalScalingDmg * mult));
        }
        return allDmg;
    }
}
