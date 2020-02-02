package cellsociety;

import java.util.Arrays;

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
        if(gridCopy[i][j] == PercolationCell.EMPTY &&
                (inBounds(i+1, j) && gridCopy[i+1][j] == PercolationCell.PERCOLATED) ||
            (inBounds(i-1, j) && gridCopy[i-1][j] == PercolationCell.PERCOLATED) ||
            (inBounds(i, j-1) && gridCopy[i][j-1] == PercolationCell.PERCOLATED) ||
            (inBounds(i, j+1) && gridCopy[i][j+1] == PercolationCell.PERCOLATED) ||
            (inBounds(i+1,j+1) && gridCopy[i+1][j+1] == PercolationCell.PERCOLATED) ||
            (inBounds(i+1,j-1) && gridCopy[i+1][j-1] == PercolationCell.PERCOLATED) ||
            (inBounds(i-1,j+1) && gridCopy[i-1][j+1] == PercolationCell.PERCOLATED) ||
            (inBounds(i-1,j-1) && gridCopy[i-1][j-1] == PercolationCell.PERCOLATED) ){
                myCells[i][j] = PercolationCell.PERCOLATED;
        }
    }
}
