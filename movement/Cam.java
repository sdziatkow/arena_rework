package movement;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.ParallelCamera;

public class Cam {
    private ParallelCamera cam;
    private DoubleProperty layoutX;
    private DoubleProperty layoutY;

    public Cam() {
        cam = new ParallelCamera();
        layoutX = new SimpleDoubleProperty();
        layoutY = new SimpleDoubleProperty();
    }

    public void bindLayout(DoubleProperty x, DoubleProperty y) {
        layoutX.bind(x);
        layoutY.bind(y);
    }
}
