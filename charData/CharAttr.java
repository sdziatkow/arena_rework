package charData;

import java.util.HashMap;
import values.IntVal;

public class CharAttr {
    private final HashMap<Attr, IntVal> ALL_ATTR;

    public CharAttr() {
        ALL_ATTR = new HashMap<>(Attr.getAttr().length);
        for (Attr a : Attr.getAttr()) {
            ALL_ATTR.put(a, new IntVal(0, 100, 5));
        }
    }

    public IntVal get(Attr a) {
        return ALL_ATTR.get(a);
    }

    public void skillUp(Attr a, int amnt) {
        get(a).inc(amnt);
    }
    public void skillDown(Attr a, int amnt) {
        get(a).dec(amnt);
    }
}
