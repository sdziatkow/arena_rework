package collision;

import javafx.scene.shape.Rectangle;
import javafx.scene.paint.Color;
import javafx.geometry.Bounds;

public class CollisionBox {

    private ColType type;
    private Rectangle colBox;

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

    /** Will return a Bounds object of rectangle. */
    public Bounds getBounds() { return colBox.localToScene(colBox.getBoundsInLocal()); }
}
