package brickbreakergame;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 * Ball 
 * - Uses position (x,y) and velocity (dx,dy)
 * - move() updates position each timer tick
 * - Wall collision: reverse dx or dy
 * - Paddle collision: reverse dy + change dx using hitPos/8 angle
 * - Draw: PNG if found, otherwise draw a circle
 */
public class Ball {

    // Position (top-left of ball)
    int x;
    int y;

    // Ball size (diameter)
    int size = 14;

    // Velocity (movement per frame)
    int dx = 3;
    int dy = -3;

    // If false, ball stays on paddle until launch()
    boolean launched = false;

    // Optional image
    private BufferedImage ballImg;

    // Constructor: set start position and load image once
    public Ball(int startX, int startY) {
        x = startX;
        y = startY;

        // Loads ball.png from the SAME package folder (brickBraker)
        // Put ball.png in: src/brickBraker/ball.png
        try {
            ballImg = ImageIO.read(getClass().getResource("ball.png"));
            // IOException = problem reading the image file
              // IllegalArgumentException = a method a bad input
        } catch (IOException | IllegalArgumentException e) {
            ballImg = null; // fallback to circle
              // Messege if forgot  file
            System.out.println("Could not load ball.png. Put it in src/brickBraker/");
        }
    }

    // Location helpers, game board can ask the ball for its position ( X and Y intergers )
    public int getX() { return x; }
    public int getY() { return y; }

    // Rectangle for collisions (invisible box around the ball)
    public Rectangle getBounds() {
        return new Rectangle(x, y, size, size);
    }

    //**
     // Moves the ball every frame and returns true if the ball falls off the screen
     //* move()
     //* Called every timer tick.
     //* @return true if ball fell below bottom of screen (lost)
     // *
    public boolean move(Paddle paddle, int boardWidth, int boardHeight) {

        // If not launched, lock ball on paddle
        if (!launched) {
            sitOnPaddle(paddle);
            return false;
        }

        // Move the ball each frame
        x += dx;
        y += dy;

        // Wall collisions

        // Left wall
          // x is the left position of the ball
             // 0 is the left edge of the game screen
        if (x <= 0) {
            x = 0;
        // Reverses the ball’s horizontal direction
           // Makes the ball bounce off the wall   
            dx = -dx;
        }

        // Right wall
          //  x = the left position of the ball
            // size = the width (diameter) of the ball
        	  // x + size = the right edge of the ball
        if (x + size >= boardWidth) {
            // Impact
        	x = boardWidth - size;
            // Reversal
        	dx = -dx;
        }

        // Top wall
          // y top of ball and 0 is top edge
        if (y <= 0) {
          // Impact 
        	y = 0;
          // Reversal  
        	dy = -dy;
        }

        //            Paddle collision +  angle 
          // dy > 0 means ball is moving DOWN (prevents sticking/double bouncing)
            //
        if (dy > 0 &&
            y + size >= paddle.y &&
            y + size <= paddle.y + paddle.height &&
            x + size >= paddle.x &&
            x <= paddle.x + paddle.width) {

            // Put ball above paddle
            y = paddle.y - size;

            // Bounce up
              // Negative dy = moving up
            dy = -Math.abs(dy);

            // "Angle" control:
            // hitPos < 0 = left side => dx negative
            // hitPos > 0 = right side => dx positive
            // Makes the ball go the direction of impact based on paddle side of impact 
            	//
            		// (x + size / 2) center of the ball 
            		// (paddle.x + paddle.width / 2) center paddle 
            		// hit <0 (left) hit >0 (right) 0 = (center)
            int hitPos = (x + size / 2) - (paddle.x + paddle.width / 2);
            dx = hitPos / 8;

            // Prevent perfectly vertical movement
              // Makes it move slightly to the right
                // Ensures the ball travels at an angle, not straight up and down
            if (dx == 0) dx = 1;
        }

        // Fell below bottom?
          // The top of the ball has gone below the bottom of the screen
        return (y > boardHeight);
    }

    // Center ball on paddle (before launch / after reset)
    public void sitOnPaddle(Paddle paddle) {
        x = paddle.x + paddle.width / 2 - size / 2;
        y = paddle.y - size - 2;
    }

    // Reset ball state (stop it and put it back on paddle in the loop)
    public void reset() {
        dx = 3;
        dy = -3;
        launched = false;
    }

    public void launch() {
        launched = true;
    }

    // Draw ball (image if available, else circle)
    public void draw(Graphics g) {
        if (ballImg != null) {
            g.drawImage(ballImg, x, y, size, size, null);
        } else {
            g.fillOval(x, y, size, size);
        }
    }
}
