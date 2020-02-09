package Model;

/**
 * Represents an ant in the Foraging Ants simulation and keeps track of whether the ant is leaving or approaching
 * its nest
 * @Author Achilles Dabrowski
 */
public enum ForagingAntsCell {

  ANT(true), EMPTY(true);
  boolean isLeavingNest;

  private ForagingAntsCell(boolean leavingNest){
    isLeavingNest = leavingNest;
  }

}
