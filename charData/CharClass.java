package charData;

import charData.attr.Attr;
import charData.attr.CharAttr;

import java.util.HashMap;

/**
 * BRUTE -
 * BARBARIAN -
 * SPELL_BLADE -
 * MAGE -
 * WITCH_HUNTER -
 * MONK -
 * DUELIST -
 * RANGER -
 */
public enum CharClass {
    BRUTE, BARBARIAN, SPELL_BLADE, MAGE, WITCH_HUNTER, MONK, DUELIST, RANGER;
    public static final CharClass[] ALL = BRUTE.getDeclaringClass().getEnumConstants();

    public static HashMap<Attr, Integer> defaultAttrVal(CharClass c) {
        HashMap<Attr, Integer> vals = new HashMap<>();
        for (Attr a : Attr.ALL) { vals.put(a, 5); }
        switch (c) {
            case BRUTE:
                vals.put(Attr.VIGOR, 25);
                vals.put(Attr.STRENGTH, 10);
                break;
            case BARBARIAN:
                vals.put(Attr.VIGOR, 10);
                vals.put(Attr.STRENGTH, 15);
                vals.put(Attr.AGILITY, 10);
                break;
            case SPELL_BLADE:
                vals.put(Attr.INTELLIGENCE, 10);
                vals.put(Attr.ENDURANCE, 10);
                vals.put(Attr.WILLPOWER, 8);
                vals.put(Attr.STRENGTH, 7);
                break;
            case MAGE:
                vals.put(Attr.INTELLIGENCE, 25);
                vals.put(Attr.WILLPOWER, 10);
                break;
            case WITCH_HUNTER:
                vals.put(Attr.INTELLIGENCE, 10);
                vals.put(Attr.WILLPOWER, 7);
                vals.put(Attr.STRENGTH, 8);
                vals.put(Attr.DEXTERITY, 10);
                break;
            case MONK:
                vals.put(Attr.WILLPOWER, 10);
                vals.put(Attr.STRENGTH, 10);
                vals.put(Attr.AGILITY, 15);
                break;
            case DUELIST:
                vals.put(Attr.ENDURANCE, 15);
                vals.put(Attr.AGILITY, 10);
                vals.put(Attr.DEXTERITY, 10);
                break;
            case RANGER:
                vals.put(Attr.AGILITY, 10);
                vals.put(Attr.DEXTERITY, 25);
                break;
            default:
                break;
        }
        return vals;
    }

}
