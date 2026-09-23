package charData.statMods;

import charData.attr.Attr;
import charData.stat.Stat;
import values.ValType;
import java.util.HashMap;

public class StatMod {
    private HashMap<StatChange, HashMap<ValType, HashMap<Attr, Integer>>> attrChanges;
    private HashMap<StatChange, HashMap<ValType, HashMap<Stat, Double>>> statChanges;
    private Integer gameCharID;
    private boolean swapped;

    public StatMod(Integer gameCharID) {
        attrChanges = new HashMap<>();
        statChanges = new HashMap<>();
        this.gameCharID = gameCharID;
        swapped = false;
    }

    public StatMod() {
        attrChanges = new HashMap<>();
        statChanges = new HashMap<>();
        gameCharID = null;
        swapped = false;
    }

//SETTERS----------------------------------------------------------------------------------------------------------------

    /** @param id The ID of the GameChar Object that this StatMod will affect. */
    public void setGameCharID(Integer id) {gameCharID = id;}

//GETTERS----------------------------------------------------------------------------------------------------------------

    /** @return The ID of the GameChar Object that this StatMod will affect. */
    public Integer getGameCharID() {return gameCharID;}

    public Integer getChange(StatChange c, ValType v, Attr a) {
        if (attrChanges.get(c) == null) return null;
        if (attrChanges.get(c).get(v) == null) return null;
        if (attrChanges.get(c).get(v).get(a) == null) return null;
        return attrChanges.get(c).get(v).get(a);
    }
    public Double getChange(StatChange c, ValType v, Stat s) {
        if (statChanges.get(c) == null) return null;
        if (statChanges.get(c).get(v) == null) return null;
        if (statChanges.get(c).get(v).get(s) == null) return null;
        return statChanges.get(c).get(v).get(s);
    }

//FLAGS------------------------------------------------------------------------------------------------------------------

    /** @return True if this StatMod is currently set to have the opposite of its intended affect. */
    public boolean isSwapped() {return swapped;}

//OPERATIONS-------------------------------------------------------------------------------------------------------------

    public void validateAddedChange(ValType v, double change) {
        if (v.equals(ValType.MIN) || change < 0.000) {
            throw new IllegalArgumentException("Given ValType can not be ValType.MIN and given change can not be less than zero.");
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

    /** Swap all mod values to be their opposite. Ex: original mod +10HP -> swapped mod -10HP. */
    public void swapChanges() {
        HashMap<ValType, HashMap<Attr, Integer>> tempAttrP = attrChanges.get(StatChange.PLUS);
        HashMap<ValType, HashMap<Attr, Integer>> tempAttrM = attrChanges.get(StatChange.MULT);;
        HashMap<ValType, HashMap<Stat, Double>> tempStatsP = statChanges.get(StatChange.PLUS);
        HashMap<ValType, HashMap<Stat, Double>> tempStatsM = statChanges.get(StatChange.MULT);

        attrChanges.put(StatChange.PLUS, attrChanges.get(StatChange.MINUS));
        attrChanges.put(StatChange.MINUS, tempAttrP);
        attrChanges.put(StatChange.MULT, attrChanges.get(StatChange.DIV));
        attrChanges.put(StatChange.DIV, tempAttrM);

        statChanges.put(StatChange.PLUS, statChanges.get(StatChange.MINUS));
        statChanges.put(StatChange.MINUS, tempStatsP);
        statChanges.put(StatChange.MULT, statChanges.get(StatChange.DIV));
        statChanges.put(StatChange.DIV, tempStatsM);

        swapped = !swapped;
    }
}
