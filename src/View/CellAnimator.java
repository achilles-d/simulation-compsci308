package View;

import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;

/**
 * Class to store the state of Cells and display those
 * in the GUI
 *
 * @author Caleb Sanford
 */
public class CellAnimator {
  private Rectangle myCell;

  /**
   * Create new instance
   *
   * @param pane Pane to draw rectangle on
   * @param X Grid position of cell
   * @param Y Grid position of cell
   * @param width Width in pixels of rectangle
   * @param height Height in pixels of rectangle
   * @param color Color of rectangle
   */
  public CellAnimator (GridPane pane, int X, int Y, int width, int height, Color color){
    myCell = new Rectangle(width, height);
    myCell.setFill(color);
    pane.add(myCell, X, Y, 1,1);
    myCell.setOnMouseClicked(e -> handleClick());
  }

  /**
   * Change the state of an existing cell
   *
   * @param color New state color
   */
  public void changeCellState (Paint color) {
    myCell.setFill(color);
  }

  private void handleClick(){
    
  }
}
