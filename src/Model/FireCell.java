package Model;

/**
 * Represents the possible states of cells in the Game of Fire simulation
 * EMPTY = empty cell. TREE = cell with tree. BURNING = cell with burning tree. NEW_TREE = tree that grew from previously
 * empty cell in the last turn
 *
 * @author Achilles Dabrowski
 */
public enum FireCell {
    EMPTY, TREE, BURNING, NEW_TREE;
}
