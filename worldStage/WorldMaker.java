package worldStage;

import itemData.weapons.Weapon;
import spriteData.backgroundSprite.StorageSprite;
import tileSet.TileSet;
import worldStage.loading.GameCharGen;
import worldStage.loading.ItemGen;
import worldStage.loading.StaticSpriteGen;

public class WorldMaker {

    public static WorldStage makeWorld(TileSet tileSet) {
        int[] spawn = new int[]{1500, 1500};
        WorldStage world = new WorldStage(tileSet, spawn);
        world.addChar(
                GameCharGen.genChar("resources/object_data/char_data/log.txt"),
                new int[]{spawn[0], spawn[1] + 100},
                null,
                false,
                true
        );

        world.addChar(GameCharGen.genChar("resources/object_data/char_data/test_enemy.txt"),
                new int[]{spawn[0], spawn[1] + 200},
                null,
                true,
                true
        );

        world.addStaticSprite(
                StaticSpriteGen.genStaticSprite("resources/object_data/bg_sprite_data/stone_tower.txt"),
                new int[]{spawn[0] + 100, spawn[1]}
        );

        world.addStorage(
                new StorageSprite("file:resources/sprites/bg_sprites/chest/open_1x3_20x20.png"),
                null,
                new int[]{spawn[0] - 50, spawn[1] - 50}
        );

        world.addItemNoStorage(
                ItemGen.genItem("resources/object_data/item_data/wpn_data/steel_sword.txt", new Weapon()),
                1,
                new int[]{spawn[0] - 50, spawn[1]}
        );
        world.setUpStatBars();
        return world;
    }
}
