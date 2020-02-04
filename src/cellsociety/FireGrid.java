package cellsociety;

/**
 * Facilitates a simulation of Spreading of Fire
 * @author Achilles Dabrowski
 */
public class FireGrid extends Grid {

    private double probCatch;
    private double probGrow;

    /**
     * Create a grid to run the Spreading of Fire simulation
     * @param initConfig an array of Strings corresponding to each cell's initial state. "TREE" = cell with tree.
     *                   "EMPTY" = empty cell. "BURNING" = cell with burning tree
     * @param pCatch probCatch, the probability that a cell with a tree neighboring a burning cell will also catch
     *               on fire in the next step of the simulation
     * @param pGrow probGrow, the probability that a tree will grow in an empty cell in the next step in the simulation
     */
    public FireGrid(String[][] initConfig, double pCatch, double pGrow){
        super(initConfig);
        probCatch = pCatch;
        probGrow = pGrow;
    }

    public FireCell setCellState(String state){

        if(state.equals(FireCell.EMPTY.toString())){
            return FireCell.EMPTY;
        }
        if(state.equals(FireCell.TREE.toString())){
            return FireCell.TREE;
        }
        else{
            return FireCell.BURNING;
        }
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
        if(! altFindNeighborIndices(i, j, gridCopy , FireCell.BURNING).isEmpty()){
            if(Math.random() < probCatch) {
                myCells[i][j] = FireCell.BURNING;
            }
        }
    }

    private void updateEmptyCells(int i, int j) {
        if(Math.random() < probGrow){
            myCells[i][j] = FireCell.NEW_TREE;
        }
    }
}
