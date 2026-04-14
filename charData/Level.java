package charData;

import values.DoubleVal;
import values.IntVal;

public class Level {
    private final double DEFAULT_TO_NEXT = 100;
    private double xp;
    private double toNext;
    private int lvl;

    public Level() {
        xp = 0.0;
        toNext = DEFAULT_TO_NEXT;
        lvl = 0;
    }

//GETTERS----------------------------------------------------------------------------------------------------------------

    /** @return Double value describing the current amount of xp */
    public double getXp() { return xp; }

    /** @return Double value describing the amount of xp required to level up */
    public double getToNext() { return toNext; }

    /** @return Integer value describing the level */
    public int getLvl() { return lvl; }

//OPERATIONS-------------------------------------------------------------------------------------------------------------

    /** Increment xp by 1 and level up if possible. */
    public void incXp() {
        ++xp;
        if (canLvlUp()) lvlUp();
    }

    /** Increment xp by given amount and level up if possible. */
    public void incXp(double amnt) {
        xp += amnt;
        if (canLvlUp()) lvlUp();
    }

    /** Increment level by 1. */
    public void incLvl() {
        ++lvl;
    }

    /** Set level to given value. Value MUST be greater than zero otherwise raise error. */
    public void setLvl(int val) {
        if (val < 0) throw new IllegalArgumentException("Given value for level must be greater than zero.");
        lvl = val;
    }

    /** @return true if current xp is greater than or equal to the required amount to level up. */
    public boolean canLvlUp() { return (xp >= toNext); }
    public void lvlUp() {
        xp -= toNext;
        ++lvl;
        toNext = (DEFAULT_TO_NEXT + toNext) * 1.13;
    }

    @Override
    public String toString() {
        String out = "|XP: " + (int) xp;
        out += " |TO-NEXT: " + (int) toNext;
        out += " |LEVEL: "   + lvl;
        return out;
    }

}
