package cellsociety;

public class PercolationGrid extends Grid {

    public PercolationGrid(String[][] initConfig){
        super(initConfig);
    }

    protected PercolationCell setCellState(String state){
        if(state.equals(PercolationCell.EMPTY.toString())){
            return PercolationCell.EMPTY;
        }
        else if(state.equals(PercolationCell.FULL.toString())){
            return PercolationCell.FULL;
        }
        else{
            return PercolationCell.PERCOLATED;
        }
    }

    protected void update(){
        for(int i = 0; i < myCells.length; i++){
            for(int j = 0; j < myCells[0].length; j++){
                if(myCells[i][j] == PercolationCell.EMPTY && neighborIsPercolated(i, j)){
                    myCells[i][j] = PercolationCell.PERCOLATED;
                }
            }
        }
    }

    protected boolean neighborIsPercolated(int i, int j){
        return ( (inBounds(i+1, j) && myCells[i+1][j] == PercolationCell.PERCOLATED) ||
            (inBounds(i-1, j) && myCells[i-1][j] == PercolationCell.PERCOLATED) ||
            (inBounds(i, j-1) && myCells[i][j-1] == PercolationCell.PERCOLATED) ||
            (inBounds(i, j+1) && myCells[i][j+1] == PercolationCell.PERCOLATED) );
    }
}
