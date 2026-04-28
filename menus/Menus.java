package menus;

import charData.GameChar;
import charData.Level;
import charData.attr.CharAttr;
import charData.stat.CharStats;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Group;
import javafx.scene.control.ListView;
import javafx.scene.layout.GridPane;
import menus.gameCharDisp.AttrMenu;
import menus.gameCharDisp.CharMenu;
import menus.gameCharDisp.StatMenu;
import menus.storageDisp.StorageMenu;
import storageData.Storage;

public class Menus {
    private static final GridPane menuSpace = new GridPane();
    public static final Group overlay = new Group(menuSpace);

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
        if (!menuSpace.getChildren().isEmpty()) clearMenus();
        StorageMenu menu = new StorageMenu(s);
        menuSpace.add(menu.main, menuSpace.getColumnCount(), menuSpace.getRowCount());
    }

    private static void dispAttr(CharAttr c, Level l) {
        if (!menuSpace.getChildren().isEmpty()) clearMenus();
        AttrMenu menu = new AttrMenu(c, l);
        menuSpace.add(menu.main, menuSpace.getColumnCount(), menuSpace.getRowCount());
    }

    private static void dispStats(CharStats s) {
        if (!menuSpace.getChildren().isEmpty()) clearMenus();
        StatMenu menu = new StatMenu(s);
        menuSpace.add(menu.main, menuSpace.getColumnCount(), menuSpace.getRowCount());
    }

    private static void dispChar(GameChar g) {
        if (!menuSpace.getChildren().isEmpty()) clearMenus();
        CharMenu menu = new CharMenu(g);
        menuSpace.add(menu.main, menuSpace.getColumnCount(), menuSpace.getRowCount());
    }

    public static void clearMenus() {
        menuSpace.getChildren().clear();
    }

}
