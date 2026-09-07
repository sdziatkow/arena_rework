package worldStage;

import charData.GameChar;
import charData.stat.Stat;
import collision.ColType;
import control.IDGen;
import itemData.Item;
import menus.statBar.StatBar;
import movement.PlayerMvmnt;
import spriteData.backgroundSprite.PickableSprite;
import spriteData.backgroundSprite.StaticSprite;
import spriteData.backgroundSprite.StorageSprite;
import spriteData.behavior.SpriteBehavior;
import spriteData.charSprite.CharSprite;
import spriteData.charSprite.CombatSprite;
import storageData.EQSlots;
import storageData.Storage;
import tileSet.TileSet;
import worldData.objectData.MvmntTracker;
import worldData.objectData.SpriteTracker;
import worldData.statData.StatTracker;
import worldData.statData.StorageTracker;
import worldStage.loading.GameCharGen;

/** For instantiating Arena Objects and creating a playable world space. */
public class WorldStage {
    private TileSet world;
    public int[] spawn;

    public WorldStage(TileSet tileSet, int[] spawnPoint) {
        world = tileSet;
        spawn = spawnPoint;

        int playerID = addChar(
                GameCharGen.genChar("resources/object_data/char_data/default_char.txt"),
                new int[]{spawn[0], spawn[1]},
                null,
                false,
                false
        );
        setUpPlayer(playerID);
    }

    public void setUpStatBars() {
        SpriteTracker.charSprites.forEach((Integer id, CharSprite sprite) -> {
//            if (id != SpriteTracker.playerID) {
                StatBar bar = new StatBar(Stat.HP, StatTracker.gameChars.get(id).stats().get(Stat.HP));
                sprite.setStatBar(bar);
//            }
        });
    }

    public void setUpPlayer(int playerID) {
        SpriteTracker.playerID = playerID;
        SpriteTracker.combatSprites.get(playerID).setNPCState(null);

        PlayerMvmnt.setSprite(SpriteTracker.combatSprites.get(playerID));
        PlayerMvmnt.cntrlSetUp();
    }

    /** This method will:
     * <br> Generate a new ID.
     * <br> Link the ID to the given gameChar
     * <br> Create the following Objects and link the same ID to them:
     * <br> CharSprite(Given pathToAttkSheet == null) or CombatSprite(given pathToAttkSheet != null)
     * <br> Storage (to store gameChar's items).
     * <br> EQSlots (to equip items from gameChar's Storage).
     * <br>
     * <br> Track the newly created Objects with SpriteTracker, StorageTracker, and StatTracker.
     * <br> Add the gameChar's spriteGroup to the world Group at the given position.
     * <br> Add all given Item Objects to this gameChar's Storage.
     * @param gameChar The gameChar to be added to the world.
     * @param pos The position of this gameChar's spriteGroup within the world group.
     * @param items The items to store in the gameChar's Storage.
     * @return The gameCharacter's ID.
     */
    public int addChar(GameChar gameChar, int[] pos, Item[] items, boolean isHostile, boolean isNPC) {
        final int ID = IDGen.genID();
        gameChar.setID(ID);
        CharSprite sprite;

        if (gameChar.getPathToAttkSheet() != null) {
            sprite = new CombatSprite(gameChar.getPathToMvSheet(), gameChar.getPathToAttkSheet());
            ((CombatSprite)sprite).getWPSprite().setID(ID);
            if (isHostile) SpriteBehavior.enableHostility((CombatSprite)sprite);
        }else sprite = new CharSprite(gameChar.getPathToMvSheet());

        Storage backpack = new Storage();
        EQSlots eqSlots = new EQSlots();

        sprite.setID(ID);
        backpack.setID(ID);
        eqSlots.setID(ID);

        SpriteTracker.trackSprite(sprite);
        StatTracker.trackGameChar(gameChar);
        StorageTracker.trackStorage(backpack);
        StorageTracker.trackEQSlots(eqSlots);
        if (isNPC) MvmntTracker.trackMvmnt(sprite);

        if (items != null) fillStorage(backpack, items);

        world.getGroup().getChildren().add(sprite.getGroup());
        sprite.setPos(pos[0], pos[1]);
//        sprite.getWorldBox().getColBox().setOpacity(1);
//        sprite.getCheckBox().getColBox().setOpacity(1);
//        sprite.getHurtBox().getColBox().setOpacity(1);
        return ID;
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

        world.getGroup().getChildren().add(sprite.getGroup());
        sprite.setPos(pos[0], pos[1]);
//        sprite.getWorldBox().getColBox().setOpacity(1);
        return ID;
    }

    /** This method will:
     * <br> Generate a new ID.
     * <br> Link the ID to the given sprite.
     * <br> Create a new Storage object and link the ID to it.
     * <br> Track the Objects with SpriteTracker and StorageTracker.
     * <br> Add the sprite's spriteGroup to the world Group at the given position.
     * @param sprite The sprite to be added.
     * @param pos The position of the sprite's spriteGroup within the world group.
     * @return The sprite's ID which is linked to its Storage Object as well.
     */
    public int addStorage(StorageSprite sprite, Item[] items, int[] pos) {
        final int ID = IDGen.genID();
        sprite.setID(ID);
        Storage storage = new Storage();
        storage.setID(ID);

        SpriteTracker.trackSprite(sprite);
        StorageTracker.trackStorage(storage);

        if (items != null) fillStorage(storage, items);

        world.getGroup().getChildren().add(sprite.getGroup());
        sprite.setPos(pos[0], pos[1]);

        //sprite.getBox(ColType.WORLDBOX).getColBox().setOpacity(1);
        //sprite.getBox(ColType.INTERACTBOX).getColBox().setOpacity(1);
        return ID;
    }

    /** This method will:
     * <br> Generate a new ID.
     * <br> Link the ID to the given item.
     * <br> Create a PickableSprite to represent the item within the world.
     * <br> Create sprites respective to the given item's pathTo fields. (varies depending on type of item).
     * <br> Link the sprites to the same ID.
     * <br> Track the item and sprites with SpriteTracker and StorageTracker.
     * <br> Add item's idleSprite's spriteGroup
     * @param item The item to add to the World.
     * @param amnt The amount of the item to be added (its amount field, not the amount of sprites).
     * @param pos The item's spriteGroup's position in the world Group.
     * @return The ID of the given item which is linked to its sprites.
     */
    public int addItemNoStorage(Item item, int amnt, int[] pos) {
        final int ID = IDGen.genID();
        item.setID(ID);
        item.setStorageID(null);
        item.amnt().set(amnt);
        StorageTracker.trackItem(item);

        PickableSprite idleSprite = new PickableSprite(item.getPathToPickableSprite());
        idleSprite.setID(ID);
        SpriteTracker.trackSprite(idleSprite);

        world.getGroup().getChildren().add(idleSprite.getGroup());
        idleSprite.setPos(pos[0], pos[1]);
        return ID;
    }

    /** Given Storage Object's ID field must be set before sending it here. */
    public void fillStorage(Storage strg, Item[] items) {
        for (int i = 0; i < items.length; ++i) {
            items[i].setStorageID(strg.getID());
            strg.store(items[i]);
        }
    }

    public TileSet getWorld() {return world;}
}
