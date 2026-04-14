package collision;

import spriteData.Dir;

public class CheckBox extends CollisionBox {
    private double[] baseBounds;

    public CheckBox() { super(ColType.CHECKBOX); }

    /**
     * This sets the base bounds of CheckBox. These are the bounds CheckBox will contract() to.
     * Allows for expanding while still being able to go back to original bounds.
     * @param bounds Must be exactly of length four. [x, y, width, height]
     */
    public void setBaseBounds(double[] bounds) {
        if (bounds.length != 4) throw new IllegalArgumentException("Array must be length of exactly 4.");
        baseBounds = bounds;
    }

    /** @return The base bounds of this CheckBox; The bounds this CheckBox will contract() to */
    public double[] getBaseBounds() { return baseBounds; };

    /** Set the bounds of this CheckBox to its base bounds */
    public void contract() { setBounds(baseBounds); }

    /**
     * Expands the bounds of this CheckBox by the given amount in the given direction
     * @param amnt The amount to expand by.
     * @param direction The direction in which to expand.
     */
    public void expand(double amnt, Dir direction) {
        switch (direction) {
            case N:
                getColBox().setX(baseBounds[0]);
                getColBox().setY(baseBounds[1] - amnt);
                break;
            case S:
                getColBox().setX(baseBounds[0]);
                getColBox().setY(baseBounds[1] + amnt);
                break;
            case E:
                getColBox().setY(baseBounds[1]);
                getColBox().setX(baseBounds[0] + amnt);
                break;
            case W:
                getColBox().setY(baseBounds[1]);
                getColBox().setX(baseBounds[0] - amnt);
                break;
            default: return;
        }
    }
}
