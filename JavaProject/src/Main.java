

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.InputStreamReader;

/**
 * Main Class of the game. Makes it possible for the user to interaction with
 * the Game.
 */
public class Main {
	 
	 /**
	  * Lunches the application. Takes three parameters: Input map file
	  * (args[0]), actions (args[1]), and an output map file (args[2]) The
	  * actions can be either taken from a File or from Input from the user via a
	  * console. It exits with following statuses: 1-If there are no three
	  * parameters. 2-If the exception is thrown during Map creation. 3-If the
	  * exception is thrown during reader's creation. 4-If the exceptino is
	  * thrown when processing Action. 5-If the exception is thrown when saving
	  * Map.
	  *
	  * @param args:String[] arguments taken in the form of array where args[0]
	  * is the path to the map with the input args[1] is either the file with the
	  * Actions inside or the "System.in" which creates the input reader and the
	  * user writes down commands (Actions) via console. args[2] is the output
	  * file where the map will be saved after the interaction will finish and
	  * the application will exit
	  */
	 public static void main (String[] args) {
		  BufferedReader bufferedReader;
		  WorldMap worldMap;
		  //Checks if there are three parameters
		  if (args.length != 3) {
				System.err.println("Usage: program inputMap actions outputMap");
				System.exit(1);
		  }
		  
		  try {
				worldMap = new WorldMap(args[0]);
				try {
					 //Checks if the File exists
					 File file = new File(args[1]);
					 if (file.exists()) {
						  try {
								//creates the reader to read the file
								bufferedReader =
										  new BufferedReader(new FileReader(args[1]));
								Action.processActions(bufferedReader, worldMap);
								//Prints a message if an exception is thrown
						  } catch (Exception e) {
								try {
									 worldMap.saveMap(args[2]);
									 //Saves the Map after the program is finished to a file
								} catch (Exception x) {
									 System.err.println(e);
									 System.exit(5);
								}
								System.err.println(e);
								System.exit(4);
						  }
					 }
					 //Checks if the second argument indicates user will write
					 // commands
					 else if (args[1].equals("System.in")) {
						  try {
								//Creates a reader which takes user input
								bufferedReader =
										  new BufferedReader(
													 new InputStreamReader(System.in));
								Action.processActions(bufferedReader, worldMap);
								//Prints a message if exception is thrown
						  } catch (Exception e) {
//								try {
//									 worldMap.saveMap(args[2]);
//									 //Saves the Map after the program is finished to a file
//								} catch (Exception x) {
//									 System.err.println(e);
//									 System.exit(5);
//								}
								System.err.println(e);
								System.exit(4);
						  }
					 }
					 //Prints a message with Exception during reader's creation
				} catch (Exception e) {
					 System.err.println(e);
					 System.exit(3);
				}
				try {
					 worldMap.saveMap(args[2]);
					 //Saves the Map after the program is finished to a file
				} catch (Exception e) {
					 System.err.println(e);
					 System.exit(5);
				}
				//Prints exception is there is a problem creating a Map
		  } catch (Exception e) {
				System.err.println(e);
				System.exit(2);
		  }
	 }
}
