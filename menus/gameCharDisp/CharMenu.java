package menus.gameCharDisp;

import charData.GameChar;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

import java.util.ArrayList;

public class CharMenu {

    public GridPane main;
    private Label header;
    private GridPane charDisp;
    private GameChar gameChar;

    public CharMenu(GameChar g) {
        gameChar = g;
        main = new GridPane();
        header = new Label();
        charDisp = new GridPane();

        main.getStylesheets().add(
                getClass().getResource("charStyle.css").toExternalForm());
        main.getStyleClass().add("char-main");
        header.getStyleClass().add("char-header");
        charDisp.getStyleClass().add("char-grid");

        header.setText("Character Info");
        main.add(header, 0, 0, 2, 1);
        setUpStatDisp();
        main.add(charDisp, 0, 1);
    }

    private void setUpStatDisp() {
        charDisp.getChildren().clear();
        ArrayList<String> info = gameChar.dispInfo();
        for (int i = 0; i < info.size(); ++i) {
            Label l = new Label(info.get(i));
            if (i % 2 == 0) l.getStyleClass().add("char-key-label");
            else l.getStyleClass().add("char-val-label");
            charDisp.add(l, i % 2, i / 2);
        }
    }
}
