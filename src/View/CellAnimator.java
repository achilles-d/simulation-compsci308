package View;

import javafx.scene.canvas.Canvas;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.paint.Color;

public class CellAnimator {
  private Rectangle myCell;

  public CellAnimator (Canvas canvas, int X, int Y, int width, int height, Paint color){
    myCell = new Rectangle(X, Y, width, height);
    myCell.setFill(color);

  }

  public void changeCellState (Color color) {
    myColor = color;
  }


}
