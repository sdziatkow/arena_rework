package menus.gameCharDisp;

import charData.GameChar;
import charData.Level;
import charData.attr.Attr;
import charData.attr.CharAttr;
import control.handlers.StatChangeHandler;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import values.IntVal;
import values.ValType;

import static values.ValType.*;

public class AttrMenu {
    public GridPane main;
    private Label header;
    private GridPane attrDisp;
    private GridPane btnDisp;
    private GameChar gameChar;
    private CharAttr attr;
    private Level lvl;

//HANDLERS---------------------------------------------------------------------------------------------------------------

    private final EventHandler<ActionEvent> onPlus = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent actionEvent) {
            Button plus = (Button)actionEvent.getSource(); // plus btn that triggered event.
            Button minus = null;                           // minus btn associated with same Attribute.

            // Get Attr from btn's user data.
            Attr selected = (Attr)plus.getUserData();

            // Get minus button.
            for (int i = 0; i < btnDisp.getChildren().size(); ++i) {
                Button b = (Button)btnDisp.getChildren().get(i);
                if (b.getUserData().equals(plus.getUserData()) && b.getText().equals("-")) {
                    minus = b;
                }
            }
            int attrVal = attr.get(selected, VAL);
            int attrMax = attr.get(selected, MAX);
            if (lvl.getAttrPoints() > 0 && attrVal < attrMax) {
                attr.skillUp(selected, 1);
                lvl.decAttrPoints();
                setAttrDisp();
                setAttrPointDisp();

                // Increment the ID value of minus btn and enable it.
                if (minus != null) {
                    minus.setId(String.valueOf(Integer.parseInt(minus.getId()) + 1));
                    minus.setDisable(false);
                }
                StatChangeHandler.updateStatsFromAttr(gameChar.getID());
            }
        }
    };

    private final EventHandler<ActionEvent> onMinus = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent actionEvent) {
            Button minus = (Button)actionEvent.getSource(); // minus btn that triggered the event.
            Attr selected = (Attr)minus.getUserData();      // Get Attr from minus btn's user data.
            int attrVal = attr.get(selected, VAL);
            int attrMin = attr.get(selected, MIN);
            if (Integer.parseInt(minus.getId()) > 0 && attrVal > attrMin) {
                attr.skillDown(selected, 1);
                lvl.incAttrPoints();
                setAttrDisp();
                setAttrPointDisp();

                // Decrement minus btn's ID value. If value is zero, disable the button.
                minus.setId(String.valueOf(Integer.parseInt(minus.getId()) - 1));
                if (Integer.parseInt(minus.getId()) < 1) {
                    minus.setDisable(true);
                }
                StatChangeHandler.updateStatsFromAttr(gameChar.getID());
            }
        }
    };

//CONSTRUCTOR------------------------------------------------------------------------------------------------------------

    public AttrMenu(GameChar c) {
        gameChar = c;
        attr = c.attr();
        lvl = c.lvl();
        main = new GridPane();
        header = new Label();
        attrDisp = new GridPane();
        btnDisp = new GridPane();

        header.setText("Attributes");
        main.add(header, 0, 0, 4, 1);
        setAttrDisp();
        main.add(attrDisp, 0, 1);

        if (lvl.getAttrPoints() > 0) {
            setBtnDisp();
            main.add(btnDisp, 1, 1);
        }

        main.getStylesheets().add(
                getClass().getResource("attrStyle.css").toExternalForm());
        main.getStyleClass().add("attr-main");
        header.getStyleClass().add("attr-header");
        btnDisp.getStyleClass().add("attr-btn-grid");
    }

    public void setAttrDisp() {
        attrDisp.getChildren().clear();
        Attr[] info = Attr.ALL;
        for (int i = 0; i < info.length; ++i) {
            Label key = new Label(info[i].toString());
            Label val = new Label(String.valueOf(attr.get(info[i], VAL)));
            key.getStyleClass().add("attr-label");
            val.getStyleClass().add("attr-label");
            key.getStyleClass().add("attr-key-label");
            val.getStyleClass().add("attr-val-label");
            attrDisp.add(key, 0, i);
            attrDisp.add(val, 1, i);
        }
    }

    public void setBtnDisp() {
        btnDisp.getChildren().clear();
        Attr[] info = Attr.ALL;
        for (int i = 0; i < info.length; ++i) {
            Button plus = new Button("+");
            Button minus = new Button("-");
            minus.setDisable(true);
            plus.setId("0");
            minus.setId("0");
            plus.setUserData(info[i]);
            minus.setUserData(info[i]);
            plus.getStyleClass().add("attr-btn");
            plus.getStyleClass().add("attr-plus");
            minus.getStyleClass().add("attr-btn");
            minus.getStyleClass().add("attr-minus");

            plus.setOnAction(onPlus);
            minus.setOnAction(onMinus);

            btnDisp.add(plus, 0, i);
            btnDisp.add(minus, 1, i);
        }
        setAttrPointDisp();
    }

    public void setAttrPointDisp() {
        Label key = new Label("Attribute Points:");
        Label val = new Label(String.valueOf(lvl.getAttrPoints()));
        key.getStyleClass().add("attr-label");
        val.getStyleClass().add("attr-label");
        attrDisp.add(key, 0, attrDisp.getRowCount());
        attrDisp.add(val, 1, attrDisp.getRowCount() - 1);
    }
}
