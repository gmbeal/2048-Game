/* Gabriel Beal 
 * cs8bwakv
 * 2/4/18
 *
 * This file defines the Board class for the game 2048. It contains all the 
 * methods that are used to manipulate the board in the game. The instance 
 * variables include a two dimensional array that serves as the board and
 * the int score that tracks the score of the game. 

/**
 *
 * Sample Board
 * <p/>
 *     0   1   2   3
 * 0   -   -   -   -
 * 1   -   -   -   -
 * 2   -   -   -   -
 * 3   -   -   -   -
 * <p/>
 * The sample board shows the index values for the columns and rows
 * Remember that you access a 2D array by first specifying the row
 * and then the column: grid[row][column]
 */

import java.util.*;
import java.io.*;

public class Board {
    public final int NUM_START_TILES = 2; 
    public final int TWO_PROBABILITY = 90;
    public final int GRID_SIZE;


    private final Random random; // a reference to the Random object, passed in 
                                 // as a parameter in Boards constructors
    private int[][] grid;  // a 2D int array, its size being boardSize*boardSize
    private int score;     // the current score, incremented as tiles merge 


    // TODO PSA3
    // Constructs a fresh board with random tiles
    // @param Random object used to choose a random tile placement
    // int Boardsize is the size of the board 
    public Board(Random random, int boardSize) {
        this.random = random;
        GRID_SIZE = boardSize;

	//initializes array and adds 2 starting tiles
	this.grid = new int [GRID_SIZE][GRID_SIZE];

	for (int i = 0 ; i < NUM_START_TILES; i++){
		this.addRandomTile();
	}

	//initializes score at 0
	this.score = 0; 
    }

    // TODO PSA3
    // Construct a board based off of an input file
    // assume board is valid
    // @param Random object used to generate random tiles
    // String inputboard is the file name that the board is saved to
    public Board(Random random, String inputBoard) throws IOException {
        this.random = random; 
        
	//creates a file and scanner obj to go through the file
	File input = new File(inputBoard);
	Scanner scanner = new Scanner (input);
	
	//assigns grid size and creates the array
	GRID_SIZE = scanner.nextInt();
	this.grid = new int [GRID_SIZE][GRID_SIZE];

	//initializes score
	this.score = scanner.nextInt();

	//loops through grid and copies from file
	for(int i = 0; i < GRID_SIZE; i++){
		for(int j = 0; j < GRID_SIZE; j++){
		grid[i][j] = scanner.nextInt();
		}
	}

	 
    }

    // TODO PSA3
    // Saves the current board to a file using the printWriter class
    // @param String outputBoard is the name of the file to save to
    public void saveBoard(String outputBoard) throws IOException {
	//creates a new printwriter obj and file
	PrintWriter writer = new PrintWriter(new File(outputBoard));

	//writes grid size and score
	writer.print(this.GRID_SIZE);
	writer.println();
	writer.print(this.score);
	writer.println();

	//writes the board
	for (int i= 0; i < this.GRID_SIZE; i++){
		for (int j = 0; j < this.GRID_SIZE; j++){	
			writer.print(this.grid[i][j]);
			writer.print(" ");
		}
		writer.println();
	}

	writer.close(); 
    }

    // TODO PSA3
    // Adds a random tile (of value 2 or 4) to a
    // random empty space on the board
    public void addRandomTile() {
	//initiates count and goes through the board to count the empty spaces
	int count = 0;
	
	for(int i = 0; i < this.GRID_SIZE; i++){
		for (int j = 0; j < this.GRID_SIZE; j++){
			if (this.grid[i][j] == 0){
				count += 1;
			}
		}
	}
	
	//if no empty spaces returns
	if (count == 0){
		return;
	}

	//creates random obj to get random values
	Random random = this.random;

	//gets random location and value
	int location = random.nextInt(count);
	int value = random.nextInt(100);

	//loops through the board until the random location is reached and places 
	//either a 2 or 4 based on value
	int locationCount = 0;

	for(int i = 0; i < this.GRID_SIZE; i++){
		for(int j = 0; j < this.GRID_SIZE; j++){
			if (this.grid[i][j] == 0){
			
			
				if (locationCount == location){
					if (value < TWO_PROBABILITY){
						this.grid[i][j] = 2;
					}
			

					else {
					this.grid[i][j] = 4;
					}
				

				}
				locationCount += 1;
			}
		}
	}
    }

    // TODO PSA3
    // determins whether the board can move in a certain direction
    // return true if such a move is possible
    public boolean canMove(Direction direction){
	//depending on the direction tests if the tiles can move in that 
	//direction using helper methods
	if (direction.getX() == 0 && direction.getY() == -1){
		if(this.canMoveUp()){
			return true;
		}
	}

	if(direction.getX() == 0 && direction.getY() == 1){
		if(this.canMoveDown()){
			return true;
		}
	}

	if(direction.getX() == -1 && direction.getY() == 0){
		if(this.canMoveLeft()){
			return true;
		}
	}

	if(direction.getX() == 1 && direction.getY() == 0){
		if(this.canMoveRight()){
			return true;
		}
	}
	return false; 
    }

    //helper method that tests if the tiles can move up
    //@return returns a true or false depending on if it can move
    private boolean canMoveUp (){
	//goes through the grid to check if there is an empty space to move into
	for(int i = 0; i < this.GRID_SIZE-1; i++){
		for(int j = 0; j < this.GRID_SIZE; j++){
			if (this.grid[i][j] == 0){
				for(int index = i + 1; index < this.GRID_SIZE; index++){
					if(this.grid[index][j] > 0){
						return true;
					}
				}
			}
		}
	}

	//checks if any tiles can combine
	for(int i = 0; i < this.GRID_SIZE-1; i++){
		for(int j = 0; j < this.GRID_SIZE; j++){
			if(this.grid[i][j] == this.grid[i+1][j] && this.grid[i][j] != 0){
				return true;
			}
		}
	}
	return false;
    }


    //helper method that tests if the tiles can move down
    //@return returns a true or false depending on if it can move
    private boolean canMoveDown (){
	//goes through the grid to check if there is an empty space to move into
	for(int i = 1; i < this.GRID_SIZE; i++){
		for(int j = 0; j < this.GRID_SIZE; j++){
			if (this.grid[i][j] == 0){
				for(int index = i - 1; index >= 0; index--){
					if(this.grid[index][j] > 0){
						return true;
					}
				}
			}
		}
	}

	//checks if any tiles can combine
	for(int i = 0; i < this.GRID_SIZE-1; i++){
		for(int j = 0; j < this.GRID_SIZE; j++){
			if(this.grid[i][j] == this.grid[i+1][j] && this.grid[i][j] != 0){
				return true;
			}
		}
	}
	return false;
    }


    //helper method that tests if the tiles can move left
    //@return returns a true or false depending on if it can move
    private boolean canMoveLeft (){
	//goes through the grid to check if there is an empty space to move into
	for(int i = 0; i < this.GRID_SIZE; i++){
		for(int j = 0; j < this.GRID_SIZE-1; j++){
			if (this.grid[i][j] == 0){
				for(int index = j + 1; index < this.GRID_SIZE; index++){
					if(this.grid[i][index] > 0){
						return true;
					}	
				}
			}
		}
	}

	//checks if any tiles can combine
	for(int i = 0; i < this.GRID_SIZE; i++){
		for(int j = 0; j < this.GRID_SIZE-1; j++){
			if(this.grid[i][j] == this.grid[i][j+1] && this.grid[i][j] != 0){
				return true;
			}
		}
	}
	return false;
    }


    //helper method that tests if the tiles can move right
    //@return returns a true or false depending on if it can move
    private boolean canMoveRight (){
	//goes through the grid to check if there is an empty space to move into
	for(int i = 0; i < this.GRID_SIZE; i++){
		for(int j = 1; j < this.GRID_SIZE; j++){
			if (this.grid[i][j] == 0){
				for(int index = j - 1; index >= 0; index--){
					if(this.grid[i][index] > 0){
						return true;
					}
				}
			}
		}
	}

	//checks if any tiles can combine
	for(int i = 0; i < this.GRID_SIZE; i++){
		for(int j = 0; j < this.GRID_SIZE-1; j++){
			if(this.grid[i][j] == this.grid[i][j+1] && this.grid[i][j] != 0){
				return true;
			}
		}
	}
	return false;
    }

    // TODO PSA3
    // move the board in a certain direction
    // return true if such a move is successful
    public boolean move(Direction direction) {
	//Depending on the passed in direction moves the tiles in 
	//that direction and returns true if successful

	if (direction.getX() == 0 && direction.getY() == -1){
		if(this.canMoveUp()){
		   this.moveUp();
			return true;

		}
	}

	if(direction.getX() == 0 && direction.getY() == 1){
		if(this.canMoveDown()){
			this.moveDown();
			return true;
		}
	}

	if(direction.getX() == -1 && direction.getY() == 0){
		if(this.canMoveLeft()){
			this.moveLeft();
			return true;
		}
	}

	if(direction.getX() == 1 && direction.getY() == 0){
		if(this.canMoveRight()){
			this.moveRight();
			return true;
		}
	}
        return false;
    }

    //helper method that moves the tiles up
    private void moveUp(){
	
	//shifts all the tiles into empty spaces 
	for(int i = 0; i < this.GRID_SIZE -1; i++){
		for(int j = 0; j < this.GRID_SIZE; j++){
			if(this.grid[i][j] == 0){
				for(int index = i +1; index < this.GRID_SIZE; index++){
					if(this.grid[index][j] > 0){
						this.grid[i][j] = this.grid[index][j];
						this.grid[index][j] = 0;
						break;
					}
					
				}
			}
		}
	}
	//combines all possible numbered tiles
	for(int i = 0; i < this.GRID_SIZE-1; i++){
		for(int j = 0; j < this.GRID_SIZE; j++){
			//if two tiles can combine they are combined and the score is updated
			if(this.grid[i][j] != 0 && this.grid[i+1][j] == this.grid[i][j]){

				this.grid[i][j] += this.grid[i+1][j];
				this.grid[i+1][j] = 0;
				this.score += this.grid [i][j];
			}

		}
	}
	
	//shifts all the tiles into empty spaces after the merge
	for(int i = 0; i < this.GRID_SIZE -1; i++){
		for(int j = 0; j < this.GRID_SIZE; j++){
			if(this.grid[i][j] == 0){
				for(int index = i +1; index < this.GRID_SIZE; index++){
					if(this.grid[index][j] > 0){
						this.grid[i][j] = this.grid[index][j];
						this.grid[index][j] = 0;
						break;
					}
					
				}
			}
		}
	}
    }

    //helper method that moves the tiles down
    private void moveDown(){

	
	//shifts all the tiles into empty spaces 
	for(int i = this.GRID_SIZE-1; i > 0; i--){
		for(int j = 0; j < this.GRID_SIZE; j++){
			if(this.grid[i][j] == 0){
				for(int index = i -1; index >= 0; index--){
					if(this.grid[index][j] > 0){
						this.grid[i][j] = this.grid[index][j];
						this.grid[index][j] = 0;
						break;
					}
					
				}
			}
		}
	}	
	//combines possible tiles
	for(int i = this.GRID_SIZE-1; i > 0; i--){
		for(int j = 0; j < this.GRID_SIZE; j++){
			//if two tiles can combine they are combined and the score is updated
			if(this.grid[i][j] != 0 && this.grid[i-1][j] == this.grid[i][j]){
				this.grid[i][j] += this.grid[i-1][j];
				this.grid[i-1][j] = 0;
				this.score += this.grid [i][j];
			}
		}
	}

	//shifts all the tiles into empty spaces after the merge
	for(int i = this.GRID_SIZE-1; i > 0; i--){
		for(int j = 0; j < this.GRID_SIZE; j++){
			if(this.grid[i][j] == 0){
				for(int index = i -1; index >= 0; index--){
					if(this.grid[index][j] > 0){
						this.grid[i][j] = this.grid[index][j];
						this.grid[index][j] = 0;
						break;
					}
					
				}
			}
		}
	}
    }

    //helper method that moves the tiles left
    private void moveLeft(){

	
	//shifts all the tiles into empty spaces
	for(int i = 0; i < this.GRID_SIZE; i++){
		for(int j = 0; j < this.GRID_SIZE-1; j++){
			if(this.grid[i][j] == 0){
				for(int index = j+1; index < this.GRID_SIZE; index++){
					if(this.grid[i][index] > 0){
						this.grid[i][j] = this.grid[i][index];
						this.grid[i][index] = 0;
						break;
					}
					
				}
			}
		}
	}
	//combines all possible tiles
	for(int i = 0; i < this.GRID_SIZE; i++){
		for(int j = 0; j < this.GRID_SIZE-1; j++){
			//if two tiles can combine they are combined and the score is updated
			if(this.grid[i][j] != 0 && this.grid[i][j+1] == this.grid[i][j]){
				this.grid[i][j] += this.grid[i][j+1];
				this.grid[i][j+1] = 0;
				this.score += this.grid [i][j];
			}
		}
	}

	//shifts all the tiles into empty spaces after the merge
	for(int i = 0; i < this.GRID_SIZE; i++){
		for(int j = 0; j < this.GRID_SIZE-1; j++){
			if(this.grid[i][j] == 0){
				for(int index = j+1; index < this.GRID_SIZE; index++){
					if(this.grid[i][index] > 0){
						this.grid[i][j] = this.grid[i][index];
						this.grid[i][index] = 0;
						break;
					}
					
				}
			}
		}
	}
	
   }

    //helper method that moves the tiles right
    private void moveRight(){

	
	//shifts all the tiles into empty spaces after the merge
	for(int i = 0; i < this.GRID_SIZE; i++){
		for(int j = this.GRID_SIZE-1; j > 0 ; j--){
			if(this.grid[i][j] == 0){
				for(int index = j-1; index >= 0; index--){
					if(this.grid[i][index] > 0){
						this.grid[i][j] = this.grid[i][index];
						this.grid[i][index] = 0;
						break;
					}
					
				}
			}
		}
	}
	//combines all possible numbered tiles
	for(int i = 0; i < this.GRID_SIZE; i++){
		for(int j = this.GRID_SIZE-1; j > 0; j--){
			//if two tiles can combine they are combined and the score is updated
			if(this.grid[i][j] != 0 && this.grid[i][j-1] == this.grid[i][j]){
				this.grid[i][j] += this.grid[i][j-1];
				this.grid[i][j-1] = 0;
				this.score += this.grid [i][j];
			}
		}
	}

	//shifts all the tiles into empty spaces after the merge
	for(int i = 0; i < this.GRID_SIZE; i++){
		for(int j = this.GRID_SIZE-1; j > 0 ; j--){
			if(this.grid[i][j] == 0){
				for(int index = j-1; index >= 0; index--){
					if(this.grid[i][index] > 0){
						this.grid[i][j] = this.grid[i][index];
						this.grid[i][index] = 0;
						break;
					}
					
				}
			}
		}
	}
	
   }


    // No need to change this for PSA3
    // Check to see if we have a game over
    // returns true if the game is over
    public boolean isGameOver() {
        //checks if the board can move in all directions, if it can returns false
        if(this.canMove(Direction.UP)){
		return false;
	}

	if(this.canMove(Direction.DOWN)){
		return false;
	}

	if(this.canMove(Direction.LEFT)){
		return false;
	}

	if(this.canMove(Direction.RIGHT)){
		return false;
	}

	//if the board can't move in any direction returns true
	return true;
    }

    // Return the reference to the 2048 Grid
    public int[][] getGrid() {
        return grid;
    }

    // Return the score
    public int getScore() {
        return score;
    }
    

    @Override
    public String toString() {
        StringBuilder outputString = new StringBuilder();
        outputString.append(String.format("Score: %d\n", score));
        for (int row = 0; row < GRID_SIZE; row++) {
            for (int column = 0; column < GRID_SIZE; column++)
                outputString.append(grid[row][column] == 0 ? "    -" :
                        String.format("%5d", grid[row][column]));

            outputString.append("\n");
        }
        return outputString.toString();
    }

}
