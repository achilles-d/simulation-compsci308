package View;

import Controller.Controller;
import java.util.ResourceBundle;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Polygon;

public class HexCellAnimator extends CellAnimator {

  protected Polygon myCell;

  /**
   * Create new instance
   *
   * @param pane       Pane to draw rectangle on
   * @param X          Grid position of cell
   * @param Y          Grid position of cell
   * @param width      Width in pixels of rectangle
   * @param height     Height in pixels of rectangle
   * @param color      Color of rectangle
   * @param controller
   */
  public HexCellAnimator(GridPane pane, int X, int Y, double width, double height,
      Color color, Controller controller, ResourceBundle resourceColors) {
    super(pane, X, Y, width, height, color, controller, resourceColors);
  }

  /**
   * @param pane   Pane to draw rectangle on
   * @param width  Width in pixels of rectangle
   * @param height Height in pixels of rectangle
   * @param color  Color of rectangle
   */
  @Override
  protected void makeCell(GridPane pane, double width, double height, Paint color) {
    width = width / 2;
    height = height / 2;
    myCell = new Polygon();
    myCell.getPoints().addAll(height / 4, 0.0,
        height * 3 / 4, 0.0,
        height, width / 2,
        height * 3 / 4, width,
        height / 4, width,
        0.0, width / 2);
    myCell.setFill(color);
    // row is even
    if (myY % 2 == 0) {
      pane.add(myCell, myX * 2, myY, 1, 1);
    }
    // row is odd
    else {
      pane.add(myCell, (myX * 2) + 1, myY, 1, 1);
    }
    myCell.setOnMouseClicked(e -> handleClick());
  }

  /**
   * @param color New state color
   */
  @Override
  public void changeCellState(Paint color) {
    myCell.setFill(color);
  }
}
