package storageData;

import control.ArenaObject;
import itemData.Item;
import itemData.ItemType;

import java.util.Arrays;
import java.util.HashMap;

/**
 * For storing items in one location.
 */
public class Storage extends ArenaObject {
    private HashMap<ItemType, HashMap<Integer, StoredItem>> items;

    public Storage() {
        items = new HashMap<>();
        for (ItemType t : ItemType.getTypes()) {
            items.put(t, new HashMap<>());
        }
    }

    public int getAmntStored(Integer itemID) {
        return items.get(ItemType.ALL).get(itemID).amnt().get();
    }

    public Item[] getItems(ItemType t) {
        StoredItem[] stored = items.get(t).values().toArray(new StoredItem[0]);
        return Arrays.stream(stored).map(StoredItem::item).toArray(Item[]::new);
    }

//STORE-ITEMS------------------------------------------------------------------------------------------------------------

    /**
     * Stores given amnt of given item.
     * If Item is already being stored, add to its amnt by given amnt.
     * @param i The item to add to this Storage.
     * @param amnt The amount to add.
     */
    public void store(Item i, int amnt) {
        if (hasItem(i.getID())) {
            items.get(ItemType.typeOf(i)).get(i.getID()).amnt().inc(amnt);
        }
        else{
            StoredItem toStore = new StoredItem(i, amnt);
            items.get(ItemType.typeOf(i)).put(i.getID(), toStore);
            items.get(ItemType.ALL).put(i.getID(), toStore);
        }
    }

//REMOVE-ITEMS-----------------------------------------------------------------------------------------------------------

    /**
     * Removes given amount of given item.
     * If no more of the item remains, it will be removed from the storage.
     * @param itemID The ID of the item to be removed.
     * @param amnt The amount to remove.
     * @return True if the item is still in this Storage Object.
     */
    public boolean removeItem(Integer itemID, int amnt) {
        StoredItem removing = items.get(ItemType.ALL).get(itemID);
        removing.amnt().dec(amnt);
        if (removing.amnt().isMin()) {
            removeItem(itemID);
            return false;
        }
        return true;
    }

    /**
     * Removes all of the given item from this storage.
     * @param itemID The ID of the item to be removed.
     */
    public void removeItem(Integer itemID) {
        StoredItem removing = items.get(ItemType.ALL).get(itemID);
        items.get(ItemType.typeOf(removing.item())).remove(itemID);
        items.get(ItemType.ALL).remove(itemID);
    }

    /** @return StoredItem Object whose Item field has the given itemID */
    private StoredItem grabStoredItem(Integer itemID) {
        return items.get(ItemType.ALL).get(itemID);
    }

    /**
     * @param itemID The ID of the Item Object that should be returned.
     * @return The Item Object with given itemID.
     */
    public Item grabItem(Integer itemID) {
        if (!hasItem(itemID)) return null;
        return grabStoredItem(itemID).item();
    }

//FLAGS------------------------------------------------------------------------------------------------------------------

    /** @return True if this storage contains exactly zero items. */
    public boolean isEmpty() {
        boolean empty = true;
        for (ItemType t : ItemType.getTypes()) {
            if (!items.get(t).isEmpty()) empty = false;
        }
        return empty;
    }

    public boolean hasItem(Integer itemID) {
        return grabStoredItem(itemID) != null;
    }
}
