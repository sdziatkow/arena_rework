package charData.attr;

import charData.GameChar;
import charData.stat.Stat;

import java.util.HashMap;

/**
 * VIGOR -        + Max HP && HP Regen
 * INTELLIGENCE - + Max MP && MP Regen
 * ENDURANCE -    + Max SP && SP Regen
 * WILLPOWER -    + MAGDEF
 * STRENGTH -     + PHYSDEF
 * AGILITY -      + SPEED && DODGE
 * DEXTERITY -    + ACCURACY && CRIT
 */
public enum Attr {
    VIGOR, INTELLIGENCE, ENDURANCE, WILLPOWER, STRENGTH, AGILITY, DEXTERITY;
    public static final Attr[] ALL = VIGOR.getDeclaringClass().getEnumConstants();

}
