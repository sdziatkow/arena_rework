package worldState;

import charData.GameChar;
import charData.stat.Stat;
import collision.ColChecker;
import control.ArenaObject;
import control.handlers.AttkHandler;
import control.ViewHelper;
import control.handlers.EquipHandler;
import control.handlers.StorageHandler;
import control.objectGen.gameEntities.GameCharGen;
import control.runtimeTrackers.WorldTracker;
import control.runtimeTrackers.spriteData.SpriteType;
import dialogue.Dialogue;
import javafx.scene.ParallelCamera;
import javafx.scene.Scene;
import menus.Menus;
import menus.statBar.StatBar;
import movement.CharMvmnt;
import movement.MvState;
import movement.PlayerMvmnt;
import movement.npcMvmnt.NPCMvmnt;
import control.runtimeTrackers.spriteData.BoxTracker;
import control.runtimeTrackers.spriteData.MvmntTracker;
import control.runtimeTrackers.spriteData.SpriteTracker;
import spriteData.Sprite;
import spriteData.behavior.boxes.Hurtable;
import spriteData.behavior.boxes.Interactable;
import spriteData.charSprite.CharSprite;
import control.objectGen.WorldMaker;
import spriteData.charSprite.CombatSprite;
import storageData.EQSlots;
import storageData.Storage;

import java.util.Collections;
import java.util.Stack;

import static collision.ColType.*;
import static control.runtimeTrackers.WorldEntity.*;
import static control.runtimeTrackers.spriteData.SpriteType.*;
import static worldState.GameState.RUNNING;

/**
 * Handles overhead operations based on game-state during run-time.
 */
public class WorldState {
    public static GameState state;
    public static WorldStage world;
    public static Integer playerID;
    public static ParallelCamera cam;
    public static Scene scene;

//STAGING----------------------------------------------------------------------------------------------------------------

    public static void stageWorld(String objID) {
        clearWorld();
        world = WorldMaker.makeWorld(objID);
        state = RUNNING;
        stagePlayer();
        setUpStatBars();
        setUpScene();
        setUpMenus();
        world.getBG().setCache(true);
    }

    private static void clearWorld() {
        state = null;
        world = null;
        playerID = null;
        cam = null;
        scene = null;
    }

    private static void stagePlayer() {
        playerID = world.addChar(
                GameCharGen.genChar("GC000"),
                new int[]{0, 200},
                false,
                false
        );
        PlayerMvmnt.setSprite((CombatSprite)SpriteTracker.get(COMBATANT, playerID));
        PlayerMvmnt.cntrlSetUp();
    }

    private static void setUpStatBars() {
        WorldTracker.getAllIDs(GAME_CHAR).forEach((Integer id) -> {
            GameChar g = (GameChar)WorldTracker.get(GAME_CHAR, id);
            CharSprite s = (CharSprite)SpriteTracker.get(MOVABLE, id);
            if (!id.equals(playerID)) {
                StatBar bar = new StatBar(Stat.HP, g.stats().progressVal(Stat.HP));
                s.setStatBar(bar);
            }
            else {
                Menus.addOverlayStatBars(
                        g.stats().progressVal(Stat.HP),
                        g.stats().progressVal(Stat.MP),
                        g.stats().progressVal(Stat.SP),
                        g.lvl().getProgressVal()
                );
            }
    });
    }

    private static void setUpScene() {
        cam = new ParallelCamera();
        cam.setCache(true);
        scene = new Scene(WorldState.world.getBG(), 3000, 3000, true);
        scene.setCamera(cam);
        scene.getCamera().setCache(true);
    }

    private static void setUpMenus() {
        Menus.overlay.setCache(true);
        Menus.overlay.getChildren().add(cam);
        world.getBG().getChildren().add(Menus.overlay);
        CharMvmnt.bindNode((CharSprite)SpriteTracker.get(MOVABLE, playerID), Menus.overlay);
    }

//STATE------------------------------------------------------------------------------------------------------------------

    public static void run() {
        if (state == null) return;
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

    private static void runMvmnt() {
        ViewHelper.updateViewOrder(BoxTracker.getBoxes(WORLDBOX));
        world.getWorldBorder().toFront();
        Menus.overlay.toFront();


        // Run Player Movement
        Stack<Integer> collidingWith = ColChecker.isColliding(
            BoxTracker.getBox(CHECKBOX, playerID), BoxTracker.getBoxes(WORLDBOX)
        );
        if (!collidingWith.isEmpty() || !isInWorldBounds(playerID)) PlayerMvmnt.forceState(MvState.STOPPED);
        else PlayerMvmnt.runMvmnt();

        // Run NPC Movement
        MvmntTracker.allNPCMvmnts.values().forEach(NPCMvmnt::runMvmnt);
    }

    public static void regenStats() {
        WorldTracker.getAll(GAME_CHAR).forEach((ArenaObject g) -> ((GameChar) g).stats().regenVitals());
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
                ((Hurtable)SpriteTracker.get(HURTABLE, id)).onHurt(cmbtSprite);
            }
        }
    }

    public static void triggerInteract(int interactor) {
        Stack<Integer> interactingWith = ColChecker.isColliding(
            BoxTracker.getBox(CHECKBOX, interactor), BoxTracker.getBoxes(INTERACTBOX)
        );
        while (!interactingWith.isEmpty()) {
            ((Interactable)SpriteTracker.get(INTERACTABLE, interactingWith.pop())).onInteract(interactor);
        }
    }

    public static void removeSprite(SpriteType type, int spriteID) {
        world.getBG().getChildren().remove(SpriteTracker.get(type, spriteID).getGroup());
        SpriteTracker.remove(type, spriteID);
    }

//STORAGE----------------------------------------------------------------------------------------------------------------

    public static void useEquippedUsable(int gameCharID) {
        int stgID = ((GameChar)WorldTracker.get(GAME_CHAR, gameCharID)).getStorageID();
        int itemID = ((EQSlots)WorldTracker.get(EQ_SLOTS, gameCharID)).use().getID();
        EquipHandler.handleUse(gameCharID, stgID, itemID);
    }

    public static void onItemPickedUp(int toStorageID, int itemID) {
        if (!WorldTracker.getAllIDs(STORAGE).contains(toStorageID)) {
            toStorageID = ((GameChar)WorldTracker.get(GAME_CHAR, toStorageID)).getStorageID();
        }
        StorageHandler.addTo(toStorageID, itemID, 1);
        removeSprite(INTERACTABLE, itemID);
    }

//MENUS------------------------------------------------------------------------------------------------------------------
    
    public static void openStorageInteraction(int interactorID, int interactableID) {
        if (!WorldTracker.getAllIDs(STORAGE).contains(interactorID)) {
            interactorID = ((GameChar)WorldTracker.get(GAME_CHAR, interactorID)).getStorageID();
        }
        if (!WorldTracker.getAllIDs(STORAGE).contains(interactableID)) {
            interactableID = ((GameChar)WorldTracker.get(GAME_CHAR, interactableID)).getStorageID();
        }
        Menus.dispStorageInteraction(
            (Storage)WorldTracker.get(STORAGE, interactorID), (Storage)WorldTracker.get(STORAGE, interactableID));
    }

    public static void openDialogueMenu(Integer dialogueID) {
        Dialogue d = (Dialogue)WorldTracker.get(DIALOGUE, dialogueID);
        if (d != null) {
            System.out.println(d.speak());
        }
    }

//WORLD-BOUNDS-----------------------------------------------------------------------------------------------------------

    public static boolean isInWorldBounds(int checkBoxID) {
        return world.getWorldBounds().contains(BoxTracker.getBox(CHECKBOX, checkBoxID).getBounds());
    }
}
