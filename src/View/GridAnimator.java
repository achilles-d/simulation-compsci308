package View;

import cellsociety.Controller;
import cellsociety.Grid;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.Node;
import javafx.scene.layout.GridPane;

public class GridAnimator {
  private GridPane myPane;
  private List<List<CellAnimator>> myCells;

  public GridAnimator(GridPane pane, Controller controller) {
    myPane = pane;

    //myCells = controller.getInitialStates();
  }

  private void makeCellArray (Grid grid) {

  }

  public void updateCells () {

  }
}
