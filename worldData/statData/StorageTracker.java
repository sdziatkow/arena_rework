package worldData.statData;

import itemData.Item;
import storageData.EQSlots;
import storageData.Storage;

import java.util.HashMap;
import java.util.Map;

/** For tracking all Storage, EQSlots, and Item Objects.
 * @see Storage
 * @see EQSlots
 * @see Item
 */
public class StorageTracker {

    public final static Map<Integer, Storage> storages = new HashMap<>();
    public final static Map<Integer, EQSlots> eqSlots = new HashMap<>();
    public final static Map<Integer, Item> items = new HashMap<>();

    public static void trackStorage(Storage storage) { storages.putIfAbsent(storage.getID(), storage); }
    public static void trackEQSlots(EQSlots eq) {eqSlots.putIfAbsent(eq.getID(), eq);}
    public static void trackItem(Item item) {items.putIfAbsent(item.getID(), item);}

    public static void addToStorage(int toStorageID, int itemID) {
        Item item = items.get(itemID);
        Storage toAdd = storages.get(toStorageID);

        // Case 1: item is not in a storage.
        if (item.getStorageID() == null) {
            toAdd.store(item);
            item.setStorageID(toAdd.getID());
        }
        else if (toStorageID == item.getStorageID()) { // Case 2: Item is already in storage.
            return;
        }
        else { // Case 2: item is in a different storage.
            removeFromStorage(item.getStorageID(), item.getID());
            toAdd.store(item);
            item.setStorageID(toAdd.getID());
        }
    }

    public static void removeFromStorage(int storageID, int itemID) {
        Storage s = storages.get(storageID);
        Item i = items.get(itemID);
        s.removeItem(i);
        i.setStorageID(null);
    }
}
