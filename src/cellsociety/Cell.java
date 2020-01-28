package cellsociety;

public class Cell {
    public char myState;
    public Cell cellAbove;
    public Cell cellBelow;
    public Cell cellLeft;
    public Cell cellRight;

    public Cell(char state, Cell above, Cell below, Cell left, Cell right){
        myState = state;
        cellAbove = above;
        cellBelow = below;
        cellLeft = left;
        cellRight = right;
    }

    /**
     * Returns the current state of the Cell in the simulation
     * @return the character representing the current state of the Cell in the simulation
     */
    public char getState(){
        return myState;
    }
}
