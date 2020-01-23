/**
 * A Grass tyle of a Block
 * */
public class GrassBlock extends GroundBlock {

    /**
     * Constructs a Grass Block
     * */
    public GrassBlock(){}

    /**
     * Gets the colour of the Grass block
     * @return "green"-String representing the block's colour
     * */
    public String getColour(){
        return "green";
    }

    /**
     * Gets the block type of the Grass Block
     * @return "grass"-String representing the block's type
     * */
    public String getBlockType(){
        return "grass";
    }

    /**
     * Checks if the Block is carryable
     * @return false- The Block cannot be carried
     * */
    public boolean isCarryable(){
        return false;
    }
}
