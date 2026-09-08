package values;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class IntVal {
    private int min;
    private int max;
    private int val;
    private IntegerProperty progressVal;

    /** Default min = 0; max = 1; val = 0; */
    public IntVal() {
        min = 0;
        max = 1;
        val = 0;
        progressVal = new SimpleIntegerProperty(0);
    }

    public IntVal(int min, int max, int val) {
        progressVal = new SimpleIntegerProperty(0);
        this.max = 1;
        setMin(min);
        setMax(max);
        set(val);
    }

    public void setMin(int v) {
        validateMin(v);
        min = v;
    }

    public void setMax(int v) {
        validateMax(v);
        max = v;
        progressVal.set(val / max);
    }

    /** Set this value to its minimum value. */
    public void reset(){set(min);}
    public void set(int v) {
        if (v < min) val = min;
        else val = Math.min(v, max); // If v is greater than max, set val to max.
        progressVal.set(val / max);
    }

    public void inc()         { set(val + 1);    }
    public void inc(int amnt) { set(val + amnt); }
    public void dec()         { set(val - 1);    }
    public void dec(int amnt) { set(val - amnt); }

    public int getMin() {return min;}
    public int getMax() {return max;}
    public int get() {return val;}

    private void validateMin(int min) {
        if (min >= max) throw new IllegalArgumentException("Can not set min value to be more than max value.");
    }
    private void validateMax(int max) {
        if (max <= min) throw new IllegalArgumentException("Can not set max value to be less than min value.");
    }

    public boolean isMax() {return val == max;}
    public boolean isMin() {return val == min;}

    @Override
    public String toString() {
        String out = "|MIN: " + min;
        out += " |MAX: " + max;
        out += " |VAL: " + val;
        out += " |";
        return out;
    }

    public IntegerProperty getProgressVal() {return progressVal;}
}
