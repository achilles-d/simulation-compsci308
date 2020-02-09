package View;

import controller.Controller;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.scene.chart.XYChart.Data;
import javafx.scene.paint.Color;
import javafx.scene.layout.GridPane;

/**
 * Class to hold the array of CellAnimators and
 * update Cell values
 *
 * @author Caleb Sanford
 */
public class GridAnimator {
  private static final String RESOURCES = "View/resources";
  public static final String DEFAULT_RESOURCE_PACKAGE = RESOURCES + ".";

  private GridPane myPane;
  private Controller myController;
  private int maxGridDimension;
  private CellAnimator[][] myCells;
  private ResourceBundle myColors;
  private int height;
  private int width;
  private Graph myGraph;
  private Map<String, Integer> myCellCounts;
  private int myX;

  /**
   * Create new instance of the GridAnimator
   *
   * @param pane This is grid that the cells will be drawn on
   * @param controller Controller that holds the simulation parameters
   * @param gridDimension max dimension for the grid to display on the screen
   */
  public GridAnimator(GridPane pane, Controller controller, int gridDimension) {
    myColors = ResourceBundle.getBundle(DEFAULT_RESOURCE_PACKAGE + "Colors");
    myPane = pane;
    myController = controller;
    maxGridDimension = gridDimension;
  }

  /**
   * Create the initial cell array when a new simulation is loaded
   */
  public void makeCellArray () {
    makeGrid();
    height = myController.getGridHeight();
    width = myController.getGridWidth();
    myCells = new CellAnimator[height][width];
    myX = 0;
    // Set all the count to zero
    myCellCounts = new HashMap<>();
    for (String s: myController.getGrid().getCellStates()){
      myCellCounts.put(s, 0);
    }

    for (int i=0; i < height; i++) {
      for (int j=0; j<width; j++) {
        String state = myController.getGrid().getCellState(i,j);
        myCellCounts.put(state, myCellCounts.get(state)+1);
        Color color = convertStateToPaint(state);
        myCells[i][j] = new CellAnimator(myPane, i, j, maxGridDimension/width, maxGridDimension/height, color);
      }
    }

    for (String s: myCellCounts.keySet()){
      myGraph.addData(s, new Data<Integer, Integer>(myX, myCellCounts.get(s)));
    }
  }

  /**
   * Loop through all of the cells and change to the new state
   */
  public void updateCells () {
    // Set all the count to zero
    myCellCounts.replaceAll((s, v) -> 0);
    myX++;

    for (int i=0; i < height; i++) {
      for (int j=0; j<width; j++) {
        String state = myController.getGrid().getCellState(i,j);
        myCellCounts.put(state, myCellCounts.get(state)+1);
        myCells[i][j].changeCellState(convertStateToPaint(state));
      }
    }

    for (String s: myCellCounts.keySet()){
      myGraph.addData(s, new Data<Integer, Integer>(myX, myCellCounts.get(s)));
    }
  }

  /**
   * Use the View.resources file to convert the String
   * states for each simulation to hex color values
   *
   * @param state String from Cell instance
   * @return Color
   */
  private Color convertStateToPaint (String state) {
    return Color.web(myColors.getString(state));
  }

  private void makeGrid(){
    myGraph = new Graph("Test", "test", 300, 300, myController.getGrid().getCellStates());
  }
}
