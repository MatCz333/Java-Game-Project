
/**
 * Interface for a Block
 * */

public interface Block {

    /**
     * Gets the colour of the Block
     * @return colour of the block in the form of String
     * */

    public String getColour();

    /**
     * Returns type of a Block
     * @return type of a Block in the form of String
     * */
    public String getBlockType();

    /**
     * Is the Block diggable?
     * If it is true the Block can be dug out (removed)
     * @return true if diggable, false if not
     */

    public boolean isDiggable();

    /**
     * Is the Block movable?
     * If it is true the Block can be moved to adjacent tiles
     * @return true if movable, false if not
     */

    public boolean isMoveable();

    /**
     * Is the Block carryable?
     * If it is true the Block can be added to builder's inventory
     * @return true if carryable, false if not
     */

    public boolean isCarryable();
}
