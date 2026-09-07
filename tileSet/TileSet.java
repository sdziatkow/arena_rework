package tileSet;

import collision.ColType;
import collision.CollisionBox;
import javafx.scene.Group;
import javafx.scene.layout.*;
import spriteData.Img;

/**
 * <br>Region floor: The very bottom background (repeating) image of the TileSet.
 * <br>GridPane border: The repeating image that will take up the area in which ArenaPersons can not reach.
 * <br>CollisionBox worldBox: The area in which the ArenaPersons can move.
 * <br>Group container: The Object that holds all above Objects.
 * <br>int[] TILE_SIZE: The total amount of repeated images in Region floor. [width, height].
 * <br>String pathToFloor: The path to the image used in Region floor.
 * <br>String pathToBorder: The path to the image used in GridPane border.
 * <br>double borderOffset: The percentage [0-1] of the floor that the border should take up.
 */
public class TileSet {
    private String pathToFloor;
    private String pathToBorder;
    private double borderOffset;
    private final int[] TILE_SIZE = new int[2];
    private Group container;
    private Region floor;
    private GridPane border;
    private CollisionBox worldBox;

    public TileSet() {
        container = new Group();
        floor = new Region();
        border = new GridPane();
    }

    public TileSet(String pathToFloorImg, String pathToBorderImg, int[] DIMENSIONS, double offset) {
        TILE_SIZE[0] = DIMENSIONS[0];
        TILE_SIZE[1] = DIMENSIONS[1];
        container = new Group();
        floor = new Region();
        border = new GridPane();
        pathToFloor = pathToFloorImg;
        pathToBorder = pathToBorderImg;
        borderOffset = offset;
    }

    private void setUpFloor() {
        Img img = new Img(pathToFloor);
        BackgroundImage bgImg = new BackgroundImage(
                img.getImage().getImage(),
                BackgroundRepeat.REPEAT,
                BackgroundRepeat.REPEAT,
                BackgroundPosition.DEFAULT,
                BackgroundSize.DEFAULT
        );
        floor.setPrefSize(img.getSize()[0] * TILE_SIZE[0], img.getSize()[1] * TILE_SIZE[1]);
        floor.setBackground(new Background(bgImg));
    }

    private void setUpBorder() {
        Img img = new Img(pathToBorder);
        double w = floor.getPrefWidth(); // Total width of bg.
        double h = floor.getPrefHeight(); // Total height of bg.

        // Set of borders [left, top, bottom, right].
        Region[] borders = new Region[4];
        for (int i = 0; i < borders.length; ++i) {
            borders[i] = new Region();
            borders[i].setPrefSize(w * borderOffset, h * borderOffset);
            borders[i].setBackground(new Background(new BackgroundImage(
                    img.getImage().getImage(),
                    BackgroundRepeat.REPEAT,
                    BackgroundRepeat.REPEAT,
                    BackgroundPosition.DEFAULT,
                    BackgroundSize.DEFAULT
            )));
        }

        borders[0].setPrefSize(w * borderOffset, h);              // Left - borderOffset width, same height.
        borders[1].setPrefSize(w * borderOffset, h * borderOffset); // Top - borderOffset width and height.
        borders[2].setPrefSize(w * borderOffset, h * borderOffset); // Bottom - borderOffset width and height.
        borders[3].setPrefSize(w * borderOffset, h);              // Right - offset width, same height.

        worldBox = new CollisionBox(
                ColType.WORLDBOX,
                new double[]{0, 0, w/2, h/2}
        );

        // Set of borders [left, top, bottom, right].
        // node, col, row, col-span, row-span.
        border.add(borders[0], 0, 0, 1, 3); // left
        border.add(borders[1], 1, 0, 1, 1); // top
        border.add(worldBox.getColBox(), 1, 1, 1, 1); // center
        border.add(borders[2], 1, 2, 1, 1); // bot
        border.add(borders[3], 2, 0, 1, 3); // right
        border.setPrefSize(w, h);
    }

    public void setUp() {
        setUpFloor();
        setUpBorder();
        container.getChildren().add(floor);
        container.getChildren().add(border);
    }

    public void setPathToFloor(String path) {pathToFloor = path;}
    public void setPathToBorder(String path) {pathToBorder = path;}
    public void setTileWidth(int w) {TILE_SIZE[0] = w;}
    public void setTileHeight(int h) {TILE_SIZE[1] = h;}
    public void setBorderOffset(double o) {borderOffset =o;}


    public Group getGroup() {
        return container;
    }
    public Region getFloor() {return floor;}
    public GridPane getBorder() {return border;}
    public CollisionBox getWorldBox() {return worldBox;}
}
