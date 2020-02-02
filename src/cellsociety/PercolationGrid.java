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
        if(gridCopy[i][j] == PercolationCell.EMPTY){
            for(int a = 0; a < maxCellNeighborCount; a++){
                if(inBounds(i + deltaX[a], j + deltaY[a]) &&
                        (gridCopy[i + deltaX[a]][j + deltaY[a]]) == PercolationCell.PERCOLATED){
                    myCells[i][j] = PercolationCell.PERCOLATED;
                    return;
                }
            }
        }
    }
}
