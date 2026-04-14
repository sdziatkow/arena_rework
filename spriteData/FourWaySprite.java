package spriteData;

import javafx.scene.image.WritableImage;

/**
 * For AnimSprites that have four different sets of animFrames for each direction.
 * @see AnimSprite
 * @see Dir
 */
public class FourWaySprite extends AnimSprite {
    public final int TOTAL_DIRECTIONS = 4;
    private WritableImage[][] allFrames;
    private Dir dir;

    public FourWaySprite() {
        allFrames = new WritableImage[TOTAL_DIRECTIONS][];
        dir = Dir.S;
    }

    /** Set up so allFrames[] looks like this: [north[], south[], east[], west[]] */
    public void setAllFrames(Dir direction, WritableImage[] frames) {
        switch (direction) {
            case N:
                allFrames[0] = frames;
                break;
            case S:
                allFrames[1] = frames;
                break;
            case E:
                allFrames[2] = frames;
                break;
            case W:
                allFrames[3] = frames;
                break;
            default:
                return;
        }
    }

    /** @return The current direction this sprite is facing / moving in **/
    public Dir getDir() { return dir; }

    /**
     * @param direction The direction in which the frames are animated for.
     * @return All frames for the animation in the given direction
     */
    public WritableImage[] getFrames(Dir direction) {
        switch (direction) {
            case N: return allFrames[0];
            case S: return allFrames[1];
            case E: return allFrames[2];
            case W: return allFrames[3];
            default: return null;
        }
    }

    /** Set up so allFrames[] looks like this: [north[], south[], east[], west[]] */
    public WritableImage[][] getAllFrames() { return allFrames; }

//OPERATIONS-------------------------------------------------------------------------------------------------------------

    /** Changes AnimFrames to be the given direction and reset frameCount */
    public void switchDir(Dir direction) {
        dir = direction;
        setAnimFrames(getFrames(dir));
    }

    /** This method will:<br>
     * Reset this Sprite's frameCount <br>
     * Set its frame to the first frame of its current direction.
     */
    public void idleFrame() {
        resetFrameCount();
        setFrame(getFrames(dir)[0]);
    }

}
