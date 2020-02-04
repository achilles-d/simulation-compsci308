package View;

import cellsociety.Controller;
import cellsociety.Grid;
import java.util.ResourceBundle;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import java.awt.PaintContext;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.awt.image.ColorModel;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.Node;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Rectangle;

public class GridAnimator {
  private static final String RESOURCES = "resources";
  public static final String DEFAULT_RESOURCE_PACKAGE = RESOURCES + ".";

  private GridPane myPane;
  private Controller myController;
  private int maxGridDimension;
  private CellAnimator[][] myCells;
  private ResourceBundle myColors;
  private int height;
  private int width;

  public GridAnimator(GridPane pane, Controller controller, int gridDimension) {
//    Rectangle myCell = new Rectangle(30, 30);
//    myCell.setFill(Color.BLUE);
//    pane.add(myCell, 1,1 , 1, 1);
    myColors = ResourceBundle.getBundle(DEFAULT_RESOURCE_PACKAGE + "Colors");
    myPane = pane;
    myController = controller;
    maxGridDimension = gridDimension;
    myCells = makeCellArray();

  }

  private CellAnimator[][] makeCellArray () {
    height = myController.getGridHeight();
    width = myController.getGridWidth();
    CellAnimator[][] result = new CellAnimator[height][width];

    for (int i=0; i < height; i++) {
      for (int j=0; j<width; j++) {
        String state = myController.getGrid().getCellState(i,j);
        Color color = convertStateToPaint(state);
        result[i][j] = new CellAnimator(myPane, i, j, maxGridDimension/width, maxGridDimension/height, color);
      }
    }

    return result;
  }

  private Color convertStateToPaint (String state) {
    System.out.println(myColors.getString("test"));
    return Color.web(myColors.getString(state));
  }

  public void updateCells () {
    for (int i=0; i < height; i++) {
      for (int j=0; j<width; j++) {
        String state = myController.getGrid().getCellState(i,j);
        myCells[i][j].changeCellState(convertStateToPaint(state));
      }
    }
  }
}
