package cellsociety;

public class FireGrid extends Grid {

    public FireGrid(String[][] initConfig){
        super(initConfig);
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

    public void update(){

    }
}
