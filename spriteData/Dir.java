package spriteData;

import java.util.Random;

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

    /**
     * Whether the direction goes in a positive or negative direction in the scene.
     * @return (N)orth and (W)est -> -1; (S)outh and (E)ast -> 1.
     */
    public static int sign(Dir d) {
        switch (d) {
            case N:
            case W: return -1;
            case S:
            case E:
            default: return 1;
        }
    }

    /**
     * Whether the direction goes along the x or y axis of the scene.
     * @return (E)ast and (W)est -> 0 (x-axis); (N)orth and (S)outh -> 1 (y-axis).
     */
    public static int axis(Dir d) {
        switch (d) {
            case E:
            case W: return 0;
            case N:
            case S:
            default: return 1;
        }
    }

    public static Dir randomDir() {
        Random gen = new Random();
        return allDirs()[Math.abs(gen.nextInt()) % allDirs().length];
    }

    public static Dir randomDir(Dir excluding) {
        Random gen = new Random();
        Dir d = allDirs()[Math.abs(gen.nextInt()) % allDirs().length];
        while (d.equals(excluding)) {
            d = allDirs()[Math.abs(gen.nextInt()) % allDirs().length];
        }
        return d;
    }
}
