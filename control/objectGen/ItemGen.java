package control.objectGen;

import charData.attr.Attr;
import charData.stat.Stat;
import charData.statMods.StatChange;
import itemData.DmgType;
import itemData.Item;
import itemData.armors.Armor;
import itemData.usables.Usable;
import itemData.weapons.Weapon;
import values.ValType;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Scanner;

public class ItemGen {

    public static Item genItem(String objID) {
        String pathToData = ObjectDataParser.getDataFromObjectID(ObjectType.ITEM, objID);
        HashMap<String, String> data = ObjectDataParser.parseObjectData(pathToData);
        Item i;
        char typeIndicator = objID.charAt(1);
        switch (typeIndicator) {
            case 'U':
                i = new Usable();
                break;
            case 'A':
                i = new Armor();
                break;
            case 'W':
                i = new Weapon();
                break;
            default:
                i = new Item();
                break;
        }
        for (String field: data.keySet()) {
            if (i instanceof Weapon w) setWeaponData(field, data.get(field), w);
            else setItemData(field, data.get(field), i);
        }
        i.setObjID(objID);
        return i;
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
            case "statMod":
                parseStatMod(val, i);
                break;
            default: throw new IllegalArgumentException("Given file is not set up correctly.");
        }
    }

    private static void parseStatMod(String statString, Item i) {
        Scanner scn = new Scanner(statString);
        scn.useDelimiter(";");
        while (scn.hasNext()) {
            String change = scn.next();
            String val = scn.next();
            String stat = scn.next();
            String amnt = scn.next();
            try {
                i.statMod().addAttrChange(
                    StatChange.valueOf(change), ValType.valueOf(val), Attr.valueOf(stat), Integer.parseInt(amnt)
                );
            }
            catch (IllegalArgumentException e) {
                i.statMod().addStatChange(
                    StatChange.valueOf(change), ValType.valueOf(val), Stat.valueOf(stat), Double.parseDouble(amnt)
                );
            }
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
