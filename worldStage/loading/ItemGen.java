package worldStage.loading;

import itemData.Item;
import itemData.weapons.Weapon;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class ItemGen {

    public static Item genItem(String pathToData, Item i) {
        try { // Get the file set up.
            FileInputStream inFile = new FileInputStream(pathToData);
            Scanner scn = new Scanner(inFile);
            scn.useDelimiter("[|]|\\n");
            while (scn.hasNext()) {
                String currField = scn.next();
                String fieldVal =  scn.next();
                fieldVal = fieldVal.replaceAll("\r", "");
                if (i instanceof Weapon w) setWeaponData(currField, fieldVal, w);
            }
            scn.close();
            return i;
        } catch (FileNotFoundException e) {
            throw new IllegalArgumentException("Given file path does not exist.");
        }
    }

    private static void setWeaponData(String field, String val, Weapon w) {
        switch (field) {
            case "name":
            case "value":
            case "pickableSheet":
                setItemData(field, val, w);
                break;
            case "wpnSheet":
                w.setPathToWpnSprite(val);
                break;
            default: throw new IllegalArgumentException("Given file is not set up correctly.");
        }
    }

    private static void setItemData(String field, String val, Item i)  {
        switch (field) {
            case "name":
                i.setName(val);
                break;
            case "value":
                i.val().set(Integer.valueOf(val));
                break;
            case "pickableSheet":
                i.setPathToPickableSprite(val);
                break;
            default: throw new IllegalArgumentException("Given file is not set up correctly.");
        }
    }

}
