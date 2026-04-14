package spriteData;

import collision.ColType;
import collision.CollisionBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.Group;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.image.WritableImage;

/**
 * <br>spriteSheet: Contains file with entire sprite sheet.
 * <br>spriteView:  Contains WritableImage of frame taken from spriteSheet.
 * <br>spritePane:  Can contain multiple spriteViews.
 * <br>spriteGroup: Contains spritePane and worldBox.
 * <br>pos:         Property that is bound to spriteGroup's scene position.
 */
public class Sprite {
    private Image spriteSheet;
    private ImageView spriteView;
    private StackPane spritePane;

    private Group spriteGroup;

    private DoubleProperty[] pos;

    /**
     * Default constructor, creates an empty sprite.
     * Must setSpriteSheet(), setFrame(), and setPos() in order to work properly.
     */
    public Sprite() {
        spriteSheet = null;
        spriteView = new ImageView();
        spritePane = new StackPane();
        spriteGroup = new Group();

        pos = new DoubleProperty[] {new SimpleDoubleProperty(0.0), new SimpleDoubleProperty(0.0)};

        spriteGroup.translateXProperty().bind(pos[0]);
        spriteGroup.translateYProperty().bind(pos[1]);
        spriteGroup.setCache(true);
    }

//SETTERS----------------------------------------------------------------------------------------------------------------

    /**
     * Creates a new Image to store the file at the given path.
     * @param path The path to the image file, ex: "file:resources/sprites/overworld.png"
     */
    public void setSheet(String path) { spriteSheet = new Image(path); }

    /**
     * This method will set the Image of ImageView spriteView.
     * The given bounds MUST be of length 4 [x, y, width, height]
     * The given bounds should refer to the frame's pixel location on
     * spriteSheet.
     */
    public void setFrame(int[] bounds) {
        spriteView.setImage(new WritableImage(
                spriteSheet.getPixelReader(),
                bounds[0],
                bounds[1],
                bounds[2],
                bounds[3]
        ));
    }

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

    /** @return The Image Object used to store the spriteSheet its frame is drawn from. */
    public Image getSheet() {
        return spriteSheet;
    }

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
