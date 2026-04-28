package worldData;

import charData.stat.Stat;
import collision.ColChecker;
import control.AttkHandler;
import control.ViewHelper;
import javafx.scene.Group;
import javafx.scene.ParallelCamera;
import menus.Menus;
import movement.MvState;
import movement.PlayerMvmnt;
import movement.npcMvmnt.NPCMvmnt;
import worldData.objectData.SpriteTracker;
import worldData.statData.StatTracker;
import worldData.statData.StorageTracker;
import java.util.Stack;

public class WorldData {
    public static GameState state = GameState.RUNNING;
    public static Group bg;
    public static ParallelCamera cam = new ParallelCamera();

//STATE------------------------------------------------------------------------------------------------------------------

    public static void runMvmnt() {
        ViewHelper.updateViewOrder(SpriteTracker.collidables);
        Menus.overlay.toFront();
        SpriteTracker.movables.forEach((id, mover) -> {
            if (id == SpriteTracker.playerID) {
                Stack<Integer> collidingWith = ColChecker.isColliding(mover, SpriteTracker.collidables);
                if (!collidingWith.isEmpty()) PlayerMvmnt.forceState(MvState.STOPPED);
                else PlayerMvmnt.runMvmnt();
            }
            else {
                NPCMvmnt.setSprite(SpriteTracker.charSprites.get(id));
                NPCMvmnt.move();
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

        Stack<Integer> hitting = ColChecker.isHitting(
                cmbtSprite,
                SpriteTracker.weapons.get(wpSprite),
                SpriteTracker.hurtables
        );
        if (!hitting.isEmpty()) {
            for (Integer id : hitting) {
                AttkHandler.handleAttk(cmbtSprite, id, StatTracker.gameChars);
            }
        }
    }

    public static void triggerInteract(int interactor) {
        Stack<Integer> interactingWith = ColChecker.isInteracting(
                SpriteTracker.movables.get(interactor), SpriteTracker.interactables
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
