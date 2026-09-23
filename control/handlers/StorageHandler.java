package control.handlers;

import control.runtimeTrackers.worldData.StorageTracker;
import itemData.Item;
import storageData.Storage;

/**
 * Handles all of the following during run-time:
 * <br>Setting the storageID of Items.
 * <br>Adding Items to Storages.
 * <br>Removing Items from Storages.
 */
public class StorageHandler {

    public static void addTo(int toStorageID, int itemID, int amnt) {
        Item item = StorageTracker.items.get(itemID);
        Storage toAdd = StorageTracker.storages.get(toStorageID);

        // Case 1: item is not in a storage.
        if (item.getStorageID() == null) {
            toAdd.store(item, amnt);
            item.setStorageID(toAdd.getID());
        }
        else if (item.getStorageID() != toStorageID) { // Case 2: item is in a different storage.
            Storage toRemove = StorageTracker.storages.get(item.getStorageID());
            toRemove.removeItem(itemID, amnt);
            toAdd.store(item, amnt);
        }
    }

    public static void removeFrom(int storageID, int itemID, int amnt) {
        Storage s = StorageTracker.storages.get(storageID);
        Item i = StorageTracker.items.get(itemID);
        if (!s.removeItem(i.getID(), amnt)) i.setStorageID(null); // None of the item remains.
    }

    public static void removeAllFrom(int storageID, int itemID) {
        Storage s = StorageTracker.storages.get(storageID);
        Item i = StorageTracker.items.get(itemID);
        s.removeItem(i.getID());
        i.setStorageID(null);
    }
}
