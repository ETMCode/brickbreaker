package brickbreakergame;
import java.awt.Graphics;
import java.awt.Rectangle;
public class Bricks {
Brick[][] grid; // this is our 2D array = table of bricks 
	
	// these control the layout of the bricks
	int rows = 6; 		// the amount rows of bricks 
	int cols = 11; 		// the amount columns of bricks
	int brickW = 70; 	// the width of each brick 
	int brickH = 40; 	// the Height of each brick 
	int space = 3; 		// gaps between each brick
	int xoffset = 25; 	// how far from the left edge of the bricks start
	int yoffset = 60; 	// how far from the top of the bricks grid start 
	
	//constructor - builds the whole grid of bricks
	public Bricks () {
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
	public int checkCollision(Ball ball) {
	    int points = 0;

	    // gets the bounding rect of the ball 
	    Rectangle ballwalls = ball.getBounds();

	    for (Brick[] row : grid) {
	        for (Brick b : row) {
	        	//skips bricks that are dead
	            if (!b.alive) continue;

	            Rectangle brickwalls = new Rectangle(b.x, b.y, b.width, b.height);

	            if (ballwalls.intersects(brickwalls)) {
	                b.alive = false;
	                points += 10;

	                //gets the edges of the ball
	                int ballLeft   = ballwalls.x;
	                int ballRight  = ballwalls.x + ballwalls.width;
	                int ballTop    = ballwalls.y;
	                int ballBottom = ballwalls.y + ballwalls.height;
	                //gets the edges of brick
	                int brickLeft   = brickwalls.x;
	                int brickRight  = brickwalls.x + brickwalls.width;
	                int brickTop    = brickwalls.y;
	                int brickBottom = brickwalls.y + brickwalls.height;
	                //how deep the ball goes into the sides of the brick.
	                int overlapLeft   = ballRight - brickLeft;   // ball into brick from left
	                int overlapRight  = brickRight - ballLeft;   // ball into brick from right
	                int overlapTop    = ballBottom - brickTop;   // ball into brick from top
	                int overlapBottom = brickBottom - ballTop;   // ball into brick from bottom

	                // Find the smallest overlap (axis of least penetration)
	                int minX = Math.min(overlapLeft, overlapRight);
	                int minY = Math.min(overlapTop, overlapBottom);

	                if (minX < minY) {
	                    // Side hit -> bounce horizontally
	                    ball.dx = -ball.dx;
	                } else {
	                    // Top/bottom hit -> bounce vertically
	                    ball.dy = -ball.dy;
	                }
	             // stop after 1 brick hit this tick (prevents weird double hits)
	                 return points;
	            }
	        }
	    }
	    
	    return points;
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
}
