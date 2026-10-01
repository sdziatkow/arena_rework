package control.objectGen;

import control.objectGen.gameEntities.GameCharGen;
import control.objectGen.gameEntities.ItemGen;
import control.objectGen.gameEntities.StaticSpriteGen;
import control.objectGen.gameEntities.StorageGen;
import control.objectGen.gameObjects.TileSetMaker;
import worldState.WorldStage;

import java.util.HashMap;
import java.util.Scanner;

public class WorldMaker {

    public static WorldStage makeWorld(String objID) {
        String pathToData = ObjectDataParser.getDataFromObjectID(ObjectType.WORLD, objID);
        HashMap<String, String> worldData = ObjectDataParser.parseObjectData(pathToData);

        WorldStage w = new WorldStage();
        w.setTileSet(TileSetMaker.makeTileSet(worldData.get("pathToTileSet")));
        w.setSpawnPoint(parsePosition(worldData.get("spawnPoint")));
        for (String field : worldData.keySet()) {
            setData(field, worldData.get(field), w);
        }
        return w;
    }

    private static void setData(String field, String val, WorldStage w) {
        switch (field) {
            case "gameChars":
                parseGameChars(val, w);
                break;
            case "staticSprites":
                parseStaticSprites(val, w);
                break;
            case "storages":
                parseStorages(val, w);
                break;
            case "items":
                parseItems(val, w);
                break;
            default:
                break;
        }
    }

    private static int[] parsePosition(String posString) {
        Scanner scn = new Scanner(posString);
        scn.useDelimiter(";");
        int x = 0;
        int y = 0;
        while (scn.hasNext()) {
            x = Integer.parseInt(scn.next());
            y = Integer.parseInt(scn.next());
        }
        scn.close();
        return new int[] {x, y};
    }

    /** objID:spawnX;spawnY:isNPC:isHostile,objID:spawnX;spawnY:isNPC:isHostile,.... */
    private static void parseGameChars(String gameCharString, WorldStage w) {
        Scanner commaSep = new Scanner(gameCharString);
        commaSep.useDelimiter(",");
        while (commaSep.hasNext()) {
            Scanner colonSep = new Scanner(commaSep.next());
            colonSep.useDelimiter(":");
            while (colonSep.hasNext()) {
                String objID = colonSep.next();
                String position = colonSep.next();
                boolean isNPC = Boolean.parseBoolean(colonSep.next());
                boolean isHostile = Boolean.parseBoolean(colonSep.next());
                w.addChar(
                    GameCharGen.genChar(objID),
                    parsePosition(position),
                    isNPC,
                    isHostile
                );
            }
        }
        commaSep.close();
    }

    /** objID:spawnX;spawnY,objID:spawnX;spawnY,.... */
    private static void parseStaticSprites(String spriteString, WorldStage w) {
        Scanner commaSep = new Scanner(spriteString);
        commaSep.useDelimiter(",");
        while (commaSep.hasNext()) {
            Scanner colonSep = new Scanner(commaSep.next());
            colonSep.useDelimiter(":");
            while (colonSep.hasNext()) {
                String objID = colonSep.next();
                String position = colonSep.next();
                w.addStaticSprite(
                    StaticSpriteGen.genStaticSprite(objID),
                    parsePosition(position)
                );
            }
        }
        commaSep.close();
    }

    /** objID:spawnX;spawnY,objID:spawnX;spawnY,.... */
    private static void parseStorages(String stgString, WorldStage w) {
        Scanner commaSep = new Scanner(stgString);
        commaSep.useDelimiter(",");
        while (commaSep.hasNext()) {
            Scanner colonSep = new Scanner(commaSep.next());
            colonSep.useDelimiter(":");
            while (colonSep.hasNext()) {
                String objID = colonSep.next();
                String position = colonSep.next();
                w.addStorage(
                        StorageGen.genStorage(objID),
                        parsePosition(position)
                );
            }
        }
        commaSep.close();
    }

    /** objID:spawnX;spawnY,objID:spawnX;spawnY,.... */
    private static void parseItems(String itemString, WorldStage w) {
        Scanner commaSep = new Scanner(itemString);
        commaSep.useDelimiter(",");
        while (commaSep.hasNext()) {
            Scanner colonSep = new Scanner(commaSep.next());
            colonSep.useDelimiter(":");
            while (colonSep.hasNext()) {
                String objID = colonSep.next();
                String position = colonSep.next();
                w.addItemToWorld(
                        ItemGen.genItem(objID),
                        parsePosition(position)
                );
            }
        }
        commaSep.close();
    }
}
