package Model;

/**
 * Facilitates a simulation of the Game of Life
 *
 * @author Achilles Dabrowski
 */
public class GameOfLifeGrid extends Grid {

    public static final int maxAliveNeighbors = 3;
    public static final int minAliveNeighbors = 2;

    /**
     * Create a grid to run the Game of Life simulation
     *
     * @param initConfig   an array of Strings corresponding to each cell's initial state. "ALIVE" =
     *                     cell with living person. "DEAD" = cell with dead person
     * @param edgeType     the type of grid edges to be used in the simulation. "FINITE" = finite
     *                     edges (edges across from one another are not connected). "TOROIDAL" =
     *                     toroidal edges (edges across from one another are connected).
     * @param neighborType the type of cell neighborhood to be used in the simulation.
     *                     "SQUARE_DIAGONAL" = square neighborhood with eight neighbors, including
     *                     diagonals, at most. "SQUARE_NO_DIAGONAL" = square neighborhood with four
     *                     neighbors at most and no diagonals. "HEXAGONAL" = hexagonal neighborhood
     *                     with six neighbors, including diagonals, at most.
     */
    public GameOfLifeGrid(String[][] initConfig, String edgeType, String neighborType) {
        super(initConfig, edgeType, neighborType);
        myCellsCopy = copyCells();
    }

    /**
     * Set the state of the cell in the ith row and jth column of the simulation grid
     *
     * @param i     the row of the desired cell in the grid
     * @param j     the column of the desired cell in the grid
     * @param state the String representation of the desired state of the cell. "ALIVE" = cell with living person.
     *              "DEAD" = cell with dead person
     */
    public void setCellState(int i, int j, String state) {
        myCells[i][j] = GameOfLifeCell.valueOf(state);
    }

    protected void updateCellState(int i, int j) {
        int aliveNeighborCount = findNeighborIndices(i, j, GameOfLifeCell.ALIVE).size();
        if (myCellsCopy[i][j] == GameOfLifeCell.ALIVE && ((aliveNeighborCount > maxAliveNeighbors) || (
                aliveNeighborCount < minAliveNeighbors))) {
            myCells[i][j] = GameOfLifeCell.DEAD;
        } else if (aliveNeighborCount == maxAliveNeighbors) {
            myCells[i][j] = GameOfLifeCell.ALIVE;
        }
    }

}
