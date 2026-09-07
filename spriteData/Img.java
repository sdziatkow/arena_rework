package spriteData;

import control.ArenaObject;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;

/**
 * <br>img:  Contains WritableImage of this Img Object's current frame. getFrame()
 */
public class Img {
    private ImageView img;
    private double width;
    private double height;

    public Img() {
        img = new ImageView();
        img.setCache(true);
        width = 0.0;
        height = 0.0;
    }

    public Img(String pathToFile) {
        img = new ImageView();
        setImg(pathToFile);
        img.setCache(true);
    }

    /**
     *
     * @param pathToFile The path to an image with one frame.
     *                   ex: "file:resources/sprites/bottom/grass/large_1x1_96x96.png"
     */
    public void setImg(String pathToFile) {
        img.setImage(new Image(pathToFile));
        width = FrameGen.frameWidth(pathToFile);
        height = FrameGen.frameHeight(pathToFile);
    }

    /**
     * @return An Array of two values: [width, height].
     */
    public double[] getSize() {
        return new double[]{width, height};
    }

    public ImageView getImage() {
        return img;
    }
}
