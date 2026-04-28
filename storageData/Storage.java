package storageData;

import control.ArenaObject;
import itemData.Item;
import itemData.armors.Armor;
import itemData.usables.Usable;
import itemData.weapons.Weapon;
import java.util.ArrayList;

public class Storage extends ArenaObject {
    private ArrayList<Item>[] items;

    @SuppressWarnings("Unchecked cast")
    public Storage() {
        final int TOTAL_ITEM_TYPES = 4; // Weapon, Armor, Usable, all.
        items = (ArrayList<Item>[]) new ArrayList[TOTAL_ITEM_TYPES];
        for (int i = 0; i < items.length; ++i) {
            items[i] = new ArrayList<>();
        }
    }

    /** @return ArrayList of all Weapon Objects in this storage. */
    public ArrayList<Item> wpn() { return items[0]; }

    /** @return ArrayList of all Armor Objects in this storage. */
    public ArrayList<Item> arm() { return items[1]; }

    /** @return ArrayList of all Usable Objects in this storage. */
    public ArrayList<Item> use() { return items[2]; }

    /** @return ArrayList of all Item Objects in this storage. */
    public ArrayList<Item> all() { return items[3]; }

    /**
     *
     * @param i The item to find the correct list for.
     * @return The list that stores Items of the given Item's type.
     */
    private ArrayList<Item> getList(Item i) {
        ArrayList<Item> type = null;
        if      (i instanceof Weapon w) type = wpn();
        else if (i instanceof Armor a)  type = arm();
        else if (i instanceof Usable u) type = use();
        if (type == null) throw new IllegalArgumentException("Given Item is not of any instantiable type.");
        return type;
    }

//STORE-ITEMS------------------------------------------------------------------------------------------------------------

    public void store(Item i) {
        if (i == null) return;
        ArrayList<Item> list = getList(i);
        Item existing = grabByName(i.getName());

        if (existing == null) { // Item does not exist in this storage.
            list.add(i);
            all().add(i);
        }
        else { // Item does exist in storage.
            existing.amnt().inc(i.amnt().get());
        }
    }

//REMOVE-ITEMS-----------------------------------------------------------------------------------------------------------

    public void removeItem(Item item) {
        ArrayList<Item> list = getList(item);
        for (int i = 0; i < list.size(); ++i) {
            Item currItem = list.get(i);
            int cmp = currItem.compareTo(item);
            if (cmp == 0) {
                list.remove(currItem);
                list.trimToSize();
                all().remove(currItem);
                all().trimToSize();
                currItem = null;
                return;
            }
        }
    }

    /**
     * Checks for default ordering (COMPARES NAMES) to find if given item in this list.
     * @return The item in the given list who's name matches the given item
     * @see Item
     */
    public Item grabByName(String itemName) {
        for (int i = 0; i < all().size(); ++i) {
            Item currItem = all().get(i);
            int cmp = currItem.getName().compareTo(itemName);
            if (cmp == 0) return currItem;
        }
        return null;
    }

//FLAGS------------------------------------------------------------------------------------------------------------------

    /** @return True if this storage contains exactly zero items. */
    public boolean isEmpty() {
        for (int i = 0; i < items.length; ++i) {
            if (!items[i].isEmpty()) return false;
        }
        return true;
    }
}
