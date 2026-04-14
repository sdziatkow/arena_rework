package charData;

import java.util.HashMap;
import values.DoubleVal;

public class CharStats {
    private final HashMap<Stat, DoubleVal> ALL_STATS;

    public CharStats() {
        ALL_STATS = new HashMap<>(Stat.getStats().length);
        for (Stat s : Stat.getStats()) {
            double[] vals = Stat.getDefaultVals(s);
            ALL_STATS.put(s, new DoubleVal(vals[0], vals[1], vals[2]));
        }
    }

    public DoubleVal get(Stat s) {
        return ALL_STATS.get(s);
    }

    public void heal(Stat s, double amnt) {
        get(s).inc(amnt);
    }
    public void damage(Stat s, double amnt) {
        get(s).dec(amnt);
    }
}
