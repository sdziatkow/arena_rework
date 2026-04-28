package spriteData;

import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;

import java.util.Scanner;

public class FrameGen {

    /** @return [frameSets, framesPerSet, width, height] */
    private static int[] fileParser(String fileName) {
        String noTag = fileName.substring(0, fileName.length() - 4);
        int[] data = new int[4];
        Scanner scnr = new Scanner(noTag);
        scnr.useDelimiter("[_|x]");
        int dataCount = 0;
        while (scnr.hasNext()) {
            String next = scnr.next();
            if (Character.isDigit(next.charAt(0))) {
                if (dataCount >= data.length) {
                    throw new IllegalArgumentException
                            ("Given file is not named correctly. See resources/sprites/nameGuide.txt");
                }
                data[dataCount] = Integer.parseInt(next);
                ++dataCount;
            }
        }
        scnr.close();
        return data;
    }

    public static double frameWidth(String fileName) { return fileParser(fileName)[2]; }
    public static double frameHeight(String fileName) {return fileParser(fileName)[3];}

    /**
     * Parses the file name to generate all of its frames.
     * <br> File name should look like: action_AxB_CxD.png
     * <br> A = Total sets of frames; B = Frames per set.
     * <br> C = width of each frame; D = height of each frame;
     */
    public static WritableImage[][] genFrames(String fileName) {
        Image sheet = new Image(fileName);

        // [frameSets, framesPerSet, frameWidth, frameHeight]
        int[] data = fileParser(fileName);
        return genFrames(sheet, data[0], data[1], data[2], data[3]);
    }
    public static WritableImage genOneFrame(String fileName) {
        Image sheet  = new Image(fileName);
        int[] data = fileParser(fileName);
        if (data[0] != 1 || data[1] != 1) throw new IllegalArgumentException("Must be one frame only.");
        WritableImage[][] frame = genFrames(sheet, data[0], data[1], data[2], data[3]);
        return frame[0][0];
    }

    private static WritableImage[][] genFrames(
        Image sheet,
        int frameSets,
        int framesPerSet,
        int frameWidth,
        int frameHeight
    ) {

        WritableImage[][] allFrames = new WritableImage[frameSets][];

        // For each frameSet...
        for (int s = 0; s < frameSets; ++s) {
            allFrames[s] = new WritableImage[framesPerSet];

            // Add each frame to its set.
            for (int f = 0; f < allFrames[s].length; ++f) {

                allFrames[s][f] = new WritableImage(
                        sheet.getPixelReader(),
                        (frameWidth * f),
                        (frameHeight * s),
                        frameWidth,
                        frameHeight
                );
            }
        }
        return allFrames;
    }
}
