package worldData;

import charData.GameChar;
import collision.ColChecker;
import collision.ColType;
import control.AttkHandler;
import control.ViewHelper;
import dialogue.Dialogue;
import itemData.Item;
import itemData.usables.Usable;
import itemData.weapons.Weapon;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.ParallelCamera;
import javafx.scene.layout.GridPane;
import menus.Menus;
import movement.MvState;
import movement.PlayerMvmnt;
import movement.npcMvmnt.NPCMvmnt;
import spriteData.charSprite.CombatSprite;
import spriteData.weaponSprite.WeaponSprite;
import storageData.EQSlots;
import storageData.Storage;
import tileSet.TileSet;
import worldData.objectData.BoxTracker;
import worldData.objectData.MvmntTracker;
import worldData.objectData.SpriteTracker;
import worldData.statData.DialogueTracker;
import worldData.statData.StatTracker;
import worldData.statData.StorageTracker;

import java.util.Objects;
import java.util.Stack;

import static collision.ColType.*;

public class WorldData {
    public static GameState state = GameState.RUNNING;
    public static TileSet bg;
    public static ParallelCamera cam = new ParallelCamera();

//STATE------------------------------------------------------------------------------------------------------------------

    public static void runMvmnt() {
        ViewHelper.updateViewOrder(BoxTracker.getBoxes(WORLDBOX));
        bg.getBorder().toFront();
        Menus.overlay.toFront();


        // Run Player Movement
        Stack<Integer> collidingWith = ColChecker.isColliding(
            BoxTracker.getBox(CHECKBOX, SpriteTracker.playerID), BoxTracker.getBoxes(WORLDBOX)
        );
        if (!collidingWith.isEmpty() || !isInWorldBounds(SpriteTracker.playerID)) PlayerMvmnt.forceState(MvState.STOPPED);
        else PlayerMvmnt.runMvmnt();

        // Run NPC Movement
        MvmntTracker.allNPCMvmnts.values().forEach(NPCMvmnt::runMvmnt);
    }

//GAME-EVENTS------------------------------------------------------------------------------------------------------------

    /** Trigger an attack as an attacker. The game character that is dealing the damage.
     * <br> Will scan the wpSprite's hitBox against all hurtboxes and damage all that collide with its bounds.
     * @param cmbtSprite The ID of the CombatSprite.
     * @param wpSprite The ID of the WeaponSprite.
     * @see AttkHandler
     */
    public static void triggerAttk(Integer cmbtSprite, Integer wpSprite) {

        Stack<Integer> hitting = ColChecker.isColliding (
                cmbtSprite,
                BoxTracker.getBox(HITBOX, wpSprite),
                BoxTracker.getBoxes(HURTBOX)
        );
        if (!hitting.isEmpty()) {
            for (Integer id : hitting) {
                SpriteTracker.hurtables.get(id).onHurt(cmbtSprite);
            }
        }
    }

    public static void triggerInteract(int interactor) {
        Stack<Integer> interactingWith = ColChecker.isColliding(
            BoxTracker.getBox(CHECKBOX, interactor), BoxTracker.getBoxes(INTERACTBOX)
        );
        while (!interactingWith.isEmpty()) {
            SpriteTracker.interactables.get(interactingWith.pop()).onInteract(interactor);
        }
    }

    public static void removeSprite(int spriteID) {
        bg.getGroup().getChildren().remove(SpriteTracker.allSprites.get(spriteID).getGroup());
        SpriteTracker.removeSprite(SpriteTracker.allSprites.get(spriteID));
    }

//STORAGE----------------------------------------------------------------------------------------------------------------

    public static void eqItemFromOwnStorage(Integer storageID, Integer itemID) {
        Item item = StorageTracker.items.get(itemID);
        if (item == null) return;
        if (item.getStorageID() != storageID) return;
        item.toggleEquipped(true);
        EQSlots eqSlots = StorageTracker.eqSlots.get(storageID);
        if (eqSlots == null) return;
        eqSlots.equip(item);
        if (item instanceof Weapon) {
            CombatSprite sprite = SpriteTracker.combatSprites.get(storageID);
            if (sprite == null) return;
            sprite.setWPSprite(new WeaponSprite(((Weapon)item).getPathToWpnSprite()));
        }
    }

    public static void unEqItemFromOwnStorage(Integer storageID, Integer itemID) {
        Item item = StorageTracker.items.get(itemID);
        if (item == null) return;
        if (item.getStorageID() != storageID) return;
        item.toggleEquipped(false);
        EQSlots eqSlots = StorageTracker.eqSlots.get(storageID);
        if (eqSlots == null) return;
        eqSlots.unequip(item);
        if (item instanceof Weapon) {
            CombatSprite sprite = SpriteTracker.combatSprites.get(storageID);
            if (sprite == null) return;
            sprite.clearWPSprite();
        }
    }
    public void useEquippedUsable(int gameCharID) {
        EQSlots eqSlots = StorageTracker.eqSlots.get(gameCharID);
        if (eqSlots == null) return;
        Usable item = (Usable)eqSlots.use();
        if (item == null) return;
        GameChar gameChar = StatTracker.gameChars.get(gameCharID);
        if (gameChar == null) return;
        //TODO: Heal shit.
    }

    public static void onItemPickedUp(int toStorageID, int itemID) {
        StorageTracker.addToStorage(toStorageID, itemID);
    }

//MENUS------------------------------------------------------------------------------------------------------------------

    public static void openPicker(int gameCharID) {
        Menus.dispPicker(StatTracker.gameChars.get(gameCharID), StorageTracker.storages.get(gameCharID));
    }
    
    public static void openStorageInteraction(int interactorID, int interactableID) {
        Menus.dispStorageInteraction(
                StorageTracker.storages.get(interactorID), StorageTracker.storages.get(interactableID));
    }

    public static void openDialogueMenu(Integer dialogueID) {
        Dialogue d = DialogueTracker.dialogues.get(dialogueID);
        if (d != null) {
            System.out.println(d.speak());
        }
    }

//WORLD-BOUNDS-----------------------------------------------------------------------------------------------------------

    public static boolean isInWorldBounds(int checkBoxID) {
        return bg.getWorldBox().getBounds().contains(BoxTracker.getBox(CHECKBOX, checkBoxID).getBounds());
    }
}
