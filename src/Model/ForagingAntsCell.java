package Model;

public enum ForagingAntsCell {

  ANT(true), EMPTY(true);
  boolean isLeavingNest;

  private ForagingAntsCell(boolean leavingNest){
    isLeavingNest = leavingNest;
  }

}
