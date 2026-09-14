package values;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class IntVal {
    private int min;
    private int max;
    private int val;
    private DoubleProperty progressVal;

    /** Default min = 0; max = 1; val = 0; */
    public IntVal() {
        min = 0;
        max = 1;
        val = 0;
        progressVal = new SimpleDoubleProperty(0);
    }

    public IntVal(int min, int max, int val) {
        progressVal = new SimpleDoubleProperty(0);
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
        progressVal.set((double)val / (double)max);
    }

    /** Set this value to its minimum value. */
    public void reset(){set(min);}
    public void set(int v) {
        if (v < min) val = min;
        else val = Math.min(v, max); // If v is greater than max, set val to max.
        progressVal.set((double)val / (double)max);
    }
    public void set(ValType v, int val) {
        switch (v) {
            case MIN: setMin(val); break;
            case MAX: setMax(val); break;
            case VAL:
            default: set(val); break;
        }
    }

    public void inc()         { set(val + 1);    }
    public void inc(int amnt) { set(val + amnt); }
    public void dec()         { set(val - 1);    }
    public void dec(int amnt) { set(val - amnt); }

    public int getMin() {return min;}
    public int getMax() {return max;}
    public int get() {return val;}
    public int get(ValType v) {
        switch (v) {
            case MIN: return getMin();
            case MAX: return getMax();
            case VAL:
            default: return get();
        }
    }

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

    public DoubleProperty getProgressVal() {return progressVal;}
}
