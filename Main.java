import collision.ColChecker;
import control.Controller;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import movement.MvState;
import movement.PlayerMvmnt;
import spriteData.backgroundSprite.TowerSprite;
import spriteData.behavior.boxes.Collidable;
import spriteData.charSprite.CharSprite16x32;
import spriteData.charSprite.CombatSprite32x32;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class Main extends Application {

    /** Runs before start **/
    public void init() {
    }

    @Override
    public void start(Stage s) {
        s = new Stage();

        CombatSprite32x32 player = new CombatSprite32x32();
        TowerSprite tower = new TowerSprite();

        s.setScene(new Scene(new Group(tower.getGroup(), player.getGroup()), 3000, 3000, true));
        s.getScene().setOnKeyPressed(new EventHandler<KeyEvent>() {
                @Override
                public void handle(KeyEvent keyEvent) { Controller.onKeyDown(keyEvent.getCode());}
            }
        );
        s.getScene().setOnKeyReleased(new EventHandler<KeyEvent>() {
                 @Override
                 public void handle(KeyEvent keyEvent) { Controller.onKeyRelease(keyEvent.getCode());}
             }
        );
        s.getScene().setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {Controller.onMouseBtnPressed(mouseEvent);}
        });
        s.getScene().setOnMouseReleased(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {Controller.onMouseBtnReleased(mouseEvent);}
        });

        s.setTitle("WASTE");
        s.setWidth(500.0);
        s.setHeight(500.0);
        s.show();

    //TESTING------------------------------------------------------------------------------------------------------------
        ArrayList<Collidable> allSprites = new ArrayList<>();
        allSprites.add(tower);
        allSprites.add(player);

        tower.setPos(100, 100);

        PlayerMvmnt.setSprite(player);
        PlayerMvmnt.cntrlSetUp();

        player.getSpeed().setMax(1.7);

        Timer gameTimer = new Timer();
        gameTimer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                if (ColChecker.isColliding(player, allSprites)) {
                    PlayerMvmnt.forceState(MvState.STOPPED);
                }
                else {
                    PlayerMvmnt.runMvmnt();
                }
            }
        }, 0, 32);

    }

    public static void main(String[] args) {
        Application.launch(args);
        Platform.exit();
        System.exit(0);
    }
}