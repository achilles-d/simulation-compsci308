package View;

import controller.Controller;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Shape;

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
      Color color, Controller controller) {
    super(pane, X, Y, width, height, color, controller);
  }

  @Override
  protected void makeCell(GridPane pane, double width, double height, Paint color){
    myCell = new Polygon();
    myCell.getPoints().addAll(height/4, 0.0,
        height*3/4, 0.0,
        height, width/2,
        height*3/4, width,
        height/4, width,
        0.0, width/2);
    myCell.setFill(color);
    // row is even
    if (myY % 2 == 0){
      pane.add(myCell, myX*2, myY, 1,1);
    }
    // row is odd
    else  {
      pane.add(myCell, (myX*2)+1, myY, 1,1);
    }
    myCell.setOnMouseClicked(e->handleClick());
  }
}
