package itemData;

import charData.stat.Stat;
import java.util.HashMap;

/**
 * <br> PHYS: Reduced by Stat.PHYSDEF
 * <br> MAG: Reduced by Stat.MAGDEF
 */
public enum DmgType {
    PHYS, MAG;

    public static DmgType[] all() {return PHYS.getDeclaringClass().getEnumConstants();}

    public static HashMap<Stat, Double> statMults(DmgType dmg) {
        HashMap<Stat, Double> stats = new HashMap<>();
        switch (dmg) {
            case PHYS:
                stats.put(Stat.HP, 1.0);
                stats.put(Stat.SP, 0.08);
                break;
            case MAG:
                stats.put(Stat.HP, 1.0);
                stats.put(Stat.MP, 0.01);
                break;
            default:
                break;
        }
        return stats;
    }
}
