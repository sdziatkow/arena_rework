package menus.statBar;

import javafx.beans.property.DoubleProperty;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.GridPane;

public class StatBarDisp {
    private final int TOTAL_STATS = 4;
    private ProgressBar[] bars = new ProgressBar[TOTAL_STATS];
    private GridPane container;

    public StatBarDisp(DoubleProperty hpVal, DoubleProperty mpVal, DoubleProperty spVal, DoubleProperty xpVal) {
        container = new GridPane();
        for (int i = 0; i < TOTAL_STATS; ++i) {
            bars[i] = new ProgressBar();
            bars[i].getStylesheets().add(
                    getClass().getResource("multiBarStyle.css").toExternalForm());
            container.add(bars[i], 0, i, 1, 1);
        }
        bars[0].getStyleClass().add("HP");
        bars[0].progressProperty().bind(hpVal);
        bars[1].getStyleClass().add("MP");
        bars[1].progressProperty().bind(mpVal);
        bars[2].getStyleClass().add("SP");
        bars[2].progressProperty().bind(spVal);
        bars[3].getStyleClass().add("XP");
        bars[3].progressProperty().bind(xpVal);
    }

    public GridPane getContainer() {
        return container;
    }
}
