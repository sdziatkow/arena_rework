package charData.statMods;

import charData.attr.Attr;
import charData.stat.Stat;
import values.ValType;
import java.util.HashMap;

public class StatMod {
    private HashMap<StatChange, HashMap<ValType, HashMap<Attr, Integer>>> attrChanges;
    private HashMap<StatChange, HashMap<ValType, HashMap<Stat, Double>>> statChanges;
    private Integer gameCharID;

    public StatMod(Integer gameCharID) {
        attrChanges = new HashMap<>();
        statChanges = new HashMap<>();
        this.gameCharID = gameCharID;
    }

    public StatMod() {
        attrChanges = new HashMap<>();
        statChanges = new HashMap<>();
        gameCharID = null;
    }

//SETTERS----------------------------------------------------------------------------------------------------------------

    /** @param id The ID of the GameChar Object that this StatMod will affect. */
    public void setGameCharID(Integer id) {gameCharID = id;}

//GETTERS----------------------------------------------------------------------------------------------------------------

    /** @return The ID of the GameChar Object that this StatMod will affect. */
    public Integer getGameCharID() {return gameCharID;}

    public Integer getChange(StatChange c, ValType v, Attr a) {
        if (!attrChanges.containsKey(c)) return null;
        if (!attrChanges.get(c).containsKey(v)) return null;
        if (!attrChanges.get(c).get(v).containsKey(a)) return null;
        return attrChanges.get(c).get(v).get(a);
    }
    public Double getChange(StatChange c, ValType v, Stat s) {
        if (!statChanges.containsKey(c)) return null;
        if (!statChanges.get(c).containsKey(v)) return null;
        if (!statChanges.get(c).get(v).containsKey(s)) return null;
        return statChanges.get(c).get(v).get(s);
    }

//FLAGS------------------------------------------------------------------------------------------------------------------

    public boolean hasAttrChanges() {return !attrChanges.isEmpty();}
    public boolean hasStatChanges() {return !statChanges.isEmpty();}

//OPERATIONS-------------------------------------------------------------------------------------------------------------

    public void validateAddedChange(ValType v, double change) {
        if (v.equals(ValType.MIN) || change < 0.000) {
            throw new IllegalArgumentException("Given ValType can not ValType.MIN and given change can not be less than zero.");
        }
    }

    public void addAttrChange(StatChange c, ValType v, Attr a, int change) {
        validateAddedChange(v, change);
        attrChanges.putIfAbsent(c, new HashMap<>());
        attrChanges.get(c).putIfAbsent(v, new HashMap<>());
        attrChanges.get(c).get(v).put(a, change);
    }

    public void addStatChange(StatChange c, ValType v, Stat s, double change) {
        validateAddedChange(v, change);
        statChanges.putIfAbsent(c, new HashMap<>());
        statChanges.get(c).putIfAbsent(v, new HashMap<>());
        statChanges.get(c).get(v).put(s, change);
    }
}
