package cellsociety;

public class PercolationGrid extends Grid {

    /**
     * Create a grid to run the Percolation simulation
     * @param initConfig an array of Strings corresponding to each cell's initial state.
     *                   "FULL" = filled cell. "EMPTY" = empty cell. "PERCOLATED" = percolated cell
     */
    public PercolationGrid(String[][] initConfig){
        super(initConfig);
    }

    protected PercolationCell setCellState(String state){
        if(state.equals(PercolationCell.EMPTY.toString())){
            return PercolationCell.EMPTY;
        }
        else if(state.equals(PercolationCell.FULL.toString())){
            return PercolationCell.FULL;
        }
        else{
            return PercolationCell.PERCOLATED;
        }
    }

    protected void updateCellState(int i, int j, Enum[][] gridCopy){
        if(gridCopy[i][j] == PercolationCell.EMPTY){
            for(int a = 0; a < MAX_CELL_NEIGHBOR_COUNT; a++){
                if(inBounds(i + DELTA_X[a], j + DELTA_Y[a]) &&
                        (gridCopy[i + DELTA_X[a]][j + DELTA_Y[a]] == PercolationCell.PERCOLATED) ){
                    myCells[i][j] = PercolationCell.PERCOLATED;
                    return;
                }
            }
        }
    }
}
