/* Gabriel Beal
 * cs8bwakv
 * 2/25/18
 *
 * This file defines the Gui for the 2048 game. It has a fully implemented
 * graphical pane that uses the methods from Board.java.
 *
 * This class creates a panel that contains the board of the game and the score.
 * It is controlled by the arrow keys on the keyboard. It can be saved and also
 * tells the user when the game is over.
 *
 */

import javafx.application.*;
import javafx.scene.control.*;
import javafx.scene.*;
import javafx.scene.paint.*;
import javafx.scene.shape.*;
import javafx.scene.layout.*;
import javafx.stage.*;
import javafx.event.*;
import javafx.scene.input.*;
import javafx.scene.text.*;
import javafx.geometry.*;
import java.util.*;

import java.io.*;

public class Gui2048 extends Application
{
    private String outputBoard; // The filename for where to save the Board
    private Board board; // The 2048 Game Board

    private GridPane pane;
    
    /** Add your own Instance Variables here */
    private BorderPane top;
    private BorderPane background;
    private Tile tile;
    private int[][] grid;
    private Tile[][] tileGrid;
   
   
    
     //inner class to create tiles that contain both a rectangle and a text field
    private class Tile extends Rectangle{
        
        //instance variables
        private Rectangle square;
        private Label label;

        //constructor
        public Tile(){
            this.square = new Rectangle();
            this.label = new Label();
        }
    
        /**
         * Getter method for the square
         * @return the square
         */
        public Rectangle getSquare(){
            return this.square;   
        }

        /**
         * Getter method for label
         * @return value
         */
        public Label getLabel(){
            return this.label; 
        }
        
    }
    
    @Override
    public void start(Stage primaryStage)
    {
        // Process Arguments and Initialize the Game Board
        processArgs(getParameters().getRaw().toArray(new String[0]));

        // Create the pane that will hold all of the visual objects
        pane = new GridPane();
        pane.setAlignment(Pos.CENTER);
        pane.setPadding(new Insets(11.5, 12.5, 13.5, 14.5));
        pane.setStyle("-fx-background-color: rgb(187, 173, 160)");
        // Set the spacing between the Tiles
        pane.setHgap(15); 
        pane.setVgap(15);

        /** Add your Code for the GUI Here */
        //Creates a new border pane that will contain all of the text at the top
        //of the game
        top = new BorderPane();
        top.setStyle("-fx-background-color: rgb(187, 173, 160)");
       
        //Adds 2048 to the top left corner
        Label text2048 = new Label("     2048");
        text2048.setFont(Font.font("Times New Roman",FontWeight.BOLD,
                FontPosture.ITALIC,30));
        
        //Adds score to the top right corner
        updateScore(board);
        
        //adds the two texts to the border pane
        top.setLeft(text2048);
        top.setPadding(new Insets(20,20,0,0));

        //calls startBoard that adds all the tiles to the gridpane
        startBoard(board);

        //creates new border pane that everything will be added to
        background = new BorderPane();
        background.setTop(top);
        background.setCenter(pane);

        //creates scene
        Scene scene = new Scene(background);
        scene.setOnKeyPressed(new KeyHandler());

        //sets the stage
        primaryStage.setTitle("Gui2048");
        primaryStage.setScene(scene);
        primaryStage.show();

        
    }

    /** Add your own Instance Methods Here */
    
    /**
     * Method that updates and adds tiles to the board with appropriate colors
     * @param board the game board
     */
    public void startBoard(Board board){
        //uses the grid of the board to create a 2d int array
        grid = board.getGrid();

        //creates 2d array of tiles
        tileGrid = new Tile[board.GRID_SIZE][board.GRID_SIZE];
        
        //for loop that adds tiles to the board with appropriate values and
        //color
        for(int c = 0; c < board.GRID_SIZE; c++){
            for(int r = 0; r < board.GRID_SIZE; r++){
                //creates a new tile object and populates the 2d tile grid
                tile = new Tile();
                tileGrid[r][c] = tile; 

                //adds the value to the board only if it is greater than 0
                if(grid[r][c] >= 2){
                    tile.getLabel().setText(Integer.toString(grid[r][c]));
                }
                
                //default font for the tiles
                tile.getLabel().setFont(Font.font("Times New Roman",FontWeight
                    .BOLD,50));
                
                //sets the font of the tiles if it differs from the default 
                setFont(c,r,grid,tileGrid);
                
                //sets the squares size and color
                tile.getSquare().setWidth(100);
                tile.getSquare().setHeight(100);
                tile.getSquare().setFill(getColor(c,r,grid));
                
                //binds the size of the squares to the window pane size
                tile.getSquare().widthProperty().bind(pane.widthProperty().
                subtract(pane.getHgap()*board.GRID_SIZE).divide(board.GRID_SIZE+1));

                tile.getSquare().heightProperty().bind(pane.heightProperty().
                subtract(pane.getVgap()*board.GRID_SIZE).divide(board.GRID_SIZE+1));
                
                
                //adds the tiles to the pane
                pane.add(tile.getSquare(),c,r);
                pane.add(tile.getLabel(),c,r);
                pane.setHalignment(tile.getSquare(),HPos.CENTER);
                pane.setValignment(tile.getSquare(),VPos.CENTER);
                pane.setHalignment(tile.getLabel(),HPos.CENTER);
                pane.setValignment(tile.getLabel(),VPos.CENTER);
            }   
        }
    }

    /**
     * Performs the same as startBoard except it doesn't add any new tiles just
     * updates the current tiles
     * @param board,tileGrid the game board and the 2d grid of tiles that can be
     *                       edited
     */
    public void updateBoard(Board board, Tile[][] tileGrid){
        //uses the grid of the board to create a 2d int array
        grid = board.getGrid();
        
        //for loop that goes through and updates the values of the board
        for(int c = 0; c < board.GRID_SIZE; c++){
            for(int r = 0; r < board.GRID_SIZE; r++){
                //adds the value to the board only if it is greater than 0
                if(grid[r][c] >= 2){
                    tileGrid[r][c].getLabel().setText(Integer.toString(grid[r][c]));
                }
                else{
                    tileGrid[r][c].getLabel().setText(" ");   
                }
               
                //sets the font of the tiles
                setFont(c,r,grid,tileGrid);
                
                //sets the color of the square
                tileGrid[r][c].getSquare().setFill(getColor(c,r,grid));
            }   
        }
    }
    /**
     * Method that returns the appropriate color for different values up to 2048
     * @return Color the appropriate color
     * @param c,r,grid the collumn and row of the 2d grid from the board
     */
    public static Color getColor(int c, int r, int[][] grid){
        //if statements that return the appropriate color for different
        //values
                if(grid[r][c] == 2){
                    return Color.OLDLACE;
                }

                else if(grid[r][c] == 4){
                    return Color.NAVAJOWHITE;
                }

                else if(grid[r][c] == 8){
                    return Color.SANDYBROWN;
                }

                else if(grid[r][c] == 16){
                    return Color.ORANGE;
                }

                else if(grid[r][c] == 32){
                    return Color.CORAL;
                }

                else if(grid[r][c] == 64){
                    return Color.TOMATO;
                }

                else if(grid[r][c] >= 128 && grid[r][c] <= 2048){
                    return Color.GOLD;
                }

                else if(grid[r][c] > 2048){
                    return Color.BLACK;
                }
               
        
        return Color.TAN;
    }

    //private inner class that handles events
    private class KeyHandler implements EventHandler<KeyEvent>{
        /**
         * Method that handles every key input and calls the correct methods
         */
        @Override
        public void handle(KeyEvent e){
            //moves the tiles up and updates the board and prints out moving up
            //if the tiles can move up
            if(e.getCode() == KeyCode.UP){
                if(board.move(Direction.UP)){
                   System.out.println("Moving Up");
                   board.addRandomTile();
                   updateBoard(board,tileGrid);
                   updateScore(board);

                   //if the game is over calls the game over method 
                   if(board.isGameOver()){
                        gameOver();   
                   }
                }
            }

            //moves the tiles down
            else if(e.getCode() == KeyCode.DOWN){
                if(board.move(Direction.DOWN)){
                    System.out.println("Moving Down");
                    board.addRandomTile();
                    updateBoard(board,tileGrid);
                    updateScore(board);

                    //if the game is over calls the game over method 
                    if(board.isGameOver()){
                        gameOver();   
                    }
                    
                }   
            }
            
            //moves tiles to the right
            else if(e.getCode() == KeyCode.RIGHT){
                if(board.move(Direction.RIGHT)){
                    System.out.println("Moving Right");
                    board.addRandomTile();
                    updateBoard(board,tileGrid);
                    updateScore(board);

                    //if the game is over calls the game over method 
                    if(board.isGameOver()){
                        gameOver();   
                    }
                }   
            }
            
            //moves tiles to the left
            else if(e.getCode() == KeyCode.LEFT){
                if(board.move(Direction.LEFT)){
                    System.out.println("Moving Left");
                    board.addRandomTile();
                    updateBoard(board,tileGrid);
                    updateScore(board);

                    //if the game is over calls the game over method 
                    if(board.isGameOver()){
                        gameOver();   
                    }
                }   
            }

            //saves the board if 's' is pressed
            else if(e.getCode() == KeyCode.S){
                System.out.println("Saving Board to " + outputBoard);

                //places save inside of a try catch statement so the code doesn't
                //crash
                try{
                    board.saveBoard(outputBoard);   
                } catch(IOException i){
                       System.out.println("saveBoard threw an Exception");   
                }

            }
               
        }
    }

    /**
     * Method that updates the score field in the corner
     * @param board the board that we get the score from
     */
    public void updateScore(Board board){
        Label textScore = new Label("Score: " + board.getScore());
        textScore.setFont(Font.font("Times New Roman",FontWeight.BOLD,25));
        top.setRight(textScore);
    }

    /**
     * Method to control the font of the tiles 
     * @param c,r,grid,tileGrid the collumn and row of the tile and the two 2d
     * arrays that contain the board grid and the tiles
     */
    public void setFont(int c, int r,int[][] grid, Tile[][] tileGrid){
           //2 & 4 have a black font and the rest have a white font
           if(grid[r][c] > 4){
               tileGrid[r][c].getLabel().setTextFill(Color.WHITE);
           }
           else{
               tileGrid[r][c].getLabel().setTextFill(Color.BLACK);   
           }
           
           //sets the font to a smaller in order to fit in the tiles
           if(grid[r][c] > 64){
               tileGrid[r][c].getLabel().setFont(Font.font("Times New Roman",FontWeight.BOLD,45));   
           }
           if(grid[r][c] > 512){   
               tileGrid[r][c].getLabel().setFont(Font.font("Times New Roman",FontWeight.BOLD,30));   
           }
           if(grid[r][c] > 8192){
               tileGrid[r][c].getLabel().setFont(Font.font("Times New Roman",FontWeight.BOLD,25));   
           }
           
    }

    /**
     * Method that places a Game Over pane over the game if there are no more
     * avaliable moves
     */
    public void gameOver(){
        //creates a tanslucent bord pane to cover the 2048 and score
        BorderPane transTop = new BorderPane();
        transTop.setStyle("-fx-background-color: rgb(187, 173, 180, 0.5)");

        //creates a new stackpane that combines the top border pane and the
        //translucent
        StackPane stackTop = new StackPane();
        stackTop.getChildren().addAll(top,transTop);

        //creates a new Border pane to add on top of the tile grid that contains
        //Game Over and is translucent
        BorderPane transGrid = new BorderPane();
        transGrid.setStyle("-fx-background-color: rgb(187, 173, 180, 0.5)");
        Label gameOver = new Label("Game Over!");
        gameOver.setFont(Font.font("Times New Roman",FontWeight.BOLD,60));
        transGrid.setCenter(gameOver);

        //creates a stack pane to combine the grid pane and game over pane
        StackPane stackCenter = new StackPane();
        stackCenter.getChildren().addAll(pane,transGrid);

        //adds the stack panes to the background pane
        background.setTop(stackTop);
        background.setCenter(stackCenter);
    }
 

    /** DO NOT EDIT BELOW */

    // The method used to process the command line arguments
    private void processArgs(String[] args)
    {
        String inputBoard = null;   // The filename for where to load the Board
        int boardSize = 0;          // The Size of the Board

        // Arguments must come in pairs
        if((args.length % 2) != 0)
        {
            printUsage();
            System.exit(-1);
        }

        // Process all the arguments 
        for(int i = 0; i < args.length; i += 2)
        {
            if(args[i].equals("-i"))
            {   // We are processing the argument that specifies
                // the input file to be used to set the board
                inputBoard = args[i + 1];
            }
            else if(args[i].equals("-o"))
            {   // We are processing the argument that specifies
                // the output file to be used to save the board
                outputBoard = args[i + 1];
            }
            else if(args[i].equals("-s"))
            {   // We are processing the argument that specifies
                // the size of the Board
                boardSize = Integer.parseInt(args[i + 1]);
            }
            else
            {   // Incorrect Argument 
                printUsage();
                System.exit(-1);
            }
        }

        // Set the default output file if none specified
        if(outputBoard == null)
            outputBoard = "2048.board";
        // Set the default Board size if none specified or less than 2
        if(boardSize < 2)
            boardSize = 4;

        // Initialize the Game Board
        try{
            if(inputBoard != null)
                board = new Board(new Random(), inputBoard);
            else
                board = new Board(new Random(), boardSize);
        }
        catch (Exception e)
        {
            System.out.println(e.getClass().getName() + 
                               " was thrown while creating a " +
                               "Board from file " + inputBoard);
            System.out.println("Either your Board(String, Random) " +
                               "Constructor is broken or the file isn't " +
                               "formated correctly");
            System.exit(-1);
        }
    }

    // Print the Usage Message 
    private static void printUsage()
    {
        System.out.println("Gui2048");
        System.out.println("Usage:  Gui2048 [-i|o file ...]");
        System.out.println();
        System.out.println("  Command line arguments come in pairs of the "+ 
                           "form: <command> <argument>");
        System.out.println();
        System.out.println("  -i [file]  -> Specifies a 2048 board that " + 
                           "should be loaded");
        System.out.println();
        System.out.println("  -o [file]  -> Specifies a file that should be " + 
                           "used to save the 2048 board");
        System.out.println("                If none specified then the " + 
                           "default \"2048.board\" file will be used");  
        System.out.println("  -s [size]  -> Specifies the size of the 2048" + 
                           "board if an input file hasn't been"); 
        System.out.println("                specified.  If both -s and -i" + 
                           "are used, then the size of the board"); 
        System.out.println("                will be determined by the input" +
                           " file. The default size is 4.");
    }
}
