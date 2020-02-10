package Model;

/**
 * Facilitates a simulation of Spreading of Fire
 *
 * @author Achilles Dabrowski
 */
public class FireGrid extends Grid {

    private double myProbCatch;
    private double myProbGrow;

    /**
     * Create a grid to run the Spreading of Fire simulation
     *
     * @param initConfig   an array of Strings corresponding to each cell's initial state. "TREE" =
     *                     cell with tree. "EMPTY" = empty cell. "BURNING" = cell with burning tree
     * @param edgeType     the type of grid edges to be used in the simulation. "FINITE" = finite
     *                     edges (edges across from one another are not connected). "TOROIDAL" =
     *                     toroidal edges (edges across from one another are connected).
     * @param neighborType the type of cell neighborhood to be used in the simulation.
     *                     "SQUARE_DIAGONAL" = square neighborhood with eight neighbors, including
     *                     diagonals, at most. "SQUARE_NO_DIAGONAL" = square neighborhood with four
     *                     neighbors at most and no diagonals. "HEXAGONAL" = hexagonal neighborhood
     *                     with six neighbors, including diagonals, at most.
     * @param probCatch    probCatch, the probability that a cell with a tree neighboring a burning
     *                     cell will also catch on fire in the next step of the simulation
     * @param probGrow     probGrow, the probability that a tree will grow in an empty cell in the
     *                     next step in the simulation
     */
    public FireGrid(String[][] initConfig, String edgeType, String neighborType, double probCatch,
                    double probGrow) {
        super(initConfig, edgeType, neighborType);
        myProbCatch = probCatch;
        myProbGrow = probGrow;
    }

    /**
     * Set the state of the cell in the ith row and jth column of the simulation grid
     *
     * @param i     the row of the desired cell in the grid
     * @param j     the column of the desired cell in the grid
     * @param state the String representation of the desired state of the cell. "EMPTY" = empty cell. "TREE" = cell with tree.
     *              "BURNING" = cell with burning tree. "NEW_TREE" = tree that grew from previously empty cell in the last turn
     */
    public void setCellState(int i, int j, String state) {
        myCells[i][j] = FireCell.valueOf(state);
    }

    protected void updateCellState(int i, int j) {
        if (myCellsCopy[i][j] == FireCell.TREE) {
            updateTreeCells(i, j);
        } else if (myCellsCopy[i][j] == FireCell.BURNING) {
            myCells[i][j] = FireCell.EMPTY;
        } else if (myCellsCopy[i][j] == FireCell.NEW_TREE) {
            myCells[i][j] = FireCell.TREE;
        } else {
            updateEmptyCells(i, j);
        }
    }

    private void updateTreeCells(int i, int j) {
        if ((!findNeighborIndices(i, j, FireCell.BURNING).isEmpty()) && (Math.random() < myProbCatch)) {
            myCells[i][j] = FireCell.BURNING;
        }
    }

    private void updateEmptyCells(int i, int j) {
        if (Math.random() < myProbGrow) {
            myCells[i][j] = FireCell.NEW_TREE;
        }
    }
}
