package collision;

import spriteData.FrameGen;

public class BoxSizer {

    public static void sizeToFrame(String pathToFile, CollisionBox box) {
        double x = 0;
        double y = 0;
        double width = FrameGen.frameWidth(pathToFile);
        double height = FrameGen.frameHeight(pathToFile);
        box.setBaseBounds(new double[] {x, y, width, height});
        box.setBounds(box.getBaseBounds());
    }

    public static void sizeBoxSmallMid(String pathToFile, CollisionBox box) {
        sizeToFrame(pathToFile, box);
        double x = 0;
        double y = 0;
        double width = box.getBaseBounds()[2];
        double height = box.getBaseBounds()[3];

        if (height != width && height % width == 0) {
            double min = Math.min(width, height);
            double max = Math.max(width, height);

            min *= (max / min);

            if (max == width) height = min;
            else              width  = min;
        }

        x = width / 2.46;
        y = height / 2.26;
        width /= 4.56;
        height /= 4.56;

        box.setBaseBounds(new double[] {x, y, width, height});
        box.setBounds(box.getBaseBounds());
    }

    public static void sizeBoxBigMid(String pathToFile, CollisionBox box) {
        sizeBoxSmallMid(pathToFile, box);
        double x = box.getBaseBounds()[0];
        double y = box.getBaseBounds()[1];
        double width = box.getBaseBounds()[2];
        double height = box.getBaseBounds()[3];

        x /= 1.26;
        y /= 1.26;
        width *= 1.56;
        height *= 1.5;

        box.setBaseBounds(new double[]{x, y, width, height});
        box.setBounds(box.getBaseBounds());
    }

    public static void sizeBoxEvenlyBiggerThan(CollisionBox boxToSize, CollisionBox baseBox) {
        double x = baseBox.getBaseBounds()[0];
        double y = baseBox.getBaseBounds()[1];
        double width = baseBox.getBaseBounds()[2];
        double height = baseBox.getBaseBounds()[3];

        x -= (x / 2);
        y -= (y / 2);
        width += (width * 2);
        height += (height * 2);

        boxToSize.setBaseBounds(new double[]{x, y, width, height});
        boxToSize.setBounds(boxToSize.getBaseBounds());
    }

    public static void sizeBoxWideBottom(String pathToFile, CollisionBox box) {
        sizeToFrame(pathToFile, box);
        double x = box.getBaseBounds()[0];
        double y = box.getBaseBounds()[1];
        double width = box.getBaseBounds()[2];
        double height = box.getBaseBounds()[3];

        x += (width * 0.082);
        y = (height / 1.5);
        width -= (width / 5.8);
        height -= (height / 1.20);

        box.setBaseBounds(new double[]{x, y, width, height});
        box.setBounds(box.getBaseBounds());
    }
}
