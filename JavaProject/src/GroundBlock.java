/**
 * Abstract class of the Ground Block
 * */
abstract class GroundBlock implements Block {
    /**
     * Ground's Block constructor
     * */
  public GroundBlock(){}

  /**
   * Checks if the Ground Block is movebale. It always returns false
   * @return false
   * */
  public final boolean isMoveable(){
      return false;
  }

  /**
   * Checks if the Ground Block is diggable. Always return false.
   * @return false
   * */
  public final boolean isDiggable(){
      return true;
  }
}
