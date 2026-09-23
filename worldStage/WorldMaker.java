package worldStage;

import control.objectGen.StorageSpriteGen;
import dialogue.DialogueMaker;
import tileSet.TileSet;
import control.objectGen.GameCharGen;
import control.objectGen.ItemGen;
import control.objectGen.StaticSpriteGen;

public class WorldMaker {

    public static WorldStage makeWorld(TileSet tileSet) {
        int[] spawn = new int[]{1500, 1500};
        WorldStage world = new WorldStage(tileSet, spawn);

        world.addChar(
                GameCharGen.genChar("GC001"),
                new int[]{spawn[0], spawn[1] + 100},
                DialogueMaker.makeDialogue("resources/object_data/char_data/dialogue/log.txt"),
                false,
                true
        );

        world.addChar(
                GameCharGen.genChar("GC002"),
                new int[]{spawn[0], spawn[1] + 400},
                null,
                true,
                true
        );

        world.addStaticSprite(
                StaticSpriteGen.genStaticSprite("BG000"),
                new int[]{spawn[0] + 100, spawn[1]}
        );

        int chestID = world.addStorage(
                StorageSpriteGen.genStorageSprite("SS000"),
                new int[]{spawn[0] - 50, spawn[1] - 50}
        );
        world.addItemToStorage(
            ItemGen.genItem("IU000"),
            chestID,
            10
        );

        world.addItemToWorld(
                ItemGen.genItem("IW000"),
                new int[]{spawn[0] - 50, spawn[1]}
        );
        world.addItemToWorld(
          ItemGen.genItem("IU000"),
          new int[] {spawn[0] - 50, spawn[1] + 80}
        );
        world.setUpStatBars();

        return world;
    }
}
