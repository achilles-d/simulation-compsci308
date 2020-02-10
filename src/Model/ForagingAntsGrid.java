package Model;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

/**
 * Facilitates the Foraging Ants simulation
 *
 * @Author Achilles Dabrowski
 */
public class ForagingAntsGrid extends Grid {

    private static double INIT_CELL_PROB_VISITED;
    private static double CELL_PROB_VISITED_BONUS;
    private static int INDICES_CHOICE_ONE_ID = 0;
    private static int INDICES_CHOICE_TWO_ID = 1;
    private static int PROB_LIST_LENGTH_MULTIPLIER = 10;

    private HashMap<Point, ArrayList<ForagingAntsCell>> myAnts;
    private double[][] myCellProbVisited;

    /**
     * Create a grid to run the Foraging Ants simulation
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
    public ForagingAntsGrid(String[][] initConfig, String edgeType, String neighborType) {
        super(initConfig, edgeType, neighborType);
        //Initialize to keep track of grid size despite not being used as the simulation grid
        myCells = new Enum[initConfig.length][initConfig[START_INDEX].length];
        myCellsCopy = myCells;
    }

    /**
     * Return the current state of the cell at the ith row and jth column of the simulation's grid
     *
     * @param i the row of the desired cell in the simulation's grid
     * @param j the column of the desired cell in the simulation's grid
     * @return the String representation the current state of the cell at row i and column j of the
     * grid. "EMPTY" = empty cell. "ANT" = cell occupied by at least one ant. "ANT_RETURNING" = cell occupied by only
     * ants returning to the nest
     */
    @Override
    public String getCellState(int i, int j) {
        if (!myAnts.containsKey(new Point(i, j))) {
            return ForagingAntsCell.EMPTY.toString();
        } else {
            return ForagingAntsCell.ANT.toString();
        }
    }

    /**
     * Return the possible cell states of the simulation
     *
     * @return the String representation of the different possible cell states in the simulation. "EMPTY" = empty cell.
     * "ANT" = cell occupied by at least one ant. "ANT_RETURNING" = cell occupied by only ants returning to the nest
     */
    @Override
    public List<String> getCellStates() {
        ForagingAntsCell cell = ForagingAntsCell.ANT;
        Object[] states = cell.getClass().getEnumConstants();
        List<String> strings = new ArrayList<>();
        for (Object o : states) {
            strings.add(o.toString());
        }
        return strings;
    }

    /**
     * Set the state of the cell at the ith row and jth column of the simulation's grid
     *
     * @param i     the row of the desired cell in the simulation's grid
     * @param j     the column of the desired cell in the simulation's grid
     * @param state the String representation of the desired state of the selected cell. "EMPTY" =
     *              empty cell; "ANT" = cell occupied by at least one ant
     */
    public void setCellState(int i, int j, String state) {
        Point antIndices = new Point(i, j);
        if (state.equals(ForagingAntsCell.ANT.toString())) {
            if (!myAnts.containsKey(antIndices)) {
                myAnts.put(antIndices, new ArrayList<>());
                myAnts.get(antIndices).add(ForagingAntsCell.ANT);
            }
        } else if (state.equals(ForagingAntsCell.ANT_RETURNING.toString())) {
            sendAntsToNest(i, j);
        } else if (myAnts.containsKey(antIndices)) {
            myAnts.remove(antIndices);
        }
    }

    /**
     * Move the grid one step forward in the simulation according to the simulation's rules
     */
    @Override
    public void update() {
        for (int i = START_INDEX; i < myCells.length; i++) {
            for (int j = START_INDEX; j < myCells[START_INDEX].length; j++) {
                updateCellState(i, j);
            }
        }
    }

    @Override
    protected void initializeCells(String[][] initConfig) {
        myAnts = new HashMap<>();
        myCellProbVisited = new double[initConfig.length][initConfig[START_INDEX].length];
        for (int i = START_INDEX; i < initConfig.length; i++) {
            for (int j = START_INDEX; j < initConfig[START_INDEX].length; j++) {
                setCellState(i, j, initConfig[i][j]);
                myCellProbVisited[i][j] = INIT_CELL_PROB_VISITED;
            }
        }
    }

    protected void updateCellState(int i, int j) {
        if (myAnts.containsKey(new Point(i, j))) {
            updateAnts(i, j);
        }
    }

    private void updateAnts(int i, int j) {
        if (i == START_INDEX && j == START_INDEX) {
            spawnAnt();
        }
        if (i == myCells.length - NEXT_INDEX && j == myCells[START_INDEX].length - 1) {
            sendAntsToNest(myCells.length - NEXT_INDEX, myCells[START_INDEX].length - 1);
        }
        myCellProbVisited[i][j] += CELL_PROB_VISITED_BONUS;
        iterateThroughAntList(i, j);

    }

    private void iterateThroughAntList(int i, int j) {
        Point antIndices = new Point(i, j);
        Iterator<ForagingAntsCell> antsItr = myAnts.get(antIndices).iterator();
        while (antsItr.hasNext()) {
            ForagingAntsCell ant = antsItr.next();
            Point newIndices;
            if (ant == ForagingAntsCell.ANT_RETURNING) {
                newIndices = moveAntTowardNest(i, j);
            } else {
                newIndices = moveAntTowardFood(i, j);
            }
            ForagingAntsCell temp = ForagingAntsCell.valueOf(ant.toString());
            myAnts.putIfAbsent(newIndices, new ArrayList<>());
            myAnts.get(newIndices).add(temp);
            antsItr.remove();
        }

    }

    private void spawnAnt() {
        Point nestIndices = new Point(myCells.length, myCells[START_INDEX].length);
        if (!myAnts.containsKey((nestIndices))) {
            myAnts.put(nestIndices, new ArrayList<>());
        }
        myAnts.get(nestIndices).add(ForagingAntsCell.ANT);
    }

    private void sendAntsToNest(int i, int j) {
        Point antIndices = new Point(i, j);
        if (myAnts.containsKey(antIndices)) {
            for (int a = START_INDEX; a < myAnts.get(antIndices).size(); a++) {
                myAnts.get(antIndices).set(a, ForagingAntsCell.ANT_RETURNING);
            }
        }
    }

    private Point moveAntTowardFood(int i, int j) {
        return selectNextCell(new Point(i - NEXT_INDEX, j), new Point(i, j - NEXT_INDEX));
    }

    private Point moveAntTowardNest(int i, int j) {
        return selectNextCell(new Point(i + NEXT_INDEX, j), new Point(i, j + NEXT_INDEX));
    }

    private Point selectNextCell(Point choice1, Point choice2) {
        if (!inBounds(choice1)) {
            return choice2;
        } else if (!inBounds(choice2)) {
            return choice1;
        }
        ArrayList<Integer> probList = buildProbabilityList(INDICES_CHOICE_ONE_ID, choice1);
        probList.addAll(buildProbabilityList(INDICES_CHOICE_TWO_ID, choice2));
        int selectIndex = new Random().nextInt(probList.size());
        if (probList.get(selectIndex) != INDICES_CHOICE_ONE_ID) {
            return choice1;
        } else {
            return choice2;
        }
    }

    private ArrayList<Integer> buildProbabilityList(int id, Point possibleIndex) {
        ArrayList<Integer> probList = new ArrayList<>();
        int probListLength = (int) (myCellProbVisited[(int) possibleIndex.getX()][(int) possibleIndex.getY()]
                * PROB_LIST_LENGTH_MULTIPLIER);
        for (int i = START_INDEX; i < probListLength; i++) {
            probList.add(id);
        }
        return probList;
    }
}
