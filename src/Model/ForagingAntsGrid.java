package Model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

public class ForagingAntsGrid extends Grid{

  private ArrayList<ForagingAntsCell>[][] myAnts;
  private double[][] myCellProbVisited;

  public ForagingAntsGrid(String[][] initConfig){
    super(null, ALT_SQUARE_INDEX_DELTA);
    myCellsCopy = myCells;
    myAnts = new ArrayList[initConfig.length][initConfig[START_INDEX].length];
    myCellProbVisited = new double[initConfig.length][initConfig[START_INDEX].length];
  }

  @Override
  public String getCellState(int i, int j){
    if(!myAnts[i][j].isEmpty()){
      return ForagingAntsCell.EMPTY.toString();
    }
    else{
      return ForagingAntsCell.ANT.toString();
    }
  }

  public void setCellState(int i, int j, String state){
    myAnts[i][j] = new ArrayList<ForagingAntsCell>();
    myCellProbVisited[i][j] = .1;
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
