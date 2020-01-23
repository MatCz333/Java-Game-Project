

import java.io.*;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Creates a Map and saves it. It is responsible for loading and saving the
 * game.
 */
public class WorldMap {
	 
	 //SparseTileArray is responsible for processing the map to create a
	 // consistent, linked Tiles
	 private SparseTileArray sparseTileArray;
	 //Starting position from which to create the WorldMap
	 private Position startPosition;
	 //The player which will start on the Map
	 private Builder builder;
	 //The List to generate World from
	 private List<Tile> tileList;
	 //The tile from which World generation begins
	 private Tile startingTile;
	 
	 /**
	  * Constructs a new Block World from a starting Tile.
	  *
	  * @param startingTile:Tile, from which the world generation begins
	  * @param startPosition:Position, from which the player and first tile is
	  * placed on
	  * @param builder:Builder, the player
	  */
	 public WorldMap (Tile startingTile, Position startPosition,
				Builder builder) throws WorldMapInconsistentException {
		  this.sparseTileArray = new SparseTileArray();
		  this.startPosition = startPosition;
		  this.builder = builder;
		  this.startingTile = startingTile;
		  this.sparseTileArray.addLinkedTiles(startingTile, startPosition.getX(),
					 startPosition.getY());
		  
	 }
	 
	 /**
	  * Creates the Block World from a file. It loads the file and creates the
	  * World from it and the Player with the starting inventory. Constructs the
	  * new Position from which create the File. Creates tiles linked by exits
	  * they are given.
	  *
	  * @param filename:String, the name of the file.
	  * @throws WorldMapFormatException: Thrown in these cases: -Any lines are
	  * missing -StartingX or startingY (lines 1 and 2) are not valid integers
	  * -There are not N entries for the amount of tiles -There are not N entries
	  * under the "exits" line - N is not a valid integer, or N is negative -The
	  * names of blocks in inventory and on tiles are not one of "grass", "soil",
	  * "wood", "stone" -The names of exits in the "exits" sections are not one
	  * of "north", "east", "south", "west" -The ids of tiles are not valid
	  * integers, are less than 0 or greater than N - 1 -The ids that the exits
	  * refer to do not exist in the list of tiles loaded tiles contain too many
	  * blocks, or GroundBlocks that have an index that is too high (i.e., if the
	  * Tile or constructors would throw exceptions). -A file operation throws an
	  * IOException that is not a FileNotFoundException
	  * @throws WorldMapInconsistentException: Thrown when: -The file is correct
	  * but sparseTileArray throws the exception because the created Map would be
	  * geometrically impossible.
	  */
	 public WorldMap (String filename)
				throws WorldMapFormatException,
				WorldMapInconsistentException,
				FileNotFoundException {
		  try {
				//Creates a File using the filename provided
				File file = new File(filename);
				//Checks if the File exits, if not the FileNotFoundException is
				// thrown
				if (!file.exists()) {
					 throw new FileNotFoundException();
				}
				//Creates a reader which reads the File
				BufferedReader bufferedReader = new BufferedReader(
						  new FileReader(file));
				//The X attribute of the starting position
				String positionX = bufferedReader.readLine();
				//The Y attribute of the starting position
				String positionY = bufferedReader.readLine();
				//The name of the Builder as read from the File
				String builderName = bufferedReader.readLine();
				
				//Checks if any of the first lines are empty
				if (positionX.isEmpty() || positionY.isEmpty()
						  || builderName.isEmpty()) {
					 throw new WorldMapFormatException();
				}
				try {
					 //Processes the String to Integer
					 this.startPosition = new Position(Integer.parseInt(positionX),
								Integer.parseInt(positionY));
					 //Throws exception if strings cannot be covnerted
				} catch (NumberFormatException e) {
					 throw new WorldMapFormatException();
				}
				//Next Line is inventory
				String builderInventory = bufferedReader.readLine();
				//Splits the string on comma
				String[] splitBuilderInventory = builderInventory.split(",");
				//Creates a list which will be used to hold the inventory
				List<Block> startingInventory = new LinkedList<>();
				//Goes over the line and creates appropriate inventory checking
				// what each string correcponds to a Block. If not, the exception
				// is thrown
				for (int i = 0; i < splitBuilderInventory.length; i++) {
					 if (splitBuilderInventory[i].equals("grass")) {
						  startingInventory.add(new GrassBlock());
					 } else if (splitBuilderInventory[i].equals("soil")) {
						  startingInventory.add(new SoilBlock());
					 } else if (splitBuilderInventory[i].equals("wood")) {
						  startingInventory.add(new WoodBlock());
					 } else if (splitBuilderInventory[i].equals("stone")) {
						  startingInventory.add((new StoneBlock()));
					 } else {
						  throw new WorldMapFormatException();
					 }
				}
				//The next line should always be empty
				String emptyLine = bufferedReader.readLine();
				if (!emptyLine.isEmpty()) {
					 throw new WorldMapFormatException();
				}
				try {
					 //Reads how many tiles should be processed
					 String totalNumberTiles = bufferedReader.readLine();
					 //Splits the Tiles first on the ":" sign
					 String[] splitTotalNumberTiles = totalNumberTiles.split(":");
					 //Instantiates the tileList
					 this.tileList = new LinkedList<>();
					 try {
						  //Attempts to convert String into Integer. If not, the
						  // exception is thrown
						  int numberOfTiles =
									 Integer.parseInt(splitTotalNumberTiles[1]);
						  
						  //Checks if the number is negative
						  if (numberOfTiles < 0) {
								throw new WorldMapFormatException();
						  }
						  //Goes over the lines with Tiles same amount of times as
						  // indicated by the total:N where N is amount of tiles
						  for (int i = 0; i < numberOfTiles; ++i) {
								String tile = bufferedReader.readLine();
								if (tile.isEmpty()) {
									 throw new WorldMapFormatException();
								}
								//Replaces spaces with comma so it can be divided
								// using one operation
								String tilereplaced = tile.replace(" ", ",");
								//Creates array of Blocks
								String[] blocks = tilereplaced.split(",");
								//The block list which will be added to corresponding
								// Tiles
								List<Block> blockList = new LinkedList<>();
								for (int j = 1; j < blocks.length; j++) {
									 try {
										  //Attempts to change String to Integer and
										  // check if it meets the proper qualifications
										  if (Integer.parseInt(blocks[0]) < 0
													 || Integer.parseInt(blocks[0])
													 > numberOfTiles - 1) {
												throw new WorldMapFormatException();
										  }
									 } catch (NumberFormatException e) {
										  throw new WorldMapFormatException();
									 }
									 //Creates Blocks and adds the to each Tile
									 if (blocks[j].equals("grass")) {
										  blockList.add(new GrassBlock());
									 } else if (blocks[j].equals("soil")) {
										  blockList.add(new SoilBlock());
									 } else if (blocks[j].equals("wood")) {
										  blockList.add(new WoodBlock());
									 } else if (blocks[j].equals("stone")) {
										  blockList.add((new StoneBlock()));
										  //Throws exception if the Blocks are not correct
									 } else {
										  throw new WorldMapFormatException();
									 }
								}
								try {
									 //Adds tiles to the list of tiles with list of
									 // Blocks in them
									 this.tileList.add(new Tile(blockList));
								} catch (TooHighException e) {
									 throw new WorldMapFormatException();
								}
						  }
						  
						  try {
								//The starting tile
								this.startingTile = tileList.get(0);
								//Constructs the Builder with a starting Tile, its
								// name, and inventory
								this.builder = new Builder(builderName,
										  startingTile,
										  startingInventory);
								//Catches if the Blocks are not correct in the List
						  } catch (InvalidBlockException e) {
								throw new WorldMapInconsistentException();
						  }
						  //Checks if the line is always empty
						  String emptyLine2 = bufferedReader.readLine();
						  if (!emptyLine2.isEmpty()) {
								throw new WorldMapFormatException();
						  }
						  //Checks if the next line reads "exits"
						  String exits = bufferedReader.readLine();
						  if (!exits.equals("exits")) {
								throw new WorldMapFormatException();
						  }
						  //Reads the amount of lines which are left as many times
						  // as there are tiles from the number N
						  for (int i = 0; i < numberOfTiles; ++i) {
								String exitsLine = bufferedReader.readLine();
								//Uses regex to split it on space" " and  comma","
								String[] splitExitsLine = exitsLine.split("[ ,]+");
								//Checks if there is anything else than a number. If
								// there is only one item it means there are no exits
								if (splitExitsLine.length > 1) {
									 for (int j = 1; j < splitExitsLine.length; j++) {
										  try {
												//Adds exits to each tile
												String singleExit = splitExitsLine[j];
												//Splits it on ":"
												String[] splitSingleExit = singleExit.split(
														  ":");
												String exitName = splitSingleExit[0];
												//Checks if the exit is the one from
												// allowed strings
												if (exitName.equals("north")
														  || exitName.equals("west")
														  || exitName.equals("south")
														  || exitName.equals("east")) {
													 try {
														  //Links tiles through exits read
														  // from the file
														  this.tileList.get(i)
																	 .addExit(exitName,
																				this.tileList
																						  .get(Integer
																									 .
																												parseInt(
																														  splitSingleExit[1])));
														  //If the String canot be
														  // converted to the Integer, the
														  // exception is thrown
													 } catch (NumberFormatException e) {
														  throw new WorldMapFormatException();
													 }
												} else {
													 throw new WorldMapFormatException();
												}
												
										  } catch (NoExitException e) {
												throw new WorldMapFormatException();
										  }
									 }
								}
						  }
					 } catch (NumberFormatException e) {
						  throw new WorldMapFormatException();
					 }
					 //Builds the sparseTileArray from the built list of tiles,
					 // and the starting position. If there is any inconsistency
					 // the WorldInconsistencyException is thrown
					 this.sparseTileArray = new SparseTileArray();
					 this.sparseTileArray.addLinkedTiles(tileList.get(0),
								this.getStartPosition().getX(),
								this.getStartPosition().getY());
				} catch (IndexOutOfBoundsException e) {
					 throw new WorldMapFormatException();
				}
		  } catch (IOException e) {
				throw new WorldMapFormatException();
		  }
		  
	 }
	 
	 /**
	  * Returns the builder from the world map.
	  *
	  * @return the builder
	  */
	 public Builder getBuilder () {
		  return this.builder;
	 }
	 
	 /**
	  * Returns the starting position from the world map
	  *
	  * @return the starting position
	  */
	 public Position getStartPosition () {
		  return this.startPosition;
	 }
	 
	 /**
	  * Returns the tile from a specified position associated with the World Map
	  *
	  * @param position:The position to check if the tile tile exits
	  * @return the Tile if exists or null if it does not
	  */
	 public Tile getTile (Position position) {
		  return this.sparseTileArray.getTile(position);
	 }
	 
	 /**
	  * Returns the tiles from world map using the breath first search/
	  *
	  * @return the tiles from the World Map
	  */
	 public List<Tile> getTiles () {
		  return this.sparseTileArray.getTiles();
	 }
	 
	 /**
	  * Saves the Map to the file to save the performed actions on the Map.
	  *
	  * @param filename: the File to write to.
	  * @throws IOException:If the file cannot be opened or if writing fails
	  */
	 public void saveMap (String filename) throws IOException {
		  
		  //The stringbuilder which writes the progress to the file
		  StringBuilder stringBuilder = new StringBuilder();
//            BufferedReader bufferedReader = new BufferedReader(
//                    new FileReader(filename));
		  //The tiles to be written to the file
		  List<Tile> tileList = this.getTiles();
		  //Appends first the starting position X,Y and then builder's name
		  stringBuilder.append(this.getStartPosition().getX()).append("\n");
		  stringBuilder.append(this.getStartPosition().getY()).append("\n");
		  stringBuilder.append(this.getBuilder().getName());
		  stringBuilder.append("\n");
		  //Loops as many times as there are Blocks in Builder's inventory
		  // and writes them into the file
		  for (int i = 0; i < this.getBuilder().getInventory().size(); i++) {
				stringBuilder
						  .append(this.builder.getInventory().get(i).getBlockType());
				//writes one less comma than there are blocks
				if (i != getBuilder().getInventory().size() - 1) {
					 stringBuilder.append(",");
				}
		  }
		  stringBuilder.append("\n");
		  stringBuilder.append("\n");
		  //Counts the number of tiles
		  int counter = tileList.size();
		  //Writed how many tiles are in total
		  stringBuilder.append("total:").append(counter).append("\n");
		  //Writes down index number as first of the tile
		  int i = 0;
		  for (Tile tiles : tileList) {
				stringBuilder.append(i).append(" ");
				//Writes down blocks to each of the tiles
				for (int j = 0; j < tiles.getBlocks().size(); j++) {
					 stringBuilder.append(tiles.getBlocks().get(j).getBlockType());
					 if (j != tiles.getBlocks().size() - 1) {
						  stringBuilder.append(",");
					 }
				}
				i++;
				stringBuilder.append("\n");
		  }
		  
		  stringBuilder.append("\n");
		  stringBuilder.append("exits").append("\n");
		  //Creates Map with Tile and its index for easier manipulation
		  Map<Tile, Integer> indexedTiles = new LinkedHashMap<>();
		  //Creates Map where each Tile has an index assigned to it
		  int index = 0;
		  for (Tile tile : tileList) {
				indexedTiles.put(tile, index);
				index++;
		  }
		  //Used to check how many lines are written down. There should be
		  // as many as there are tiles
		  int numberOfEnters = 0;
		  for (Map.Entry<Tile, Integer> indexTile : indexedTiles.entrySet()) {
				stringBuilder.append(indexTile.getValue()).append(" ");
				//Counts how many commas there should be added. As many as
				// number of exits minus one
				int numberOfCommas = 0;
				for (Map.Entry<String, Tile> exitMap :
						  indexTile.getKey().getExits().entrySet()) {
					 //Appends the exit name and its index to the file
					 stringBuilder.append(exitMap.getKey()).append(":")
								.append(indexedTiles.get(exitMap.getValue()));
					 if (numberOfCommas
								!= indexTile.getKey().getExits().size() - 1) {
						  stringBuilder.append(",");
					 }
					 numberOfCommas++;
				}
				if (numberOfEnters != indexedTiles.size() - 1) {
					 stringBuilder.append("\n");
				}
				numberOfEnters++;
		  }
		  //Writes it to the file
		  FileWriter fileWriter = new FileWriter(filename);
		  //Appends all of the string builder to the file
		  fileWriter.write(stringBuilder.toString());
		  //Closes the file
		  fileWriter.close();
	 }
}
