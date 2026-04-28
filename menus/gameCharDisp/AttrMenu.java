package menus.gameCharDisp;

import charData.Level;
import charData.attr.Attr;
import charData.attr.CharAttr;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import values.IntVal;

public class AttrMenu {
    public GridPane main;
    private Label header;
    private GridPane attrDisp;
    private GridPane btnDisp;
    private CharAttr attr;
    private Level lvl;

//HANDLERS---------------------------------------------------------------------------------------------------------------

    private final EventHandler<ActionEvent> onPlus = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent actionEvent) {
            Button src = (Button)actionEvent.getSource();
            Attr selected = Attr.valueOf(src.getId());
            IntVal data = attr.get(selected);
            if (lvl.getAttrPoints() > 0 && data.get() < data.getMax()) {
                data.inc();
                lvl.decAttrPoints();
                setAttrDisp();
                setAttrPointDisp();
            }
        }
    };

    private final EventHandler<ActionEvent> onMinus = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent actionEvent) {
            Button src = (Button)actionEvent.getSource();
            Attr selected = Attr.valueOf(src.getId());
            IntVal data = attr.get(selected);
            if (data.get() > data.getMin()) {
                data.dec();
                lvl.incAttrPoints();
                setAttrDisp();
                setAttrPointDisp();
            }
        }
    };

//CONSTRUCTOR------------------------------------------------------------------------------------------------------------

    public AttrMenu(CharAttr c, Level l) {
        attr = c;
        lvl = l;
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
        Attr[] info = Attr.getAttr();
        for (int i = 0; i < info.length; ++i) {
            Label key = new Label(info[i].toString());
            Label val = new Label(String.valueOf(attr.get(info[i]).get()));
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
        Attr[] info = Attr.getAttr();
        for (int i = 0; i < info.length; ++i) {
            Button plus = new Button("+");
            Button minus = new Button("-");

            plus.setId(info[i].toString());
            minus.setId(info[i].toString());
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
