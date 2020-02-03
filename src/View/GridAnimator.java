package View;

import cellsociety.Controller;
import cellsociety.Grid;
import java.util.ResourceBundle;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import java.awt.PaintContext;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.awt.image.ColorModel;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.Node;
import javafx.scene.layout.GridPane;

public class GridAnimator {
  private static final String RESOURCES = "resources";
  public static final String DEFAULT_RESOURCE_PACKAGE = RESOURCES + ".";

  private GridPane myPane;
  private Controller myController;
  private int maxGridDimension;
  private CellAnimator[][] myCells;
  private ResourceBundle myColors;

  public GridAnimator(GridPane pane, Controller controller, int gridDimension) {
    myPane = pane;
    myController = controller;
    myCells = makeCellArray();
    maxGridDimension = gridDimension;
    myColors = ResourceBundle.getBundle(DEFAULT_RESOURCE_PACKAGE + "Colors");
  }

  private CellAnimator[][] makeCellArray () {
    int height = myController.getGridHeight();
    int width = myController.getGridWidth();
    CellAnimator[][] result = new CellAnimator[height][width];

    for (int i=0; i < height; i++) {
      for (int j=0; j<width; j++) {
        String state = myController.getGrid().getCellState(i,j);
        Paint color = convertStateToPaint(state);
        result[i][j] = new CellAnimator(myPane, i, j, maxGridDimension/width, maxGridDimension/height, color);
      }
    }

    return result;
  }

  private Paint convertStateToPaint (String state) {
    return Color.web(myColors.getString("color2"));
  }

  public void updateCells () {
    myController.getGrid().update();
  }
}
