package movement;

import collision.ColChecker;
import spriteData.Dir;
import spriteData.charSprite.CharSprite;
import worldData.objectData.SpriteTracker;

import java.util.Random;
import java.util.Stack;

public class PathFinder extends CharMvmnt{

    private static class PathGraph {
        private final Dir[][] adj = new Dir[Dir.TOTAL_DIRS][];

        public PathGraph() {
            adj[0] = new Dir[]{Dir.S, Dir.E, Dir.W};
            adj[1] = new Dir[]{Dir.N, Dir.E, Dir.W};
            adj[2] = new Dir[]{Dir.N, Dir.S, Dir.W};
            adj[3] = new Dir[]{Dir.N, Dir.S, Dir.E};
        }

        public Dir[] getAdj(Dir vertex) {
            switch (vertex) {
                case N:
                    return adj[0];
                case S:
                    return adj[1];
                case E:
                    return adj[2];
                case W:
                    return adj[3];
                default:
                    return null;
            }
        }
    }

    private Dir path = Dir.S;

    private Dir findNextPath(Dir currDir, double[] pos, double[] target) {
        double x = pos[0];
        double y = pos[1];
        double tx = target[0];
        double ty = target[1];
        return null;
    }
}
