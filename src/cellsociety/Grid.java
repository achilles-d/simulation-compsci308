package cellsociety;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Provides a skeleton for the operation of each of the assigned cellular automata (CA) simulations
 * @author Achilles Dabrowski
 */
public abstract class Grid {
    protected static final int[] DELTA_X = {1, -1, 0, 0, 1, -1, 1, -1};
    protected static final int[] DELTA_Y = {0, 0, 1, -1, -1, 1, 1, -1};
    protected static final int[] ALT_DELTA_X = {1, -1, 0, 0};
    protected static final int[] ALT_DELTA_Y = {0, 0, 1, -1};
    protected static final int MAX_CELL_NEIGHBOR_COUNT = 8;
    protected static final int ALT_CELL_NEIGHBOR_COUNT = 4;
    protected Enum[][] myCells;

    /**
     * Create a grid that runs the cellular automata (CA) simulation
     * @param initConfig an array of Strings corresponding to each cell's initial state
     */
    public Grid(String[][] initConfig){
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
        Object[] states = myCells[0][0].getClass().getEnumConstants();
        List<String> strings = new ArrayList<>();
        for (Object o: states) {
            strings.add(o.toString());
        }
        return strings;
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
        for(int a = 0; a < MAX_CELL_NEIGHBOR_COUNT; a++){
            if(inBounds(i + DELTA_X[a], j + DELTA_Y[a]) &&
                    gridCopy[i + DELTA_X[a]][j + DELTA_Y[a]] == targetCell){
                cellIndices.add(new IndexPair(i + DELTA_X[a], j + DELTA_Y[a]));
            }
        }
        return cellIndices;
    }

    protected ArrayList<IndexPair> altFindNeighborIndices(int i, int j, Enum[][] gridCopy, Enum targetCell) {
        ArrayList<IndexPair> cellIndices = new ArrayList<IndexPair>();
        for(int a = 0; a < ALT_CELL_NEIGHBOR_COUNT; a++){
            if(inBounds(i + ALT_DELTA_X[a], j + ALT_DELTA_Y[a]) &&
                    gridCopy[i + ALT_DELTA_X[a]][j + ALT_DELTA_Y[a]] == targetCell){
                cellIndices.add(new IndexPair(i + ALT_DELTA_X[a], j + ALT_DELTA_Y[a]));
            }
        }
        return cellIndices;
    }

    abstract protected Enum setCellState(String state);

    abstract protected void updateCellState(int i, int j, Enum[][] gridCopy);

}
