/**
 *A Soil Block
 * */
public class SoilBlock extends GroundBlock {

    /**
     * Constructs a Soil Block
     * */
    public SoilBlock(){}

    /**
     * Gets the colour of the Soil Block
     * @return "black"-String, represents the Block's colour
     * */
    public String getColour(){
        return "black";
    }

    /**
     * Gets the type of the Soil Block
     * @return "soil"-String, representing the Block's type
     * */
    public String getBlockType(){
        return "soil";
    }

    /**
     * Checks if the Soil Block is carryable
     * @return true- The Block can be carried
     * */
    public boolean isCarryable(){
        return true;
    }
}
