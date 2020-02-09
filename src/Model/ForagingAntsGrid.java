package Model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

/**
 * Facilitates the Foraging Ants simulation
 * @Author Achilles Dabrowski
 */
public class ForagingAntsGrid extends Grid{

  private ArrayList<ForagingAntsCell>[][] myAnts;
  private double[][] myCellProbVisited;

  /**
   * Create a grid to run the Foraging Ants simulation
   * @param initConfig an array of Strings corresponding to each cell's initial state.
   */
  public ForagingAntsGrid(String[][] initConfig, String edgeType, String neighborType){
    super(initConfig, edgeType, neighborType);
    myCellsCopy = myCells;
    myAnts = new ArrayList[initConfig.length][initConfig[START_INDEX].length];
    myCellProbVisited = new double[initConfig.length][initConfig[START_INDEX].length];
  }

  /**
   * Return the current state of the cell at the ith row and jth column of the simulation's grid
   * @param i the row of the desired cell in the simulation's grid
   * @param j the column of the desired cell in the simulation's grid
   * @return the String representing the current state of the cell at row i and column j of the grid. "EMPTY" = empty cell;
   * "ANT" = cell occupied by at least one ant
   */
  @Override
  public String getCellState(int i, int j){
    if(!myAnts[i][j].isEmpty()){
      return ForagingAntsCell.EMPTY.toString();
    }
    else{
      return ForagingAntsCell.ANT.toString();
    }
  }

  /**
   * Set the state of the cell at the ith row and jth column of the simulation's grid
   * @param i the row of the desired cell in the simulation's grid
   * @param j the column of the desired cell in the simulation's grid
   * @param state the String representation of the desired state of the selected cell. "EMPTY" = empty cell;
   * "ANT" = cell occupied by at least one ant
   */
  public void setCellState(int i, int j, String state){
    myAnts[i][j] = new ArrayList<>();
    if(state.equals(ForagingAntsCell.ANT)){
      myAnts[i][j].add(ForagingAntsCell.ANT);
    }
  }

  protected void updateCellState(int i, int j){
    if(!myAnts[i][j].isEmpty()){
      updateAnts(i, j);
    }
  }

  private void updateAnts(int i, int j){
    if(i == START_INDEX && j == START_INDEX){
      spawnAnt();
    }
    else if(i == myAnts.length - 1 && j == myAnts[START_INDEX].length - 1){
      sendAntsToNest();
    }
    myCellProbVisited[i][j] += .1;
    Iterator<ForagingAntsCell> antItr = myAnts[i][j].iterator();
    while(antItr.hasNext()){
      ForagingAntsCell ant = antItr.next();
      IndexPair newIndices;
      if(ant.isLeavingNest){
        newIndices = moveAntTowardFood(i, j);
      }
      else{
        newIndices = moveAntTowardNest(i, j);
      }
      myAnts[newIndices.getRow()][newIndices.getCol()].add(ant);
      antItr.remove();
    }
  }

  private void spawnAnt(){
    myAnts[START_INDEX][START_INDEX].add(ForagingAntsCell.ANT);
  }

  private void sendAntsToNest(){
    for(ForagingAntsCell ant : myAnts[myAnts.length - 1][myAnts[START_INDEX].length - 1]){
      ant.isLeavingNest = true;
    }
  }

  private IndexPair moveAntTowardFood(int i, int j) {
    return selectNextCell(new IndexPair(i - 1, j), new IndexPair(i, j - 1));
  }

  private IndexPair moveAntTowardNest(int i, int j) {
    return selectNextCell(new IndexPair(i + 1, j), new IndexPair(i, j + 1));
  }

  private IndexPair selectNextCell(IndexPair choice1, IndexPair choice2){
    ArrayList<Integer> probList = buildProbabilityList(0, choice1);
    probList.addAll(buildProbabilityList(1, choice2));
    int selectIndex = new Random(probList.size()).nextInt();
    if(probList.get(selectIndex) != 0 || ( !inBounds(choice2.getRow(), choice2.getCol()) )){
      return choice1;
    }
    else{
      return choice2;
    }
  }

  private ArrayList<Integer> buildProbabilityList(int a, IndexPair possibleIndex){
    ArrayList<Integer> probList = new ArrayList<>();
    int probListLength = (int) myCellProbVisited[possibleIndex.getRow()][possibleIndex.getCol()] * 10;
    for(int i = 0; i < probListLength; i++){
      probList.add(a);
    }
    return probList;
  }
}
