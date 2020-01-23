import java.util.LinkedList;
import java.util.List;

/**
 * A Player who modifies the map
 * Manages an inventory of Blocks
 * Maintains a position in the map (by maintaining the current tile that the
 * Builder is on)
 */
public class Builder {
    private String name;
    private Tile startingTile;
    private List<Block> startingInventory = new LinkedList<>();

    public Builder(String name, Tile startingTile) {
        if (name != null && startingTile != null) {
            this.name = name;
            this.startingTile = startingTile;
        }
    }

    public Builder(String name, Tile startingTile,
                   List<Block> startingInventory) throws InvalidBlockException {
        if (name != null && startingTile != null && startingInventory != null) {
            for (Block block : startingInventory) {
                if (!block.isCarryable()) {
                    throw new InvalidBlockException();
                }
            }
            this.name = name;
            this.startingTile = startingTile;
            this.startingInventory = startingInventory;
        }
    }

    public String getName() {
        return this.name;
    }

    public Tile getCurrentTile() {
        return this.startingTile;
    }
    private void setCurrentTile(Tile newTile){
        this.startingTile = newTile;
    }

    public List<Block> getInventory() {
        return this.startingInventory;
    }

    /**
     * Drop a block from inventory on the top of the current tile
     * The block at inventoryIndex should be removed from the Builder's
     * inventory, and added to the Builder's current tile.
     * Blocks can only be dropped on tiles with less than 8 blocks, or tiles
     * with less than 3 blocks if a GroundBlock.
     */
    public void dropFromInventory(int inventoryIndex) throws TooHighException
            , InvalidBlockException {
        if (inventoryIndex < 0 || inventoryIndex >= this.getInventory().size()) {
            throw new InvalidBlockException();
        }
        Block block = this.getInventory().remove(inventoryIndex);
        this.getCurrentTile().placeBlock(block);
    }

    public void digOnCurrentTile() throws TooLowException,
            InvalidBlockException {
        if (this.getCurrentTile().getBlocks().size() == 0) {
            throw new TooLowException();
        } else if (!this.getCurrentTile().getTopBlock().isDiggable()) {
            throw new InvalidBlockException();
        } else if (!this.getCurrentTile().getTopBlock().isCarryable()) {
            this.getCurrentTile().removeTopBlock();
            return;
        }
        this.getInventory().add(this.getCurrentTile().dig());
    }

    public boolean canEnter(Tile newTile) {
        if (newTile == null) {
            return false;
        }
        if (this.getCurrentTile().getExits().containsValue(newTile)) {
            if ((Math.abs(this.getCurrentTile().getBlocks().size() - newTile.getBlocks().size()) <= 1)) {
                return true;
            }
        }
        return false;
    }

    public void moveTo(Tile newTile) throws NoExitException{
        if(canEnter(newTile)){
            this.setCurrentTile(newTile);
            return;
        }
        throw new NoExitException();
    }
}


