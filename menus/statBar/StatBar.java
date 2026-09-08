package menus.statBar;

import charData.stat.Stat;
import javafx.beans.property.DoubleProperty;
import javafx.scene.control.ProgressBar;

public class StatBar {
    private ProgressBar bar;

    public StatBar(Stat s, DoubleProperty val) {
        bar = new ProgressBar();
        bar.getStylesheets().add(
                getClass().getResource("singleBarStyle.css").toExternalForm());
        bar.getStyleClass().add(s.toString());
        bar.progressProperty().bind(val);
    }

    public ProgressBar get() {return bar;}


}
