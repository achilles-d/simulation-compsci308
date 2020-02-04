package cellsociety;

public class Test {
    public static void main(String args[]){
        String[][] states = new String [5][5];
        states[0] = new String[]{"EMPTY", "EMPTY", "EMPTY", "EMPTY", "EMPTY"};
        states[1] = new String[]{"EMPTY", "TREE", "TREE", "TREE", "EMPTY"};
        states[2] = new String[]{"EMPTY", "TREE", "BURNING", "TREE", "EMPTY"};
        states[3] = new String[]{"EMPTY","TREE","TREE","TREE","EMPTY"};
        states[4] = new String[]{"EMPTY","EMPTY","EMPTY","EMPTY","EMPTY"};
        FireGrid grid = new FireGrid(states, .3, .3);
        print(grid, states.length);
        grid.update();
        print(grid, states.length);
        grid.update();
        print(grid, states.length);
        grid.update();
        print(grid, states.length);
    }

    static void print(Grid g, int length){
        for(int i = 0; i < length; i++){
            for(int j = 0; j < length; j++){
                System.out.print(g.getCellState(i,j) + " ");
            }        System.out.println(" ");
        }

        System.out.println(" ");
    }
}
