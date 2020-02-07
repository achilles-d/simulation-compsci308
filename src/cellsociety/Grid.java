package cellsociety;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Provides a skeleton for the operation of each of the assigned cellular automata (CA) simulations
 * @author Achilles Dabrowski
 */
public abstract class Grid {
    protected static final int[][] SQUARE_INDEX_DELTA = {{1, -1, 0, 0, 1, -1, 1, -1}, {0, 0, 1, -1, -1, 1, 1, -1}};
    protected static final int[][] ALT_SQUARE_INDEX_DELTA = {{1, -1, 0, 0}, {0, 0, 1, -1}};
    protected static final int[][] HEX_INDEX_DELTA = {{-1, 0, 1, 0, -1, -1}, {-1, -1, 0, 1, 1, 0}};
    protected static final int START_INDEX = 0;
    protected Enum[][] myCells;
    protected int[][] myIndexDelta;

    // more than two packages,
    // protected grid DS
    /**
     * Create a grid that runs the cellular automata (CA) simulation
     * @param initConfig an array of Strings corresponding to each cell's initial state
     */
    public Grid(String[][] initConfig, int[][] indexDelta){
        myIndexDelta = indexDelta;
        initialize(initConfig);
    }

    /**
     * Return the current state of the cell at the ith row and jth column of the simulation's grid
     * @param i the row of the desired cell in the simulation's grid
     * @param j the column of the desired cell in the simulation's grid
     * @return the String representing the current state of the cell at row i and column j of the grid
     */
    public String getCellState(int i, int j){
        return myCells[i][j].toString();
    }

    public List<String> getCellStates() {
        return Arrays.asList(Arrays.toString(myCells[0][0].getClass().getEnumConstants()));
    }

    /**
     * Move the grid one step forward in the simulation according to the simulation's rules
     */
    public void update(){
        Enum[][] temp = copyCells();
        for(int i = 0; i < temp.length; i++){
            for(int j = 0; j < temp[0].length; j++){
                updateCellState(i, j, temp);
            }
        }
    }

    /**
     * Make the simulation grid use a square layout of cells for finding cell neighbors
     */
    public void makeGridSquare(){
        myIndexDelta = SQUARE_INDEX_DELTA;
    }

    /**
     * Make the simulation grid use a hexagonal layout of cells for finding cell neighbors
     */
    public void makeGridHexagonal(){
        myIndexDelta = HEX_INDEX_DELTA;
    }

    protected void initialize(String[][] initConfig){
        myCells = new Enum[initConfig.length][initConfig[0].length];
        for(int i = 0; i < initConfig.length; i++){
            for(int j = 0; j < initConfig[0].length; j++){
                myCells[i][j] = setCellState(initConfig[i][j]);
            }
        }
    }

    protected boolean inBounds(int i, int j){
        return (i >= 0 && i < myCells.length && j >= 0 && j< myCells[0].length);
    }

    protected Enum[][] copyCells(){
        Enum[][] copy = new Enum[myCells.length][myCells[0].length];
        for(int i = 0; i < myCells.length; i++){
            for(int j = 0; j < myCells[0].length; j++){
                copy[i][j] = myCells[i][j];
            }
        }
        return copy;
    }

    protected ArrayList<IndexPair> findNeighborIndices(int i, int j, Enum[][] gridCopy, Enum targetCell) {
        ArrayList<IndexPair> cellIndices = new ArrayList<IndexPair>();
        for(int a = 0; a < myIndexDelta[START_INDEX].length; a++){
            int newRow = i + myIndexDelta[START_INDEX][a];
            int newCol = j + myIndexDelta[START_INDEX + 1][a];

            if(inBounds(newRow, newCol) && gridCopy[newRow][newCol] == targetCell){
                cellIndices.add(new IndexPair(newRow, newCol));
            }
        }
        return cellIndices;
    }

    abstract protected Enum setCellState(String state);

    abstract protected void updateCellState(int i, int j, Enum[][] gridCopy);

}
