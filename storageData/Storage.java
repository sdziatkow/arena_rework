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
    private HashMap<String, Integer> objIDs;
    private String pathToAnimSprite;

    public Storage() {
        items = new HashMap<>();
        for (ItemType t : ItemType.getTypes()) {
            items.put(t, new HashMap<>());
        }
        objIDs = new HashMap<>();
    }

//SETTERS----------------------------------------------------------------------------------------------------------------

    public void setPathToAnimSprite(String path) {pathToAnimSprite = path;}

//GETTERS----------------------------------------------------------------------------------------------------------------

    public String getPathToAnimSprite(){return pathToAnimSprite;}

    /**
     * @param itemID The ID of the item whose amount stored will be returned.
     * @return The amount of the item that is stored.
     */
    public int getAmntStored(Integer itemID) {
        return items.get(ItemType.ALL).get(itemID).amnt().get();
    }

    /**
     * @param t The type of items to return.
     * @return An Array-copy of all items of the given type.
     */
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
     * @return True if given Item Object is NEWLY added to this storage. False if it already existed.
     */
    public boolean store(Item i, int amnt) {
        StoredItem toStore = grabStoredSameAs(i.getObjID());
        if (toStore != null) {
            toStore.amnt().inc(amnt);
            return false;
        }
        else{
            toStore = new StoredItem(i, amnt);
            items.get(ItemType.typeOf(i)).put(i.getID(), toStore);
            items.get(ItemType.ALL).put(i.getID(), toStore);
            objIDs.put(toStore.item().getObjID(), toStore.item().getID());
            return true;
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
        objIDs.remove(removing.item().getObjID());
    }

    /** @return StoredItem Object whose Item field has the given itemID */
    private StoredItem grabStoredItem(Integer itemID) {
        return items.get(ItemType.ALL).get(itemID);
    }
    private StoredItem grabStoredSameAs(String objID) {return items.get(ItemType.ALL).get(objIDs.get(objID));}

    /**
     * @param itemID The ID of the Item Object that should be returned.
     * @return The Item Object with given itemID.
     */
    public Item grabItem(Integer itemID) {
        if (!hasItem(itemID)) return null;
        return grabStoredItem(itemID).item();
    }

    /**
     * @param objID The ID of the Item Object that should be returned.
     * @return The Item Object with given objID.
     */
    public Item grabSameAs(String objID) {
        if (!hasSameAs(objID)) return null;
        return grabStoredSameAs(objID).item();
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
    public boolean hasSameAs(String objID) {return grabStoredSameAs(objID) != null;}
}
