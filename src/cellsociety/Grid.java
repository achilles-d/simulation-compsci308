package cellsociety;

public abstract class Grid {
    protected Enum[][] myCells;

    public Grid(String[][] initConfig){
        initialize(initConfig);
    }

    /**
     * Set grid size and initial cell states of grid according to size and contents of array
     * created by Controller
     * @param initConfig directory for initial cell configuration XML file
     */
    protected void initialize(String[][] initConfig){
        //Set grid size and cell states according to XML file
    }

    /**
     * Return the current state of the cell at the ith row and jth column of the simulation's grid
     * @param i the row of the desired cell in the simulation's grid
     * @param j the column of the desired cell in the simulation's grid
     * @return the String representing the current state of the cell at row i and column j of the grid
     */
    public String getCellState(int i, int j){
        return myCells[i][j].toString();
    }

}
