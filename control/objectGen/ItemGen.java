package control.objectGen;

import charData.attr.Attr;
import charData.stat.Stat;
import itemData.DmgType;
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
                else setItemData(currField, fieldVal, i);
            }
            scn.close();
            return i;
        } catch (FileNotFoundException e) {
            throw new IllegalArgumentException("Given file path does not exist.");
        }
    }

    private static void setWeaponData(String field, String val, Weapon w) {
        switch (field) {
            case "wpnSheet":
                w.setPathToWpnSprite(val);
                break;
            case "dmgType":
                w.setDmgType(DmgType.valueOf(val));
                break;
            case "attrScaling":
                parseAttrScalings(val, w);
                break;
            case "maxDmg":
                w.setMaxDmg(Double.valueOf(val));
                break;
            case "minDmg":
                w.setMinDmg(Double.valueOf(val));
                break;
            case "name":
            case "value":
            case "pickableSheet":
            case "attrChange":
            case "statChange":
            default:
                setItemData(field, val, w);
                break;
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
            case "attrChange":
                parseAttrOrStatChanges(val, i, true);
                break;
            case "statChange":
                parseAttrOrStatChanges(val, i, false);
                break;
            default: throw new IllegalArgumentException("Given file is not set up correctly.");
        }
    }

    private static void parseAttrOrStatChanges(String statString, Item i, boolean isAttr) {
        Scanner scn = new Scanner(statString);
        scn.useDelimiter(";");
        while (scn.hasNext()) {
            String stat = scn.next();
            String val = scn.next();
            if (isAttr) i.addAttrChange(Attr.valueOf(stat), Integer.parseInt(val));
            else i.addStatChange(Stat.valueOf(stat), Double.parseDouble(val));
        }
        scn.close();
    }

    private static void parseAttrScalings(String statString, Weapon w) {
        Scanner scn = new Scanner(statString);
        scn.useDelimiter(";");
        while (scn.hasNext()) {
            String stat = scn.next();
            String val = scn.next();
            w.addAttrScaling(Attr.valueOf(stat), Double.parseDouble(val));
        }
        scn.close();
    }

}
