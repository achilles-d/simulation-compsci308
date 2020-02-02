package cellsociety;

public class SegregationGrid extends Grid {

    public SegregationGrid(String[][] initConfig){
        super(initConfig);
    }

    protected SegregationCell setCellState(String state){
        SegregationCell cell = SegregationCell.EMPTY;
        cell.similarPercentage = 0;
        if(state.equals(SegregationCell.X.toString())){
            cell = SegregationCell.X;
        }
        if(state.equals(SegregationCell.O.toString())){
            cell = SegregationCell.O;
        }
        return cell;
    }

    protected void update(){

    }
}
