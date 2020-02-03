package cellsociety;

public class PredatorPreyGrid extends Grid{

    public PredatorPreyGrid(String[][] initConfig){
        super(initConfig);
    }

    protected PredatorPreyCell setCellState(String state){
        if(state.equals(PredatorPreyCell.SHARK.toString())){
            return PredatorPreyCell.SHARK;
        }
        else if(state.equals(PredatorPreyCell.FISH.toString()))
        {
            return PredatorPreyCell.FISH;
        }
        else{
            return PredatorPreyCell.EMPTY;
        }
    }

    public void update(){
        
    }
}
