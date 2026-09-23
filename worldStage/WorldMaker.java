package worldStage;

import control.IDGen;
import control.runtimeTrackers.worldData.StorageTracker;
import dialogue.DialogueMaker;
import itemData.Item;
import itemData.usables.StatPot;
import itemData.weapons.Weapon;
import spriteData.backgroundSprite.StorageSprite;
import tileSet.TileSet;
import control.objectGen.GameCharGen;
import control.objectGen.ItemGen;
import control.objectGen.StaticSpriteGen;

public class WorldMaker {

    public static WorldStage makeWorld(TileSet tileSet) {
        int[] spawn = new int[]{1500, 1500};
        WorldStage world = new WorldStage(tileSet, spawn);

        world.addChar(
                GameCharGen.genChar("resources/object_data/char_data/log.txt"),
                new int[]{spawn[0], spawn[1] + 100},
                DialogueMaker.makeDialogue("resources/object_data/char_data/dialogue/log.txt"),
                false,
                true
        );

        world.addChar(
                GameCharGen.genChar("resources/object_data/char_data/test_enemy.txt"),
                new int[]{spawn[0], spawn[1] + 400},
                null,
                true,
                true
        );

        world.addStaticSprite(
                StaticSpriteGen.genStaticSprite("resources/object_data/bg_sprite_data/stone_tower.txt"),
                new int[]{spawn[0] + 100, spawn[1]}
        );

        int chestID = world.addStorage(
                new StorageSprite("file:resources/sprites/bg_sprites/chest/open_1x3_20x20.png"),
                new int[]{spawn[0] - 50, spawn[1] - 50}
        );
        world.addItemToStorage(
            ItemGen.genItem("resources/object_data/item_data/usable_data/statpot_data/hp_pot.txt", new StatPot()),
            chestID,
            10
        );

        world.addItemToWorld(
                ItemGen.genItem("resources/object_data/item_data/wpn_data/steel_sword.txt", new Weapon()),
                new int[]{spawn[0] - 50, spawn[1]}
        );
        world.addItemToWorld(
          ItemGen.genItem("resources/object_data/item_data/usable_data/statpot_data/hp_pot.txt", new StatPot()),
          new int[] {spawn[0] - 50, spawn[1] + 80}
        );
        world.setUpStatBars();

        return world;
    }
}
