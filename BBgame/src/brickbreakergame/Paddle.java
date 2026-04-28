package brickbreaker;

import java.awt.Color;
import java.awt.Graphics;

public class Paddle {

	int x, y; // Position of the top-left corner of paddle 
	int width = 100; // how wide the paddle is
	int height = 10; // how tall the paddle is 
	int speed = 10; // how fast the paddle is
	
	// these are set to true by Game Panel when arrow keys are pressed  
	boolean movingleft = false; 
	boolean movingright = false;
	
	//constructor - called when we do: new paddle 
	public Paddle(int x, int y) {
		this.x =x; // save starting x position
		this.y =y; // save starting y position 
	}
	//called every frame - moves paddle if a key is being held
	public void move(int panelWidth) {
		// only move left if we are not at the edge of the left side so we don't go off screen
		if (movingleft & x >0) {
			x -= speed; // move left by subtracting pixels 
		}
		// only move right if we are not at the edge of the right side so don't go off screen 
		if (movingright & x + width < panelWidth) {
			x += speed; } // move right by adding pixels 
		}
			// draws the paddle on the screen
			public void draw (Graphics g) {
				g.setColor(new Color (225, 100,100)); 
				//fill round Rect makes a rectangle with rounded corners
				//last two make how round it is 
				g.fillRoundRect(x, y, width, height, 10, 10);
				
			
	}
}
