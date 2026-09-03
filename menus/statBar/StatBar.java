package menus.statBar;

import charData.stat.Stat;
import javafx.scene.control.ProgressBar;
import values.DoubleVal;

public class StatBar {
    private ProgressBar bar;
    private DoubleVal val;

    public StatBar(Stat s, DoubleVal v) {
        val = v;
        bar = new ProgressBar();
        updateProgress();
        bar.getStylesheets().add(
                getClass().getResource("barStyle.css").toExternalForm());
        bar.getStyleClass().add(s.toString());
    }

    public void updateProgress() {
        bar.setProgress(val.get() / val.getMax());
    }

    public ProgressBar get() {return bar;}


}
