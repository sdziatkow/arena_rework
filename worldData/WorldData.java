package worldData;

import charData.GameChar;
import charData.stat.Stat;
import collision.ColChecker;
import control.handlers.AttkHandler;
import control.ViewHelper;
import control.handlers.EquipHandler;
import dialogue.Dialogue;
import javafx.scene.ParallelCamera;
import menus.Menus;
import movement.MvState;
import movement.PlayerMvmnt;
import movement.npcMvmnt.NPCMvmnt;
import tileSet.TileSet;
import control.runtimeTrackers.spriteData.BoxTracker;
import control.runtimeTrackers.spriteData.MvmntTracker;
import control.runtimeTrackers.spriteData.SpriteTracker;
import control.runtimeTrackers.worldData.DialogueTracker;
import control.runtimeTrackers.worldData.StatTracker;
import control.runtimeTrackers.worldData.StorageTracker;
import java.util.Stack;

import static collision.ColType.*;
import static values.ValType.MAX;
import static worldData.GameState.IN_MENU;
import static worldData.GameState.RUNNING;

public class WorldData {
    public static GameState state = RUNNING;
    public static TileSet bg;
    public static ParallelCamera cam = new ParallelCamera();

//STATE------------------------------------------------------------------------------------------------------------------

    public static void run() {
        switch (state) {
            case RUNNING:
                runMvmnt();
                regenStats();
                break;
            case IN_MENU:
                break;
            case PAUSED:
                break;
            default: break;
        }
    }

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

    //TODO: Make this systematic. Dont calculate in this function, calculate based on Attributes.
    public static void regenStats() {
        StatTracker.gameChars.forEach((Integer id, GameChar c) -> {
            for (Stat vital : Stat.VITALS) {
                double amnt = c.stats().get(vital, MAX) / 10000;
                c.stats().heal(vital, amnt);
            }
        });
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

    public static void useEquippedUsable(int gameCharID) {
        EquipHandler.handleUse(gameCharID, StorageTracker.eqSlots.get(gameCharID).use().getID());
    }

    public static void onItemPickedUp(int toStorageID, int itemID) {
        StorageTracker.addToStorage(toStorageID, itemID);
        removeSprite(itemID);
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
