package itemData.weapons;

import itemData.Item;

public class Weapon extends Item {
    private String pathToWpnSprite;

    public Weapon() {
        super();
        pathToWpnSprite = null;
    }

    public Weapon(String name, String pathToPickableSprite, String pathToWpnSprite) {
        super(name);
        setPathToPickableSprite(pathToPickableSprite);
        this.pathToWpnSprite = pathToWpnSprite;
    }

    public void setPathToWpnSprite(String path) {pathToWpnSprite = path;}
    public String getPathToWpnSprite() {return pathToWpnSprite;}
}
