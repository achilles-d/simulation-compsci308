package Model;

import java.util.ArrayList;
import java.util.Random;

/**
 * Facilitate a simulation of Schelling's Model of Segregation
 * @author Achilles Dabrowski
 */
public class SegregationGrid extends Grid {

    private double myMinAgentSatisfaction;
    private ArrayList<IndexPair> myEmptyCellIndices;

    /**
     * Create a grid to run the Segregation simulation
     * @param initConfig an array of Strings containing each cell's initial state. "X" = person of
     *                   type X. "O" = person of type O.
     * @param minAgentSatisfaction the minimum proportion of a cell's neighbors of the same type
     *                             as that cell (excluding empty cells) for the cell - the agent in
     *                             the simulation - to be satisfied
     */
    public SegregationGrid(String[][] initConfig, String edgeType, String neighborType, double minAgentSatisfaction){
        super(initConfig, edgeType, neighborType);
        myCellsCopy = myCells;
        myMinAgentSatisfaction = minAgentSatisfaction;
    }

    @Override
    protected void initializeCells(String[][] initConfig){
        myCells = new Enum[initConfig.length][initConfig[0].length];
        myEmptyCellIndices = new ArrayList<IndexPair>();
        for(int i = 0; i < initConfig.length; i++){
            for(int j = 0; j < initConfig[0].length; j++){
                setCellState(i, j, initConfig[i][j]);
                if(initConfig[i][j].equals(SegregationCell.EMPTY.toString())){
                    myEmptyCellIndices.add(new IndexPair(i, j));
                }
            }
        }
    }

    public void setCellState(int i, int j, String state) {
        myCells[i][j] = SegregationCell.valueOf(state);
    }

    protected void updateCellState(int i, int j){
        if((myCells[i][j] != SegregationCell.EMPTY) && !(isSatisfied(i, j)) && !(myEmptyCellIndices.isEmpty())){
            int emptyCellIndex = new Random().nextInt(myEmptyCellIndices.size());
            int newRow = myEmptyCellIndices.get(emptyCellIndex).getRow();
            int newCol = myEmptyCellIndices.get(emptyCellIndex).getCol();
            myCells[newCol][newRow] = myCells[i][j];
            myEmptyCellIndices.remove(emptyCellIndex);
            myCells[i][j] = SegregationCell.EMPTY;
            myEmptyCellIndices.add(new IndexPair(i, j));
        }
    }

    private boolean isSatisfied(int i, int j) {
        int oppositeCellNeighborCount;
        int sameCellNeighborCount;
        if(myCells[i][j] == SegregationCell.X){
            oppositeCellNeighborCount = findNeighborIndices(i, j, SegregationCell.O).size();
            sameCellNeighborCount = findNeighborIndices(i, j, SegregationCell.X).size();
        }
        else{
            oppositeCellNeighborCount = findNeighborIndices(i, j, SegregationCell.X).size();
            sameCellNeighborCount = findNeighborIndices(i, j, SegregationCell.O).size();
        }
        return oppositeCellNeighborCount == 0 ||
                ((double)sameCellNeighborCount) / ((double)oppositeCellNeighborCount) > myMinAgentSatisfaction;

    }

}
