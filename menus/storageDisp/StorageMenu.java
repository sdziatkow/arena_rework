package menus.storageDisp;

import itemData.Item;
import itemData.ItemType;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Orientation;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import spriteData.FrameGen;
import storageData.Storage;

import java.util.ArrayList;

public class StorageMenu {
    public GridPane main;
    private ListView<String> typeList;
    private ListView<String> itemList;
    private GridPane itemDisp;
    private Storage stg;

//LISTENERS--------------------------------------------------------------------------------------------------------------

    private final ChangeListener<String> onTypeSelected = new ChangeListener<String>() {
        @Override
        public void changed(ObservableValue<? extends String> observableValue, String prev, String selected) {
            itemDisp.getChildren().clear();
            itemList.getItems().clear();
            itemList.refresh();
            if (selected == null) return;

            ObservableList<String> items = FXCollections.observableArrayList();
            ItemType type = ItemType.valueOf(selected);
            switch (type) {
                case WEAPON:
                    items.addAll(itemNames(stg.wpn().toArray(new Item[0])));
                    break;
                case ARMOR:
                    items.addAll(itemNames(stg.arm().toArray(new Item[0])));
                    break;
                case USABLE:
                    items.addAll(itemNames(stg.use().toArray(new Item[0])));
                    break;
                case ALL:
                    items.addAll(itemNames(stg.all().toArray(new Item[0])));
                    break;
                default: break;
            }
            itemList.setItems(items);
        }
    };

    private final ChangeListener<String> onItemSelected = new ChangeListener<String>() {
        @Override
        public void changed(ObservableValue<? extends String> observableValue, String prev, String selected) {
            itemDisp.getChildren().clear();
            if (selected == null) return;
            else createItemDisp(selected);
        }
    };

//CONSTRUCTOR------------------------------------------------------------------------------------------------------------

    public StorageMenu(Storage s) {
        stg = s;
        main = new GridPane();
        typeList = new ListView<>();
        itemList = new ListView<>();
        itemDisp = new GridPane();
        setUpTypeList();
    }

//ITEM-TYPE-LIST-VIEW----------------------------------------------------------------------------------------------------

    private void setUpTypeList() {
        typeList.setItems(itemTypes());
        typeList.selectionModelProperty().get().selectionModeProperty().set(SelectionMode.SINGLE);
        typeList.getSelectionModel().selectedItemProperty().addListener(onTypeSelected);
        typeList.orientationProperty().set(Orientation.HORIZONTAL);
        typeList.setPrefHeight(25.0);

        itemList.selectionModelProperty().get().selectionModeProperty().set(SelectionMode.SINGLE);
        itemList.getSelectionModel().selectedItemProperty().addListener(onItemSelected);

        main.add(typeList, 0, 0);
        main.add(itemList, 0, 1);
        main.add(itemDisp, 1, 1, 1, 2);
    }

    private ObservableList<String> itemTypes() {
        ObservableList<String> types = FXCollections.observableArrayList();
        ItemType[] t = ItemType.getTypes();
        for (int i = 0; i < t.length; ++i) {
            types.add(t[i].toString());
        }
        return types;
    }

//ITEMS-OF-SELECTED-TYPE-------------------------------------------------------------------------------------------------

    private ObservableList<String> itemNames(Item[] n) {
        ObservableList<String> items = FXCollections.observableArrayList();
        for (int i = 0; i < n.length; ++i) {
            items.add(n[i].getName());
        }
        return items;
    }

//INDIVIDUAL-ITEM--------------------------------------------------------------------------------------------------------

    private void createItemDisp(String itemName) {
        Item i = stg.grabByName(itemName);
        ImageView img = new ImageView(FrameGen.genOneFrame(i.getPathToPickableSprite()));

        itemDisp.add(img, 0, 0, 2, 1);

        ArrayList<String> dispInfo = i.dispInfo();
        int rowCount = 1;
        for (int n = 0; n < dispInfo.size(); ++n) {
            itemDisp.add(new Label(dispInfo.get(n)), n % 2, rowCount);
            if (n % 2 == 1) ++rowCount;
        }
    }
}
