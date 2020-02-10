package Model;

import java.util.ArrayList;
import java.util.Random;

/**
 * Facilitates a Wa-Tor World / Predator-Prey simulation
 * @author Achilles Dabrowski
 */
public class PredatorPreyGrid extends Grid{

    private static final int INIT_TURNS_TAKEN = 0;

    private int myMinFishTurnsToBreed;
    private int myMaxSharkTurns;
    private int myMinSharkTurnsToBreed;
    private int[][] myCellTurnsSurvived;
    private int[][] myTotalTurnsSurvived;

    /**
     * Create a grid to run the Predator-Prey (Wa-tor World) simulation
     * @param initConfig an array of Strings corresponding to each cell's initial state. "FISH" = cell with fish. "SHARK"
     *                   = cell with shark. "EMPTY" = empty cell
     * @param edgeType the type of grid edges to be used in the simulation. "FINITE" = finite edges
     *                 (edges across from one another are not connected). "TOROIDAL" = toroidal edges
     *                 (edges across from one another are connected).
     * @param neighborType the type of cell neighborhood to be used in the simulation. "SQUARE_DIAGONAL" =
     *                     square neighborhood with eight neighbors, including diagonals, at most.
     *                     "SQUARE_NO_DIAGONAL" = square neighborhood with four neighbors at most and no diagonals.
     *                     "HEXAGONAL" = hexagonal neighborhood with six neighbors, including diagonals, at most.
     * @param minFishTurnsToBreed the amount of turns for which a fish must be alive in the simulation
     *                            before it can breed new fish
     * @param maxSharkTurns the maximum amount of turns for which a shark can live in the simulation
     *                      without eating any fish
     * @param minSharkTurnsToBreed the amount of turns for which a fish must be alive in the simulation
     *                             before it can breed new fish
     */
    public PredatorPreyGrid(String[][] initConfig, String edgeType, String neighborType,
        int minFishTurnsToBreed, int maxSharkTurns, int minSharkTurnsToBreed){
        super(initConfig, edgeType, neighborType);
        myCellsCopy = myCells;
        myMinFishTurnsToBreed = minFishTurnsToBreed;
        myMaxSharkTurns = maxSharkTurns;
        myMinSharkTurnsToBreed = minSharkTurnsToBreed;
    }

    @Override
    protected void initializeCells(String[][] initConfig){
        super.initializeCells(initConfig);
        myCellTurnsSurvived = new int[myCells.length][myCells[START_INDEX].length];
        myTotalTurnsSurvived = new int[myCells.length][myCells[START_INDEX].length];
        myNeighborType = SQUARE_NO_DIAGONAL_TYPE;
    }


    public void setCellState(int i, int j, String state){
        myCells[i][j] = PredatorPreyCell.valueOf(state);
    }

    protected void updateCellState(int i, int j){
        if(myCells[i][j] != PredatorPreyCell.EMPTY) {
            myCellTurnsSurvived[i][j]++;
        }
        if(myCells[i][j] == PredatorPreyCell.SHARK){
            updateSharkCell(i, j);
        }
        else if(myCells[i][j] == PredatorPreyCell.FISH){
            updateFishCell(i, j);
        }
    }

    private void updateFishCell(int i, int j) {
        ArrayList<IndexPair> emptyCellIndices = findNeighborIndices(i, j, PredatorPreyCell.EMPTY);
        if(!emptyCellIndices.isEmpty()) {
            int emptyCellIndex = new Random().nextInt(emptyCellIndices.size());
            int newRow = emptyCellIndices.get(emptyCellIndex).getRow();
            int newCol = emptyCellIndices.get(emptyCellIndex).getCol();
            myCells[newRow][newCol] = PredatorPreyCell.FISH;
            myCellTurnsSurvived[newRow][newCol] = myCellTurnsSurvived[i][j];
            tryBreedingEntity(i, j, PredatorPreyCell.FISH);
        }
    }

    private void updateSharkCell(int i, int j) {
        myTotalTurnsSurvived[i][j]++;
        if(myCellTurnsSurvived[i][j] > myMaxSharkTurns){
            setCellEmpty(i,j);
            return;
        }
        else {
            ArrayList<IndexPair> fishCellIndices = findNeighborIndices(i, j, PredatorPreyCell.FISH);
            if(!fishCellIndices.isEmpty()) {
                eatNeighboringFish(i, j, fishCellIndices);
                return;
            }
        }
        ArrayList<IndexPair> emptyCellIndices = findNeighborIndices(i, j, PredatorPreyCell.EMPTY);
        if(!emptyCellIndices.isEmpty()){
            moveSharkToEmptyCell(i, j, emptyCellIndices);
        }
    }

    private void setCellEmpty(int i, int j){
        myCells[i][j] = PredatorPreyCell.EMPTY;
        myCellTurnsSurvived[i][j] = INIT_TURNS_TAKEN;
        myTotalTurnsSurvived[i][j] = INIT_TURNS_TAKEN;
    }

    private void tryBreedingEntity(int i, int j, PredatorPreyCell cell){
        if(((cell == PredatorPreyCell.FISH) && (myCellTurnsSurvived[i][j] > myMinFishTurnsToBreed)) ||
            ((cell == PredatorPreyCell.SHARK) && (myTotalTurnsSurvived[i][j] > myMinSharkTurnsToBreed))){
            setCellEmpty(i, j);
            myCells[i][j] = cell;
        }
        else {
            setCellEmpty(i, j);
        }
    }

    private void eatNeighboringFish(int i, int j, ArrayList<IndexPair> fishCellIndices) {
        int fishCellIndex = new Random().nextInt(fishCellIndices.size());
        int newRow = fishCellIndices.get(fishCellIndex).getRow();
        int newCol = fishCellIndices.get(fishCellIndex).getCol();
        myCells[newRow][newCol] = PredatorPreyCell.SHARK;
        myCellTurnsSurvived[newRow][newCol] = INIT_TURNS_TAKEN;
    }

    private void moveSharkToEmptyCell(int i, int j, ArrayList<IndexPair> emptyCellIndices) {
        int emptyCellIndex = new Random().nextInt(emptyCellIndices.size());
        myCells[emptyCellIndices.get(emptyCellIndex).getRow()][emptyCellIndices.get(emptyCellIndex).getCol()] = PredatorPreyCell.SHARK;
        setCellEmpty(i, j);
    }

}
