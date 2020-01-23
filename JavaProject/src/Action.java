

import java.io.BufferedReader;
import java.io.IOException;

/**
 * Represents Action we can perform on the World Map (or the Block World).
 * Action is something the player(Builder) can perform and change the Block
 * World
 */
public class Action {
	 
	 //Constant representing dig on current tile action of the Player
	 public static final int DIG = 2;
	 //Constant representing the drop a tile action of the Player
	 public static final int DROP = 3;
	 //Constant representing moving block action of the Player
	 public static final int MOVE_BLOCK = 1;
	 //Constant representing moving action of the Player
	 public static final int MOVE_BUILDER = 0;
	 //The first part of the Action which Player wants to perform
	 private int primaryAction;
	 //Second part of the Action player wants to perform
	 private String secondaryAction;
	 
	 /**
	  * Contructs an Action which takes the primary action as a constant integer
	  * and secondary Action.
	  *
	  * @param primaryAction-Constant integer, represents what action Player
	  * wants to perform
	  * @param secondaryAction-String,gives direction to MOVE_BLOCK and
	  * MOVE_BUILDER, an index number what tile DROP action should drop and is
	  * empty for DIG
	  */
	 public Action (int primaryAction, String secondaryAction) {
		  this.primaryAction = primaryAction;
		  this.secondaryAction = secondaryAction;
	 }
	 
	 /**
	  * Reads the input from reader, either file or command input and loads one
	  * action for each line the reader reads. Each line consist of primary
	  * action and optional secondary action.
	  *
	  * @param reader: BufferedReader which reads the line
	  * @return created Action or null if the reader is at the end of the file
	  * @throws ActionFormatException:It throws it in the following cases: -If
	  * any line consists of 2 or more spaces (i.e. more than 2 tokens). -If the
	  * primary action is not one of MOVE_BLOCK, MOVE_BUILDER, DROP or DIG. -If
	  * the primary action is DIG, and DIG is not on a line by itself, with no
	  * trailing whitespace.\ -If an IOException is thrown by the reader the
	  * ActionFormatException is thrown.
	  */
	 public static Action loadAction (BufferedReader reader)
				throws ActionFormatException {
		  
		  try {
				//Reads the line from which the Action will be contructed
				String line = reader.readLine();
				
				//If the reader is at the end of the file. Returns null
				if (line.isEmpty()) {
					 return null;
				}
				//Counts amount of spaces in the line. There should be no more
				// than 1
				int spaceCount = 0;
				for (char c : line.toCharArray()) {
					 if (c == ' ') {
						  spaceCount++;
					 }
				}
				if (spaceCount > 1) {
					 throw new ActionFormatException();
				}
				//Splits the line on the space
				String loadedActions[] = line.split(" ");
				//Loads the primary action
				String primaryAction = loadedActions[0];
				//Checks if the action has more than 2 elements
				if (loadedActions.length > 2) {
					 throw new ActionFormatException();
				}
				//Checks if the primary action is one of the allowed actions
				if (primaryAction.equals("MOVE_BLOCK") || primaryAction.equals(
						  "MOVE_BUILDER") || primaryAction.equals("DROP")
						  || primaryAction.equals("DIG")) {
					 //For DIG the action must not have any spaces and have length
					 // of 1
					 if (primaryAction.equals("DIG") && loadedActions.length == 1
								&& spaceCount == 0) {
						  return new Action(DIG, "");
					 }
					 //For MOVE_BLOCk,MOVE_BUILDER and DROP the split line must be
					 // of length 2
					 if ((primaryAction.equals("MOVE_BLOCK")
								|| primaryAction.equals("MOVE_BUILDER")
								|| primaryAction.equals("DROP"))
								&& loadedActions.length == 2) {
						  //Loads the secondary action and creates Action with it.
						  String secondaryAction = loadedActions[1];
						  if (primaryAction.equals("MOVE_BLOCK")) {
								return new Action(MOVE_BLOCK, secondaryAction);
						  }
						  if (primaryAction.equals("DROP")) {
								return new Action(DROP, secondaryAction);
						  }
						  if (primaryAction.equals("MOVE_BUILDER")) {
								return new Action(MOVE_BUILDER, secondaryAction);
						  }
						  //If MOVE_ACTION,DROP and MOVE_BLOCK are not meeting the
						  // criteria the exception is thrown
					 } else {
						  throw new ActionFormatException();
					 }
					 //If the primary action is not one of the allowed strings
					 // exception is thrown
				} else {
					 throw new ActionFormatException();
				}
				//Catches IOException and generalises it to ActionFormatException
		  } catch (IOException e) {
				throw new ActionFormatException();
				//If a NullPointerException is thrown it returns null
		  } catch (NullPointerException e) {
				return null;
		  }
		  //If somehow it all passes through here, the method returns null
		  return null;
	 }
	 
	 
	 /**
	  * Reads all actions and reanacts them on the blcok world created. All
	  * actions should print a message to the user and the method stops after
	  * reaching the end of the file or input. If the input is from the command
	  * lines the method would perform one line at a time. If this is from a File
	  * then the method would read it until it reaches an end of the file or an
	  * exception is thrown
	  *
	  * @param reader:BufferedReader- the input to be processed
	  * @param startingMap:WorkdMap- the Block World map which is being updated
	  * from processed actions
	  * @throws ActionFormatException:if loadAction() throws this exception
	  */
	 public static void processActions (BufferedReader reader,
				WorldMap startingMap) throws ActionFormatException {
		  //Action to be loaded
		  Action action;
		  try {
				//Reads line until it is at the end of the file
				do {
					 action = loadAction(reader);
					 processAction(action, startingMap);
				} while (action != null);
		  } catch (NullPointerException e) {
		  }
		  
	 }
	 
	 /**
	  * Processed the Action and prints out the feedback to the user about what
	  * happened with on the Map.It also updates the World Map after each Action
	  * is performed Printed messages if performed correctly: For DIG:"Top block
	  * on current tile removed" For DROP:"Dropped a block from inventory" For
	  * MOVE_BLOCK:"Moved block {direction}" For MOVE_BUILDER:"Moved builder
	  * {direction}" Messages for Exceptions: NoExitException: "No exit this way"
	  * TooHighException:"Too high" TooLowException: "Too low"
	  * InvalidBlockException: "Cannot use that block"
	  *
	  * @param action:Action to be processed
	  * @param map: The Block World map which is updated after each aciton
	  */
	 public static void processAction (Action action, WorldMap map) {
		  //Checks if the primary action has illegal values
		  if (action.getPrimaryAction() < 0 || action.getPrimaryAction() > 3) {
				System.out.println("Error: Invalid action");
				//Processes the DIG action
		  } else if (action.getPrimaryAction() == DIG) {
				try {
					 //If successful, digs on the current Tile
					 map.getBuilder().digOnCurrentTile();
					 System.out.println("Top block on current tile removed");
					 //Catches TooLowException
				} catch (TooLowException e) {
					 System.out.println("Too low");
					 //Catches InvalidBlockException
				} catch (InvalidBlockException e) {
					 System.out.println("Cannot use that block");
				}
				//Processes DROP action
		  } else if (action.getPrimaryAction() == DROP) {
				try {
					 //Parses the integer to secondary action
					 Integer.parseInt(action.getSecondaryAction());
					 //Updates the map by dropping the Block from intventory
					 map.getBuilder().dropFromInventory(
								Integer.parseInt(action.getSecondaryAction()));
					 System.out.println("Dropped a block from inventory");
					 //If the block cannot be dropped
				} catch (InvalidBlockException e) {
					 System.out.println("Cannot use that block");
					 //If the block is a Ground Block and is on index more than 3
				} catch (TooHighException e) {
					 System.out.println("Too high");
					 //If the secondaryAction cannot be converted into an integer
				} catch (NumberFormatException e) {
					 System.out.println("Error: Invalid action");
				}
				//Processes the MOVE_BLOCK action
		  } else if (action.getPrimaryAction() == MOVE_BLOCK) {
				//Checks if secondaryAction() is one of the allowed strings
				if (action.getSecondaryAction().equals("north")
						  || action.getSecondaryAction().equals("south")
						  || action.getSecondaryAction().equals("west")
						  || action.getSecondaryAction().equals("east")) {
					 try {
						  //Moves the Block to the tile in the specified direction
						  map.getBuilder().getCurrentTile()
									 .moveBlock(action.getSecondaryAction());
						  System.out.println(
									 "Moved block " + action.getSecondaryAction());
						  //If the difference in height between tiles is too hgih
					 } catch (TooHighException e) {
						  System.out.println("Too high");
						  //If the block we want to move is not moveable
					 } catch (InvalidBlockException e) {
						  System.out.println("Cannot use that block");
						  //If there is no exit in that directionj
					 } catch (NoExitException e) {
						  System.out.println("No exit this way");
					 } catch (TooLowException e) {
						 e.printStackTrace();
					 }
				} else {
					 //If the secondaryAction() is not one of the allowed strings
					 System.out.println("Error: Invalid action");
				}
				//Processes the MOVE_BUILDER action
		  } else if (action.getPrimaryAction() == MOVE_BUILDER) {
				//Checks if secondaryAction() is one of the allowed strings
				if (action.getSecondaryAction().equals("north")
						  || action.getSecondaryAction().equals("south")
						  || action.getSecondaryAction().equals("west")
						  || action.getSecondaryAction().equals("east")) {
					 try {
						  //Moved the builder in the direction specified
						  map.getBuilder()
									 .moveTo(map.getBuilder().getCurrentTile().getExits()
												.get(action.getSecondaryAction()));
						  System.out.println(
									 "Moved builder " + action.getSecondaryAction());
						  //If there is no exit that way
					 } catch (NoExitException e) {
						  System.out.println("No exit this way");
					 }
					 
				} else {
					 //If the secondaryAction is not one of the allowed Strings
					 System.out.println("Error: Invalid action");
				}
		  }
	 }
	 
	 /**
	  * Returns the primaryAction of the Action
	  *
	  * @return the primary Action
	  */
	 public int getPrimaryAction () {
		  
	 	 return this.primaryAction;
	 }
	 
	 /**
	  * Returns the secondaryAction of the Action
	  *
	  * @return the secondary Action
	  */
	 public String getSecondaryAction () {
		  
	 	 return this.secondaryAction;
	 }
}
