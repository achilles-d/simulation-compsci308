package cellsociety;

import java.util.ArrayList;

/**
 * Facilitates a simulation of Percolation
 * @author Achilles Dabrowski
 */
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
        return PercolationCell.valueOf(state);
    }

    protected void updateCellState(int i, int j, Enum[][] gridCopy){
        if(gridCopy[i][j] == PercolationCell.EMPTY) {
            ArrayList<IndexPair> percolatedCellIndices = findNeighborIndices(i, j, gridCopy, PercolationCell.PERCOLATED, STD_INDEX_DELTA);
            if (!percolatedCellIndices.isEmpty()) {
                myCells[i][j] = PercolationCell.PERCOLATED;
            }
        }
    }
}
