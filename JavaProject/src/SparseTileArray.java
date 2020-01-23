

import java.util.*;

/**
 * Sparse representations of tiles in an array. Stores Tiles with their
 * positions (x and y) in a map
 */
public class SparseTileArray {
	 
	 //The Map containing the Tile and its Position on the Map
	 private Map<Tile, Position> tilePositionMap;
	 //The list to store Tiles and give it to modifiable list
	 private List<Tile> modifiableTileList = new LinkedList<>();
	 
	 /**
	  * Constructs the Map which holds the Tile and its Position
	  */
	 public SparseTileArray () {
		  tilePositionMap = new LinkedHashMap<>();
	 }
	 
	 /**
	  * Returns a Tile from a specified Position or null of the Tile is not
	  * found
	  *
	  * @param position:Position to search for
	  */
	 public Tile getTile (Position position) {
		  
		  //Checks if the Map is not empty
		  if (tilePositionMap.size() > 0) {
				for (Map.Entry<Tile, Position> searchedTile :
						  tilePositionMap.entrySet()) {
					 //Returns a Tile from a specified position
					 if (searchedTile.getValue().equals(position)) {
						  return searchedTile.getKey();
					 }
				}
		  }
		  //Returns null if not found
		  return null;
	 }
	 
	 
	 /**
	  * Adds the linked tiles to the map. Does the breath first search. If the
	  * sparse Map is populated it removes all tiles from it. It creates a map of
	  * exits from a starting Tile and places it on the starting Position. Then,
	  * it creates a "path" from it using tiles exits and all of the connected
	  * Tiles are then formed into a Map.
	  *
	  * @param startingTile:Tile from which the breath first search starts
	  * @param startingX:The X attribute of the Position on whcih the starting
	  * Tile is placed
	  * @param startingY:The Y attribute of the Position on whcih the starting *
	  * Tile is placed
	  */
	 public void addLinkedTiles (Tile startingTile,
				int startingX,
				int startingY)
				throws WorldMapInconsistentException {
		  //Each time the method is called it resets the Map
		  tilePositionMap = new LinkedHashMap<>();
		  //The starting position of the Tile
		  Position startingPosition = new Position(startingX, startingY);
		  //Set used in breath first search
		  Set<Tile> tilesAlreadyVisited = new HashSet<>();
		  //Queue used in breath first search
		  Queue<Tile> tilesToVisit = new LinkedList<>();
		  //Starts the search from here by adding the startingTile
		  tilesToVisit.add(startingTile);
		  while (tilesToVisit.size() != 0) {
				//Each tile when added to Queue will be removed from the Set in
				// FIFO fashion
				Tile tile = tilesToVisit.remove();
				if (!tilesAlreadyVisited.contains(tile)) {
					 //Checks if the Map does not contain the Position. If not, it
					 // puts the current tile being search with this position
					 if (!tilePositionMap.containsValue(startingPosition)) {
						  tilePositionMap.put(tile, startingPosition);
					 }
					 //If the exit from the currently searched tile is "north" it
					 // gives it a new position (X,Y-1) and adds it as first.
					 if (tile.getExits().containsKey("north")
								&& tile.getExits().get("north") != null) {
						  tilesToVisit.add(tile.getExits().get("north"));
						  tilePositionMap.put(tile.getExits().get("north"),
									 new Position(tilePositionMap.get(tile).getX(),
												tilePositionMap.get(tile).getY() - 1));
					 }
					 //If the exit from the currently searched tile is "east" it
					 // gives it a new position (X+1,Y) and adds it as first.
					 if (tile.getExits().containsKey("east")
								&& tile.getExits().get("east") != null) {
						  tilesToVisit.add(tile.getExits().get("east"));
						  tilePositionMap.put(tile.getExits().get("east"),
									 new Position(tilePositionMap.get(tile).getX() + 1,
												tilePositionMap.get(tile).getY()));
					 }
					 //If the exit from the currently searched tile is "south" it
					 // gives it a new position (X,Y+1) and adds it as first.
					 if (tile.getExits().containsKey("south")
								&& tile.getExits().get("south") != null) {
						  tilesToVisit.add(tile.getExits().get("south"));
						  tilePositionMap.put(tile.getExits().get("south"),
									 new Position(tilePositionMap.get(tile).getX(),
												tilePositionMap.get(tile).getY() + 1));
					 }
					 //If the exit from the currently searched tile is "west" it
					 // gives it a new position (X-1,Y) and adds it as first.
					 if (tile.getExits().containsKey("west")
								&& tile.getExits().get("west") != null) {
						  tilesToVisit.add(tile.getExits().get("west"));
						  tilePositionMap.put(tile.getExits().get("west"),
									 new Position(tilePositionMap.get(tile)
												.getX() - 1, tilePositionMap
												.get(tile).getY()));
					 }
					 //After processing the tile it adds it to the alreadyVisited
					 // Stack
					 tilesAlreadyVisited.add(tile);
				}
		  }
		  
		  //Performs checks on the created Map after breath first search
		  for (Map.Entry<Tile, Position> tiles : tilePositionMap.entrySet()) {
				//Checks if the tiles at "north" from a tile meet proper
				// conditions. Does not occupy same position or point to
				// incorrect tile.
				if (tiles.getKey().getExits().containsKey("north")) {
					 if (tiles.getKey().getExits().get("north").getExits()
								.containsKey(
										  "south")
								&& tiles.getKey().getExits().get("north").getExits()
								.get("south") != tiles.getKey()) {
						 tilePositionMap = new LinkedHashMap<>();
						  throw new WorldMapInconsistentException();
					 }
				}
				//Checks if the tiles at "south" from a tile meet proper
				// conditions. Does not occupy same position or point to
				// incorrect tile.
				if (tiles.getKey().getExits().containsKey("south")) {
					 if (tiles.getKey().getExits().get("south").getExits()
								.containsKey(
										  "north")
								&& tiles.getKey().getExits().get("south").getExits()
								.get(
										  "north") != tiles.getKey()) {
						 tilePositionMap = new LinkedHashMap<>();
						  throw new WorldMapInconsistentException();
					 }
				}
				//Checks if the tiles at "east" from a tile meet proper
				// conditions. Does not occupy same position or point to
				// incorrect tile.
				if (tiles.getKey().getExits().containsKey("east")) {
					 if (tiles.getKey().getExits().get("east").getExits()
								.containsKey(
										  "west")
								&& tiles.getKey().getExits().get("east").getExits().get(
								"west") != tiles.getKey()) {
						 tilePositionMap = new LinkedHashMap<>();
						  throw new WorldMapInconsistentException();
					 }
				}
				//Checks if the tiles at "west" from a tile meet proper
				// conditions. Does not occupy same position or point to
				// incorrect tile.
				if (tiles.getKey().getExits().containsKey("west")) {
					 if (tiles.getKey().getExits().get("west").getExits()
								.containsKey("east")
								&& tiles.getKey().getExits().get("west").getExits().get(
								"east") != tiles.getKey()) {
						 tilePositionMap = new LinkedHashMap<>();
						  throw new WorldMapInconsistentException();
					 }
				}
		  }
		  //Adds all Tiles to the List which is then used in getTiles() method
		  modifiableTileList.addAll(tilePositionMap.keySet());
	 }
	 
	 /**
	  * Returns a set from tiles following the order: The "north", "east",
	  * "south" and "west"  tiles  if they exits from the startingTile. Then for
	  * each of those tiles, the next tiles will be their "north", "east",
	  * "south" and "west" exits. The list returned is immutable.
	  *
	  * @return immutable List of Tiles
	  */
	 public List<Tile> getTiles () {
		  List<Tile> tileList = this.modifiableTileList;
		  return Collections.unmodifiableList(tileList);
	 }
	 
}
