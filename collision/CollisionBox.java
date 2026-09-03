package collision;

import javafx.scene.shape.Rectangle;
import javafx.scene.paint.Color;
import javafx.geometry.Bounds;
import spriteData.Dir;
import java.util.Objects;

public class CollisionBox {

    private int id;
    private ColType type;
    private Rectangle colBox;
    private double[] baseBounds;

//CONSTRUCTORS-----------------------------------------------------------------------------------------------------------

    /** Default constructor, by default is of type ColType.WORLDBOX with bounds: [0, 0, 0, 0]. */
    public CollisionBox() {
        type = ColType.WORLDBOX;
        colBox = new Rectangle();
        setUp();
    }

    /**
     * Creates CollisionBox of given type with bounds [0, 0, 0, 0].
     * @param type The type of collision this box checks for.
     */
    public CollisionBox(ColType type) {
        this.type = type;
        colBox = new Rectangle();
        setUp();
    }

    /**
     * @param type The type of collision this box checks for.
     * @param bounds The size of the CollisionBox; Array must be of length 4 exactly: [west, north, width, height].
     */
    public CollisionBox(ColType type, double[] bounds) {
        this.type = type;

        colBox = new Rectangle();
        setBounds(bounds);
        setUp();
    }

    private void setUp() {

        colBox.setFill(Color.TRANSPARENT);

        // Give each collision box a different color for testing purposes.
        // Stroke/strokeWidth is the border color/border width.
        switch (type) {
            case STAGEBOX:
                colBox.setStroke(Color.GREY);
                colBox.setStrokeWidth(2);
                break;
            case HURTBOX:
                colBox.setStroke(Color.BLUE);
                colBox.setStrokeWidth(2);
                break;
            case HITBOX:
                colBox.setStroke(Color.RED);
                colBox.setStrokeWidth(1);
                break;
            case DETECTBOX:
                colBox.setStroke(Color.PINK);
                colBox.setStrokeWidth(1.5);
                break;
            case INTERACTBOX:
                colBox.setStroke(Color.PURPLE);
                colBox.setStrokeWidth(2);
                break;
            case WORLDBOX:
                colBox.setStroke(Color.BLACK);
                colBox.setStrokeWidth(4);
                break;
            case CHECKBOX:
                colBox.setStroke(Color.GREEN);
                colBox.setStrokeWidth(3);
                break;

        }

        colBox.setOpacity(0);
        colBox.setCache(true);
    }

//SETTERS----------------------------------------------------------------------------------------------------------------

    /**
     * @param x The ID of this object.
     */
    public void setID(int x) {
        id = x;
    }

    /**
     * Sets the size of the rectangle
     * @param bounds The size of the CollisionBox; Array must be of length 4 exactly: [west, north, width, height].
     */
    public void setBounds(double[] bounds) {
        if (bounds.length != 4) throw new IllegalArgumentException(
            "Given bounds Array must be of size 4: [west, north, width, height]."
        );
        colBox.setX(bounds[0]);
        colBox.setY(bounds[1]);
        colBox.setWidth(bounds[2]);
        colBox.setHeight(bounds[3]);
    }

//GETTERS----------------------------------------------------------------------------------------------------------------

    public int getID() {
        return id;
    }

    /** @return The type of collision this box checks for. */
    public ColType getColType() { return type; }

    /** Will return Rectangle object. */
    public Rectangle getColBox() { return colBox; }

    /** Will return x value at center of rectangle. */
    public double getMidX() { return colBox.localToScene(colBox.getBoundsInLocal()).getCenterX(); }

    /** Will return y value at center of rectangle. */
    public double getMidY() { return colBox.localToScene(colBox.getBoundsInLocal()).getCenterY(); }

    /** Will return x value at eastern-most point of rectangle. */
    public double getMaxX() { return colBox.localToScene(colBox.getBoundsInLocal()).getMaxX(); }

    /** Will return y value at southern-most point of rectangle. */
    public double getMaxY() { return colBox.localToScene(colBox.getBoundsInLocal()).getMaxY(); }

    /** Will return x value at western-most point of rectangle. */
    public double getMinX() { return colBox.localToScene(colBox.getBoundsInLocal()).getMinX(); }

    /** will return y value of northern-most point of rectangle. */
    public double getMinY() { return colBox.localToScene(colBox.getBoundsInLocal()).getMinY(); }

    /** @return Array of length 2 [midX, midY]. */
    public double[] midPos() { return new double[]{getMidX(), getMidY()};}

    /** Will return a Bounds object of rectangle. */
    public Bounds getBounds() { return colBox.localToScene(colBox.getBoundsInLocal()); }

//OPERATIONS-------------------------------------------------------------------------------------------------------------

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
     * Moves the bounds of this CollisionBox by the given amount in the given direction
     * @param amnt The amount to move the box by in the given direction, should be the sprite's max speed.
     * @param direction The direction in which to expand.
     */
    public void checkDir(double amnt, Dir direction) {
        contract();
        expandBox(amnt, direction);
    }

    public void expandBox(double amnt, Dir direction) {

        // North or south.
        if (direction.equals(Dir.N) || direction.equals(Dir.S)) {
            getColBox().setHeight(baseBounds[3] / 3.0); // Shrink height.
            if (direction.equals(Dir.N)) getColBox().setY(baseBounds[1] - amnt); // From top-most point minus amnt.
            else getColBox().setY(baseBounds[1] + (baseBounds[3] / 1.5) + amnt); // From bottom-most point plus amnt.
        }

        // East or West.
        else if (direction.equals(Dir.E) || direction.equals(Dir.W)) {
            getColBox().setWidth(baseBounds[2] / 3.0); // Shrink width.

            // From right-most point plus amnt.
            if (direction.equals(Dir.E)) getColBox().setX(baseBounds[0] + (baseBounds[2] / 1.5) + amnt);
            else getColBox().setX(baseBounds[0] - amnt); // From left-most point minus amnt.
        }
    }

    @Override
    public boolean equals(Object other) {
        if (!(other instanceof CollisionBox box)) return false;
        return this.id == box.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(String.valueOf(id));
    }
}
