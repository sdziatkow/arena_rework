package spriteData.charSprite;

import collision.BoxSizer;
import collision.CollisionBox;
import control.AttkHandler;
import menus.statBar.StatBar;
import movement.NPCState;
import spriteData.MovingSprite;
import spriteData.behavior.boxes.Hurtable;
import spriteData.behavior.boxes.Interactable;
import worldData.WorldData;

import static collision.ColType.*;

public class CharSprite extends MovingSprite implements Hurtable, Interactable {
    private NPCState npcState;
    private StatBar statBar;

    public CharSprite() {
        final String DEFAULT_PATH = "file:resources/sprites/character/move_4x4_16x32.png";
        setUp(DEFAULT_PATH);
    }
    public CharSprite(String pathToSheet) {
        setUp(pathToSheet);
    }

    private void setUp(String pathToSheet) {
        setMaxSpeed(1.5);
        npcState = NPCState.FREE;
        addBox(new CollisionBox(WORLDBOX));
        addBox(new CollisionBox(HURTBOX));

        setUpSprite(this, pathToSheet, new int[]{32, 32});
        BoxSizer.sizeBoxSmallMid(pathToSheet, getBox(WORLDBOX));
        getGroup().getChildren().add(getBox(WORLDBOX).getColBox());

        BoxSizer.sizeBoxBigMid(pathToSheet, getBox(HURTBOX));
        getGroup().getChildren().add(getBox(HURTBOX).getColBox());

        getBox(CHECKBOX).setBaseBounds(getBox(WORLDBOX).getBaseBounds());
        getBox(CHECKBOX).contract();
        getGroup().getChildren().add(getBox(CHECKBOX).getColBox());
    }

    public NPCState getNPCState() { return npcState; }
    public StatBar getStatBar() { return statBar; }

    public void setNPCState(NPCState s) { npcState = s; }
    public void setStatBar(StatBar bar) {
        statBar = bar;
        getGroup().getChildren().add(bar.get());
        bar.get().setTranslateY(getPane().getPrefHeight() / 1.12);
    }

    @Override
    public void onHurt(int attkID) {
        AttkHandler.handleAttk(attkID, getID());
        statBar.updateProgress();
    }

    @Override
    public void onInteract(int interactorID) {
        WorldData.openDialogueMenu(getID());
    }
}
