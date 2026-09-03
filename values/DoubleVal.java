package values;

public class DoubleVal {
    private double min;
    private double max;
    private double val;

    public DoubleVal() {
        min = 0;
        max = 1;
        val = 0;
    }

    public DoubleVal(double min, double max, double val) {
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
    }

    /** Set this value to its minimum value. */
    public void reset(){set(min);}
    public void set(double v) {
        if (v < min) val = min;
        else val = Math.min(v, max);
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
}
