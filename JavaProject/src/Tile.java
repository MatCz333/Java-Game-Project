import java.io.Serializable;
import java.util.*;

/**
 * Tiles for a map.
 * Contains Blocks
 * Maintains a mapping between exit names and other tiles.
 */
public class Tile implements Serializable {


    private Map<String, Tile> exitMap;
    private List<Block> startingBlocks;

    public Tile() {
        exitMap = new LinkedHashMap<>();
        this.startingBlocks = new LinkedList<>();
        startingBlocks.add(new SoilBlock());
        startingBlocks.add(new SoilBlock());
        startingBlocks.add(new GrassBlock());
    }

    public Tile(List<Block> startingBlocks) throws TooHighException {
        this.startingBlocks = new LinkedList<>();
        exitMap = new LinkedHashMap<>();
        if (startingBlocks != null || startingBlocks.size() != 0) {
            if (startingBlocks.size() > 8) {
                throw new TooHighException();
            }
            for (Block block : startingBlocks) {
                if (block instanceof GroundBlock
                        && startingBlocks.indexOf(block) >= 3) {
                    throw new TooHighException();
                }
                this.startingBlocks.add(block);
            }
        }
    }

    public static void main(String[] args) throws TooHighException,
            TooLowException, InvalidBlockException {
        List<Block> startingBlocks = new ArrayList<>();
        startingBlocks.add(new SoilBlock());
        startingBlocks.add(new SoilBlock());
        startingBlocks.add(new GrassBlock());
//        startingBlocks.add(new StoneBlock());
//        startingBlocks.add(new StoneBlock());
        Tile tile = new Tile(startingBlocks);
        Tile tile1 = new Tile();
        System.out.println(tile.getBlocks().size());
        tile.dig();
        System.out.println(tile.getBlocks().size());
        tile.dig();
        System.out.println(tile.getBlocks().size());
        tile.dig();
        System.out.println(tile.getBlocks().size());
    }

    /**
     * Add a new exit to this tile
     * The Map returned by getExits() must now include an entry (name, target).
     */
    public void addExit(String name, Tile target) throws NoExitException {
        if (name == null || target == null) {
            throw new NoExitException();
        }
        this.getExits().put(name, target);
    }

    /**
     * Attempt to dig in the current tile.
     */
    public Block dig() throws TooLowException, InvalidBlockException {
        if (this.getBlocks().size() == 0 || this.getTopBlock() == null) {
            throw new TooLowException();
        }
        if (!this.getTopBlock().isDiggable()) {
            throw new InvalidBlockException();
        }
        Block topBlock = this.getTopBlock();
        this.removeTopBlock();
        return topBlock;
    }


    /**
     * What Blocks are on this Tile?
     * Order of blocks returned must be in order of height.
     */
    public List<Block> getBlocks() {
        return this.startingBlocks;
    }

    /**
     * What exits are there from this Tile?
     * No ordering is required.
     */
    public Map<String, Tile> getExits() {
        return this.exitMap;
    }

    /**
     * Return the block that is the top block on the tile.
     */
    public Block getTopBlock() throws TooLowException {
        if (this.getBlocks().size() == 0) {
            throw new TooLowException();
        }
        return this.getBlocks().get(this.getBlocks().size() - 1);
    }

    /**
     * Attempt to move the current top block to another tile.
     */
    public void moveBlock(String exitName) throws TooHighException,
            InvalidBlockException, NoExitException, TooLowException {
        Block block = this.getTopBlock();
        if (!this.getExits().containsKey(exitName) || exitName == null || exitName.equals("") ) {
            throw new NoExitException();
        }  else if (this.getExits().get(exitName).getBlocks().size() > this.getBlocks().size()) {
            throw new TooHighException();
        }
        else if (!block.isMoveable()) {
            throw new InvalidBlockException();
        }
        this.getExits().get(exitName).placeBlock(block);
        this.removeTopBlock();
    }


    /**
     * Place a block on a tile.
     */
    public void placeBlock(Block block) throws TooHighException,
            InvalidBlockException {
        if (block == null) {
            throw new InvalidBlockException();
        }
        if (this.getBlocks().size() >= 8 || (block instanceof GroundBlock && this.getBlocks().size() >= 3)) {
            throw new TooHighException();
        }
        this.getBlocks().add(block);
    }

    /**
     * Remove an exit from this tile
     * The Map returned by getExits() must no longer have the key name.
     */
    public void removeExit(String name) throws NoExitException {
        if (!this.getExits().containsKey(name) || name == null) {
            throw new NoExitException();
        }
        this.getExits().remove(name);
    }

    /**
     * Remove the block on top of the tile
     * Throw a TooLowException if there are no blocks on the tile
     */
    public void removeTopBlock() throws TooLowException {
        if (this.getBlocks().size() == 0 || this.getBlocks() == null) {
            throw new TooLowException();
        }
        this.getBlocks().remove(this.getBlocks().size() - 1);
    }
}
