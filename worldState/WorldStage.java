package worldState;

import charData.GameChar;
import control.IDGen;
import control.handlers.StatChangeHandler;
import control.objectGen.gameObjects.DialogueMaker;
import control.runtimeTrackers.WorldTracker;
import itemData.Item;
import javafx.geometry.Bounds;
import javafx.scene.Group;
import javafx.scene.layout.GridPane;
import spriteData.Sprite;
import spriteData.backgroundSprite.PickableSprite;
import spriteData.backgroundSprite.StaticSprite;
import spriteData.backgroundSprite.StorageSprite;
import spriteData.behavior.SpriteBehavior;
import spriteData.charSprite.CharSprite;
import spriteData.charSprite.CombatSprite;
import storageData.EQSlots;
import storageData.Storage;
import tileSet.TileSet;
import control.runtimeTrackers.spriteData.MvmntTracker;
import control.runtimeTrackers.spriteData.SpriteTracker;

import static control.runtimeTrackers.WorldEntity.*;

/**
 * For instantiating Game Entities and creating a playable world space.
 * @see control.objectGen.ObjectType
 * @see control.objectGen.WorldMaker
 */
public class WorldStage {
    private TileSet tileSet;
    private int[] spawn;

    public WorldStage() {
        tileSet = null;
        spawn = null;
    }

//SETTERS----------------------------------------------------------------------------------------------------------------

    public void setTileSet(TileSet t) {tileSet = t;}
    public void setSpawnPoint(int[] point) {spawn = point;}

//GETTERS----------------------------------------------------------------------------------------------------------------

    public Group getBG() {return tileSet.getGroup();}
    public GridPane getWorldBorder() {return tileSet.getBorder();}
    public Bounds getWorldBounds() {return tileSet.getWorldBox().getBounds();}

//STAGING----------------------------------------------------------------------------------------------------------------

    /**
     * Set the position of the given sprite to (spawnPos + givenPos).
     * @param s The Sprite Object whose position will be set.
     * @param pos The position to set the given Sprite to (relative to spawn).
     */
    private void setPosRelativeToSpawn(Sprite s, int[] pos) {s.setPos(spawn[0] + pos[0], spawn[1] + pos[1]);}

    /** This method will:
     * <br> Generate two new IDs (gcID and stgID).
     * <br> Link gcID to the given gameChar.
     * <br> Link stgID to its newly made Storage and to GameChar.setStorageID().
     * <br> Create the following Objects and link gcID to them:
     * <br> CharSprite(Given pathToAttkSheet == null) or CombatSprite(given pathToAttkSheet != null)
     * <br> EQSlots (to equip items from gameChar's Storage).
     * <br> Dialogue (to speak).
     * <br>
     * <br> Track the newly created Objects with SpriteTracker, StorageTracker, and StatTracker.
     * <br> Add the gameChar's spriteGroup to the world Group at the given position.
     * <br> Add all given Item Objects to this gameChar's Storage.
     * @param gameChar The gameChar to be added to the world.
     * @param pos The position of this gameChar's spriteGroup within the world group.
     * @return The gameCharacter's ID.
     */
    public int addChar(GameChar gameChar, int[] pos, boolean isNPC, boolean isHostile) {
        final int gcID = IDGen.genID();
        final int stgID = IDGen.genID();
        gameChar.setID(gcID);
        gameChar.setStorageID(stgID);

        Storage backpack = new Storage();
        backpack.setID(stgID);

        CharSprite sprite;
        if (gameChar.getPathToAttkSheet() != null) {
            sprite = new CombatSprite(gameChar.getPathToMvSheet(), gameChar.getPathToAttkSheet());
            ((CombatSprite)sprite).getWPSprite().setID(gcID);
            if (isHostile) SpriteBehavior.enableHostility((CombatSprite)sprite);
        }
        else sprite = new CharSprite(gameChar.getPathToMvSheet());
        sprite.setID(gcID);
        if (gameChar.getPathToDialogue() != null) {
            SpriteBehavior.enableDialogue(sprite, DialogueMaker.makeDialogue(gameChar.getPathToDialogue()));
        }

        EQSlots eqSlots = new EQSlots();
        eqSlots.setID(gcID);

        WorldTracker.add(GAME_CHAR, gameChar);
        WorldTracker.add(STORAGE, backpack);
        WorldTracker.add(EQ_SLOTS, eqSlots);
        SpriteTracker.trackSprite(sprite);
        if (isNPC) MvmntTracker.trackMvmnt(sprite);

        StatChangeHandler.updateStatsFromAttr(gcID);
        getBG().getChildren().add(sprite.getGroup());
        setPosRelativeToSpawn(sprite, pos);
        return gcID;
    }

    /** This method will:
     * <br> Generate a new ID.
     * <br> Link the ID to the given sprite.
     * <br> Track the sprite with SpriteTracker.
     * <br> Add the given sprite's spriteGroup to the world group at the given position.
     * @param sprite The sprite to be added to the world.
     * @param pos The position of this sprite's spriteGroup within the world group.
     * @return The ID of the given sprite.
     */
    public int addStaticSprite(StaticSprite sprite, int[] pos) {
        final int ID = IDGen.genID();
        sprite.setID(ID);
        SpriteTracker.trackSprite(sprite);

        getBG().getChildren().add(sprite.getGroup());
        setPosRelativeToSpawn(sprite, pos);
        return ID;
    }

    /** This method will:
     * <br> Generate a new ID.
     * <br> Link the ID to the given Storage.
     * <br> Create a new StorageSprite object and link the ID to it.
     * <br> Track the Objects with SpriteTracker and StorageTracker.
     * <br> Add the sprite's spriteGroup to the world Group at the given position.
     * @param stg The Storage Object to be added.
     * @param pos The position of the sprite's spriteGroup within the world group.
     * @return The sprite's ID which is linked to its Storage Object as well.
     */
    public int addStorage(Storage stg, int[] pos) {
        final int ID = IDGen.genID();
        StorageSprite sprite = new StorageSprite(stg.getPathToAnimSprite());
        stg.setID(ID);
        sprite.setID(ID);

        WorldTracker.add(STORAGE, stg);
        SpriteTracker.trackSprite(sprite);

        getBG().getChildren().add(sprite.getGroup());
        setPosRelativeToSpawn(sprite, pos);
        return ID;
    }

    /** This method will:
     * <br> Generate a new ID.
     * <br> Link the ID to the given item.
     * <br> Create a PickableSprite to represent the item within the world.
     * <br> Link the sprites to the same ID.
     * <br> Track the item and sprites with SpriteTracker and StorageTracker.
     * <br> Add item's idleSprite's spriteGroup
     * @param item The item to add to the World.
     * @param pos The item's spriteGroup's position in the world Group.
     * @return The ID of the given item which is linked to its sprites.
     */
    public int addItemToWorld(Item item, int[] pos) {
        final int ID = IDGen.genID();
        item.setID(ID);
        item.setStorageID(null);
        WorldTracker.add(ITEM, item);

        PickableSprite idleSprite = new PickableSprite(item.getPathToPickableSprite());
        idleSprite.setID(ID);
        SpriteTracker.trackSprite(idleSprite);

        getBG().getChildren().add(idleSprite.getGroup());
        setPosRelativeToSpawn(idleSprite, pos);
        return ID;
    }
}
