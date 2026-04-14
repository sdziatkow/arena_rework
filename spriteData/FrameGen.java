package spriteData;

import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;

public class FrameGen {

    public static WritableImage[][] genFrames(
        int totalDirections,
        int frameCount,
        Image sheet,
        int xOffset,
        int yOffset,
        int frameWidth,
        int frameHeight
    ) {
        WritableImage[][] allFrames = new WritableImage[totalDirections][];

        // For each direction.
        for (int d = 0; d < allFrames.length; ++d) {
            allFrames[d] = new WritableImage[frameCount];

            // Add each
            for (int f = 0; f < allFrames[d].length; ++f) {

                allFrames[d][f] = new WritableImage(
                        sheet.getPixelReader(),
                        xOffset + (frameWidth * f),
                        yOffset + (frameHeight * d),
                        frameWidth,
                        frameHeight
                );
            }
        }
        return allFrames;
    }
}
