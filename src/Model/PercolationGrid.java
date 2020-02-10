package Model;

import java.awt.*;
import java.util.ArrayList;

/**
 * Facilitates a simulation of Percolation
 *
 * @author Achilles Dabrowski
 */
public class PercolationGrid extends Grid {

    /**
     * Create a grid to run the Percolation simulation
     *
     * @param initConfig   an array of Strings corresponding to each cell's initial state. "FULL" =
     *                     filled cell. "EMPTY" = empty cell. "PERCOLATED" = percolated cell
     * @param edgeType     the type of grid edges to be used in the simulation. "FINITE" = finite
     *                     edges (edges across from one another are not connected). "TOROIDAL" =
     *                     toroidal edges (edges across from one another are connected).
     * @param neighborType the type of cell neighborhood to be used in the simulation.
     *                     "SQUARE_DIAGONAL" = square neighborhood with eight neighbors, including
     *                     diagonals, at most. "SQUARE_NO_DIAGONAL" = square neighborhood with four
     *                     neighbors at most and no diagonals. "HEXAGONAL" = hexagonal neighborhood
     *                     with six neighbors, including diagonals, at most.
     */
    public PercolationGrid(String[][] initConfig, String edgeType, String neighborType) {
        super(initConfig, edgeType, neighborType);
        myCellsCopy = copyCells();
    }

    /**
     * Set the state of the cell in the ith row and jth column of the simulation grid
     *
     * @param i     the row of the desired cell in the grid
     * @param j     the column of the desired cell in the grid
     * @param state the String representation of the desired state of the cell. "FULL" = filled cell. "EMPTY" = empty cell.
     *              "PERCOLATED" = percolated cell
     */
    public void setCellState(int i, int j, String state) {
        myCells[i][j] = PercolationCell.valueOf(state);
    }

    protected void updateCellState(int i, int j) {
        ArrayList<Point> percolatedCellIndices = findNeighborIndices(i, j,
                PercolationCell.PERCOLATED);
        if (myCellsCopy[i][j] == PercolationCell.EMPTY && (!percolatedCellIndices.isEmpty())) {
            myCells[i][j] = PercolationCell.PERCOLATED;
        }
    }
}
