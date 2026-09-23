package menus.storageDisp;

import control.WorldLocation;
import control.handlers.EQAction;
import control.handlers.EquipHandler;
import itemData.Item;
import itemData.ItemType;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Orientation;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import spriteData.FrameGen;
import storageData.Storage;
import worldData.WorldData;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Objects;

public class StorageMenu {
    public GridPane main;
    private ListView<String> typeList;
    private ListView<String> itemList;
    public GridPane itemDisp;
    private ArrayList<Button> itemBtns;
    private Storage stg;
    private Integer selectedItemID;
    HashMap<Integer, Integer> itemIDs;

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
            items.addAll(itemNames(stg.getItems(type)));
            itemList.setItems(items);
        }
    };

    private final ChangeListener<Number> onItemSelected = new ChangeListener<Number>() {
        @Override
        public void changed(ObservableValue<? extends Number> observableValue, Number prev, Number selected) {
            selectedItemID = itemIDs.get(selected.intValue());
            itemDisp.getChildren().clear();
            if (selectedItemID == null) return;
            else createItemDisp();
        }
    };

//CONSTRUCTOR------------------------------------------------------------------------------------------------------------

    public StorageMenu(Storage s, boolean useEQBtn) {
        itemIDs = new HashMap<>();
        stg = s;
        main = new GridPane();
        typeList = new ListView<>();
        itemList = new ListView<>();
        itemDisp = new GridPane();
        itemBtns = new ArrayList<Button>();
        itemDisp.setHgap(4.0);
        setUpTypeList();
        if (useEQBtn) {
            Button eqBtn = new Button();
            eqBtn.setId("eq");
            EventHandler<ActionEvent> onEq = new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {
                    String src = ((Button) actionEvent.getSource()).getText();
                    if (src.equals(EQAction.dispInfo(EQAction.EQUIP))) {
                        EquipHandler.handleEquip(
                            stg.getID(),
                            stg.grabItem(selectedItemID).getID(),
                            WorldLocation.STORAGE,
                            EQAction.EQUIP
                        );
                        ((Button) actionEvent.getSource()).setText(EQAction.dispInfo(EQAction.UN_EQUIP));
                    }
                    else if (src.equals(EQAction.dispInfo(EQAction.UN_EQUIP))) {
                        EquipHandler.handleEquip(
                            stg.getID(),
                            stg.grabItem(selectedItemID).getID(),
                            WorldLocation.STORAGE,
                            EQAction.UN_EQUIP
                        );
                        ((Button) actionEvent.getSource()).setText(EQAction.dispInfo(EQAction.EQUIP));
                    }
                }
            };
            eqBtn.setOnAction(onEq);
            addBtnToItemDisp(eqBtn);
        }
        typeList.getSelectionModel().select(0);
    }

    public void reset() {
        itemIDs.clear();
        main.getChildren().clear();
        setUpTypeList();
        typeList.getSelectionModel().select(typeList.getSelectionModel().getSelectedIndex());

    }

//ITEM-TYPE-LIST-VIEW----------------------------------------------------------------------------------------------------

    private void setUpTypeList() {
        typeList.setItems(itemTypes());
        typeList.selectionModelProperty().get().selectionModeProperty().set(SelectionMode.SINGLE);
        typeList.getSelectionModel().selectedItemProperty().addListener(onTypeSelected);
        typeList.orientationProperty().set(Orientation.HORIZONTAL);
        typeList.setPrefHeight(25.0);

        itemList.selectionModelProperty().get().selectionModeProperty().set(SelectionMode.SINGLE);
        itemList.getSelectionModel().selectedIndexProperty().addListener(onItemSelected);

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
            String name = n[i].getName() + " x" + stg.getAmntStored(n[i].getID());
            items.add(name);
            itemIDs.putIfAbsent(i, n[i].getID());
        }
        return items;
    }

//INDIVIDUAL-ITEM--------------------------------------------------------------------------------------------------------

    public void createItemDisp() {
        Item i = stg.grabItem(selectedItemID);

        if (i.getPathToPickableSprite() != null) {
            ImageView img = new ImageView(FrameGen.genOneFrame(i.getPathToPickableSprite()));
            itemDisp.add(img, 0, 0, 2, 1);
        }

        ArrayList<String> dispInfo = i.dispInfo();
        for (int n = 2; n < dispInfo.size(); ++n) {
            itemDisp.add(new Label(dispInfo.get(n)), n % 2, n / 2);
        }
        for (int n = 0; n < itemBtns.size(); ++n) {
            Button btn = itemBtns.get(n);
            if (Objects.equals(btn.getId(), "eq")) {
                if (i.isEquipped()) btn.setText(EQAction.dispInfo(EQAction.UN_EQUIP));
                else btn.setText(EQAction.dispInfo(EQAction.EQUIP));
            }
            if (Objects.equals(btn.getId(), "interaction")) {
                if (i.isEquipped()) {
                    btn.setText("EQUIPPED");
                    btn.setDisable(true);
                }
            }
            itemDisp.add(btn, 0, itemDisp.getRowCount(), 2, 1);
        }
    }

    public Integer selectedItem() {return selectedItemID;}

    public void addBtnToItemDisp(Button btn) {
        itemBtns.add(btn);
    }
}
