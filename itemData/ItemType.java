package itemData;

import itemData.armors.Armor;
import itemData.usables.Usable;
import itemData.weapons.Weapon;

public enum ItemType {
    ALL, WEAPON, ARMOR, USABLE;

    public static ItemType[] getTypes() {
        return ALL.getDeclaringClass().getEnumConstants();
    }
    public static ItemType typeOf(Item i) {
        if (i instanceof Weapon) return WEAPON;
        if (i instanceof Armor)  return ARMOR;
        if (i instanceof Usable) return USABLE;
        return ALL;
    }
}
