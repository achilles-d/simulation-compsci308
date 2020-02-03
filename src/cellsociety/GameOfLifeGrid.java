package cellsociety;

public class GameOfLifeGrid extends Grid{

  public static final int maxAliveNeighbors = 3;
  public static final int minAliveNeighbors = 2;

  public GameOfLifeGrid(String[][] configFileDir){
    super(configFileDir);
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
    int aliveCount = 0;
    for(int a = 0; a < maxCellNeighborCount; a++){
      if(inBounds(i + deltaX[a],j + deltaY[a]) && (gridCopy[i + deltaX[a]][j + deltaY[a]] == GameOfLifeCell.ALIVE)){
        aliveCount++;
      }
    }
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
