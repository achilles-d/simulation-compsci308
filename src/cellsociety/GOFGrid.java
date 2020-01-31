package cellsociety;

public class GOFGrid extends Grid{

  public GOFGrid(String[][] configFileDir){
    super(configFileDir);
  }

  protected GOFCell setCellState(String state){
    if(state.equals(GOFCell.ALIVE.toString())){
      return GOFCell.ALIVE;
    }
    else{
      return GOFCell.DEAD;
    }
  }

  protected void update(){
    
  }

}
