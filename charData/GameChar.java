package charData;

import charData.attr.Attr;
import charData.attr.CharAttr;
import charData.stat.CharStats;
import values.IntVal;

import java.util.ArrayList;

/** A representation of all data making up a game character.
 * <br> Allows access to all the mentioned classes.
 * <br> Can only set charClass upon instantiation.
 * <br> Default CharClass is BARBARIAN.
 * @see CharClass
 * @see Level
 * @see CharStats
 * @see CharAttr
 */
public class GameChar extends CharData {
    private CharAttr attr;
    private CharStats stats;
    private Level lvl;
    private CharClass charClass;
    private IntVal gold;

    public GameChar() {
        attr = new CharAttr();
        stats = new CharStats();
        lvl = new Level();
        gold = new IntVal();
        gold.setMax(1000000000);
    }

    public GameChar(String pathToMvSheet, String pathToAttkSheet, String name, CharClass c) {
        super(pathToMvSheet, pathToAttkSheet, name);
        attr = new CharAttr();
        stats = new CharStats();
        lvl = new Level();
        charClass = c;
        setInitialAttrValues();

        gold = new IntVal();
        gold.setMax(1000000000);
    }

    public void setCharClass(CharClass c) {charClass = c;}

    public CharAttr attr() {
        return attr;
    }
    public CharStats stats() {
        return stats;
    }
    public Level lvl() {
        return lvl;
    }
    public CharClass getCharClass() {
        return charClass;
    }

    /** SHOULD ONLY BE CALLED ONCE. (for gameplay reasons). */
    public void setInitialAttrValues() {
        switch (charClass) {
            case BARBARIAN:
                attr.get(Attr.VIGOR).inc(10);
                attr.get(Attr.ENDURANCE).inc(10);
                attr.get(Attr.STRENGTH).inc(10);
                break;
            case BRUTE:
                attr.get(Attr.ENDURANCE).inc(10);
                attr.get(Attr.WILLPOWER).inc(10);
                attr.get(Attr.STRENGTH).inc(10);
                break;
            case DRIFTER:
                attr.get(Attr.AGILITY).inc(10);
                attr.get(Attr.ENDURANCE).inc(10);
                attr.get(Attr.WILLPOWER).inc(10);
                break;
            case RANGER:
                attr.get(Attr.AGILITY).inc(10);
                attr.get(Attr.DEXTERITY).inc(10);
                attr.get(Attr.ENDURANCE).inc(10);
                break;
            case SCOUT:
                attr.get(Attr.VIGOR).inc(10);
                attr.get(Attr.AGILITY).inc(10);
                attr.get(Attr.ENDURANCE).inc(10);
                break;
            case MONK:
                attr.get(Attr.VIGOR).inc(10);
                attr.get(Attr.WILLPOWER).inc(10);
                attr.get(Attr.AGILITY).inc(10);
                break;
        }
    }

    /** This is not an Object field, SHOULD BE STORED IN LOCAL VARIABLE FOR USE. */
    public ArrayList<String> dispInfo() {
        ArrayList<String> info = new ArrayList<>();
        info.add("NAME");
        info.add(getName());
        info.add("CLASS");
        info.add(charClass.toString());

        info.add("LEVEL");
        info.add(String.valueOf(lvl.getLvl()));
        info.add("TO-NEXT");
        info.add((int)lvl.getToNext() - (int)lvl.getXp() + "xp");

        info.add("GOLD");
        info.add(String.valueOf(gold.get()));
        return info;
    }
}
