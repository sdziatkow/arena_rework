package spriteData;

import control.ArenaObject;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.Group;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.image.WritableImage;

/**
 * <br>spriteView:  Contains WritableImage of this sprite's current frame. getFrame()
 * <br>spritePane:  Can contain multiple spriteViews stacked on one another. getPane()
 * <br>spriteGroup: Contains spritePane and any other additional elements such as CollisionBoxes.
 * <br>pos:         Property that is bound to spriteGroup's scene position.
 */
public class Sprite extends ArenaObject {
    private ImageView spriteView;
    private StackPane spritePane;

    private Group spriteGroup;

    private DoubleProperty[] pos;

    /**
     * Default constructor, creates an empty sprite.
     * Must setSpriteSheet(), setFrame(), and setPos() in order to work properly.
     */
    public Sprite() {
        spriteView = new ImageView();
        spritePane = new StackPane();
        spriteGroup = new Group();

        pos = new DoubleProperty[] {new SimpleDoubleProperty(0.0), new SimpleDoubleProperty(0.0)};

        spriteGroup.translateXProperty().bind(pos[0]);
        spriteGroup.translateYProperty().bind(pos[1]);
        spriteGroup.setCache(true);
        spriteView.setCache(true);
        spritePane.setCache(true);
    }

//SETTERS----------------------------------------------------------------------------------------------------------------

    /**
     * This method will set the Image of ImageView spriteView with the given img.
     * @param img The img to set the sprite's frame to.
     */
    public void setFrame(WritableImage img) { spriteView.setImage(img); }

    /**
     * @param x: The x position of the sprite in relation to the scene.
     * @param y: The y position of the sprite in relation to the scene.
     */
    public void setPos(double x, double y) {
        pos[0].set(x);
        pos[1].set(y);
    }

//GETTERS----------------------------------------------------------------------------------------------------------------

    /** @return The ImageView Object used to store the Sprite's current frame. */
    public ImageView getFrame() { return spriteView; }

    /** @return The StackPane Object that is used to store the ImageView Object getFrame() */
    public StackPane getPane() { return spritePane; }

    /** @return The Group Object that stores all components of this Sprite: getFrame(), getPane(), and getWorldBox(). */
    public Group getGroup() { return spriteGroup; }

    /**
     * The position of this Sprite relative to getGroup()'s Parent in the Scene.
     * @param axis Must be either ZERO or ONE, otherwise throws error.
     * @return The Object that stores the value of the given axis; <br> ex: [0, 1] ->[x, y].
     */
    public DoubleProperty getPos(int axis) {
        if (axis != 0 && axis != 1) throw new IllegalArgumentException(
                "Given axis MUST be ZERO or ONE; ex: [0, 1] ->[x, y]."
        );
        return pos[axis];
    }
}
