package cellsociety;

public class GameOfLifeGrid extends Grid{

  public static final int maxAliveNeighbors = 3;
  public static final int minAliveNeighbors = 2;

  /**
   * Create a grid to run the Game of Life simulation
   * @param initConfig an array of Strings corresponding to each cell's initial state. "ALIVE" = cell with living person.
   *                   "DEAD" = cell with dead person
   */
  public GameOfLifeGrid(String[][] initConfig){
    super(initConfig);
  }

  protected GameOfLifeCell setCellState(String state){
    if(state.equals(GameOfLifeCell.ALIVE.toString())){
      return GameOfLifeCell.ALIVE;
    }
    else{
      return GameOfLifeCell.DEAD;
    }
  }

  protected void updateCellState(int i, int j, Enum[][] gridCopy){
    int aliveCount = findNeighborIndices(i, j, gridCopy, GameOfLifeCell.ALIVE).size();
    if(gridCopy[i][j] == GameOfLifeCell.ALIVE){
      if((aliveCount > maxAliveNeighbors) || (aliveCount < minAliveNeighbors)){
        myCells[i][j] = GameOfLifeCell.DEAD;
      }
    }
    else{
      if(aliveCount == maxAliveNeighbors){
        myCells[i][j] = GameOfLifeCell.ALIVE;
      }
    }
  }

}
