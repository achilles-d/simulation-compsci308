package Model;

public class RockPaperScissorsGrid extends Grid{

    private int myThreshold;

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
