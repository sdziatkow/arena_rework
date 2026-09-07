package movement.npcMvmnt;

import collision.ColChecker;
import collision.CollisionBox;
import javafx.geometry.Bounds;
import movement.CharMvmnt;
import movement.NPCState;
import movement.PathFinder;
import spriteData.Dir;
import spriteData.charSprite.CharSprite;
import spriteData.charSprite.CombatSprite;
import values.IntVal;
import worldData.WorldData;
import worldData.objectData.BoxTracker;

import java.nio.file.Path;
import java.util.Random;
import java.util.Stack;

import static collision.ColType.*;
import static collision.ColType.HURTBOX;
import static movement.NPCState.COMBAT;

public class NPCMvmnt {
    private CharSprite sprite;
    private NPCState state;
    private CollisionBox movingTo;
    private IntVal mvCount;
    private IntVal cmbtMvCount;
    private int mvGen;

    public NPCMvmnt() {
        sprite = null;
        state = null;
        movingTo = null;
        mvCount = null;
        mvGen = 10;
    }

    public NPCMvmnt(CharSprite s) {
        sprite = s;
        state = sprite.getNPCState();
        movingTo = null;
        mvCount = new IntVal();
        mvCount.setMax(40);
        cmbtMvCount = new IntVal();
        cmbtMvCount.setMax(10);
        mvGen = 10;
    }

//STATE-MACHINE----------------------------------------------------------------------------------------------------------

    public void runMvmnt() {
        move();
    }

    private void move() {
        switch (state) {
            case IDLE:
                break;
            case FREE:
                free();
                break;
            case HUNTING:
                hunt();
                break;
            case COMBAT:
                moveToBox(movingTo);
                break;
        }
    }

//BASIC-OPERATIONS-------------------------------------------------------------------------------------------------------

    private void moveCheckBox(Dir dir) {
        sprite.getBox(CHECKBOX).checkDir(sprite.getSpeed().getMax() * 2, dir);
    }

    private Stack<Integer> getCollidingWith() {
        Stack<Integer> collidingWith = ColChecker.isColliding(
                sprite.getID(), sprite.getBox(CHECKBOX), BoxTracker.getBoxes(WORLDBOX)
        );
        return collidingWith;
    }
    private boolean canMove() {
        Stack<Integer> collidingWith = getCollidingWith();
        return collidingWith.isEmpty() && WorldData.isInWorldBounds(sprite.getID());
    }
    private boolean canMove(Stack<Integer> collidingWith) {
        return collidingWith.isEmpty() && WorldData.isInWorldBounds(sprite.getID());
    }

    private Stack<Integer> getDetectedHurtBoxes() {
        Stack<Integer> collidingWith = ColChecker.isColliding(
                sprite.getID(), sprite.getBox(DETECTBOX), BoxTracker.getBoxes(HURTBOX)
        );
        return collidingWith;
    }

//STATES-----------------------------------------------------------------------------------------------------------------

    public void free() {
        if (mvGen > 9) { // So the sprite is not constantly moving.
            CharMvmnt.halt(sprite.getSpeed());
            CharMvmnt.onStopped(sprite);
            Random gen = new Random();
            mvGen = Math.abs(gen.nextInt(1000));
        }
        else if (mvCount.isMax()) { // Move has been completed, switch to random direction.
            mvCount.reset();
            sprite.switchDir(Dir.randomDir());
            moveCheckBox(sprite.getDir());
            mvGen = 10;
        }
        else { // Otherwise move the NPC.
            if (canMove()) { // If can move, then move.
                CharMvmnt.onMove(sprite);
                mvCount.inc();
            }
            else { // Otherwise stop and switch to new direction.
                CharMvmnt.halt(sprite.getSpeed());
                CharMvmnt.onStopped(sprite);

                sprite.switchDir(Dir.randomDir(sprite.getDir()));
                moveCheckBox(sprite.getDir());
            }
        }
    }

    public void hunt() {
        free();
        Stack<Integer> detected = getDetectedHurtBoxes();
        if (!detected.isEmpty()) {
            movingTo = BoxTracker.getBox(HURTBOX, detected.pop());
            state = COMBAT;
        }
    }

//MOVEMENT---------------------------------------------------------------------------------------------------------------

    public void moveToBox(CollisionBox box) {
        double[] boxMidPos = box.midPos();
        Bounds boxBounds = box.getBounds();
        double[] myMidPos = sprite.getBox(WORLDBOX).midPos();
        Dir nextMove = sprite.getDir();
        if (cmbtMvCount.isMax()) {
            nextMove = PathFinder.bestMoveTowards(myMidPos, boxMidPos);
            cmbtMvCount.reset();
        }
        if (PathFinder.distanceTo(myMidPos, boxMidPos) < 20.0) {
            cmbtMvCount.reset();
            cmbtMvCount.setMax(1);
        }
        else cmbtMvCount.setMax(20);

        // Check if can move in nextMove
        moveCheckBox(nextMove);
        Stack<Integer> collidingWith = getCollidingWith();

        if (canMove(collidingWith)) { // Nothing in the way.
            sprite.switchDir(nextMove);
            CharMvmnt.onMove(sprite);
            cmbtMvCount.inc();
        }
        else {
            if (collidingWith.contains(box.getID())) { // Colliding with box that I am moving towards (made it).
                CharMvmnt.halt(sprite.getSpeed());
                CharMvmnt.onStopped(sprite);
                if (state.equals(COMBAT)) ((CombatSprite)sprite).onAttk();
            }
            else { // Colliding with box that is in the way, I must move around it.
                Dir newMove;
                CollisionBox moveAround = BoxTracker.getBox(WORLDBOX, collidingWith.pop());
                newMove = PathFinder.bestMoveAround(myMidPos, boxBounds);
                moveCheckBox(newMove);
                collidingWith = getCollidingWith();
                if (canMove(collidingWith)) {
                    sprite.switchDir(newMove);
                    CharMvmnt.onMove(sprite);
                }
            }
        }
    }
}
