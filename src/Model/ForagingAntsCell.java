package Model;

/**
 * Represents an ant in the Foraging Ants simulation and keeps track of whether the ant is leaving
 * or approaching its nest
 * EMPTY = empty cell. ANT = cell occupied by at least one ant. ANT_RETURNING = cell with only ants returning to the nest
 * @Author Achilles Dabrowski
 */
public enum ForagingAntsCell {

  ANT(true), ANT_RETURNING(false), EMPTY(true);
  boolean isLeavingNest;

  private ForagingAntsCell(boolean leavingNest) {
    isLeavingNest = leavingNest;
  }

}
