

import javafx.animation.FadeTransition;
import javafx.animation.FillTransition;
import javafx.animation.Timeline;

import javafx.geometry.Insets;
import javafx.geometry.Pos;

import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.transform.Rotate;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javafx.util.Duration;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import java.util.*;

/**
 * Starting point of the application. Run it to run the game.
 *
 * Javafx Application Class. It is responsible for rendering all of the
 * elements. It is responsible for rendering GUI.
 */
public class MainApplication extends javafx.application.Application {

    //The text field which is displaying Builder's inventory
    private TextField inventoryIndex = new TextField();
    //The map of the game world
    private WorldMap worldMap;
    //The main grid of the GUI containing all other nodes in it
    private BorderPane grid = new BorderPane();
    //The main stage of the GUI
    private Stage stage = new Stage();
    //The StringBuilder which builds the string with builder's inventory
    private StringBuilder buildersInventory;
    //The Label containing the builder's inventory
    private Label inventory = new Label();
    //The North Exit button
    private Button north = new Button("North");
    //The West Exit button
    private Button west = new Button("West");
    //The East Exit button
    private Button east = new Button("East");
    //The South Exit button
    private Button south = new Button("South");
    //The Look button
    private Button drop = new Button("Drop");
    //The Dig button
    private Button dig = new Button("Dig");
    //The Toggle buttons for move builder and move block
    //Depending on which one is pressed this functionality is enabled
    private ToggleButton moveBuilder = new ToggleButton("Move Builder");
    private ToggleButton moveBlock = new ToggleButton("Move Block");
    //Boolean check if the map has been loaded
    private boolean isMapLoaded = false;
    //The Pane containing all the tiles
    private GridPane gridPane;
    //The StackPane containing the gridpane which contains all
    // the tiles to display
    private StackPane stackPaneMain;
    //Integer which moves the map around after loading on X plane
    // to keep the player in the centre at all times
    private int translateX = 0;
    //Integer which moves the map after loading around on Y plane to keep
    // the player in the centre at all times
    private int translateY = 0;
    //The correction for the map  on Y plane after loading to keep the player
    // in the centre
    private double mapCorrectionY = 0;
    //The correction for the map  on X plane after loading to keep the player
    // in the centre
    private double mapCorrectionX = 0;
    //The boolean check if the map correction for Y coordinate has been saved
    private boolean savedX = false;
    //The boolean check if the map correction for X coordinate has been saved
    private boolean savedY = false;

    /**
     * The main application constructor
     */
    public MainApplication() {
    }

    /**
     * The main method launching the application.
     */
    public static void main(String[] args) {
        launch(args);
    }

    /**
     * The functionality of the North button. Moves the Builder or block
     * to north if possible. Displays errors otherwise.
     */
    private void northButtonFunctionality() {

        //Checks if the map has been loaded
        if (isMapLoaded) {
            try {
                //Checks if the toggle button for moving builder is selected
                if (moveBuilder.isSelected()) {
                    //moves builder north
                    worldMap.getBuilder()
                            .moveTo(worldMap.getBuilder().getCurrentTile()
                                    .getExits().get("north"));
                    //refreshes the view with builder being in new tile
                    stackPaneMain.getChildren().add(tiles());
                    //corrects the position to keep player in the middle
                    translateY = translateY + 50;
                    gridPane.setTranslateY(mapCorrectionY + translateY);
                    gridPane.setTranslateX(mapCorrectionX + translateX);


                }
                //Checks if the toggle button for moving block is selcted.
                else if (moveBlock.isSelected()) {

                    try {

                        //Moves the block from the current tile north
                        worldMap.getBuilder().getCurrentTile()
                                .moveBlock("north");
                        //Updates the view
                        stackPaneMain.getChildren().add(tiles());
                        gridPane.setTranslateY(mapCorrectionY + translateY);
                        gridPane.setTranslateX(mapCorrectionX + translateX);
                    } catch (TooHighException e) {
                        //Displays the alert for TooHighException
                        tooHighAlert();
                    } catch (InvalidBlockException e) {
                        //Displays alert for InvalidBlockException
                        invalidBlockAlert();
                    } catch (TooLowException e) {
                        e.printStackTrace();
                    }
                }

            }
            //Displays alert that there is no exit to move builder/block to
            catch (NoExitException e) {
                noExitAlert();
            }
        }
    }


    /**
     * Alert box for TooHighException
     */
    private void tooHighAlert() {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Too High Error");
        alert.setHeaderText(null);
        alert.setContentText("Can't move block between tiles." +
                "One of them is too High.");
        alert.showAndWait();
    }

    /**
     * Alert box for the invalid block exception
     */
    private void invalidBlockAlert() {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Invalid Block");
        alert.setHeaderText(null);
        alert.setContentText("This type of Block can't be moved.");
        alert.showAndWait();
    }

    /**
     * Alert box for when there is no exit in the direction provided
     */
    private void noExitAlert() {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("No exit");
        alert.setHeaderText(null);
        alert.setContentText("No exit this way.");
        alert.showAndWait();
    }

    /**
     * Alert box for when there is no exit in the direction provided
     */
    private void numberFormatAlert() {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Wrong input format");
        alert.setHeaderText(null);
        alert.setContentText("Please put positive integer in the field.");
        alert.showAndWait();
    }

    /**
     * The South button functionality. Moves builder/block south if possible.
     * Displays error messages otherwise.
     */
    private void southButtonFunctionality() {

        //Checks if the map is loaded
        if (isMapLoaded) {
            try {
                //Checks if toggle button for moving builder is selected
                if (moveBuilder.isSelected()) {

                    //moves builder south
                    worldMap.getBuilder()
                            .moveTo(worldMap.getBuilder().getCurrentTile()
                                    .getExits().get("south"));
                    //Upates the view
                    stackPaneMain.getChildren().add(tiles());
                    translateY = translateY - 50;
                    gridPane.setTranslateY(mapCorrectionY + translateY);
                    gridPane.setTranslateX(mapCorrectionX + translateX);
                }
                //Checks if toggle button for moving block is selected
                else if (moveBlock.isSelected()) {

                    try {
                        //Moves the builder south
                        worldMap.getBuilder().getCurrentTile()
                                .moveBlock("south");
                        //Updates the view
                        stackPaneMain.getChildren().add(tiles());
                        gridPane.setTranslateY(mapCorrectionY + translateY);
                        gridPane.setTranslateX(mapCorrectionX + translateX);
                    } catch (TooHighException e) {
                        //Display alert box for TooHighException
                        tooHighAlert();
                    } catch (InvalidBlockException e) {
                        //Display alert for InvalidBlockExcepion
                        invalidBlockAlert();
                    } catch (TooLowException e) {
                        e.printStackTrace();
                    }
                }

            } catch (NoExitException e) {
                //Display alert box for NoExitException
                noExitAlert();
            }
        }
    }


    /**
     * Functionality of the West button. Moves builder or block south if
     * possible.Display alert box otherwise.
     */
    private void westButtonFunctionality() {

        //Check if the map is loaded
        if (isMapLoaded) {
            try {

                //Checks if toggle button for moving builder is selected
                if (moveBuilder.isSelected()) {

                    //Moves builder west
                    worldMap.getBuilder()
                            .moveTo(worldMap.getBuilder().getCurrentTile()
                                    .getExits().get("west"));
                    //Refreshes the view
                    stackPaneMain.getChildren().add(tiles());
                    translateX = translateX - 50;
                    gridPane.setTranslateY(mapCorrectionY + translateY);
                    gridPane.setTranslateX(mapCorrectionX + translateX);
                }
                //Checks if the toggle button for moving block is selected
                else if (moveBlock.isSelected()) {

                    try {

                        //Moves the builder west
                        worldMap.getBuilder().getCurrentTile()
                                .moveBlock("west");
                        //Updates the view
                        stackPaneMain.getChildren().add(tiles());
                        //Corrects the view to keep the builder in the centre
                        gridPane.setTranslateY(mapCorrectionY + translateY);
                        gridPane.setTranslateX(mapCorrectionX + translateX);
                    } catch (TooHighException e) {
                        //Displays alert for TooHighException
                        tooHighAlert();
                    } catch (InvalidBlockException e) {
                        //Displays alert for InvalidBlockException
                        invalidBlockAlert();
                    } catch (TooLowException e) {
                        e.printStackTrace();
                    }
                }
            } catch (NoExitException e) {
                //Displays alert box for NoExitException
                noExitAlert();
            }
        }
    }

    /**
     * The functionality of the East button. Moves builder or block east
     * if possible.Displays alert message otherwise.
     */
    private void eastButtonFunctionality() {

        //Checks if the map is loaded
        if (isMapLoaded) {
            try {

                //Checks if the toggle button for moving builder is selected
                if (moveBuilder.isSelected()) {

                    //Moves builder east
                    worldMap.getBuilder()
                            .moveTo(worldMap.getBuilder().getCurrentTile()
                                    .getExits().get("east"));
                    //Updates view
                    stackPaneMain.getChildren().add(tiles());
                    translateX = translateX + 50;
                    gridPane.setTranslateY(mapCorrectionY + translateY);
                    gridPane.setTranslateX(mapCorrectionX + translateX);
                }

                //Checks if toggle button to move block is selected
                else if (moveBlock.isSelected()) {

                    try {

                        //Moves block towards east if possible
                        worldMap.getBuilder().getCurrentTile()
                                .moveBlock("east");
                        //Updates view
                        stackPaneMain.getChildren().add(tiles());
                        //Keeps the position of view the same
                        gridPane.setTranslateY(mapCorrectionY + translateY);
                        gridPane.setTranslateX(mapCorrectionX + translateX);
                    } catch (TooHighException e) {
                        //Alert box for TooHighException
                        tooHighAlert();
                    } catch (InvalidBlockException e) {
                        //Alert box for InvalidBlockException
                        invalidBlockAlert();
                    } catch (TooLowException e) {
                        e.printStackTrace();
                    }
                }
            } catch (NoExitException e) {
                //Alert box for NoExitException
                noExitAlert();
            }
        }
    }

    /**
     * The functionality of the Drop button. Drops the block chosen by index
     * if possible.Displays alert boxes otherwise.
     */
    private void dropButtonFunctionality() {

        //Checks if the input box is not empty to process
        if (!inventoryIndex.getText().isEmpty()) {
            try {
                //Drops the block from provided index
                worldMap.getBuilder().dropFromInventory(Integer.
                        parseInt(inventoryIndex.getText()));
                //Updates the view
                stackPaneMain.getChildren().add(tiles());
                gridPane.setTranslateY(mapCorrectionY + translateY);
                gridPane.setTranslateX(mapCorrectionX + translateX);
            } catch (InvalidBlockException e) {
                //Alert box for InvalidBlockException
                invalidBlockAlert();
            } catch (TooHighException e) {
                //Alert for TooHighException
                tooHighAlert();
            } catch (NumberFormatException e) {
                //Alert if there is no file provided
                numberFormatAlert();
            }
        }
    }

    /**
     * Alert informing that there is no file probided yet.
     */
    private void noFileAlert() {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("No map error");
        alert.setHeaderText(null);
        alert.setContentText("No Map loaded yet!");
        alert.showAndWait();
    }


    /**
     * The save button functionality.Saves the map into the file selected.
     * Displays alerts if it cannot be done.
     */
    private void saveButtonFunctionality() {

        //The window we use to select file
        FileChooser chooser = new FileChooser();
        //File we want to choose
        File file = chooser.showOpenDialog(stage);
        //Checks if the file is null
        if (file != null) {
            //Changes the file selected to string
            String fileAsString = file.toString();
            try {
                //Attempts to save the map in the file selected
                worldMap.saveMap(fileAsString);
            } catch (IOException e) {
                //Aler box if the map cannot be saved
                noSaveAlert();
            }
        }
    }

    /**
     * Alert box displaying if the map cannot be saved.
     */
    private void noSaveAlert() {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Save error");
        alert.setHeaderText(null);
        alert.setContentText("Map could not have been saved");
        alert.showAndWait();
    }

    /**
     * The Dig button functionality.Digs the tile the Builder stand on and adds
     * it to backpack if possible.Displays alerts otherwise.
     */
    private void digButtonFunctionality() {
        try {
            //Attempts to dig on the current tile
            worldMap.getBuilder().digOnCurrentTile();

        } catch (TooLowException e) {
            //Alert informing of TooLowException
            tooLowAlert();
        } catch (InvalidBlockException e) {
            //Alert informing if the block cannot be dug
            invalidBlockAlert();
        }
        //Updates the view
        stackPaneMain.getChildren().add(tiles());
        //Keeps the display in the same position
        gridPane.setTranslateY(mapCorrectionY + translateY);
        gridPane.setTranslateX(mapCorrectionX + translateX);
    }

    /**
     * The alert box for the TooLowException. Informs if the tile has not
     * enough blocks.
     */
    private void tooLowAlert() {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Too low alert");
        alert.setHeaderText(null);
        alert.setContentText("The number of blocks on a tile is too low");
        alert.showAndWait();
    }

    /**
     * The functionality of the Move Builder button.
     * All direction control buttons are disabled until the map is loaded.
     */
    private void moveBuilderButtonFunctionality() {
        if (isMapLoaded) {
            this.south.setDisable(false);
            this.west.setDisable(false);
            this.east.setDisable(false);
            this.north.setDisable(false);
        }
    }

    /**
     * The functionality of the Move Block button.
     * All direction buttons are disabled until the map is loaded
     */
    private void moveBlockButtonFunctionality() {
        if (isMapLoaded) {
            this.west.setDisable(false);
            this.south.setDisable(false);
            this.east.setDisable(false);
            this.north.setDisable(false);
        }
    }

    /**
     * The Load Button functionality. Loads the map from the file
     * selected if possible.Display alerts otherwise.
     */
    private void loadButtonFunctionality() {

        //Opens the choosing window on the stage
        FileChooser chooser = new FileChooser();
        File file = chooser.showOpenDialog(stage);
        //Checks if the there is file selected
        if (file != null) {
            //Changes the name of the file to string
            String fileAsString = file.toString();

            try {
                //Attempts to load the map using WorldMap
                worldMap = new WorldMap(fileAsString);
                //Boolean informing that loading of the map has been successful
                isMapLoaded = true;
                //positino of the tiles resets to 0
                translateY = 0;
                translateX = 0;
                //Booleans informing that position of the player is not
                // saved yet
                savedX = false;
                savedY = false;
                //Anabling buttons after loading the file
                this.drop.setDisable(false);
                this.dig.setDisable(false);
                this.moveBuilder.setDisable(false);
                this.moveBlock.setDisable(false);
            } catch (WorldMapFormatException e1) {
                //Alert of the file is not properly formatted
                worldFormatAlert();
            } catch (WorldMapInconsistentException e) {
                //Alert if the file is inconsistent
                worldInconsistentAlert();
            } catch (FileNotFoundException e2) {
                //Alert if the file is not found
                noFileAlert();
            }
            //Updates the view to display tiles
            stackPaneMain.getChildren().add(tiles());
        }
    }

    /**
     * Alert for WorldMapInconsistent exceltion.
     * If the file with map in it has inconsistencies this will be displayed.
     */
    private void worldInconsistentAlert() {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Inconsistent map");
        alert.setHeaderText(null);
        alert.setContentText("The map is not consistent");
        alert.showAndWait();
    }

    /**
     * Alert for the WorldFormatException.
     * Alert will be displayed if the file with the map is
     * not properly formatted.
     */
    private void worldFormatAlert() {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("World Format Alert");
        alert.setHeaderText(null);
        alert.setContentText("The file storing the world is not " +
                "properly formatted");
        alert.showAndWait();
    }

    /**
     * Core function of the GUI. Starts the processing and displays all
     * of the elements in the application.
     * The Main frame is the GridPane. Inside are following elements:
     * Top-FileMenu
     * Left- Tile display
     * Right-Buttons
     * Bottom- Builder's menu
     *
     * @param stage: Stage- stage to be displayed.
     */
    public void start(Stage stage) {

        //The main Stack Pane containing the Tile map.
        stackPaneMain = new StackPane();
        //The builder's inventory
        buildersInventory = new StringBuilder();
        //GridPane containing the welcome message
        gridPane = new GridPane();
        //Prompt message for the use what to put inside the field
        inventoryIndex.setPromptText("Put index of a Tile to drop");
        //Max column count
        inventoryIndex.setPrefColumnCount(10);
        //Text to put at the beginning for Builder's inventory before file
        // is loaded
        inventory.setText(
                "Builder's inventory:\n" +"["+ buildersInventory.toString()+
                "]");
        //Font for the Builder's inventory
        inventory.setFont(Font.font("Helvetica", 16));
        //Padding for the text.
        inventory.setPadding(new Insets(50));
        //Title of the game
        stage.setTitle("Block World");
        //The MenuBar with file selection
        MenuBar menuBar = new MenuBar();
        //File Menu Bar and its style
        menuBar.setStyle("-fx-background-color: gray; ");
        Menu fileMenu = new Menu("File");
        fileMenu.setStyle("-fx-border-color: black; ");
        MenuItem loadFile = new MenuItem("Load");
        loadFile.setStyle("-fx-border-color: black; ");
        MenuItem saveFile = new MenuItem("Save");
        saveFile.setStyle("-fx-border-color: black; ");
        fileMenu.getItems().addAll(loadFile, saveFile);
        menuBar.getMenus().addAll(fileMenu);
        //Disabling buttonsa and adding styles to them
        this.south.setDisable(true);
        this.south.setStyle("-fx-background-color: #ff0000; ");
        this.west.setDisable(true);
        this.west.setStyle("-fx-background-color: #ff0000; ");
        this.east.setDisable(true);
        this.east.setStyle("-fx-background-color: #ff0000; ");
        this.north.setDisable(true);
        this.north.setStyle("-fx-background-color: #ff0000; ");
        this.moveBlock.setDisable(true);
        this.moveBlock.setStyle("-fx-background-color: white; ");
        this.moveBlock.setStyle("-fx-border-color: black; ");
        this.moveBuilder.setDisable(true);
        this.moveBuilder.setStyle("-fx-background-color: white; ");
        this.moveBuilder.setStyle("-fx-border-color: black; ");
        this.dig.setDisable(true);
        this.dig.setStyle("-fx-background-color: brown; ");
        this.drop.setDisable(true);
        this.drop.setStyle("-fx-background-color: blue; ");

        //Click listener for the move Builder Action
        this.moveBuilder.setOnAction(e -> {

                    //Main functionality of the Button
                    moveBuilderButtonFunctionality();
                }
        );

        //Click listener for the Move Block button
        this.moveBlock.setOnAction(e -> {

                    //Main functionality of the Button
                    moveBlockButtonFunctionality();
                }
        );

        //Click listener for the North button
        this.north.setOnAction(e -> {

                    // Main functionality of the button
                    northButtonFunctionality();
                }
        );

        //West button controller when pressed
        this.west.setOnAction(e -> {

                    //main functionality of the button
                    westButtonFunctionality();
                }
        );

        //East Button controller when pressed
        this.east.setOnAction(e -> {

                    //Main functionality of the Button
                    eastButtonFunctionality();
                }
        );

        //The South button controller
        this.south.setOnAction(e -> {

                    //Main functionality of the South button
                    southButtonFunctionality();
                }
        );

        //Load button controller
        loadFile.setOnAction(e -> {

                    //Main functionality of the button
                    // triggered after being pressed
                    loadButtonFunctionality();
                }
        );

        //The Drop Button controller
        this.drop.setOnAction(e -> {

                    //Main functionality of the button
                    // triggered after being pressed
                    dropButtonFunctionality();
                }
        );

        //The Dig Button controller
        this.dig.setOnAction(e -> {

                    //Main functionality of the button
                    // triggered after being pressed
                    digButtonFunctionality();
                }
        );

        //Save Button controller
        saveFile.setOnAction(e -> {

                    //Main functionality of the button
                    // triggered after being pressed
                    saveButtonFunctionality();
                }
        );

        //Sets the buttons to the right
        grid.setRight(buttons());
        //Sets the menu bar on top
        grid.setTop(menuBar);
        //The welcoming message for the user
        Text welcomingMessage = new Text("Please\nload a map\nto begin");
        welcomingMessage.setFont(Font.font("Helvetica", 50));
        welcomingMessage.setFill(Color.GREEN);
        stackPaneMain.setStyle("-fx-border: 2px; -fx-border-color: black; " +
                "-fx-border-insets: 10px;");
        //Setting the size of the display for the tile map
        stackPaneMain.setPrefWidth(425);
        stackPaneMain.setPrefHeight(425);
        stackPaneMain.setMaxHeight(425);
        stackPaneMain.setMinHeight(425);
        stackPaneMain.setMinWidth(425);
        stackPaneMain.setMaxWidth(425);
        //Rectangle used to hide overflow
        Rectangle clipView = new Rectangle();
        clipView.setHeight(425);
        clipView.setWidth(425);
        stackPaneMain.setClip(clipView);
        //Adding welcome message before map is loaded
        gridPane.add(welcomingMessage, 0, 0);
        //Setting allignment of the message to Center
        gridPane.setAlignment(Pos.CENTER);
        //Adding message to the stack pane
        stackPaneMain.getChildren().add(gridPane);
        //Animation changing colour of welcome message
        FillTransition ft = new FillTransition(Duration.millis(800),
                welcomingMessage,
                Color.GREEN, Color.BLUE);
        ft.setCycleCount(Timeline.INDEFINITE);
        ft.setAutoReverse(true);
        ft.play();
        grid.setLeft(stackPaneMain);
        //Putting textarea at the bottom of the BorderPane
        grid.setBottom(this.inventory);
        //setting height to the application
        grid.setPrefHeight(500);
        grid.setPrefWidth(500);
        //Padding for objects in the grid
        grid.setPadding(new Insets(30));
        //Initiating the scene using the BorderPane
        //The Scene object used in the Application
        Scene scene = new Scene(grid);
        //Setting up the stage to display
        stage.setScene(scene);
        //Showing the stage
        stage.show();
    }

    /**
     * Method which calculated the length of the map loaded.
     * It calculated the actual length using the coordinates.
     * It is a helper method to add the map to Grid Plane.
     *
     * @return lengthOfPlane: Position- length of the map on X and Y axis.
     */
    private Position getCoordinateLength() {
        Position lengthOfPlane;
        List<Integer> positionListX = new LinkedList<>();
        List<Integer> positionListY = new LinkedList<>();
        int minX;
        int maxX;
        int minY;
        int maxY;
        for (Position position : getMap().values()) {
            positionListX.add(position.getX());
            positionListY.add(position.getY());
        }
        //Uses Collections min and max to collect minimal and maximal values
        minX = Collections.min(positionListX);
        maxX = Collections.max(positionListX);
        minY = Collections.min(positionListY);
        maxY = Collections.max(positionListY);
        //Calculates difference for actual map size
        int differenceX = Math.abs(maxX - minX);
        int differenceY = Math.abs(maxY - minY);
        return lengthOfPlane = new Position(differenceX, differenceY);
    }

    /**
     * Helper method which takes the tile map from the World Map and
     * sorts it out by putting the tile with builder on it as first and
     * coordinates as its value. Used for simpler access to the position of each
     * Tile
     *
     * @return tilePositionMap; Map- the map of tiles and their coordinates in
     * NESW order
     */
    private Map<Tile, Position> getMap() {
        //Each time the method is called it resets the Map
        Map<Tile, Position> tilePositionMap = new LinkedHashMap<>();
        //The starting position of the Tile
        Position startingPosition = new Position(
                worldMap.getStartPosition().getX()
                , worldMap.getStartPosition().getY());
        //Set used in breath first search
        Set<Tile> tilesAlreadyVisited = new HashSet<>();
        //Queue used in breath first search
        Queue<Tile> tilesToVisit = new LinkedList<>();
        //Starts the search from here by adding the startingTile
        tilesToVisit.add(worldMap.getTiles().get(0));
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
        return tilePositionMap;
    }

    /**
     * Core method of the application. Displays the map and process it after
     * each move or button press.Ensures it is consistent and keeps the
     * Player Tile in the centre of view.
     *
     * @return gridPane-GridPane: the processed Grid Pane with the
     * tiles displayed on it.
     */
    private GridPane tiles() {
        //The player position on Y axis
        int playerPositionY = 0;
        //The player position on X axis
        int playerPositionX = 0;
        //boolean check if the column numbers are less than zero.
        // It tells us if the player is above or below the centre of view
        boolean lowerThanZeroColumn = false;
        //boolean check if the row numbers are less than zero.
        // It tells us if the player is on the left or right the centre of view
        boolean lowerThanZeroRow = false;
        //removes the old gridPane and will substitute it with new one
        stackPaneMain.getChildren().remove(gridPane);
        //builder inventory to write
        buildersInventory = new StringBuilder();
        //Starts the new gridPane
        gridPane = new GridPane();
        //Transition animation for each action taken
        FadeTransition trans = new FadeTransition(Duration.seconds(1),
                gridPane);
        trans.setFromValue(0);
        trans.setToValue(1);
        trans.setCycleCount(1);
        trans.play();
        StackPane stackPane;
        Position planeLength = getCoordinateLength();
        //Text printed on each tile with their number of blocks
        Text text;
        //Column integer taken from the helper method and corrected
        // to start from 1
        int column = planeLength.getY() + 1;
        //Row integer taken from the helper method and corrected
        // to start from 1
        int row = planeLength.getX() + 1;
        //Circle representing the Player
        //Circle representing the player
        Circle player = new Circle(5);
        player.setFill(Color.YELLOW);
        player.setTranslateX(-10);
        //Rectangle as a base for every Tile
        Rectangle rectangle;
        //Check if the map size is not 0 to avoid NullPointerException
        if (getMap().size() != 0) {

            //Created the builder's inventory
            buildersInventory.append("Builder's inventory:\n[");
            for (int i = 0; i < worldMap.getBuilder().getInventory().size();
                 i++) {
                buildersInventory
                        .append(worldMap.getBuilder().getInventory().get(i)
                                .getBlockType());
                if (i < worldMap.getBuilder().getInventory().size() - 1) {
                    buildersInventory.append(", ");
                }

            }
            buildersInventory.append("]");
            inventory.setText(buildersInventory.toString());
            //Takes the starting position from the loaded map
            Position startingPosition = worldMap.getStartPosition();
            for (Map.Entry<Tile, Position> entry : getMap().entrySet()) {

                //Creates new stackPane. It is used because it automatically
                // centers all objects in it
                stackPane = new StackPane();
                //Creates Tiles
                rectangle = new Rectangle(50, 50);
                //Triangle representing the exits from Tile
                Polygon triangle;
                try {

                    //Fills the Tiles depending on the type
                    switch (entry.getKey().getTopBlock().getColour()) {
                        case "green":
                            rectangle.setFill(Color.GREEN);
                            break;
                        case "gray":
                            rectangle.setFill(Color.GRAY);
                            break;
                        case "black":
                            rectangle.setFill(Color.BLACK);
                            break;
                        case "brown":
                            rectangle.setFill(Color.BROWN);
                            break;
                        default:
                            rectangle.setFill(Color.TRANSPARENT);
                            break;
                    }

                } catch (TooLowException e) {
                    tooLowAlert();
                }

                //Takes the number of blocks in each tile and paints it on the
                //restangle
                text = new Text(
                        Integer.toString(entry.getKey().getBlocks().size()));
                text.setFill(Color.WHITE);
                text.setTranslateX(5);
                text.setFont(Font.font("Verdana", 20));
                stackPane.getChildren().add(rectangle);
                stackPane.getChildren().add(text);

                //Adds triangles to tile if contains north exit
                if (entry.getKey().getExits().containsKey("north")) {
                    triangle = new Polygon();
                    triangle.setFill(Color.WHITE);
                    triangle.getPoints().addAll(0.0, -5.0,
                            -5.0, 0.0,
                            5.0, 0.0);
                    triangle.setTranslateY(-20.00);
                    stackPane.getChildren().add(triangle);
                }

                //Adds triangles to tile if contains east exit
                if (entry.getKey().getExits().containsKey("east")) {
                    triangle = new Polygon();
                    triangle.setFill(Color.WHITE);
                    triangle.getPoints().addAll(0.0, -5.0,
                            -5.0, 0.0,
                            5.0, 0.0);
                    triangle.setTranslateX(-17.00);
                    triangle.setTranslateY(-3.00);
                    triangle.getTransforms().add(new Rotate(-90));
                    stackPane.getChildren().add(triangle);
                }

                //Adds triangles to tile if contains south exit
                if (entry.getKey().getExits().containsKey("south")) {
                    triangle = new Polygon();
                    triangle.setFill(Color.WHITE);
                    triangle.getPoints().addAll(0.0, -5.0,
                            -5.0, 0.0,
                            5.0, 0.0);
                    triangle.setTranslateY(16.00);
                    triangle.getTransforms().add(new Rotate(180));
                    stackPane.getChildren().add(triangle);
                }

                //Adds triangles to tile if contains west exit
                if (entry.getKey().getExits().containsKey("west")) {
                    triangle = new Polygon();
                    triangle.setFill(Color.WHITE);
                    triangle.getPoints().addAll(0.0, -5.0,
                            -5.0, 0.0,
                            5.0, 0.0);
                    triangle.setTranslateX(17.00);
                    triangle.setTranslateY(-3.00);
                    triangle.getTransforms().add(new Rotate(90));
                    stackPane.getChildren().add(triangle);
                }

                //Calculates the current column using starting position
                int currentColumn =
                        entry.getValue().getY() - startingPosition.getY();
                //Calculates the current row using starting position
                int currentRow =
                        entry.getValue().getX() - startingPosition.getX();

                //Checks which tile contains the player currently
                if (entry.getKey() == worldMap.getBuilder().getCurrentTile()) {
                    stackPane.getChildren().addAll(player);
                    playerPositionY = column;
                    playerPositionX = row;
                }

                //Adds tiles to the gridPane depending on their position on
                // the Map
                gridPane.add(stackPane, row - currentRow,
                        column + currentColumn);

                //Checks if the numbers are negative to determine if player
                //is displayed above or below the centre
                if (currentColumn < 0) {
                    lowerThanZeroColumn = true;
                }

                //Checks if the numbers are negative to determine if player is
                // on the left or right of the centre
                if (currentRow < 0) {
                    lowerThanZeroRow = true;
                }
            }

            //Alligns the content in the centre
            gridPane.setAlignment(Pos.CENTER);
            //Creates small gap between tiles for better visibility
            gridPane.setHgap(1);
            gridPane.setVgap(1);
        }

        //Calculates the centre of the map on Y and X axis
        double centreY = Math.floor(planeLength.getY() + 1) / 2;
        double centreX = Math.floor(planeLength.getX() + 1) / 2;

        //Calculated where to put the player on the map so it is in the
        //centre
        double correctionY = (playerPositionY - 1 - centreY) * 50;
        double correctionX = (playerPositionX - 1 - centreX) * 50;

        //Checks if correction is bigger than 1 tile size and if the map
        // correction was already saved. It is saved once only for the first
        // Tile with player.
        if (correctionY > 50 && !savedY) {
            if (lowerThanZeroColumn) {
                gridPane.setTranslateY(-correctionY);
                mapCorrectionY = -correctionY;
                savedY = true;
            } else {
                gridPane.setTranslateY(correctionY);
                mapCorrectionY = correctionY;
                savedY = true;
            }
        }
        if (correctionX > 50 && !savedX) {
            if (lowerThanZeroRow) {
                gridPane.setTranslateX(correctionX);
                mapCorrectionX = correctionX;
                savedX = true;
            } else {
                gridPane.setTranslateX(-correctionX);
                mapCorrectionX = -correctionX;
                savedX = true;
            }
        }
        return gridPane;
    }

    /**
     * The GridPane having position of all the buttons.
     *
     * @return the GridPane with the buttons in it which is then added to the
     * BorderPane
     */
    private GridPane buttons() {
        GridPane grid = new GridPane();
        grid.add(controlButtons(), 0, 0);
        grid.add(toggeleButtons(), 0, 1);
        grid.add(dropControl(), 0, 2);
        grid.add(this.dig, 0, 6);
        grid.setPadding(new Insets(10));
        return grid;
    }

    /**
     * The controlls for the drop functionality.
     *
     * @return dropControl:GridPane- the buttons needed for Drop functionality
     */
    private GridPane dropControl() {
        GridPane dropControl = new GridPane();
        dropControl.add(this.drop, 0, 0);
        dropControl.add(this.inventoryIndex, 1, 0);
        return dropControl;
    }

    /**
     * The control buttons for movement of the builder and moving blocks
     *
     * @return controlButtons:GridPane- the pane with all buttons for movement.
     */
    private GridPane controlButtons() {
        GridPane controlButtons = new GridPane();
        controlButtons.add(this.north, 1, 0);
        controlButtons.add(this.east, 0, 1);
        controlButtons.add(this.west, 2, 1);
        controlButtons.add(this.south, 1, 3);
        controlButtons.setHgap(10);
        controlButtons.setAlignment(Pos.CENTER);
        return controlButtons;
    }

    /**
     * The grid pane with the toggle buttons controlling the
     * movement buttons.
     *
     * @return toggleButton:GridPane- the group with toggle buttons
     */
    private GridPane toggeleButtons() {
        GridPane toggleButtons = new GridPane();
        ToggleGroup toggleGroup = new ToggleGroup();
        this.moveBlock.setToggleGroup(toggleGroup);
        this.moveBlock.setPadding(new Insets(5));
        this.moveBlock.setPrefWidth(100);
        this.moveBuilder.setToggleGroup(toggleGroup);
        this.moveBuilder.setPadding(new Insets(5));
        this.moveBuilder.setPrefWidth(100);
        toggleButtons.add(this.moveBuilder, 0, 0);
        toggleButtons.add(this.moveBlock, 1, 0);
        toggleButtons.setPadding(new Insets(20, 0, 20, 0));
        toggleButtons.setHgap(10);
        return toggleButtons;
    }
}

