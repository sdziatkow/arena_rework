package control.handlers;

import control.WorldLocation;
import itemData.Item;
import itemData.usables.Usable;
import itemData.weapons.Weapon;
import spriteData.charSprite.CombatSprite;
import spriteData.weaponSprite.WeaponSprite;
import storageData.EQSlots;
import control.runtimeTrackers.spriteData.SpriteTracker;
import control.runtimeTrackers.worldData.StorageTracker;
import storageData.Storage;
import values.ValType;

/**
 * Handles all of the following during run-time:
 * <br>Item equipping
 * <br>Item un-equipping
 * <br>Using an equipped Usable Item
 */
public class EquipHandler {

    public static void handleEquip(Integer storageID, Integer itemID, WorldLocation from, EQAction actn) {
        Item item = StorageTracker.items.get(itemID);
        if (item == null) return;
        switch (from) {
            case OVERWORLD: // no implementation for equipping from overworld.
                break;
            case STORAGE:
                if (storageID == null) return;
                EQSlots eqSlots = StorageTracker.eqSlots.get(storageID);
                if (eqSlots == null) return;
                if (item.getStorageID().equals(storageID)) handleItemFromOwnStorage(item, eqSlots, actn);
                break;
            default:
                break;
        }
    }

    public static void handleUse(Integer storageID, Integer itemID) {
        Usable item = (Usable)StorageTracker.items.get(itemID);
        if (item == null) return;
        Storage stg = StorageTracker.storages.get(storageID);
        EQSlots eqSlots = StorageTracker.eqSlots.get(storageID);
        if (stg == null || eqSlots == null) return;
        if (storageID.equals(item.getStorageID())) {
            useEquippedUsable(item, eqSlots, stg);
        }

    }


//EQUIPPING--------------------------------------------------------------------------------------------------------------

    private static void handleItemFromOwnStorage(Item item, EQSlots eqSlots, EQAction actn) {
        boolean eqChangeStatus = (actn.equals(EQAction.EQUIP));
        if (item.isEquipped() == eqChangeStatus) return; // If the item's equip state matches the given action, do nothing.
        item.toggleEquipped(eqChangeStatus);

        // Equip or Dequip given item.
        if (eqChangeStatus) {
            eqSlots.equip(item);
        }
        else {
            eqSlots.unequip(item);
        }

        // Apply the Item's StatMod on equip, only if it is not Usable.
        if (!(item instanceof Usable)) {
            if (eqChangeStatus) {
                if (item.statMod().isSwapped()) item.statMod().swapChanges(); // Apply mod
                StatChangeHandler.applyStatMod(eqSlots.getID(), item.statMod());
            }
            else {
                if (!item.statMod().isSwapped()) item.statMod().swapChanges(); // Undo mod
                StatChangeHandler.applyStatMod(eqSlots.getID(), item.statMod());
            }
        }

        if (item instanceof Weapon) {

            // Get CombatSprite Object related to given eqSlots.
            CombatSprite sprite = SpriteTracker.combatSprites.get(eqSlots.getID());
            if (sprite == null) return;

            // If it exists, set its WeaponSprite Object to the Weapon Object's WeaponSprite; clear it if un-equipping.
            if (eqChangeStatus) {
                sprite.setWPSprite(new WeaponSprite(((Weapon)item).getPathToWpnSprite()));
            }
            else sprite.clearWPSprite();
        }
    }

//USING------------------------------------------------------------------------------------------------------------------

    public static void useEquippedUsable(Usable item, EQSlots eqSlots, Storage stg) {
        StatChangeHandler.applyStatMod(eqSlots.getID(), item.statMod());
        StorageHandler.removeFrom(stg.getID(), item.getID(), 1);
        if (!stg.hasItem(item.getID())) { // If item was removed from storage.
            handleEquip(stg.getID(), item.getID(), WorldLocation.STORAGE, EQAction.UN_EQUIP);
            StorageTracker.items.remove(item.getID());
        }
    }
}
