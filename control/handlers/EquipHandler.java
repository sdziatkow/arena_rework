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
        char changeSign;
        if (eqChangeStatus) {
            eqSlots.equip(item);
            changeSign = '+';
        }
        else {
            eqSlots.unequip(item);
            changeSign = '-';
        }
        if (!(item instanceof Usable)) {
            StatChangeHandler.applyItemChanges(eqSlots.getID(), item.getID(), ValType.MAX, changeSign);
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
        StatChangeHandler.applyItemChanges(eqSlots.getID(), item.getID(), ValType.VAL, '+');
        item.amnt().dec();
        if (item.amnt().isMin()) {
            handleEquip(stg.getID(), item.getID(), WorldLocation.STORAGE, EQAction.UN_EQUIP);
            StorageTracker.removeFromStorage(stg.getID(), item.getID());
            StorageTracker.items.remove(item.getID());
        }
    }
}
