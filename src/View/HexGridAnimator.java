package View;

import Controller.Controller;
import java.util.HashMap;
import javafx.scene.chart.XYChart.Data;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;

public class HexGridAnimator extends GridAnimator {

  /**
   * Create new instance of the GridAnimator
   *
   * @param pane          This is grid that the cells will be drawn on
   * @param controller    Controller that holds the simulation parameters
   * @param gridDimension max dimension for the grid to display on the screen
   */
  public HexGridAnimator(GridPane pane, Controller controller,
      int gridDimension) {
    super(pane, controller, gridDimension);
  }

  /**
   *
   */
  @Override
  public void makeCellArray() {
    makeGraph();
    height = myController.getGridHeight();
    width = myController.getGridWidth();
    myCells = new HexCellAnimator[height][width];
    myX = 0;
    // Set all the count to zero
    myCellCounts = new HashMap<>();
    System.out.println(myController.getCellStates());
    for (String s : myController.getCellStates()) {
      System.out.println("S: " + s);
      myCellCounts.put(s, 0);
    }

    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        String state = myController.getCellState(i, j);
        System.out.println(state);
        myCellCounts.put(state, myCellCounts.get(state) + 1);
        Color color = convertStateToPaint(state);
        myCells[i][j] = new HexCellAnimator(myPane, i, j, maxGridDimension / width,
            maxGridDimension / height, color, myController, myColors);
      }
    }

    for (String s : myCellCounts.keySet()) {
      myGraph.addData(s, new Data<Integer, Integer>(myX, myCellCounts.get(s)));
    }
  }
}
