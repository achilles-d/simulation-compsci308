package cellsociety;

/**
 * Facilitates a simulation of Spreading of Fire
 * @author Achilles Dabrowski
 */
public class FireGrid extends Grid {

    private double myProbCatch;
    private double myProbGrow;

    /**
     * Create a grid to run the Spreading of Fire simulation
     * @param initConfig an array of Strings corresponding to each cell's initial state. "TREE" = cell with tree.
     *                   "EMPTY" = empty cell. "BURNING" = cell with burning tree
     * @param probCatch probCatch, the probability that a cell with a tree neighboring a burning cell will also catch
     *               on fire in the next step of the simulation
     * @param probGrow probGrow, the probability that a tree will grow in an empty cell in the next step in the simulation
     */
    public FireGrid(String[][] initConfig, double probCatch, double probGrow){
        super(initConfig);
        myProbCatch = probCatch;
        myProbGrow = probGrow;
    }

    public FireCell setCellState(String state){
        return FireCell.valueOf(state);
    }

    protected void updateCellState(int i, int j, Enum[][] gridCopy){
        if(gridCopy[i][j] == FireCell.TREE){
            updateTreeCells(i, j, gridCopy);
        }
        else if(gridCopy[i][j] == FireCell.BURNING){
            myCells[i][j] = FireCell.EMPTY;
        }
        else if(gridCopy[i][j] == FireCell.NEW_TREE){
            myCells[i][j] = FireCell.TREE;
        }
        else{
            updateEmptyCells(i, j);
        }
    }

    private void updateTreeCells(int i, int j, Enum[][] gridCopy) {
        if(! findNeighborIndices(i, j, gridCopy , FireCell.BURNING, ALT_INDEX_DELTA).isEmpty()){
            if(Math.random() < myProbCatch) {
                myCells[i][j] = FireCell.BURNING;
            }
        }
    }

    private void updateEmptyCells(int i, int j) {
        if(Math.random() < myProbGrow){
            myCells[i][j] = FireCell.NEW_TREE;
        }
    }
}
