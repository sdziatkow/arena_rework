package storageData;

import control.ArenaObject;
import itemData.Item;
import itemData.armors.Armor;
import itemData.usables.Usable;
import itemData.weapons.Weapon;

import java.util.Arrays;

public class EQSlots extends ArenaObject {

    // The index of each weapon type in Array items.
    private final int WPN = 0;
    private final int ARM = 1;
    private final int USE = 2;
    private final Item[] items;

    public EQSlots() {
        final int TOTAL_ITEM_TYPES = 3;
        items = new Item[TOTAL_ITEM_TYPES];
    }

    public Item wpn() {return items[0];}
    public Item arm() {return items[1];}
    public Item use() {return items[2];}
    public Item[] all() {return Arrays.copyOf(items, items.length);}

    public void equip(Item item) {
        if (item instanceof Weapon w){
            items[WPN] = w;
        }
        if (item instanceof Armor a){
            items[ARM] = a;
        }
        if (item instanceof Usable u){
            items[USE] = u;
        }

    }

    public void unequip(Item item) {
        if (item instanceof Weapon w){
            items[WPN] = null;
        }
        if (item instanceof Armor a){
            items[ARM] = null;
        }
        if (item instanceof Usable u){
            items[USE] = null;
        }
    }
}
