package itemData;

import control.ArenaObject;

import java.util.Objects;

public class ItemData extends ArenaObject {
    private Boolean equipped;
    private Integer storageID;
    private String pathToPickableSprite;

    public ItemData() {
        equipped = false;
        storageID = null;
        pathToPickableSprite = null;
    }

    /** @param id The data ID of the Storage Object that contains this Item. */
    public void setStorageID(Integer id){storageID = id;}

    /** @return The data ID of the Storage Object that contains this Item. */
    public Integer getStorageID(){return storageID;}

    /** Interactable PickableSprite for inventory display and when not in storage */
    public void setPathToPickableSprite(String s) {pathToPickableSprite = s;}

    /** @return Interactable PickableSprite for inventory display and when not in storage */
    public String getPathToPickableSprite(){return pathToPickableSprite;}

    public boolean isEquipped() {return equipped;}
    public void toggleEquipped(Boolean b) {equipped = b;}
}
