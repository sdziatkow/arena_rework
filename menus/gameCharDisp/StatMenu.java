package menus.gameCharDisp;

import charData.stat.CharStats;
import charData.stat.Stat;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import values.DoubleVal;

public class StatMenu {

    public GridPane main;
    private Label header;
    private GridPane statDisp;
    private CharStats stats;

    public StatMenu(CharStats s) {
        stats = s;
        main = new GridPane();
        header = new Label();
        statDisp = new GridPane();

        header.setText("Stats");
        main.add(header, 0, 0, 2, 1);
        setUpStatDisp();
        main.add(statDisp, 0, 1);

        main.getStylesheets().add(
                getClass().getResource("statStyle.css").toExternalForm());
        main.getStyleClass().add("stat-main");
        header.getStyleClass().add("stat-header");
        statDisp.getStyleClass().add("stat-grid");
    }

    private void setUpStatDisp() {
        statDisp.getChildren().clear();
        Stat[] info = Stat.getStats();
        DoubleVal currStat;
        for (int i = 0; i < info.length; ++i) {
            currStat = stats.get(info[i]);
            Label key = new Label(info[i].toString());
            key.getStyleClass().add("stat-key-label");

            Label val = new Label(String.valueOf(currStat.get()));
            val.getStyleClass().add("stat-val-label");

            statDisp.add(key, 0, i);
            statDisp.add(val, 1, i);

            if (info[i].equals(Stat.HP) || info[i].equals(Stat.SP)) {
                Label div = new Label("/");
                div.getStyleClass().add("stat-div-label");

                Label max = new Label(String.valueOf(currStat.getMax()));
                max.getStyleClass().add("stat-max-label");
                statDisp.add(div, 2, i);
                statDisp.add(max, 3, i);
            }
        }
    }
}
