/**
 * A Wood Block
 * */
public class WoodBlock implements Block {

    /**
     * A Wood Block constructor
     * */
    public WoodBlock(){}

    /**
     * Gets the colour of the Block.
     * @return "brown"-String, represents the colour of the Block
     * */
    public String getColour(){
        return "brown";
    }

    /**
     *Gets the block's Type
     * @return "wood"-String, representing the Block's type
     * */
    @Override
    public String getBlockType() {
        return "wood";
    }

    /**
     * Checks if the Block is diggable
     * @return true-Always return's true
     * */
    @Override
    public boolean isDiggable(){
        return true;
    }

    /**
     * Checks if the Block is carryable
     * @return true-Always returns true. This Block can be carried
     * */
    @Override
    public boolean isCarryable() {
        return true;
    }

    /**
     * Checks if the Block can be moved
     * @return true- This Block can be moved around.
     * */
    @Override
    public boolean isMoveable() {
        return true;
    }
}
