package spriteData;

/** NORTH(-); SOUTH(+); EAST(+); WEST(-) */
public enum Dir {
    N, S, E, W;
    public static final int TOTAL_DIRS = 4;

    public static Dir[] allDirs() { return Dir.N.getDeclaringClass().getEnumConstants(); }

    public static Dir oppositeOf(Dir dir) {
        if (dir == null) return null;
        switch (dir) {
            case N: return S;
            case S: return N;
            case E: return W;
            case W: return E;
            default: return null;
        }
    }

    public static Dir adjacentTo(Dir dir) {
        if (dir == null) return null;
        switch (dir) {
            case N: return W;
            case S: return E;
            case E: return S;
            case W: return N;
            default: return null;
        }
    }
}
