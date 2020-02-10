package View;

import controller.Controller;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

/**
 * Class to store the state of Cells and display those
 * in the GUI
 *
 * @author Caleb Sanford
 */
public class CellAnimator {
  protected Rectangle myCell;
  protected Controller myController;
  protected int myX;
  protected int myY;

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
  public CellAnimator (GridPane pane, int X, int Y, double width, double height, Color color, Controller controller){
    myController = controller;
    myX = X;
    myY = Y;
    makeCell(pane, width, height, color);
  }

  protected void makeCell(GridPane pane, double width, double height, Paint color){
    myCell = new Rectangle(width, height);
    myCell.setFill(color);
    pane.add(myCell, myX, myY, 1,1);
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

  /**
   *
   */
  protected void handleClick(){
    //myController.cycleState(myX, myY);
  }
}
