package Model;

/**
 * A container for indices corresponding to an element of interest in a 2D-grid
 * @author Achilles Dabrowski
 */
public class IndexPair {
    private int myRow;
    private int myCol;

    /**
     * Create a container for a pair of indices of interest in a 2D-grid
     * @param i the row index to be stored
     * @param j the column index to be stored
     */
    IndexPair(int i, int j){
        myRow = i;
        myCol = j;
    }

    /**
     * @return the row index that is stored
     */
    public int getRow(){
        return myRow;
    }

    /*
     * @return the column index that is stored
     */
    public int getCol(){
        return myCol;
    }
}
