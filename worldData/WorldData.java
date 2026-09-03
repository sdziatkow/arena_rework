package worldData;

import collision.ColChecker;
import control.AttkHandler;
import control.ViewHelper;
import javafx.scene.Group;
import javafx.scene.ParallelCamera;
import menus.Menus;
import movement.MvState;
import movement.PlayerMvmnt;
import movement.npcMvmnt.NPCMvmnt;
import worldData.objectData.BoxTracker;
import worldData.objectData.MvmntTracker;
import worldData.objectData.SpriteTracker;
import worldData.statData.StatTracker;
import worldData.statData.StorageTracker;
import java.util.Stack;

import static collision.ColType.*;

public class WorldData {
    public static GameState state = GameState.RUNNING;
    public static Group bg;
    public static ParallelCamera cam = new ParallelCamera();

//STATE------------------------------------------------------------------------------------------------------------------

    public static void runMvmnt() {
        ViewHelper.updateViewOrder(BoxTracker.getBoxes(WORLDBOX));
        Menus.overlay.toFront();

        // Run Player Movement
        Stack<Integer> collidingWith = ColChecker.isColliding(
            BoxTracker.getBox(CHECKBOX, SpriteTracker.playerID), BoxTracker.getBoxes(WORLDBOX)
        );
        if (!collidingWith.isEmpty()) PlayerMvmnt.forceState(MvState.STOPPED);
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

    public static void onItemPickedUp(int toStorageID, int itemID) {
        StorageTracker.addToStorage(toStorageID, itemID);
    }

    public static void removeSprite(int spriteID) {
        bg.getChildren().remove(SpriteTracker.allSprites.get(spriteID).getGroup());
        SpriteTracker.removeSprite(SpriteTracker.allSprites.get(spriteID));
    }

//MENUS------------------------------------------------------------------------------------------------------------------

    public static void openPicker(int gameCharID) {
        Menus.dispPicker(StatTracker.gameChars.get(gameCharID), StorageTracker.storages.get(gameCharID));
    }
    
    public static void openStorageInteraction(int interactorID, int interactableID) {
        Menus.dispStorageInteraction(
                StorageTracker.storages.get(interactorID), StorageTracker.storages.get(interactableID));
    }
}
