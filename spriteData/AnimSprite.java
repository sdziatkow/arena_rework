package spriteData;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.image.WritableImage;
import javafx.util.Duration;
import values.IntVal;

/** Animated Sprite
 * @see Sprite
 */
public class AnimSprite extends Sprite {

    public final int BASE_FRAME_RATE = 128;

    private IntVal frameCount;
    private WritableImage[] animFrames;

    private Timeline anim;
    private KeyFrame animEvent;
    private EventHandler<ActionEvent> onAnimFrameFinish;

//CONSTRUCTOR------------------------------------------------------------------------------------------------------------

    /** Creates an empty AnimSprite by default has 1 total frame */
    public AnimSprite() {
        frameCount = new IntVal();
        animFrames = new WritableImage[frameCount.getMax()];

        // Switch to next frame when frame is finished.
        onAnimFrameFinish = new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e) {
                nextFrame();
            }
        };
        animEvent= new KeyFrame(new Duration(BASE_FRAME_RATE), onAnimFrameFinish);
        anim = new Timeline(animEvent);
        anim.setCycleCount(frameCount.getMax());
    }

    /** @param totalFrames The total amount of frames that this Sprite's animation has. */
    public AnimSprite(int totalFrames) {
        frameCount = new IntVal();
        animFrames = new WritableImage[frameCount.getMax()];

        // Switch to next frame when frame is finished.
        onAnimFrameFinish = new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e) {
                nextFrame();
            }
        };
        animEvent= new KeyFrame(new Duration(BASE_FRAME_RATE), onAnimFrameFinish);
        anim = new Timeline(animEvent);
        anim.setCycleCount(frameCount.getMax());
    }

//SETTERS----------------------------------------------------------------------------------------------------------------

    /**
     * This method will set totalFrames to given frames and re-initialize
     * allFrames and anim.cycleCount because they are dependent on
     * totalFrames.
     * @param frames The total amount of frames in this AnimSprite's animation.
     */
    public void setTotalFrames(int frames) {
        frameCount.setMax(frames);
        animFrames = new WritableImage[getTotalFrames()];
        anim.setCycleCount(getTotalFrames());
    }

    /**
     * The given 2D array bounds should be length equal to getTotalFrames and
     * each sub-array should be length 4 [x, y, width, height].
     */
    public void setAnimFrames(int[][] bounds) {

        for (int i = 0; i < getTotalFrames(); ++i) {
            animFrames[i] = new WritableImage(
                    getSheet().getPixelReader(),
                    bounds[i][0],
                    bounds[i][1],
                    bounds[i][2],
                    bounds[i][3]
            );
        }
    }

    /** Sets all frames of this Sprite's animation to the given frames. */
    public void setAnimFrames(WritableImage[] frames) { animFrames = frames; }

//GETTERS----------------------------------------------------------------------------------------------------------------

    /** @return The frameCount's current value */
    public int getFrameCount() { return frameCount.get(); }

    /** @return The frameCount's maximum value */
    public int getTotalFrames() { return frameCount.getMax(); }

    /** @return An array containing each frame of this AnimSprite's animation */
    public WritableImage[] getAnimFrames() { return animFrames; }

    /** @return A Timeline describing this AnimSprite's animation. */
    public Timeline getAnim() { return anim; }

    /** @return A KeyFrame Object that determines getAnim()'s frame rate and onFinishHandler */
    public KeyFrame getAnimEvent() { return animEvent; }

//ANIMATE----------------------------------------------------------------------------------------------------------------

    /**
     * This method will:
     * 	Increment frameCount and reset it to zero if it exceeds totalFrames.
     * 	Set the sprite frame to animFrame at index frameCount.
     */
    public void nextFrame() {
        setFrame(getAnimFrames()[frameCount.get()]);
        if (isLastFrame()) resetFrameCount();
        else frameCount.inc(1);
    }

    /** This method will reverse the order of allFrames. */
    public void reverseFrames() {
        WritableImage[] reversed;

        reversed = new WritableImage[getTotalFrames()];
        for (int f = 0; f < getTotalFrames(); ++f) {
            reversed[f] = animFrames[(getTotalFrames() - 1) - f];
        }
        animFrames = reversed;
    }

    public void resetFrameCount() { frameCount.set(frameCount.getMin()); }
    public boolean isLastFrame() { return getFrameCount() == getTotalFrames() - 1; }
    public boolean isFirstFrame() { return getFrameCount() == frameCount.getMin(); }
    public boolean isAnimRunning() { return anim.statusProperty().get().equals(Animation.Status.RUNNING); }

}
