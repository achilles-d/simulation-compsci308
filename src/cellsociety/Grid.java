package cellsociety;

public abstract class Grid {
    public Cell[][] myCells;

    public Grid(String configFileDir){

    }

    /**
     * Set grid size and initial cell states of grid according to XML file in the directory of fileDir
     * @param fileDir directory for initial cell configuration XML file
     */
    public void initialize(String fileDir){
        //Set grid size and cell states according to XML file
    }

    /**
     * Return the current state of the cell at the ith row and jth column of the simulation's grid
     * @param i the row of the desired cell in the simulation's grid
     * @param j the column of the desired cell in the simulation's grid
     * @return the character representing the current state of the cell at row i and column j of the grid
     */
    public char getCellState(int i, int j){
        return myCells[i][j].getState();
    }

}
