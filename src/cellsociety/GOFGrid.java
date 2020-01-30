package cellsociety;

public class GOFGrid extends Grid{

  public GOFGrid(String[][] configFileDir){
    super(configFileDir);
  }

  protected void initialize(String[][] initConfig){
    myCells = new GOFCell[initConfig.length][initConfig[0].length];
    for(int i = 0; i < initConfig.length; i++){
      for(int j = 0; j < initConfig[0].length; j++){
        if(initConfig[i][j].equals(GOFCell.ALIVE.toString())){
          myCells[i][j] = GOFCell.ALIVE;
        }
        else{
          myCells[i][j] = GOFCell.DEAD;
        }
      }
    }
  }

}
