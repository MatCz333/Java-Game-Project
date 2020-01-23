import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class TileTest {
    @Test
    public void firstConstructorTest(){
        Tile tile = new Tile();
        assertEquals(Tile.class.getSuperclass(), java.lang.Object.class);
        assertEquals(Tile.class.getConstructors().length, 2);
        assertEquals(tile.getBlocks().size(),3);
        assertTrue(tile.getBlocks().get(0) instanceof SoilBlock );
        assertTrue(tile.getBlocks().get(1) instanceof SoilBlock );
        assertTrue(tile.getBlocks().get(2) instanceof GrassBlock );
        assertEquals(tile.getExits().size(),0);

    }

    @Test(expected = TooHighException.class)
    public void secondConstructorTest() throws TooHighException {
        List<Block> startingBlocks = new ArrayList<>();
        SoilBlock soilBlock = new SoilBlock();
        GrassBlock grassBlock = new GrassBlock();
        GroundBlock groundBlock = new GrassBlock();
        GroundBlock groundBlock1 = new SoilBlock();
        startingBlocks.add(soilBlock);
        startingBlocks.add(soilBlock);
        startingBlocks.add(grassBlock);
        Tile tile = new Tile(startingBlocks);
        assertEquals(tile.getBlocks().size(),3);
        assertTrue(tile.getBlocks().get(0) instanceof SoilBlock );
        assertTrue(tile.getBlocks().get(1) instanceof SoilBlock );
        assertTrue(tile.getBlocks().get(2) instanceof GrassBlock );
       assertEquals(tile.getExits().size(),0);
       startingBlocks.add(groundBlock);
        Tile tile1 = new Tile(startingBlocks);
        startingBlocks.add(soilBlock);
        startingBlocks.add(soilBlock);
        startingBlocks.add(grassBlock);
        startingBlocks.add(grassBlock);
        startingBlocks.add(grassBlock);
        Tile tile2 = new Tile(startingBlocks);

        }

    @Test(expected = TooHighException.class)
    public void secondConstructorTest1() throws TooHighException {
        List<Block> startingBlocks = new ArrayList<>();
        SoilBlock soilBlock = new SoilBlock();
        GrassBlock grassBlock = new GrassBlock();
        GroundBlock groundBlock = new GrassBlock();
        startingBlocks.add(soilBlock);
        startingBlocks.add(soilBlock);
        startingBlocks.add(grassBlock);
        startingBlocks.add(groundBlock);
        startingBlocks.add(soilBlock);
        startingBlocks.add(soilBlock);
        startingBlocks.add(grassBlock);
        startingBlocks.add(grassBlock);
        startingBlocks.add(grassBlock);
        Tile tile2 = new Tile(startingBlocks);

    }
    @Test(expected = NoExitException.class)
    public void getExitsTest() throws NoExitException {
        Tile tile = new Tile();
        Tile tile1 = new Tile();
        Tile tile2 = new Tile();
        assertEquals(tile.getExits().size(),0);
        tile.addExit("North",tile1);
        tile.addExit("North",tile1);
        assertEquals(tile.getExits().size(),1);
        tile.addExit("North",tile2);
        assertEquals(tile.getExits().size(),1);
        assertTrue(tile.getExits().containsKey("North"));
        tile.addExit(null,tile1);
        }
    @Test(expected = NoExitException.class)
    public void getExitsTest1() throws NoExitException {
        Tile tile = new Tile();
        Tile tile1 = new Tile();
        Tile tile2 = new Tile();
        assertEquals(tile.getExits().size(),0);
        tile.addExit("North",null);
    }


    @Test
    public void getBlocks() throws TooHighException {

        List<Block> startingBlocks = new ArrayList<>();
        Block block = new GrassBlock();
        Block block1 = new GrassBlock();
        Block block2 = new SoilBlock();
        startingBlocks.add(block);
        startingBlocks.add(block1);
        startingBlocks.add(block2);
        Tile tile = new Tile(startingBlocks);
        assertEquals(tile.getBlocks().size(),3);
        startingBlocks.add(block2);
        assertEquals(tile.getBlocks().size(),3);
    }
    @Test(expected = TooLowException.class)
    public void getTopBlock() throws TooHighException, TooLowException {
        List<Block> startingBlocks = new ArrayList<>();
        List<Block> startingBlocks1 = new ArrayList<>();
        Block block = new GrassBlock();
        Block block1 = new GrassBlock();
        Block block2 = new SoilBlock();
        startingBlocks.add(block);
        startingBlocks.add(block1);
        startingBlocks.add(block2);
        Tile tile = new Tile(startingBlocks);
        assertEquals(tile.getTopBlock(),block2);
        Tile tile1 = new Tile(startingBlocks1);
        assertEquals(tile.getTopBlock(),block2);
        assertEquals(tile.getBlocks().size(),3);
        tile1.getTopBlock();

    }

    @Test(expected = TooLowException.class)
    public void removeTopBlock() throws TooHighException, TooLowException {
        List<Block> startingBlocks = new ArrayList<>();
        List<Block> startingBlocks1 = new ArrayList<>();
        Block block = new GrassBlock();
        Block block1 = new GrassBlock();
        Block block2 = new SoilBlock();
        startingBlocks.add(block);
        startingBlocks.add(block1);
        startingBlocks.add(block2);
        Tile tile = new Tile(startingBlocks);
        Tile tile1 = new Tile(startingBlocks1);
        assertEquals(tile.getBlocks().size(),3);
        tile.removeTopBlock();
        assertEquals(tile.getBlocks().size(),2);
        assertFalse(tile.getBlocks().contains(block2));
        tile1.removeTopBlock();

    }

    @Test(expected = NoExitException.class)
    public void removeExit() throws NoExitException {
        Tile tile = new Tile();
        Tile tile1 = new Tile();
        tile.addExit("North",tile1);
        assertEquals(tile.getExits().size(),1);
        tile.removeExit("North");
        assertEquals(tile.getExits().size(),0);
        assertFalse(tile.getExits().containsKey("North"));
       tile.removeExit("North ");
        }
    @Test(expected = NoExitException.class)
    public void removeExit1() throws NoExitException {
        Tile tile = new Tile();
        Tile tile1 = new Tile();
        tile.addExit("North",tile1);
        assertEquals(tile.getExits().size(),1);
        for(String s: tile.getExits().keySet()){
            assertNotNull(s);
        }
        tile.removeExit("NoSuchExitExists");
    }

    @Test(expected = TooLowException.class)
    public void dig() throws TooHighException, InvalidBlockException, TooLowException {
        List<Block> startingBlocks = new ArrayList<>();
        List<Block> startingBlocks1 = new ArrayList<>();
        Block block = new GrassBlock();
        Block block1 = new GrassBlock();
        Block block2 = new SoilBlock();
        startingBlocks.add(block);
        startingBlocks.add(block1);
        startingBlocks.add(block2);
        Tile tile = new Tile(startingBlocks);
        Tile tile1 = new Tile(startingBlocks1);
        assertEquals(tile.dig(),block2);
        assertEquals(tile.getBlocks().size(),2);
        tile1.dig();

    }

    @Test(expected = InvalidBlockException.class)
    public void dig1() throws TooHighException, InvalidBlockException,
            TooLowException {
        List<Block> startingBlocks = new ArrayList<>();
        List<Block> startingBlocks1 = new ArrayList<>();
        Block block = new GrassBlock();
        Block block1 = new GrassBlock();
        Block block2 = new SoilBlock();
        StoneBlock cantDigBlock = new StoneBlock();
        startingBlocks.add(block);
        startingBlocks.add(block1);
        startingBlocks.add(block2);
        startingBlocks.add(cantDigBlock);
        Tile tile = new Tile(startingBlocks);
        Tile tile1 = new Tile(startingBlocks1);
        assertEquals(tile.dig(), block2);
        assertEquals(tile.getBlocks().size(), 2);
        tile1.dig();
    }

    /**
     *Attempt to move the current top block to another tile. Remove the top
     * block (given by getTopBlock()) from this tile and add it to the tile
     * at the named exit (exitName in getExits()), if the block is moveable
     * (block.isMoveable()) and the height of that tile (the number of blocks
     * given by getBlocks().size()) is less than the current tile *before*
     * the move.
     * Handle the following cases:
     * If the exit is null, or does not exist, throw a NoExitException
     * If the number of blocks on the target tile is ≥ to this one, throw a
     * TooHighException
     * If the block is not moveable, throw a InvalidBlockException
     * */
    @Test(expected = NoExitException.class)
    public void moveBlock() throws TooHighException, NoExitException, TooLowException, InvalidBlockException {
        List<Block> startingBlocks = new ArrayList<>();
        List<Block> startingBlocks1 = new ArrayList<>();
        Block block = new GrassBlock();
        Block block1 = new GrassBlock();
        Block block2 = new SoilBlock();
        Block block3= new WoodBlock();
        startingBlocks.add(block);
        startingBlocks.add(block1);
        startingBlocks.add(block2);
        startingBlocks.add(block3);
        Tile tile = new Tile(startingBlocks);
        Tile tile1 = new Tile(startingBlocks1);
        assertEquals(tile1.getBlocks().size(),0);
        assertEquals(tile.getBlocks().size(),4);
        tile.addExit("North",tile1);
        tile1.addExit("North",tile);
        tile.moveBlock("North");
        assertTrue(tile1.getBlocks().contains(block3));
        assertEquals(tile.getBlocks().size(),3);
        assertEquals(tile1.getBlocks().size(),1);
        tile.moveBlock("NoSuchExit");
    }

    @Test(expected = TooHighException.class)
    public void moveBlock1() throws TooHighException, NoExitException,
            TooLowException, InvalidBlockException {
        List<Block> startingBlocks = new ArrayList<>();
        List<Block> startingBlocks1 = new ArrayList<>();
        Block block = new GrassBlock();
        Block block1 = new GrassBlock();
        Block block2 = new SoilBlock();
        Block block3= new WoodBlock();
        startingBlocks.add(block);
        startingBlocks1.add(block1);
        startingBlocks1.add(block2);
        startingBlocks.add(block3);
        Tile tile = new Tile(startingBlocks);
        Tile tile1 = new Tile(startingBlocks1);
        tile.addExit("North",tile1);
        tile1.addExit("North",tile);
        tile.moveBlock("North");
    }

    @Test(expected = InvalidBlockException.class)
    public void moveBlock2() throws TooHighException, NoExitException,
            TooLowException, InvalidBlockException {
        List<Block> startingBlocks = new ArrayList<>();
        List<Block> startingBlocks1 = new ArrayList<>();
        Block block = new GrassBlock();
        Block block1 = new GrassBlock();
        Block block2 = new SoilBlock();
        startingBlocks.add(block);
        startingBlocks.add(block1);
        startingBlocks1.add(block2);
        Tile tile = new Tile(startingBlocks);
        Tile tile1 = new Tile(startingBlocks1);
        tile.addExit("North",tile1);
        tile1.addExit("North",tile);
        tile.moveBlock("North");
    }

    @Test(expected = InvalidBlockException.class)
    public void placeBlock() throws TooHighException, InvalidBlockException, TooLowException {
        List<Block> startingBlocks = new ArrayList<>();
        Block block = new GrassBlock();
        Block block2 = new GrassBlock();
        Block block1 = new WoodBlock();
        startingBlocks.add(block);
        startingBlocks.add(block2);
        Tile tile = new Tile(startingBlocks);
        tile.placeBlock(block);
        assertEquals(tile.getBlocks().get(0),block);
        tile.placeBlock(block1);
        assertEquals(tile.getTopBlock(),block1);
        tile.placeBlock(null);
    }

    @Test(expected = TooHighException.class)
    public void placeBlock1() throws TooHighException, InvalidBlockException,
            TooLowException {
        Block block = new GrassBlock();
        Block block1 = new WoodBlock();
        Tile tile = new Tile();
        tile.placeBlock(block1);
        tile.placeBlock(block1);
        tile.placeBlock(block1);
        tile.placeBlock(block1);
        tile.placeBlock(block1);
        tile.placeBlock(block1);


    }

    @Test(expected = TooHighException.class)
    public void placeBlock2() throws TooHighException, InvalidBlockException,
            TooLowException {
        Block block = new GrassBlock();
        Block block1 = new WoodBlock();
        Tile tile = new Tile();
        tile.placeBlock(block );
    }
}