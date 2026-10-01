package menus;

import charData.GameChar;
import charData.stat.CharStats;
import control.handlers.StorageHandler;
import control.runtimeTrackers.WorldTracker;
import javafx.beans.property.DoubleProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import menus.gameCharDisp.AttrMenu;
import menus.gameCharDisp.CharMenu;
import menus.gameCharDisp.StatMenu;
import menus.statBar.StatBarDisp;
import menus.storageDisp.StorageMenu;
import storageData.Storage;

import static control.runtimeTrackers.WorldEntity.GAME_CHAR;
import static control.runtimeTrackers.WorldEntity.STORAGE;

public class Menus {
    private static final GridPane menuSpace = new GridPane();
    private static final FlowPane screenSpace = new FlowPane(menuSpace);
    public static final Group overlay = new Group(screenSpace);

    public static void clearMenus() {menuSpace.getChildren().clear();}
    public static boolean isMenuShowing() {return !menuSpace.getChildren().isEmpty();}

//GAME-CHARACTER---------------------------------------------------------------------------------------------------------

    public static void dispGameChar(Integer gameCharID) {
        if (isMenuShowing()) clearMenus();
        GameChar g = (GameChar)WorldTracker.get(GAME_CHAR, gameCharID);
        if (g == null) throw new IllegalArgumentException("ERROR: CAN NOT DISPLY MENU - Given gameCharID is null.");
        menuSpace.add(new GameCharMenu(g).typeSelector, menuSpace.getColumnCount(), menuSpace.getRowCount());
    }

    /** Overall ListView of which GameChar sub-menus can be displayed. */
    private static class GameCharMenu {
        private final ListView<String> typeSelector;
        private final GameChar gameChar;
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
                    case CHARACTER: dispChar(gameChar); break;
                    case STATS: dispStats(gameChar.stats()); break;
                    case ATTRIBUTES: dispAttr(gameChar); break;
                    case BACKPACK: dispStorage(gameChar.getID(), (Storage)WorldTracker.get(STORAGE, gameChar.getStorageID())); break;
                    case EQUIPPED: break;
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
        private GameCharMenu(GameChar g) {
            typeSelector = new ListView<>(menuTypes());
            typeSelector.getSelectionModel().selectedItemProperty().addListener(onTypeSelected);
            gameChar = g;
        }
    }

    private static void dispStorage(int gameCharID, Storage s) {
        StorageMenu menu = new StorageMenu(s);
        menu.addEQBtn(gameCharID);
        menuSpace.add(menu.main, menuSpace.getColumnCount(), menuSpace.getRowCount());
    }

    private static void dispAttr(GameChar c) {
        AttrMenu menu = new AttrMenu(c);
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
        if (isMenuShowing()) clearMenus();
        StorageMenu interactorMenu = new StorageMenu(interactor);
        StorageMenu interactableMenu = new StorageMenu(interactable);
        Button put = new Button("Put");
        Button take = new Button("Take");
        put.setId("interaction");
        take.setId("interaction");
        EventHandler<ActionEvent> onMove = new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Button src = (Button)actionEvent.getSource();
                Storage toStg = interactable;
                StorageMenu menuWithItem = interactorMenu;
                if (src.getText().equals("Take")) {
                    toStg = interactor;
                    menuWithItem = interactableMenu;
                }
                StorageHandler.addTo(toStg.getID(), menuWithItem.selectedItem(), 1);
                interactorMenu.resetAndKeepItemDisp();
                interactableMenu.resetAndKeepItemDisp();
            }
        };
        put.setOnAction(onMove);
        take.setOnAction(onMove);
        interactorMenu.addBtnToItemDisp(put);
        interactableMenu.addBtnToItemDisp(take);
        menuSpace.add(interactorMenu.main, 0, 0);
        menuSpace.add(interactableMenu.main, 0, 1);
    }

//STAT-BARS--------------------------------------------------------------------------------------------------------------

    public static void addOverlayStatBars(DoubleProperty hp, DoubleProperty mp, DoubleProperty sp, DoubleProperty xp) {
        StatBarDisp s = new StatBarDisp(hp, mp, sp, xp);
        s.getContainer().setTranslateY(5.0);
        screenSpace.getChildren().add(s.getContainer());
        screenSpace.setAlignment(Pos.TOP_LEFT);
        screenSpace.setOrientation(Orientation.VERTICAL);
        screenSpace.setRowValignment(VPos.TOP);
        screenSpace.setPrefSize(100, 1000);
    }

}
