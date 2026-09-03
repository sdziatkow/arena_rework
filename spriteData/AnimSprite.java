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
 * For animations with one set of frames.
 * @see Sprite
 */
public abstract class AnimSprite extends Sprite {

    public final int BASE_FRAME_RATE = 128;

    private IntVal frameCount;
    private WritableImage[] frameSet;

    private Timeline anim;
    private KeyFrame animEvent;
    private EventHandler<ActionEvent> onAnimFrameFinish;

//CONSTRUCTOR------------------------------------------------------------------------------------------------------------

    /** Creates an empty AnimSprite by default has 1 total frame */
    public AnimSprite() {
        frameCount = new IntVal();
        frameSet = new WritableImage[frameCount.getMax()];

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
        frameSet = new WritableImage[frameCount.getMax()];

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

    public void setAnimEvent(KeyFrame event) {
        animEvent = event;
    }

    /**
     * This method will set totalFrames to given frames and re-initialize
     * allFrames and anim.cycleCount because they are dependent on
     * totalFrames.
     * @param frames The total amount of frames in this AnimSprite's animation.
     */
    public void setTotalFrames(int frames) {
        frameCount.setMax(frames);
        frameSet = new WritableImage[getTotalFrames()];
        anim.setCycleCount(getTotalFrames());
    }

    /** Sets all frames of this Sprite's animation to the given frames. */
    public void setFrameSet(WritableImage[] frameSet) { this.frameSet = frameSet; }

//GETTERS----------------------------------------------------------------------------------------------------------------

    /** @return The frameCount's current value */
    public int getFrameCount() { return frameCount.get(); }

    /** @return The frameCount's maximum value */
    public int getTotalFrames() { return frameCount.getMax(); }

    /** @return An array containing each frame of this AnimSprite's animation */
    public WritableImage[] getFrameSet() { return frameSet; }

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
        setFrame(getFrameSet()[frameCount.get()]);
        if (isLastFrame()) resetFrameCount();
        else frameCount.inc(1);
    }

    /** This method will reverse the order of allFrames. */
    public void reverseFrames() {
        WritableImage[] reversed;

        reversed = new WritableImage[getTotalFrames()];
        for (int f = 0; f < getTotalFrames(); ++f) {
            reversed[f] = frameSet[(getTotalFrames() - 1) - f];
        }
        frameSet = reversed;
    }

    public void resetFrameCount() { frameCount.set(frameCount.getMin()); }
    public boolean isLastFrame() { return getFrameCount() == getTotalFrames() - 1; }
    public boolean isFirstFrame() { return getFrameCount() == frameCount.getMin(); }
    public boolean isAnimRunning() { return anim.statusProperty().get().equals(Animation.Status.RUNNING); }

}
