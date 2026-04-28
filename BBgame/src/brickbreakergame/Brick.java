package brickbreakergame;

import java.awt.Color;
import java.awt.Graphics;

public class Brick {

	//position of the top-left corner of this brick
		int x, y; 
		//size of the brick 
		int width, height; 
		// track if the brick is still visible on the screen
		// true = brick is still there, false = is was hit
		boolean alive = true;
		
		//Constructor to create a brick with a specific position and size 
		//and called when BrickGrid Creates each brick
		public Brick(int x, int y, int width, int height) {
			this.x = x; 	// save our x position 
			this.y = y; 	// save our y position 
			this.width = width; 	// save width 
			this.height = height; 	// save height 
			// alive is already true by default
		}
		// draws the brick on screen
			public void draw(Graphics g) {
				if (!alive) return; //check the brick to stop drawing after it gets hit
				g.setColor(new Color(255, 120, 120)); // color 
				g.fillRoundRect(x, y, width, height, 5, 5); // makes the brick rounded
				}			
}
