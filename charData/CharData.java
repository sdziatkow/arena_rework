package charData;

public class CharData {
    private CharAttr attr;
    private CharStats stats;
    private Level lvl;
    private CharClass charClass;

    public CharData() {
        attr = new CharAttr();
        stats = new CharStats();
        lvl = new Level();
        charClass = CharClass.BARBARIAN;
        setInitialAttrValues();
    }

    public CharData(CharClass c) {
        attr = new CharAttr();
        stats = new CharStats();
        lvl = new Level();
        charClass = c;
        setInitialAttrValues();
    }

    public CharAttr attr() {
        return attr;
    }
    public CharStats stats() {
        return stats;
    }
    public Level lvl() {
        return lvl;
    }
    public CharClass getCharClass() {
        return charClass;
    }

    /** SHOULD ONLY BE CALLED ONCE. (for gameplay reasons). */
    private void setInitialAttrValues() {
        switch (charClass) {
            case BARBARIAN:
                attr.get(Attr.VIGOR).inc(10);
                attr.get(Attr.ENDURANCE).inc(10);
                attr.get(Attr.STRENGTH).inc(10);
                break;
            case BRUTE:
                attr.get(Attr.ENDURANCE).inc(10);
                attr.get(Attr.WILLPOWER).inc(10);
                attr.get(Attr.STRENGTH).inc(10);
                break;
            case DRIFTER:
                attr.get(Attr.AGILITY).inc(10);
                attr.get(Attr.ENDURANCE).inc(10);
                attr.get(Attr.WILLPOWER).inc(10);
                break;
            case RANGER:
                attr.get(Attr.AGILITY).inc(10);
                attr.get(Attr.DEXTERITY).inc(10);
                attr.get(Attr.ENDURANCE).inc(10);
                break;
            case SCOUT:
                attr.get(Attr.VIGOR).inc(10);
                attr.get(Attr.AGILITY).inc(10);
                attr.get(Attr.ENDURANCE).inc(10);
                break;
            case MONK:
                attr.get(Attr.VIGOR).inc(10);
                attr.get(Attr.WILLPOWER).inc(10);
                attr.get(Attr.AGILITY).inc(10);
                break;
        }
    }

}
