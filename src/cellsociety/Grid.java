package cellsociety;

public abstract class Grid {
    public static final int[] deltaX = {1, -1, 0, 0, 1, -1, 1, -1};
    public static final int[] deltaY = {0, 0, 1, -1, -1, 1, 1, -1};
    public static final int maxCellNeighborCount = 8;
    protected Enum[][] myCells;

    public Grid(String[][] initConfig){
        initialize(initConfig);
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


    /**
     * Set grid size and initial cell states of grid according to size and contents of array
     * created by Controller
     * @param initConfig directory for initial cell configuration XML file
     */
    public void initialize(String[][] initConfig){
        myCells = new Enum[initConfig.length][initConfig[0].length];
        for(int i = 0; i < initConfig.length; i++){
            for(int j = 0; j < initConfig[0].length; j++){
                myCells[i][j] = setCellState(initConfig[i][j]);
            }
        }
    }

    /**
     * Move the grid one step forward in the simulation according the to the simulation's rules
     */
    public void update(){
        Enum[][] temp = copyCells();
        for(int i = 0; i < temp.length; i++){
            for(int j = 0; j < temp[0].length; j++){
                updateCellState(i, j, temp);
            }
        }
    }

    protected boolean inBounds(int i, int j){
        return (i >= 0 && i < myCells.length && j >= 0 && j< myCells[0].length);
    }

    protected Enum[][] copyCells(){
        Enum[][] copy = new Enum[myCells.length][myCells[0].length];
        for(int i = 0; i < myCells.length; i++){
            for(int j = 0; j < myCells[0].length; j++){
                copy[i][j] = myCells[i][j];
            }
        }
        return copy;
    }

    abstract protected Enum setCellState(String state);

    abstract protected void updateCellState(int i, int j, Enum[][] gridCopy);



}
