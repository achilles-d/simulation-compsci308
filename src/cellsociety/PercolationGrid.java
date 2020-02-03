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

    protected void updateCellState(int i, int j, Enum[][] gridCopy){
        if(gridCopy[i][j] == PercolationCell.EMPTY){
            for(int a = 0; a < MAX_CELL_NEIGHBOR_COUNT; a++){
                if(gridCopy[i + DELTA_X[a]][j + DELTA_Y[a]] == PercolationCell.PERCOLATED){
                    myCells[i][j] = PercolationCell.PERCOLATED;
                    return;
                }
            }
        }
    }
}
