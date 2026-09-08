package menus;

import charData.GameChar;
import charData.Level;
import charData.attr.CharAttr;
import charData.stat.CharStats;
import itemData.Item;
import javafx.beans.property.DoubleProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.GridPane;
import menus.gameCharDisp.AttrMenu;
import menus.gameCharDisp.CharMenu;
import menus.gameCharDisp.StatMenu;
import menus.statBar.StatBarDisp;
import menus.storageDisp.StorageMenu;
import storageData.Storage;
import values.DoubleVal;

public class Menus {
    private static final GridPane menuSpace = new GridPane();
    public static final Group overlay = new Group(menuSpace);

    public static void clearMenus() { menuSpace.getChildren().clear(); }

//GAME-CHARACTER---------------------------------------------------------------------------------------------------------

    private static class MenuPicker {
        private final ListView<String> typeSelector;
        private final GameChar gameChar;
        private final Storage backpack;
        private final ChangeListener<String> onTypeSelected = new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String prev, String selected) {
                if (selected == null) return;
                if (selected.equals("EXIT")) {
                    Menus.clearMenus();
                    return;
                }
                if (!menuSpace.getChildren().isEmpty()) clearMenus();
                switch (MenuType.valueOf(selected)) {
                    case CHARACTER:
                        dispChar(gameChar);
                        break;
                    case STATS:
                        dispStats(gameChar.stats());
                        break;
                    case ATTRIBUTES:
                        dispAttr(gameChar.attr(), gameChar.lvl());
                        break;
                    case BACKPACK:
                        dispStorage(backpack);
                        break;
                    case EQUIPPED:
                        break;
                    default: break;
                }
            }
        };
        private ObservableList<String> menuTypes() {
            ObservableList<String> all = FXCollections.observableArrayList();
            MenuType[] types = MenuType.all();
            for (int i = 0; i < types.length - 1; ++i) { // Exclude storage type.
                all.add(types[i].toString());
            }
            all.add("EXIT");
            return all;
        }
        private MenuPicker(GameChar g, Storage b) {
            typeSelector = new ListView<>(menuTypes());
            typeSelector.getSelectionModel().selectedItemProperty().addListener(onTypeSelected);
            gameChar = g;
            backpack = b;
        }
    }

    public static void dispPicker(GameChar g, Storage s) {
        if (!menuSpace.getChildren().isEmpty()) clearMenus();
        menuSpace.add(new MenuPicker(g, s).typeSelector, menuSpace.getColumnCount(), menuSpace.getRowCount());
    }

    public static void dispStorage(Storage s) {
        StorageMenu menu = new StorageMenu(s, true);
        menuSpace.add(menu.main, menuSpace.getColumnCount(), menuSpace.getRowCount());
    }

    private static void dispAttr(CharAttr c, Level l) {
        AttrMenu menu = new AttrMenu(c, l);
        menuSpace.add(menu.main, menuSpace.getColumnCount(), menuSpace.getRowCount());
    }

    private static void dispStats(CharStats s) {
        StatMenu menu = new StatMenu(s);
        menuSpace.add(menu.main, menuSpace.getColumnCount(), menuSpace.getRowCount());
    }

    private static void dispChar(GameChar g) {
        CharMenu menu = new CharMenu(g);
        menuSpace.add(menu.main, menuSpace.getColumnCount(), menuSpace.getRowCount());
    }

//STORAGE----------------------------------------------------------------------------------------------------------------

    public static void dispStorageInteraction(Storage interactor, Storage interactable) {
        if (!menuSpace.getChildren().isEmpty()) clearMenus();
        StorageMenu m1 = new StorageMenu(interactor, false);
        StorageMenu m2 = new StorageMenu(interactable, false);
        Button put = new Button("Put");
        Button take = new Button("Take");
        put.setId("interaction");
        take.setId("interaction");
        EventHandler<ActionEvent> onMove = new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Button src = (Button)actionEvent.getSource();
                Storage fromStg = interactor;
                Storage toStg = interactable;
                StorageMenu m = m1;
                if (src.getText().equals("Put")) {
                    fromStg = interactor;
                    toStg = interactable;
                    m = m1;
                }
                else if (src.getText().equals("Take")) {
                    fromStg = interactable;
                    toStg = interactor;
                    m = m2;
                }
                Item i = fromStg.grabByName(m.selectedItem());
                fromStg.removeItem(i);
                toStg.store(i);
                i.setStorageID(toStg.getID());
                m1.reset();
                m2.reset();
            }
        };
        put.setOnAction(onMove);
        take.setOnAction(onMove);
        m1.addBtnToItemDisp(put);
        m2.addBtnToItemDisp(take);
        menuSpace.add(m1.main, 0, 0);
        menuSpace.add(m2.main, 0, 1);
    }

//STAT-BARS--------------------------------------------------------------------------------------------------------------

    public static void addOverlayStatBars(DoubleProperty hp, DoubleProperty sp, DoubleProperty xp) {
        StatBarDisp s = new StatBarDisp(hp, sp, xp);
        s.getContainer().setTranslateY(5.0);
        s.getContainer().setTranslateX(-5.0);
        overlay.getChildren().add(s.getContainer());

    }

}
