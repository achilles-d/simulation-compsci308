package cellsociety;

public class GameOfLifeGrid extends Grid{

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

  protected void update(){

  }

}
