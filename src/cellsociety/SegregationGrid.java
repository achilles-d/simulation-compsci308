package cellsociety;

import java.util.ArrayList;
import java.util.Random;

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
    public SegregationGrid(String[][] initConfig, double minAgentSatisfaction){
        super(initConfig);
        myMinAgentSatisfaction = minAgentSatisfaction;
    }

    @Override
    /**
     * Move the Segregation grid simulation one step forward
     */
    public void update(){
        for(int i = 0; i < myCells.length; i++){
            for(int j = 0; j < myCells[0].length; j++){
                updateCellState(i, j, myCells);
            }
        }
    }

    @Override
    protected void initialize(String[][] initConfig){
        myCells = new Enum[initConfig.length][initConfig[0].length];
        myEmptyCellIndices = new ArrayList<IndexPair>();
        for(int i = 0; i < initConfig.length; i++){
            for(int j = 0; j < initConfig[0].length; j++){
                myCells[i][j] = setCellState(initConfig[i][j]);
                if(initConfig[i][j].equals(SegregationCell.EMPTY.toString())){
                    myEmptyCellIndices.add(new IndexPair(i, j));
                }

            }
        }
    }

    protected SegregationCell setCellState(String state) {
        if (state.equals(SegregationCell.X.toString())) {
            return SegregationCell.X;
        } else if (state.equals(SegregationCell.O.toString())) {
            return SegregationCell.O;
        } else {
            return SegregationCell.EMPTY;
        }
    }

    protected void updateCellState(int i, int j, Enum[][] gridCopy){
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
            oppositeCellNeighborCount = findNeighborIndices(i, j, myCells, SegregationCell.O).size();
            sameCellNeighborCount = findNeighborIndices(i, j, myCells, SegregationCell.X).size();
        }
        else{
            oppositeCellNeighborCount = findNeighborIndices(i, j, myCells, SegregationCell.X).size();
            sameCellNeighborCount = findNeighborIndices(i, j, myCells, SegregationCell.O).size();
        }
        if(oppositeCellNeighborCount == 0){
            return true;
        }
        return ((double)sameCellNeighborCount) / ((double)oppositeCellNeighborCount) > myMinAgentSatisfaction;

    }

}
