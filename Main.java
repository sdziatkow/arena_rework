import control.Controller;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import menus.Menus;
import movement.CharMvmnt;
import tileSet.TileSetMaker;
import worldData.WorldData;
import worldData.objectData.SpriteTracker;
import worldData.statData.StatTracker;
import worldStage.WorldMaker;
import worldStage.WorldStage;
import java.util.Timer;
import java.util.TimerTask;

import static worldData.WorldData.cam;

public class Main extends Application {

    /** Runs before start **/
    public void init() {
        WorldStage world = WorldMaker.makeWorld(TileSetMaker.makeTileSet("resources/object_data/tile_set_data/grass_area.txt"));
        WorldData.bg = world.getWorld();
        WorldData.bg.getGroup().getChildren().add(Menus.overlay);
        WorldData.bg.getGroup().setCache(true);
    }

    @Override
    public void start(Stage stage) {
        final Stage s = stage;
        Scene sc = new Scene(WorldData.bg.getGroup(), 3000, 3000, true);
        sc.setCamera(cam);
        cam.setCache(true);
        Menus.overlay.getChildren().add(cam);
        Menus.overlay.setCache(true); // Must have this or else will not render correctly.
        CharMvmnt.bindNode(SpriteTracker.charSprites.get(SpriteTracker.playerID), Menus.overlay);

        s.setScene(sc);
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

        s.widthProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
                Menus.overlay.setLayoutX(-s.getWidth() / 2.25);
                Menus.overlay.setLayoutY(-s.getHeight() / 2.25);
            }
        });
        s.heightProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
                Menus.overlay.setLayoutX(-s.getWidth() / 2.25);
                Menus.overlay.setLayoutY(-s.getHeight() / 2.25);
            }
        });

        s.setTitle("WASTE");
        s.setWidth(1000.0);
        s.setHeight(800.0);
        s.show();

    //TESTING------------------------------------------------------------------------------------------------------------
        SpriteTracker.charSprites.get(SpriteTracker.playerID).setMaxSpeed(1.5);
        StatTracker.gameChars.get(SpriteTracker.playerID).lvl().incAttrPoints();

        Timer gameTimer = new Timer();
        gameTimer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(WorldData::runMvmnt);
            }
        }, 0, 32);
    }

    public static void main(String[] args) {
        Application.launch(args);
        Platform.exit();
        System.exit(0);
    }
}