

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;

public class ActionTest {
	 
	 @Test
	 public void constructorTest () {
		  int primaryAction = 0;
		  int primaryAction1 = 1;
		  int primaryAction2 = 2;
		  int primaryAction3 = 3;
		  String secondaryAction = "MOVE_BUILDER";
		  String secondaryAction1 = "MOVE_BLOCK";
		  String secondaryAction2 = "DIG";
		  String secondaryAction3 = "DROP";
		  assertEquals(Action.class.getSuperclass(), java.lang.Object.class);
		  assertEquals(Action.class.getConstructors().length, 1);
		  Action action = new Action(primaryAction, secondaryAction);
		  Action action1 = new Action(primaryAction1, secondaryAction1);
		  Action action2 = new Action(primaryAction2, secondaryAction2);
		  Action action3 = new Action(primaryAction3, secondaryAction3);
		  assertEquals(action.getPrimaryAction(), primaryAction);
		  assertEquals(action.getSecondaryAction(), secondaryAction);
		  assertEquals(action1.getPrimaryAction(), primaryAction1);
		  assertEquals(action1.getSecondaryAction(), secondaryAction1);
		  assertEquals(action2.getPrimaryAction(), primaryAction2);
		  assertEquals(action2.getSecondaryAction(), secondaryAction2);
		  assertEquals(action3.getPrimaryAction(), primaryAction3);
		  assertEquals(action3.getSecondaryAction(), secondaryAction3);
	 }
	 
	 @Test(expected = ActionFormatException.class)
	 public void loadAction () throws ActionFormatException {
		  int DIG = 2;
		  int DROP = 3;
		  int MOVE_BLOCK = 1;
		  int MOVE_BUILDER = 0;
		  BufferedReader dig = new BufferedReader(new StringReader("DIG"));
		  BufferedReader drop = new BufferedReader(new StringReader("DROP " +
					 "anythingHere"));
		  BufferedReader moveBuilder = new BufferedReader(new StringReader(
					 "MOVE_BUILDER anythingHere"));
		  BufferedReader moveBlock = new BufferedReader(new StringReader(
					 "MOVE_BLOCK anythingHere"));
		  BufferedReader error = new BufferedReader(new StringReader(
					 "error here 1"));
		  Action action = Action.loadAction(dig);
		  assertEquals(action.getPrimaryAction(), DIG);
		  assertEquals(action.getSecondaryAction(), "");
		  Action action1 = Action.loadAction(drop);
		  ;
		  assertEquals(action1.getPrimaryAction(), DROP);
		  assertEquals(action1.getSecondaryAction(), "anythingHere");
		  Action action2 = Action.loadAction(moveBuilder);
		  ;
		  assertEquals(action2.getPrimaryAction(), MOVE_BUILDER);
		  assertEquals(action2.getSecondaryAction(), "anythingHere");
		  Action action3 = Action.loadAction(moveBlock);
		  ;
		  assertEquals(action3.getPrimaryAction(), MOVE_BLOCK);
		  assertEquals(action3.getSecondaryAction(), "anythingHere");
		  Action.loadAction(error);
	 }
	 
	 @Test(expected = ActionFormatException.class)
	 public void loadAction1 () throws ActionFormatException {
		  BufferedReader exceptionTwoSpaced = new BufferedReader(new StringReader(
					 "DIG  "));
		  Action.loadAction(exceptionTwoSpaced);
	 }
	 
	 @Test(expected = ActionFormatException.class)
	 public void loadAction2 () throws ActionFormatException {
		  BufferedReader exceptionSingleSpace =
					 new BufferedReader(new StringReader(
								"DIG "));
		  Action.loadAction(exceptionSingleSpace);
	 }
	 
	 @Test(expected = ActionFormatException.class)
	 public void loadAction3 () throws ActionFormatException {
		  BufferedReader noSuchAction =
					 new BufferedReader(new StringReader(
								"RANDOM_EXCEPTION"));
		  Action.loadAction(noSuchAction);
	 }
	 
	 @Test(expected = ActionFormatException.class)
	 public void loadAction4 () throws ActionFormatException {
		  BufferedReader actionWithoutSecondary =
					 new BufferedReader(new StringReader(
								"MOVE_BUILDER"));
		  Action.loadAction(actionWithoutSecondary);
	 }
	 
	 @Test(expected = ActionFormatException.class)
	 public void loadAction5 () throws ActionFormatException {
		  BufferedReader actionWithoutSecondary =
					 new BufferedReader(new StringReader(
								"MOVE_BLOCK"));
		  Action.loadAction(actionWithoutSecondary);
	 }
	 
	 @Test(expected = ActionFormatException.class)
	 public void loadAction6 () throws ActionFormatException {
		  BufferedReader actionWithoutSecondary =
					 new BufferedReader(new StringReader(
								"DROP "));
		  Action.loadAction(actionWithoutSecondary);
	 }
	 
	 @Test(expected = ActionFormatException.class)
	 public void loadAction7 () throws ActionFormatException {
		  BufferedReader exceptionThreeSpaces =
					 new BufferedReader(new StringReader(
								"DROP a a "));
		  Action.loadAction(exceptionThreeSpaces);
		  
	 }
	 
	 @Test(expected = ActionFormatException.class)
	 public void loadAction8 () throws ActionFormatException, IOException {
		  BufferedReader reader =
					 new BufferedReader(new StringReader(
								"MOVE_BUILDER north"));
		  reader.close();
		  Action.loadAction(reader);
		  
	 }
	 
	 @Test()
	 public void loadAction9 () throws ActionFormatException, IOException,
				WorldMapInconsistentException, NoExitException {
		  BufferedReader reader =
					 new BufferedReader(new StringReader(
								"MOVE_BUILDER north\nDIG"));
		  Action.loadAction(reader);
		  Action.loadAction(reader);
	 }
	 
	 @Test
	 public void processAction () throws ActionFormatException, NoExitException
				, InvalidBlockException, WorldMapInconsistentException, TooHighException, TooLowException {
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
		  tile5.addExit("south", tile1);
		  List<Block> inventoryList = new ArrayList<>();
		  Block soilBlock = new SoilBlock();
		  Block soilBlock1 = new SoilBlock();
		  Block woodBlock = new WoodBlock();
		  inventoryList.add(soilBlock);
		  inventoryList.add(woodBlock);
		  inventoryList.add(soilBlock1);
		  Builder builder = new Builder("Tester", tile, inventoryList);
		  WorldMap worldMap = new WorldMap(tile, position, builder);
		  BufferedReader dig =
					 new BufferedReader(new StringReader(
								"DIG"));
		  
		  assertEquals(tile.getBlocks().size(), 3);
		  Action digAction = Action.loadAction(dig);
		  ByteArrayOutputStream outContent = new ByteArrayOutputStream();
		  System.setOut(new PrintStream(outContent));
		  Action.processAction(digAction, worldMap);
		  assertEquals(
					 "Top block on current tile removed" + System.lineSeparator(),
					 outContent.toString());
		  assertEquals(tile.getBlocks().size(), 2);
		  assertEquals(worldMap.getTile(position).getBlocks().size(), 2);
		  PrintStream originalOut = System.out;
		  System.setOut(new PrintStream(originalOut));
		  BufferedReader moveBuilder =
					 new BufferedReader(new StringReader(
								"MOVE_BUILDER north"));
		  Action moveBuilderAction = Action.loadAction(moveBuilder);
		  outContent = new ByteArrayOutputStream();
		  System.setOut(new PrintStream(outContent));
		  Action.processAction(moveBuilderAction, worldMap);
		  assertEquals("Moved builder north" + System.lineSeparator(),
					 outContent.toString());
		  assertEquals(builder.getCurrentTile(), tile1);
		  System.out.println(tile5);
		  System.out.println(builder.getCurrentTile());
		  outContent = new ByteArrayOutputStream();
		  assertTrue(builder.getCurrentTile() == tile1);
		  BufferedReader moveBuilder1 =
					 new BufferedReader(new StringReader(
								"MOVE_BUILDER west"));
		  Action moveBuilderAction1 = Action.loadAction(moveBuilder1);
		  outContent = new ByteArrayOutputStream();
		  System.setOut(new PrintStream(outContent));
		  Action.processAction(moveBuilderAction1, worldMap);
		  assertEquals("No exit this way" + System.lineSeparator(),
					 outContent.toString());
		  outContent = new ByteArrayOutputStream();
		  System.setOut(new PrintStream(outContent));
		  BufferedReader moveBlock =
					 new BufferedReader(new StringReader(
								"MOVE_BLOCK north"));
		  Action.processAction(Action.loadAction(moveBlock), worldMap);
		  assertEquals("Too high" + System.lineSeparator(),
					 outContent.toString());
		  WoodBlock woodBlock1 = new WoodBlock();
		  tile1.placeBlock(woodBlock1);
		  outContent = new ByteArrayOutputStream();
		  System.setOut(new PrintStream(outContent));
		  BufferedReader moveBlock2 =
					 new BufferedReader(new StringReader(
								"MOVE_BLOCK north"));
		  Action.processAction(Action.loadAction(moveBlock2), worldMap);
		  assertEquals("Moved block north" + System.lineSeparator(),
					 outContent.toString());
		  assertEquals(tile5.getBlocks().size(), 4);
		  assertEquals(tile5.getTopBlock(), woodBlock1);
		  outContent = new ByteArrayOutputStream();
		  System.setOut(new PrintStream(outContent));
		  BufferedReader drop =
					 new BufferedReader(new StringReader(
								"DROP 3"));
		  Action.processAction(Action.loadAction(drop), worldMap);
		  assertEquals("Cannot use that block" + System.lineSeparator(),
					 outContent.toString());
	 }
	 
	 @Test
	 public void processAction1 ()
				throws NoExitException, InvalidBlockException, WorldMapInconsistentException, ActionFormatException, TooLowException, TooHighException {
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
		  tile5.addExit("south", tile1);
		  List<Block> inventoryList = new ArrayList<>();
		  Block soilBlock = new SoilBlock();
		  Block soilBlock1 = new SoilBlock();
		  Block woodBlock = new WoodBlock();
		  Block woodBlock1 = new WoodBlock();
		  inventoryList.add(soilBlock);
		  inventoryList.add(soilBlock1);
		  inventoryList.add(woodBlock);
		  Builder builder = new Builder("Tester", tile, inventoryList);
		  WorldMap worldMap = new WorldMap(tile, position, builder);
		  ByteArrayOutputStream outContent = new ByteArrayOutputStream();
		  System.setOut(new PrintStream(outContent));
		  BufferedReader drop =
					 new BufferedReader(new StringReader(
								"DROP 2"));
		  Action.processAction(Action.loadAction(drop), worldMap);
		  assertEquals("Dropped a block from inventory" + System.lineSeparator(),
					 outContent.toString());
		  assertEquals(tile.getBlocks().size(), 4);
		  outContent = new ByteArrayOutputStream();
		  System.setOut(new PrintStream(outContent));
		  BufferedReader drop1 =
					 new BufferedReader(new StringReader(
								"DROP 1"));
		  Action.processAction(Action.loadAction(drop1), worldMap);
		  assertEquals("Too high" + System.lineSeparator(),
					 outContent.toString());
		  outContent = new ByteArrayOutputStream();
		  System.setOut(new PrintStream(outContent));
		  BufferedReader drop2 =
					 new BufferedReader(new StringReader(
								"DROP NotAValidInteger"));
		  Action.processAction(Action.loadAction(drop2), worldMap);
		  assertEquals("Error: Invalid action" + System.lineSeparator(),
					 outContent.toString());
		  outContent = new ByteArrayOutputStream();
		  System.setOut(new PrintStream(outContent));
		  Action action = new Action(4, "");
		  Action.processAction(action, worldMap);
		  assertEquals("Error: Invalid action" + System.lineSeparator(),
					 outContent.toString());
		  outContent = new ByteArrayOutputStream();
		  System.setOut(new PrintStream(outContent));
		  Action action1 = new Action(-1, "");
		  Action.processAction(action1, worldMap);
		  assertEquals("Error: Invalid action" + System.lineSeparator(),
					 outContent.toString());
		  outContent = new ByteArrayOutputStream();
		  builder.digOnCurrentTile();
		  builder.digOnCurrentTile();
		  builder.digOnCurrentTile();
		  builder.digOnCurrentTile();
		  System.setOut(new PrintStream(outContent));
		  Action action2 = new Action(2, "");
		  Action.processAction(action2, worldMap);
		  assertEquals("Too low" + System.lineSeparator(),
					 outContent.toString());
		  Block stoneBlock = new StoneBlock();
		  tile.placeBlock(stoneBlock);
		  outContent = new ByteArrayOutputStream();
		  System.setOut(new PrintStream(outContent));
		  Action action3 = new Action(2, "");
		  Action.processAction(action3, worldMap);
		  assertEquals("Cannot use that block" + System.lineSeparator(),
					 outContent.toString());
		  
	 }
	 
	 @Test
	 public void processAction2 () throws NoExitException,
				InvalidBlockException, WorldMapInconsistentException, ActionFormatException, TooLowException, TooHighException {
		  
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
		  tile5.addExit("south", tile1);
		  List<Block> inventoryList = new ArrayList<>();
		  Block soilBlock = new SoilBlock();
		  Block soilBlock1 = new SoilBlock();
		  Block woodBlock = new WoodBlock();
		  Block woodBlock1 = new WoodBlock();
		  inventoryList.add(soilBlock);
		  inventoryList.add(soilBlock1);
		  inventoryList.add(woodBlock);
		  Builder builder = new Builder("Tester", tile, inventoryList);
		  WorldMap worldMap = new WorldMap(tile, position, builder);
		  ByteArrayOutputStream outContent = new ByteArrayOutputStream();
		  System.setOut(new PrintStream(outContent));
		  Action moveBlockSouth = new Action(1, "south");
		  tile4.removeTopBlock();
		  tile.placeBlock(woodBlock);
		  Action.processAction(moveBlockSouth, worldMap);
		  assertEquals("Moved block south" + System.lineSeparator(),
					 outContent.toString());
		  
		  outContent = new ByteArrayOutputStream();
		  System.setOut(new PrintStream(outContent));
		  Action moveBlockNorth = new Action(1, "north");
		  tile1.removeTopBlock();
		  tile.placeBlock(woodBlock);
		  Action.processAction(moveBlockNorth, worldMap);
		  assertEquals("Moved block north" + System.lineSeparator(),
					 outContent.toString());
		  
		  outContent = new ByteArrayOutputStream();
		  System.setOut(new PrintStream(outContent));
		  Action moveBlockWest = new Action(1, "west");
		  tile3.removeTopBlock();
		  tile.placeBlock(woodBlock);
		  Action.processAction(moveBlockWest, worldMap);
		  assertEquals("Moved block west" + System.lineSeparator(),
					 outContent.toString());
		  
		  outContent = new ByteArrayOutputStream();
		  System.setOut(new PrintStream(outContent));
		  Action moveBlockEast = new Action(1, "east");
		  tile2.removeTopBlock();
		  tile.placeBlock(woodBlock);
		  Action.processAction(moveBlockEast, worldMap);
		  assertEquals("Moved block east" + System.lineSeparator(),
					 outContent.toString());
		  
		  outContent = new ByteArrayOutputStream();
		  System.setOut(new PrintStream(outContent));
		  Action cannotUseBlock = new Action(1, "east");
		  tile2.removeTopBlock();
		  Action.processAction(moveBlockEast, worldMap);
		  assertEquals("Cannot use that block" + System.lineSeparator(),
					 outContent.toString());
		  Action movebuilder = new Action(Action.MOVE_BUILDER, "south");
		  
		  Action.processAction(movebuilder, worldMap);
		  
		  outContent = new ByteArrayOutputStream();
		  System.setOut(new PrintStream(outContent));
		  Action noExit = new Action(1, "north");
		  Action.processAction(noExit, worldMap);
		  assertEquals("No exit this way" + System.lineSeparator(),
					 outContent.toString());
		  
		  outContent = new ByteArrayOutputStream();
		  System.setOut(new PrintStream(outContent));
		  Action invalidAction = new Action(1, "invalid");
		  Action.processAction(invalidAction, worldMap);
		  assertEquals("Error: Invalid action" + System.lineSeparator(),
					 outContent.toString());
		  
		  outContent = new ByteArrayOutputStream();
		  System.setOut(new PrintStream(outContent));
		  Action invalidAction1 = new Action(Action.MOVE_BUILDER, "invalid");
		  Action.processAction(invalidAction1, worldMap);
		  assertEquals("Error: Invalid action" + System.lineSeparator(),
					 outContent.toString());
		  
	 }
	 
	 @Test(expected = ActionFormatException.class)
	 public void processActions ()
				throws NoExitException, InvalidBlockException, WorldMapInconsistentException, ActionFormatException {
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
		  tile5.addExit("south", tile1);
		  List<Block> inventoryList = new ArrayList<>();
		  Block soilBlock = new SoilBlock();
		  Block soilBlock1 = new SoilBlock();
		  Block woodBlock = new WoodBlock();
		  inventoryList.add(soilBlock);
		  inventoryList.add(woodBlock);
		  inventoryList.add(soilBlock1);
		  Builder builder = new Builder("Tester", tile, inventoryList);
		  WorldMap worldMap = new WorldMap(tile, position, builder);
		  ByteArrayOutputStream outContent = new ByteArrayOutputStream();
		  System.setOut(new PrintStream(outContent));
		  BufferedReader actionSequence =
					 new BufferedReader(new StringReader(
								"MOVE_BUILDER north" + System.lineSeparator() +
										  "MOVE_BUILDER south" + System.lineSeparator() +
										  "MOVE_BUILDER west" + System.lineSeparator() +
										  "DROP 1" + System.lineSeparator() +
										  "DROP 3" + System.lineSeparator() +
										  "DROP text" + System.lineSeparator() +
										  "DIG" + System.lineSeparator() +
										  "MOVE_BUILDER south" + System.lineSeparator() +
										  "MOVE_BLOCK north" + System.lineSeparator()));
		  
		  Action.processActions(actionSequence, worldMap);
		  assertEquals("Moved builder north" + System.lineSeparator()
								+ "No exit this way" + System.lineSeparator()
								+ "No exit this way" + System.lineSeparator()
								+ "Dropped a block from inventory" + System.lineSeparator()
								+ "Cannot use that block" + System.lineSeparator()
								+ "Error: Invalid action" + System.lineSeparator()
								+ "Top block on current tile removed" + System.lineSeparator()
								+ "No exit this way" + System.lineSeparator()
								+ "Too high" + System.lineSeparator()
					 ,
					 outContent.toString());
		  BufferedReader actionSequence1 =
					 new BufferedReader(new StringReader("ERROR_COMING"));
		  Action.processActions(actionSequence1, worldMap);
	 }
	 
	 @Test
	 public void finalTouches () throws NoExitException, InvalidBlockException,
				WorldMapInconsistentException, ActionFormatException {
		  BufferedReader actionSequence1 =
					 new BufferedReader(new StringReader(""));
		  assertNull(Action.loadAction(actionSequence1));
	 }
	 
	 @Test
	 public void finalTouches1 () throws NoExitException, InvalidBlockException,
				WorldMapInconsistentException, ActionFormatException {
		  BufferedReader actionSequence1 =
					 new BufferedReader(new StringReader("DIG"));
		  Action.loadAction(actionSequence1);
	 }
}