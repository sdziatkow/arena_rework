package storageData;

import itemData.Item;
import values.IntVal;

/**
 * Specifically used for Storage Objects only.
 * Entire life-cycle is controlled by Storage Objects.
 * Main purpose is to separate amnt from Item.
 */
public class StoredItem {
    private Item item;
    private IntVal amnt;

    public StoredItem() {
        item = null;
        amnt = new IntVal();
        amnt.setMax(10000);
    }

    public StoredItem(Item i, int amntStored) {
        item = i;
        amnt = new IntVal();
        amnt.setMax(10000);
        amnt.set(amntStored);
    }

    public Item item() {return item;}
    public IntVal amnt() {return amnt;}
}
