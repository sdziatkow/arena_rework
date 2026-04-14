package control;

import javafx.collections.FXCollections;
import javafx.collections.ObservableSet;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;

public class Controller {
    public static final ObservableSet<KeyCode> mvmntKeysDown = FXCollections.observableSet();
    public static final ObservableSet<MouseButton> mouseBtnsDown = FXCollections.observableSet();

    public static void onKeyDown(KeyCode key) {
        switch (key) {

            // Movement keys.
            case W:
            case A:
            case S:
            case D:
                mvmntKeysDown.add(key);
            break;
            default: return;
        }
    }
    public static void onKeyRelease(KeyCode key) {
        switch (key) {

            // Movement keys
            case W:
            case A:
            case S:
            case D:
                mvmntKeysDown.remove(key);
                break;
            default: return;
        }
    }

    public static void onMouseBtnPressed(MouseEvent event) {
        mouseBtnsDown.add(event.getButton());
    }

    public static void onMouseBtnReleased(MouseEvent event) {
        mouseBtnsDown.remove(event.getButton());
    }


}
