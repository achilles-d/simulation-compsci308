package View;

import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;

public class CellAnimator {
  private Rectangle myCell;
  private GridPane myPane;

  public CellAnimator (GridPane pane, int X, int Y, int width, int height, Color color){
    myCell = new Rectangle(width, height);
    myCell.setFill(color);
    myPane = pane;
    System.out.println(width);
    System.out.println(height);
    myPane.add(myCell, X, Y, 1,1);
  }

  public void changeCellState (Paint color) {
    myCell.setFill(color);
  }

  public void removeCell () {
    myPane.getChildren().remove(myCell);
  }
}
