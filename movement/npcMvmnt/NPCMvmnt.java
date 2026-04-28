package movement.npcMvmnt;

import collision.ColChecker;
import movement.CharMvmnt;
import movement.MvState;
import movement.NPCState;
import spriteData.Dir;
import spriteData.charSprite.CharSprite;
import worldData.objectData.SpriteTracker;
import java.util.Stack;

public class NPCMvmnt extends CharMvmnt {
    private static CharSprite sprite = null;
    private static MvState mvState = null;
    private static NPCState npcState = null;
    private static FreeMvmnt freeMv = new FreeMvmnt();

    public static void setSprite(CharSprite s) {
        sprite = s;
        mvState = sprite.getMvState();
        npcState = sprite.getNPCState();
    }

    private static void setMvState() {

    }

    private static void setNpcState() {

    }

    public static void move() {
        if (sprite.getNPCState() == null) return;
        switch (sprite.getNPCState()) {
            case IDLE: return;
            case FREE:
                freeMove();
                break;
            case COMBAT:
                break;
            default: return;
        }
    }

    private static void freeMove() {
        boolean readyToMv = sprite.dirCount.get() < sprite.dirCount.getMax() && freeMv.readyToMove();
        if (readyToMv && canMove(sprite.getDir())) {
            if (sprite.dirCount.get() == sprite.dirCount.getMin()) { // This means NPC is just beginning his new move.
                sprite.switchDir(freeMv.randomNewDir(sprite.getDir()));
            }
            onMove(sprite);
            sprite.dirCount.inc();
        }
        else if (readyToMv) {
            sprite.switchDir(freeMv.randomNewDir(sprite.getDir()));
        }
        else {
            halt(sprite.getSpeed());
            onStopped(sprite);
            freeMv.genMv();
            sprite.dirCount.reset();
        }
    }

    private static void moveCheckBox(CharSprite sprite, Dir dir) {
        sprite.getCheckBox().checkDir(sprite.getSpeed().getMax() * 2, dir);
    }

    private static boolean canMove(Dir move) {
        moveCheckBox(sprite, move);
        Stack<Integer> collidingWith = ColChecker.isColliding(sprite, SpriteTracker.collidables);
        if (!collidingWith.isEmpty()) {
            moveCheckBox(sprite, sprite.getDir());
            return false;
        }
        else return true;
    }
}
