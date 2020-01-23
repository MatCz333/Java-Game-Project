/**
 * A Stone Block
 * */
public class StoneBlock implements Block {


    /**
     * Stone Block constructor
     * */
    public StoneBlock(){}

    /**
     * Gets the Stone Block colour which is gray
     * @return "gray"
     * */
    public String getColour(){
        return "gray";
    }

    /**
     * Gets the type of the Block which is stone
     * @return "stone"
     * */
    public String getBlockType(){
        return "stone";
    }

    /**
     * Checks if the Stone Block is diggable.
     * @return false
     * */
    public boolean isDiggable(){
        return false;
    }

    /**
     * Checks if the Stone Block is moveable. Returns false.
     * @return false
     * */
    public boolean isMoveable(){
        return false;
    }

    /**
     * Checks if the Stone Block is carryable.Returns false.
     * @return false
     * */
    public boolean isCarryable(){
        return false;
    }
}
