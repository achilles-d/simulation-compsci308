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

    protected void update(){
        Enum[][] temp = new Enum[myCells.length][myCells[0].length];
        temp = myCells.clone();
        for(int i = 0; i < temp.length; i++){
            for(int j = 0; j < temp[0].length; j++){
                if(temp[i][j] == PercolationCell.EMPTY && neighborIsPercolated(i, j, temp)){
                    myCells[i][j] = PercolationCell.PERCOLATED;
                }
            }
        }
    }

    private boolean neighborIsPercolated(int i, int j, Enum[][] grid){
        return ( (inBounds(i+1, j) && grid[i+1][j] == PercolationCell.PERCOLATED) ||
            (inBounds(i-1, j) && grid[i-1][j] == PercolationCell.PERCOLATED) ||
            (inBounds(i, j-1) && grid[i][j-1] == PercolationCell.PERCOLATED) ||
            (inBounds(i, j+1) && grid[i][j+1] == PercolationCell.PERCOLATED) ) ||
            (inBounds(i+1,j+1) && grid[i+1][j+1] == PercolationCell.PERCOLATED) ||
            (inBounds(i+1,j-1) && grid[i+1][j-1] == PercolationCell.PERCOLATED) ||
            (inBounds(i-1,j+1) && grid[i-1][j+1] == PercolationCell.PERCOLATED) ||
            (inBounds(i-1,j-1) && grid[i-1][j-1] == PercolationCell.PERCOLATED);
    }
}
