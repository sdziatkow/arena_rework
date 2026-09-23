package control.runtimeTrackers.worldData;

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
}
