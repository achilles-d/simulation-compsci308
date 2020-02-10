package Model;

public class RockPaperScissorsGrid extends Grid{

    private int myThreshold;

    /**
     *
     * @param initConfig
     * @param edgeType the type of grid edges to be used in the simulation. "FINITE" = finite edges
     *                 (edges across from one another are not connected). "TOROIDAL" = toroidal edges
     *                 (edges across from one another are connected).
     * @param neighborType the type of cell neighborhood to be used in the simulation. "SQUARE_DIAGONAL" =
     *                     square neighborhood with eight neighbors, including diagonals, at most.
     *                     "SQUARE_NO_DIAGONAL" = square neighborhood with four neighbors at most and no diagonals.
     *                     "HEXAGONAL" = hexagonal neighborhood with six neighbors, including diagonals, at most.
     */
    public RockPaperScissorsGrid(String[][] initConfig, String edgeType, String neighborType, int threshold){
        super(initConfig, edgeType, neighborType);
        myThreshold = threshold;
        myCellsCopy = copyCells();
    }

    public void setCellState(int i, int j, String state){
        myCells[i][j] = RockPaperScissorsCell.valueOf(state);
    }

    protected void updateCellState(int i, int j){
        if(myCells[i][j] == RockPaperScissorsCell.ROCK){
            myCells[i][j] = playRock(i, j);
        }
        else if(myCells[i][j] == RockPaperScissorsCell.PAPER){
            myCells[i][j] = playPaper(i, j);
        }
        else{
            myCells[i][j] = playScissors(i, j);
        }
    }

    private RockPaperScissorsCell playRock(int i, int j){
        if(findNeighborIndices(i, j, RockPaperScissorsCell.PAPER).size() > myThreshold){
            return RockPaperScissorsCell.PAPER;
        }
        else {
            return RockPaperScissorsCell.ROCK;
        }
    }

    private RockPaperScissorsCell playPaper(int i, int j){
        if(findNeighborIndices(i, j, RockPaperScissorsCell.SCISSORS).size() > myThreshold){
            return RockPaperScissorsCell.SCISSORS;
        }
        else {
            return RockPaperScissorsCell.PAPER;
        }
    }

    private RockPaperScissorsCell playScissors(int i, int j){
        if(findNeighborIndices(i, j, RockPaperScissorsCell.ROCK).size() > myThreshold){
            return RockPaperScissorsCell.ROCK;
        }
        else {
            return RockPaperScissorsCell.SCISSORS;
        }
    }

}
