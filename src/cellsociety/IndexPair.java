package cellsociety;

public class IndexPair {
    private int myRow;
    private int myCol;

    IndexPair(int i, int j){
        myRow = i;
        myCol = j;
    }

    public int getRow(){
        return myRow;
    }

    public int getCol(){
        return myCol;
    }
}
