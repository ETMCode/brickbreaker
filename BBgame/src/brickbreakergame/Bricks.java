package brickbreakergame;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
public class BrickGrid {
Brick[][] grid; // this is our 2D array = table of bricks 
	
	// these control the layout of the bricks
	int rows = 5; 		// the amount rows of bricks 
	int cols = 10; 		// the amount columns of bricks
	int brickW = 70; 	// the width of each brick 
	int brickH = 40; 	// the Height of each brick 
	int space = 3; 		// gaps between each brick
	int xoffset = 25; 	// how far from the left edge of the bricks start
	int yoffset = 60; 	// how far from the top of the bricks grid start 
	
	//constructor - builds the whole grid of bricks
	public BrickGrid () {
		grid = new Brick[rows][cols]; //create the 2d array structure 
		
		//loops through every row and column to place a brick 
		for (int r = 0; r < rows; r ++) { 		// r = current row number 
			for (int c = 0; c < cols; c ++) {   // c = current column number 
				
				// Calculate where this brick should appear on the screen 
				
				int bx = xoffset + c * (brickW + space); // x = move right for each column 
				int by = yoffset + r * (brickH + space); // y = move down for each row
				
				// crate a new brick and store it in the array 
				grid[r][c] = new Brick (bx, by, brickW, brickH);
				
			}
		}
	}
	// checks if the ball is hitting any bricks
	// Returns how many points were scored 
	public int checkCollision( Ball ball ) {
		int points = 0; // start with 0 points
		
		//loop through every brick in the grid 
		for (Brick[] row : grid) {
			for (Brick b : row) {
				
				 if (!b.alive) continue ; //  skips the bricks that are dead

				 	// Check if the ball is overlapping with this brick
	                // We check all 4 sides to see if they overlap
				   
	                if (ball.x + ball.size > b.x             			// ball's right side past brick's left side
	                        && ball.x - ball.size < b.x + b.width  	// ball's left side past brick's right side
	                        && ball.y + ball.size > b.y             	// ball's bottom past brick's top
	                        && ball.y - ball.size < b.y + b.height) { // ball's top past brick's bottom

	                    b.alive = false;    // destroy the brick
	                    ball.dy = -ball.dy; // make ball bounce (flip up or down direction)
	                    points += 10;       // add 10 points for this brick 
	                }
	            }
	        }

	        return points; // send the points back to GamePanel
	    }

	    // Counts how many bricks are still alive
	    // GamePanel uses this to check if the player won
	    public int countAlive() {
	        int count = 0; // start counting from zero

	        for (Brick[] row : grid) // loop through each row
	            for (Brick b : row) // loop through each brick in the row
	                if (b.alive) count++; // if the brick is alive, increase the count

	        return count; // return the total of brick still alive
	    }

	    // Brings all bricks back to life (used when restarting the game)
	    public void resetAll() {
	        for (Brick[] row : grid) // loop through each row
	            for (Brick b : row)  // loop through each brick
	                b.alive = true; // set every brick back to alive
	    }
	        // draws the all brick on screen
	        public void draw(Graphics g) {
	        	for (Brick[] row : grid) // loop through each raw
	        	for (Brick b : row) // loop through each brick
	        	b.draw(g); // draw the brick in the screen
	        	
	        	}			
}
