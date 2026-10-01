package control.runtimeTrackers;

import control.ArenaObject;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;


public class WorldTracker {
    private static final HashMap<WorldEntity, HashMap<Integer, ArenaObject>> all = new HashMap<>();

    public static void add(WorldEntity entity, ArenaObject obj) {
        if (entity == null) return;
        if (obj == null || obj.getID() == null) return;
        all.putIfAbsent(entity, new HashMap<>());
        all.get(entity).put(obj.getID(), obj);
    }

    public static void remove(WorldEntity entity, int arenaID) {
        validateRetrieval(entity, arenaID);
        all.get(entity).remove(arenaID);
    }

    public static ArenaObject get(WorldEntity entity, int arenaID) {
        validateRetrieval(entity, arenaID);
        return all.get(entity).get(arenaID);
    }

    public static Set<ArenaObject> getAll(WorldEntity entity) {
        validateRetrieval(entity);
        return new HashSet<>(all.get(entity).values());
    }

    public static Set<Integer> getAllIDs(WorldEntity entity) {
        validateRetrieval(entity);
        return all.get(entity).keySet();
    }

    private static void validateRetrieval(WorldEntity entity) {
        if
        (
            entity == null
            || all.get(entity) == null
        ) {
            throw new IllegalArgumentException(
                    "Attempting to access an ArenaObject that does not exist or was not properly added.");
        }
    }

    private static void validateRetrieval(WorldEntity entity, int arenaID) {
        validateRetrieval(entity);
        if (all.get(entity).get(arenaID) == null) {
            throw new IllegalArgumentException(
                    "Attempting to access an ArenaObject that does not exist or was not properly added.");
        }
    }
}
