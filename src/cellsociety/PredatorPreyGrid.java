package cellsociety;

import java.util.ArrayList;
import java.util.Random;

public class PredatorPreyGrid extends Grid{

    private int myMinFishTurnsToBreed;
    private int myMaxSharkTurns;
    private int myMinSharkTurnsToBreed;
    private int[][] myCellTurnsSurvived;
    private int[][] myTotalTurnsSurvived;

    /**
     * Create a grid to run the Predator-Prey (Wa-tor World) simulation
     * @param initConfig an array of Strings corresponding to each cell's initial state. "FISH" = cell with fish. "SHARK"
     *                   = cell with shark. "EMPTY" = empty cell
     * @param minFishTurnsToBreed the amount of turns for which a fish must be alive in the simulation
     *                            before it can breed new fish
     * @param maxSharkTurns the maximum amount of turns for which a shark can live in the simulation
     *                      without eating any fish
     * @param minSharkTurnsToBreed the amount of turns for which a fish must be alive in the simulation
     *                             before it can breed new fish
     */
    public PredatorPreyGrid(String[][] initConfig, int minFishTurnsToBreed, int maxSharkTurns, int minSharkTurnsToBreed){
        super(initConfig);
        myMinFishTurnsToBreed = minFishTurnsToBreed;
        myMaxSharkTurns = maxSharkTurns;
        myMinSharkTurnsToBreed = minSharkTurnsToBreed;
    }

    @Override
    protected void initialize(String[][] initConfig){
        super.initialize(initConfig);
        myCellTurnsSurvived = new int[myCells.length][myCells[0].length];
        myTotalTurnsSurvived = new int[myCells.length][myCells[0].length];
    }


    protected PredatorPreyCell setCellState(String state){
        if(state.equals(PredatorPreyCell.SHARK.toString())){
            return PredatorPreyCell.SHARK;
        }
        else if(state.equals(PredatorPreyCell.FISH.toString()))
        {
            return PredatorPreyCell.FISH;
        }
        else{
            return PredatorPreyCell.EMPTY;
        }
    }

    protected void updateCellState(int i, int j, Enum[][] gridCopy){
        if(! (gridCopy[i][j] == PredatorPreyCell.EMPTY) ) {
            myCellTurnsSurvived[i][j]++;
        }

        if(gridCopy[i][j] == PredatorPreyCell.SHARK){
            updateSharkCell(i, j, gridCopy);
        }
        else if(gridCopy[i][j] == PredatorPreyCell.FISH){
            updateFishCell(i, j, gridCopy);
        }
    }

    private void updateFishCell(int i, int j, Enum[][] gridCopy) {
        ArrayList<IndexPair> emptyCellIndices = altFindNeighborIndices(i, j, gridCopy, PredatorPreyCell.EMPTY);
        if(!emptyCellIndices.isEmpty()) {
            int emptyCellIndex = new Random().nextInt(emptyCellIndices.size());
            myCells[emptyCellIndices.get(emptyCellIndex).getRow()][emptyCellIndices.get(emptyCellIndex).getCol()] = PredatorPreyCell.FISH;
            setCellEmpty(i, j);
            if(myCellTurnsSurvived[emptyCellIndices.get(emptyCellIndex).getRow()][emptyCellIndices.get(emptyCellIndex).getCol()] >= myMinFishTurnsToBreed){
                myCells[i][j] = PredatorPreyCell.FISH;
            }
        }
    }

    private void updateSharkCell(int i, int j, Enum[][] gridCopy) {
        myTotalTurnsSurvived[i][j]++;
        if(myCellTurnsSurvived[i][j] > myMaxSharkTurns){
            setCellEmpty(i,j);
        }
        else {
            ArrayList<IndexPair> fishCellIndices = altFindNeighborIndices(i, j, gridCopy, PredatorPreyCell.FISH);
            if (!fishCellIndices.isEmpty()) {
                int fishCellIndex = new Random().nextInt(fishCellIndices.size());
                int newRow = fishCellIndices.get(fishCellIndex).getRow();
                int newCol = fishCellIndices.get(fishCellIndex).getCol();
                myCells[newRow][newCol] = PredatorPreyCell.SHARK;
                myCellTurnsSurvived[newRow][newCol] = 0;
                if(myTotalTurnsSurvived[newRow][newCol] >= myMinSharkTurnsToBreed){
                    setCellEmpty(i, j);
                    myCells[i][j] = PredatorPreyCell.SHARK;
                }
            }
        }
    }

    private void setCellEmpty(int i, int j){
        myCells[i][j] = PredatorPreyCell.EMPTY;
        myCellTurnsSurvived[i][j] = 0;
        myTotalTurnsSurvived[i][j] = 0;
    }
}
