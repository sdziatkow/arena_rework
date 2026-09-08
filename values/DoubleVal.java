package values;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class DoubleVal {
    private double min;
    private double max;
    private double val;
    private DoubleProperty progressVal;

    public DoubleVal() {
        progressVal = new SimpleDoubleProperty(0);
        min = 0;
        max = 1;
        val = 0;
    }

    public DoubleVal(double min, double max, double val) {
        progressVal = new SimpleDoubleProperty(0);
        this.max = 1;
        setMin(min);
        setMax(max);
        set(val);
    }

    public void setMin(double v) {
        validateMin(v);
        min = v;
    }

    public void setMax(double v) {
        validateMax(v);
        max = v;
        progressVal.set(val / max);
    }

    /** Set this value to its minimum value. */
    public void reset(){set(min);}
    public void set(double v) {
        if (v < min) val = min;
        else val = Math.min(v, max);
        progressVal.set(val / max);
    }
    public void inc()         { set(val + 1.0);    }
    public void inc(double amnt) { set(val + amnt); }
    public void dec()         { set(val - 1.0);    }
    public void dec(double amnt) { set(val - amnt); }

    public double getMin() {return min;}
    public double getMax() {return max;}
    public double get() {return val;}

    private void validateMin(double min) {
        if (min >= max) throw new IllegalArgumentException("Can not set min value to be more than max value.");
    }
    private void validateMax(double max) {
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
