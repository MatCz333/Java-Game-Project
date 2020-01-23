import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class SparseTileArrayTest {
	 
	 @Test
	 public void testContructors () throws WorldMapInconsistentException {
		  Position position = new Position(2, 0);
		  Tile tile = new Tile();
		  SparseTileArray sparseTileArray = new SparseTileArray();
		  assertEquals(SparseTileArray.class.getSuperclass(),
					 java.lang.Object.class);
		  assertEquals(SparseTileArray.class.getConstructors().length, 1);
		  sparseTileArray.addLinkedTiles(tile, position.getX(), position.getY());
		  assertEquals(sparseTileArray.getTile(position), tile);
		  assertEquals(sparseTileArray.getTiles().size(), 1);
		  sparseTileArray = new SparseTileArray();
		  assertNull(sparseTileArray.getTile(position));
		  assertEquals(sparseTileArray.getTiles().size(), 0);
	 }
	 
	 @Test
	 public void getTile ()
				throws NoExitException, WorldMapInconsistentException {
		  SparseTileArray sparseTileArray = new SparseTileArray();
		  Position position = new Position(0, 0);
		  Position position1 = new Position(0, -1);
		  Position position2 = new Position(0, -2);
		  Position position3 = new Position(-1, -2);
		  Position position4 = new Position(1, 0);
		  Position position5 = new Position(-1, 0);
		  Position noSuchPosition = new Position(-50, 199);
		  Tile tile = new Tile();
		  Tile tile1 = new Tile();
		  Tile tile2 = new Tile();
		  Tile tile3 = new Tile();
		  Tile tile4 = new Tile();
		  Tile tile5 = new Tile();
		  tile.addExit("north", tile1);
		  tile1.addExit("north", tile2);
		  tile1.addExit("south", tile);
		  tile2.addExit("west", tile3);
		  tile.addExit("east", tile4);
		  tile.addExit("west", tile5);
		  sparseTileArray.addLinkedTiles(tile, position.getX(), position.getY());
		  assertEquals(sparseTileArray.getTile(position), tile);
		  assertEquals(sparseTileArray.getTile(position1), tile1);
		  assertEquals(sparseTileArray.getTile(position2), tile2);
		  assertEquals(sparseTileArray.getTile(position3), tile3);
		  assertNull(sparseTileArray.getTile(noSuchPosition));
		  assertEquals(sparseTileArray.getTile(position4), tile4);
		  assertEquals(sparseTileArray.getTile(position5), tile5);
		  assertNotNull(sparseTileArray.getTile(position5));
		  
	 }
	 
	 @Test(expected = UnsupportedOperationException.class)
	 public void getTiles ()
				throws NoExitException, WorldMapInconsistentException {
		  Position position = new Position(0, 0);
		  Tile tile = new Tile();
		  Tile tile1 = new Tile();
		  Tile tile2 = new Tile();
		  Tile tile3 = new Tile();
		  Tile tile4 = new Tile();
		  Tile tile5 = new Tile();
		  tile.addExit("north", tile1);
		  tile.addExit("east", tile2);
		  tile.addExit("west", tile3);
		  tile.addExit("south", tile4);
		  tile1.addExit("north", tile5);
		  SparseTileArray sparseTileArray = new SparseTileArray();
		  assertTrue(sparseTileArray.getTiles().isEmpty());
		  sparseTileArray.addLinkedTiles(tile, 0, 0);
		  assertEquals(sparseTileArray.getTiles().get(0), tile);
		  assertEquals(sparseTileArray.getTiles().get(0).getExits().get("north"),
					 tile1);
		  assertEquals(sparseTileArray.getTiles().get(1), tile1);
		  assertEquals(sparseTileArray.getTiles().get(0).getExits().get("east"),
					 tile2);
		  assertEquals(sparseTileArray.getTiles().get(2), tile2);
		  assertEquals(sparseTileArray.getTiles().get(0).getExits().get("south"),
					 tile4);
		  assertEquals(sparseTileArray.getTiles().get(3), tile4);
		  assertEquals(sparseTileArray.getTiles().get(0).getExits().get("west"),
					 tile3);
		  assertEquals(sparseTileArray.getTiles().get(4), tile3);
		  assertEquals(sparseTileArray.getTiles().get(5), tile5);
		  assertEquals(sparseTileArray.getTiles().size(), 6);
		  sparseTileArray.getTiles().remove(tile);
		  assertEquals(sparseTileArray.getTiles().size(), 6);
	 }
	 
	 @Test(expected = WorldMapInconsistentException.class)
	 public void addLinkedTiles ()
				throws NoExitException, WorldMapInconsistentException {
		  Position position = new Position(0, 0);
		  Tile tile = new Tile();
		  Tile tile1 = new Tile();
		  Tile tile2 = new Tile();
		  tile.addExit("north", tile1);
		  tile1.addExit("south", tile2);
		  SparseTileArray sparseTileArray = new SparseTileArray();
		  sparseTileArray.addLinkedTiles(tile, position.getX(), position.getY());
	 }
	 
	 @Test(expected = WorldMapInconsistentException.class)
	 public void addLinkedTiles1 () throws NoExitException,
				WorldMapInconsistentException {
		  Position position = new Position(-1, 2);
		  Tile tile = new Tile();
		  Tile tile1 = new Tile();
		  Tile tile2 = new Tile();
		  tile.addExit("west", tile1);
		  tile1.addExit("east", tile2);
		  SparseTileArray sparseTileArray = new SparseTileArray();
		  sparseTileArray.addLinkedTiles(tile, position.getX(), position.getY());
	 }
	 
	 @Test(expected = WorldMapInconsistentException.class)
	 public void addLinkedTiles2 () throws NoExitException,
				WorldMapInconsistentException {
		  Position position = new Position(-1, 2);
		  Tile tile = new Tile();
		  Tile tile1 = new Tile();
		  Tile tile2 = new Tile();
		  tile.addExit("east", tile1);
		  tile1.addExit("west", tile2);
		  SparseTileArray sparseTileArray = new SparseTileArray();
		  sparseTileArray.addLinkedTiles(tile, position.getX(), position.getY());
	 }
	 
	 @Test(expected = WorldMapInconsistentException.class)
	 public void addLinkedTiles3 () throws NoExitException,
				WorldMapInconsistentException {
		  Position position = new Position(-1, 2);
		  Tile tile = new Tile();
		  Tile tile1 = new Tile();
		  Tile tile2 = new Tile();
		  tile.addExit("south", tile1);
		  tile1.addExit("north", tile2);
		  SparseTileArray sparseTileArray = new SparseTileArray();
		  sparseTileArray.addLinkedTiles(tile, position.getX(), position.getY());
	 }
	 
}