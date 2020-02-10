package Model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import javax.xml.stream.events.StartDocument;

/**
 * Facilitates the Foraging Ants simulation
 * @Author Achilles Dabrowski
 */
public class ForagingAntsGrid extends Grid{

  private HashMap<IndexPair, ArrayList<ForagingAntsCell>> myAnts;
  private double[][] myCellProbVisited;

  /**
   * Create a grid to run the Foraging Ants simulation
   * @param initConfig an array of Strings corresponding to each cell's initial state
   * @param edgeType the type of grid edges to be used in the simulation. "FINITE" = finite edges
   *                 (edges across from one another are not connected). "TOROIDAL" = toroidal edges
   *                 (edges across from one another are connected).
   * @param neighborType the type of cell neighborhood to be used in the simulation. "SQUARE_DIAGONAL" =
   *                     square neighborhood with eight neighbors, including diagonals, at most.
   *                     "SQUARE_NO_DIAGONAL" = square neighborhood with four neighbors at most and no diagonals.
   *                     "HEXAGONAL" = hexagonal neighborhood with six neighbors, including diagonals, at most.
   */
  public ForagingAntsGrid(String[][] initConfig, String edgeType, String neighborType){
    super(initConfig, edgeType, neighborType);
    //Initialize to keep track of grid size despite not being used as the simulation grid
    myCells = new Enum[initConfig.length][initConfig[START_INDEX].length];
    myCellsCopy = myCells;
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
    if(!myAnts.containsKey(new IndexPair(i, j))){
      return ForagingAntsCell.EMPTY.toString();
    }
    else{
      return ForagingAntsCell.ANT.toString();
    }
  }

  @Override
  public List<String> getCellStates() {
    ForagingAntsCell cell = ForagingAntsCell.ANT;
    Object[] states = cell.getClass().getEnumConstants();
    List<String> strings = new ArrayList<>();
    for (Object o: states) {
      strings.add(o.toString());
    }
    return strings;
  }

  /**
   * Set the state of the cell at the ith row and jth column of the simulation's grid
   * @param i the row of the desired cell in the simulation's grid
   * @param j the column of the desired cell in the simulation's grid
   * @param state the String representation of the desired state of the selected cell. "EMPTY" = empty cell;
   * "ANT" = cell occupied by at least one ant
   */
  public void setCellState(int i, int j, String state){
    IndexPair antIndices = new IndexPair(i, j);
    if(state.equals(ForagingAntsCell.ANT.toString())){
      if(!myAnts.containsKey(antIndices)){
        myAnts.put(antIndices, new ArrayList<>());
        myAnts.get(antIndices).add(ForagingAntsCell.ANT);
      }
    }
    else if(state.equals(ForagingAntsCell.ANT_RETURNING.toString())){
      sendAntsToNest(i, j);
    }
    else if(myAnts.containsKey(antIndices)){
      myAnts.remove(antIndices);
    }
  }

  /**
   * Move the grid one step forward in the simulation according to the simulation's rules
   */
  @Override
  public void update(){
    for(int i = 0; i < myCells.length; i++){
      for(int j = 0; j < myCells[START_INDEX].length; j++){
        updateCellState(i, j);
      }
    }
  }

  @Override
  protected void initializeCells(String[][] initConfig){
    myAnts = new HashMap<>();
    myCellProbVisited = new double[initConfig.length][initConfig[START_INDEX].length];
    for(int i = 0; i < initConfig.length; i++){
      for(int j = 0; j < initConfig[START_INDEX].length; j++){
        setCellState(i, j, initConfig[i][j]);
        myCellProbVisited[i][j] = .1;
      }
    }
  }

  protected void updateCellState(int i, int j){
    if(myAnts.containsKey(new IndexPair(i, j))){
      updateAnts(i, j);
    }
  }

  private void updateAnts(int i, int j){
    if(i == START_INDEX && j == START_INDEX){
      spawnAnt();
    }
    if(i == myCells.length - 1 && j == myCells[START_INDEX].length - 1){
      sendAntsToNest(myCells.length - 1, myCells[START_INDEX].length - 1);
    }
    myCellProbVisited[i][j] += 1.0;
    iterateThroughAntList(i, j);

  }

  private void iterateThroughAntList(int i, int j) {
    IndexPair antIndices = new IndexPair(i,j);
    Iterator<ForagingAntsCell> antsItr = myAnts.get(antIndices).iterator();
    while(antsItr.hasNext()){
      ForagingAntsCell ant = antsItr.next();
      IndexPair newIndices;
      if(ant == ForagingAntsCell.ANT_RETURNING){
        newIndices = moveAntTowardNest(i, j);
      }
      else{
        newIndices = moveAntTowardFood(i, j);
      }
      ForagingAntsCell temp = ForagingAntsCell.valueOf(ant.toString());
      myAnts.putIfAbsent(newIndices, new ArrayList<>());
      myAnts.get(newIndices).add(temp);
      antsItr.remove();
    }

  }

  private void spawnAnt(){
    IndexPair nestIndices = new IndexPair(myCells.length, myCells[START_INDEX].length);
    if(!myAnts.containsKey((nestIndices))){
      myAnts.put(nestIndices, new ArrayList<>());
    }
    myAnts.get(nestIndices).add(ForagingAntsCell.ANT);
  }

  private void sendAntsToNest(int i, int j) {
    IndexPair antIndices = new IndexPair(i, j);
    if (myAnts.containsKey(antIndices)) {
      for (int a = 0; a < myAnts.get(antIndices).size(); a++) {
        myAnts.get(antIndices).set(a, ForagingAntsCell.ANT_RETURNING);
      }
    }
  }

  private IndexPair moveAntTowardFood(int i, int j) {
    return selectNextCell(new IndexPair(i - 1, j), new IndexPair(i, j - 1));
  }

  private IndexPair moveAntTowardNest(int i, int j) {
    return selectNextCell(new IndexPair(i + 1, j), new IndexPair(i, j + 1));
  }

  private IndexPair selectNextCell(IndexPair choice1, IndexPair choice2){
    if(!inBounds(choice1.getRow(), choice1.getCol())){
      return choice2;
    }
    else if(!inBounds(choice2.getRow(), choice2.getCol())){
      return choice1;
    }
    ArrayList<Integer> probList = buildProbabilityList(0, choice1);
    probList.addAll(buildProbabilityList(1, choice2));
    int selectIndex = new Random().nextInt(probList.size());
    if(probList.get(selectIndex) != 0 || ( !inBounds(choice2.getRow(), choice2.getCol()) )){
      return choice1;
    }
    else{
      return choice2;
    }
  }

  private ArrayList<Integer> buildProbabilityList(int id, IndexPair possibleIndex){
    ArrayList<Integer> probList = new ArrayList<>();
    int probListLength = (int) (myCellProbVisited[possibleIndex.getRow()][possibleIndex.getCol()] * 10);
    for(int i = 0; i < probListLength; i++){
      probList.add(id);
    }
    return probList;
  }
}
