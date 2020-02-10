package Model;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

/**
 * Facilitate a simulation of Schelling's Model of Segregation
 *
 * @author Achilles Dabrowski
 */
public class SegregationGrid extends Grid {

    private double myMinAgentSatisfaction;
    private ArrayList<Point> myEmptyCellIndices;

    /**
     * Create a grid to run the Segregation simulation
     *
     * @param initConfig           an array of Strings containing each cell's initial state. "X" = person of type X.
     *                             "O" = person of type O.
     * @param initConfig           an array of Strings corresponding to each cell's initial state. "ROCK" = cell playing rock.
     *                             "PAPER" = cell playing paper. "SCISSORS" = cell playing scissors
     * @param edgeType             the type of grid edges to be used in the simulation. "FINITE" = finite
     *                             edges (edges across from one another are not connected). "TOROIDAL" =
     *                             toroidal edges (edges across from one another are connected).
     * @param neighborType         the type of cell neighborhood to be used in the simulation.
     *                             "SQUARE_DIAGONAL" = square neighborhood with eight neighbors, including
     *                             diagonals, at most. "SQUARE_NO_DIAGONAL" = square neighborhood with four
     *                             neighbors at most and no diagonals. "HEXAGONAL" = hexagonal neighborhood
     *                             with six neighbors, including diagonals, at most.
     * @param minAgentSatisfaction the minimum proportion of a cell's neighbors of the same type as
     *                             that cell (excluding empty cells) for the cell - the agent in the
     *                             simulation - to be satisfied
     */
    public SegregationGrid(String[][] initConfig, String edgeType, String neighborType,
                           double minAgentSatisfaction) {
        super(initConfig, edgeType, neighborType);
        myCellsCopy = myCells;
        myMinAgentSatisfaction = minAgentSatisfaction;
    }

    /**
     * Set the state of the cell in the ith row and jth column of the simulation grid
     *
     * @param i     the row of the desired cell in the grid
     * @param j     the column of the desired cell in the grid
     * @param state the String representation of the desired state of the cell. "X" = person of type X. "O" = person
     *              of type O
     */
    public void setCellState(int i, int j, String state) {
        myCells[i][j] = SegregationCell.valueOf(state);
    }

    @Override
    protected void initializeCells(String[][] initConfig) {
        myCells = new Enum[initConfig.length][initConfig[START_INDEX].length];
        myEmptyCellIndices = new ArrayList<Point>();
        for (int i = START_INDEX; i < initConfig.length; i++) {
            for (int j = START_INDEX; j < initConfig[START_INDEX].length; j++) {
                setCellState(i, j, initConfig[i][j]);
                if (initConfig[i][j].equals(SegregationCell.EMPTY.toString())) {
                    myEmptyCellIndices.add(new Point(i, j));
                }
            }
        }
    }

    protected void updateCellState(int i, int j) {
        if ((myCells[i][j] != SegregationCell.EMPTY) && !(isSatisfied(i, j)) && !(myEmptyCellIndices
                .isEmpty())) {
            int emptyCellIndex = new Random().nextInt(myEmptyCellIndices.size());
            int newRow = (int) myEmptyCellIndices.get(emptyCellIndex).getX();
            int newCol = (int) myEmptyCellIndices.get(emptyCellIndex).getX();
            myCells[newCol][newRow] = myCells[i][j];
            myEmptyCellIndices.remove(emptyCellIndex);
            myCells[i][j] = SegregationCell.EMPTY;
            myEmptyCellIndices.add(new Point(i, j));
        }
    }

    private boolean isSatisfied(int i, int j) {
        int oppositeCellNeighborCount;
        int sameCellNeighborCount;
        if (myCells[i][j] == SegregationCell.X) {
            oppositeCellNeighborCount = findNeighborIndices(i, j, SegregationCell.O).size();
            sameCellNeighborCount = findNeighborIndices(i, j, SegregationCell.X).size();
        } else {
            oppositeCellNeighborCount = findNeighborIndices(i, j, SegregationCell.X).size();
            sameCellNeighborCount = findNeighborIndices(i, j, SegregationCell.O).size();
        }
        return oppositeCellNeighborCount == 0 ||
                ((double) sameCellNeighborCount) / ((double) oppositeCellNeighborCount)
                        > myMinAgentSatisfaction;

    }

}
