package cellsociety;

public class FireGrid extends Grid {

    private double probCatch;
    private double probGrow;

    public FireGrid(String[][] initConfig, double pCatch, double pGrow){
        super(initConfig);
        probCatch = pCatch;
        probGrow = pGrow;
    }

    public FireCell setCellState(String state){

        if(state.equals(FireCell.EMPTY.toString())){
            return FireCell.EMPTY;
        }
        if(state.equals(FireCell.TREE.toString())){
            return FireCell.TREE;
        }
        else{
            return FireCell.BURNING;
        }
    }

    protected void updateCellState(int i, int j, Enum[][] gridCopy){
        if(gridCopy[i][j] == FireCell.TREE){
            updateTreeCells(i, j, gridCopy);
        }
        else if(gridCopy[i][j] == FireCell.BURNING){
            myCells[i][j] = FireCell.EMPTY;
        }
        else if(gridCopy[i][j] == FireCell.NEW_TREE){
            myCells[i][j] = FireCell.TREE;
        }
        else{
            updateEmptyCells(i, j);
        }
    }

    private void updateTreeCells(int i, int j, Enum[][] gridCopy) {
        for(int a = 0; a < ALT_CELL_NEIGHBOR_COUNT; a++){
            if(inBounds(i + ALT_DELTA_X[a], j + ALT_DELTA_Y[a]) &&
                    (gridCopy[i + ALT_DELTA_X[a]][j + ALT_DELTA_Y[a]] == FireCell.BURNING) ){
                if(Math.random() < probCatch){
                    myCells[i][j] = FireCell.BURNING;
                }
                return;
            }
        }
    }

    private void updateEmptyCells(int i, int j) {
        if(Math.random() < probGrow){
            myCells[i][j] = FireCell.NEW_TREE;
        }
    }
}
