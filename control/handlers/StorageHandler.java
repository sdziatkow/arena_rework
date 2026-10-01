package control.handlers;

import control.IDGen;
import control.objectGen.gameEntities.ItemGen;
import control.runtimeTrackers.WorldTracker;
import itemData.Item;
import storageData.Storage;

import static control.runtimeTrackers.WorldEntity.ITEM;
import static control.runtimeTrackers.WorldEntity.STORAGE;

/**
 * Handles all of the following during run-time:
 * <br>Setting the storageID of Items.
 * <br>Adding Items to Storages.
 * <br>Removing Items from Storages.
 */
public class StorageHandler {

    /** This method will:
     * <br> Create a new Item Object with given itemObjID.
     * <br> Generate a new ID.
     * <br> Link the ID to the given item.
     * <br> Track the item with WorldTracker.
     * <br> Add the item to the storage with given storageID using WorldTracker.addToStorage().
     * @param itemObjID The item to add to the World.
     * @param storageID The ID of the Storage to add the item to.
     * @param amnt The amount of the item to add.
     * @return The ID of the newly made Item Object.
     */
    public static int addNewItemToStorage(String itemObjID, int storageID, int amnt) {
        Item i = ItemGen.genItem(itemObjID);
        final int ID = IDGen.genID();
        i.setID(ID);
        WorldTracker.add(ITEM, i);
        addTo(storageID, ID, amnt);
        return ID;
    }

    public static void addTo(int toStorageID, int itemID, int amnt) {
        Item item = (Item)WorldTracker.get(ITEM, itemID);
        Storage toAdd = (Storage)WorldTracker.get(STORAGE, toStorageID);

        // Case 1: item is not in a storage.
        if (item.getStorageID() == null) {
            boolean isNewlyAdded = toAdd.store(item, amnt);
            if (isNewlyAdded) { // toAdd now references the incoming item. Update its storageID
                item.setStorageID(toAdd.getID());
            }
            else { // toAdd increments the amount on its own reference. Incoming item can be destroyed.
                WorldTracker.remove(ITEM, item.getID());
            }
        }
        else if (item.getStorageID() != toStorageID) { // Case 2: item is in a different storage.
            Storage toRemove = (Storage)WorldTracker.get(STORAGE, item.getStorageID());
            boolean toRemoveHasItem = toRemove.removeItem(item.getID(), amnt);
            boolean toAddHasItem = toAdd.hasSameAs(item.getObjID());

            // toRemove still has instance, toAdd does not have an instance.
            // Instance remains in toRemove after removing. Storage has no instance same as incoming item.
            if (toRemoveHasItem && !toAddHasItem) {
                addNewItemToStorage(item.getObjID(), toAdd.getID(), amnt);
            }
            else {

                // If toRemove no longer has its reference, the item's ID and storageID no longer matter.
                // If toAdd has no reference, it is being moved to a new storage. Update its storageID and keep the item.
                if (!toRemoveHasItem && !toAddHasItem) {
                    item.setStorageID(toAdd.getID());
                }

                // toAdd has its own reference, incoming reference can be fully removed.
                else if (!toRemoveHasItem && toAddHasItem) {
                    WorldTracker.remove(ITEM, item.getID());
                }

                // All other cases are accounted for by Storage.store() logic.
                toAdd.store(item, amnt);
            }
        }
    }

    public static void removeFrom(int storageID, int itemID, int amnt) {
        Storage s = (Storage)WorldTracker.get(STORAGE, storageID);
        Item i = (Item)WorldTracker.get(ITEM, itemID);
        if (!s.removeItem(i.getID(), amnt)) i.setStorageID(null); // None of the item remains.
    }

    public static void removeAllFrom(int storageID, int itemID) {
        Storage s = (Storage)WorldTracker.get(STORAGE, storageID);
        Item i = (Item)WorldTracker.get(ITEM, itemID);
        s.removeItem(i.getID());
        i.setStorageID(null);
    }
}
