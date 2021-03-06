package Model;

import static java.util.Map.entry;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Provides a skeleton for the operation of each of the assigned cellular automata (CA) simulations
 *
 * @author Achilles Dabrowski
 */
public abstract class Grid {

    public static final int[][] SQUARE_DIAGONAL_TYPE = {{1, -1, 0, 0, 1, -1, 1, -1},
            {0, 0, 1, -1, -1, 1, 1, -1}};
    public static final int[][] SQUARE_NO_DIAGONAL_TYPE = {{1, -1, 0, 0}, {0, 0, 1, -1}};
    public static final int[][] HEXAGONAL_TYPE = {{-1, 0, 1, 0, -1, -1}, {-1, -1, 0, 1, 1, 0}};
    public static final int START_INDEX = 0;
    public static final int NEXT_INDEX = 1;
    public static final String FINITE_GRID_EDGE_TYPE = "FINITE";
    public static final String TOROIDAL_GRID_EDGE_TYPE = "TOROIDAL";
    public static final Map<String, int[][]> NEIGHBOR_TYPES = Map.ofEntries(entry("SQUARE_DIAGONAL", SQUARE_DIAGONAL_TYPE),
            entry("SQUARE_NO_DIAGONAL", SQUARE_NO_DIAGONAL_TYPE), entry("HEXAGONAL", HEXAGONAL_TYPE));

    protected Enum[][] myCells;
    protected Enum[][] myCellsCopy;
    protected int[][] myNeighborType;
    protected String myGridEdgeType;

    /**
     * Create a grid that runs the cellular automata (CA) simulation
     *
     * @param initConfig   an array of Strings corresponding to each cell's initial state
     * @param edgeType     the type of grid edges to be used in the simulation. "FINITE" = finite
     *                     edges (edges across from one another are not connected). "TOROIDAL" =
     *                     toroidal edges (edges across from one another are connected).
     * @param neighborType the type of cell neighborhood to be used in the simulation.
     *                     "SQUARE_DIAGONAL" = square neighborhood with eight neighbors, including
     *                     diagonals, at most. "SQUARE_NO_DIAGONAL" = square neighborhood with four
     *                     neighbors at most and no diagonals. "HEXAGONAL" = hexagonal neighborhood
     *                     with six neighbors, including diagonals, at most.
     */
    public Grid(String[][] initConfig, String edgeType, String neighborType) {
        initializeCells(initConfig);
        myGridEdgeType = edgeType;
        myNeighborType = NEIGHBOR_TYPES.get(neighborType);
    }

    /**
     * Return the current state of the cell at the ith row and jth column of the simulation's grid
     *
     * @param i the row of the desired cell in the simulation's grid
     * @param j the column of the desired cell in the simulation's grid
     * @return the String representing the current state of the cell at row i and column j of the grid
     */
    public String getCellState(int i, int j) {
        return myCells[i][j].toString();
    }

    public List<String> getCellStates() {
        Object[] states = myCells[START_INDEX][START_INDEX].getClass().getEnumConstants();
        List<String> strings = new ArrayList<>();
        for (Object o : states) {
            strings.add(o.toString());
        }
        return strings;
    }

    /**
     * Move the grid one step forward in the simulation according to the simulation's rules
     */
    public void update() {
        if (myCells != myCellsCopy) {
            myCellsCopy = copyCells();
        }
        for (int i = START_INDEX; i < myCellsCopy.length; i++) {
            for (int j = START_INDEX; j < myCellsCopy[START_INDEX].length; j++) {
                updateCellState(i, j);
            }
        }
    }

    /**
     * Make the simulation grid's cell neighborhoods
     */
    public void makeGridSquareDiagonal() {
        myNeighborType = SQUARE_DIAGONAL_TYPE;
    }

    /**
     * Make the simulation grid use a hexagonal layout of cells for finding cell neighbors
     */
    public void makeGridHexagonal() {
        myNeighborType = HEXAGONAL_TYPE;
    }

    /**
     * Make the simulation's edges finite. Cells at one edge will not be connected to cells on the
     * edge across from it. Set by default when an instance of Grid is created
     */
    public void makeEdgesFinite() {
        myGridEdgeType = FINITE_GRID_EDGE_TYPE;
    }

    /**
     * Make the simulation's edges toroidal. Cells at one edge will be connected to cells on the edge
     * across from it
     */
    public void makeEdgesToroidal() {
        myGridEdgeType = TOROIDAL_GRID_EDGE_TYPE;
    }

    protected void initializeCells(String[][] initConfig) {
        myCells = new Enum[initConfig.length][initConfig[START_INDEX].length];
        for (int i = START_INDEX; i < initConfig.length; i++) {
            for (int j = START_INDEX; j < initConfig[START_INDEX].length; j++) {
                setCellState(i, j, initConfig[i][j]);
            }
        }
    }

    protected boolean inBounds(int i, int j) {
        return (i >= START_INDEX && i < myCells.length && j >= START_INDEX && j < myCells[START_INDEX].length);
    }

    protected boolean inBounds(Point indices) {
        return indices.getX() >= START_INDEX && indices.getX() < myCells.length && indices.getY() >= START_INDEX && indices.getY() < myCells[START_INDEX].length;
    }

    protected Enum[][] copyCells() {
        Enum[][] copy = new Enum[myCells.length][myCells[0].length];
        for (int i = 0; i < myCells.length; i++) {
            for (int j = 0; j < myCells[0].length; j++) {
                copy[i][j] = myCells[i][j];
            }
        }
        return copy;
    }

    protected ArrayList<Point> findNeighborIndices(int i, int j, Enum targetCell) {
        ArrayList<Point> cellIndices = new ArrayList<>();
        for (int a = START_INDEX; a < myNeighborType[START_INDEX].length; a++) {
            int newRow = i + myNeighborType[START_INDEX][a];
            int newCol = j + myNeighborType[START_INDEX + NEXT_INDEX][a];
            if (neighborMatchesTarget(newRow, newCol, targetCell)) {
                cellIndices.add(connectEdgeIndices(newRow, newCol));
            }
        }
        return cellIndices;
    }

    protected boolean neighborMatchesTarget(int newRow, int newCol, Enum targetCell) {
        if (!myGridEdgeType.equals(TOROIDAL_GRID_EDGE_TYPE)) {
            return inBounds(newRow, newCol) && (myCellsCopy[newRow][newCol] == targetCell);
        } else {
            Point edgeIndices = connectEdgeIndices(newRow, newCol);
            return inBounds(edgeIndices) &&
                    (myCellsCopy[(int) edgeIndices.getX()][(int) edgeIndices.getY()] == targetCell);
        }
    }

    protected Point connectEdgeIndices(int newRow, int newCol) {
        if (newRow == -NEXT_INDEX) {
            newRow = myCells.length - NEXT_INDEX;
        } else if (newRow == myCells.length) {
            newRow = START_INDEX;
        }
        if (newCol == -NEXT_INDEX) {
            newCol = myCells[START_INDEX].length - NEXT_INDEX;
        } else if (newCol == myCells[START_INDEX].length) {
            newCol = START_INDEX;
        }
        return new Point(newRow, newCol);
    }

    abstract public void setCellState(int i, int j, String state);

    abstract protected void updateCellState(int i, int j);

}
