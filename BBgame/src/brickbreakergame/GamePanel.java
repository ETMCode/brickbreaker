package brickbreakergame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
/**
* Project: GamePanel.java
* author Altaf Khan
* version 3.1
* Created:
* Description: 
* The main game panel. Runs the game loop via a Swing Timer,
* handles keyboard input, and draws everything each frame.
*/
@SuppressWarnings("serial")
public class GamePanel extends JPanel implements ActionListener, KeyListener {

    // Board dimensions
    static final int WIDTH  = 880;
    static final int HEIGHT = 680;

    // Game objects
    Paddle    paddle;
    Ball      ball;
    bricks    bricks;

    // Game state
    int     score    = 0;
    int     lives    = 3;
    boolean gameOver = false;
    boolean youWon   = false;

    // Drives the game loop  
    Timer timer;

    // Constructor  sets up the panel and initializes game objects
    public GamePanel() {
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
     // Dark background color
        setBackground(new Color(15, 15, 30));  
        // Lets the panel receive keyboard input
        setFocusable(true);                    
        addKeyListener(this);

        // Create the paddle, bricks, and ball
        paddle = new Paddle(370, 620);
        bricks = new bricks();
        ball   = new Ball(430, 600);
        
        // Start ball resting on the paddle
        ball.sitOnPaddle(paddle);  

        // Start the game loop timer (fires every 10ms)
        timer = new Timer(12, this);
        timer.start();
    }

    // Game loop 
    @Override
    public void actionPerformed(ActionEvent e) {
        if (!gameOver && !youWon) {
            // Move the paddle based on key input
            paddle.move(WIDTH);

            if (ball.launched) {
                // Move the ball and check if a life was lost
                boolean lostLife = ball.move(paddle, WIDTH, HEIGHT);

                if (lostLife) {
                    lives--;
                    if (lives <= 0) {
                        // No lives left, game over
                        gameOver = true;
                    } else {
                        // Still have lives, reset the ball
                        ball.reset();
                        ball.sitOnPaddle(paddle);
                    }
                }

                // Check if the ball hit any bricks and add points
                int points = bricks.checkCollision(ball);
                score += points;

                // If all bricks are gone, player wins
                if (bricks.countAlive() == 0) {
                    youWon = true;
                }

            } else {
                // Ball sits on paddle until SPACE is pressed
                ball.sitOnPaddle(paddle);
            }
        }

        // Redraw the screen
        repaint();
    }

    // Drawing
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Draw all game objects
        bricks.draw(g);
        paddle.draw(g);
        ball.draw(g);
        // Draw score and lives
        drawHUD(g);  

        // Show launch hint if ball hasn't been launched yet
        if (!ball.launched && !gameOver && !youWon) {
            drawCentered(g, "Press SPACE to launch!",
                         WIDTH / 2, HEIGHT - 20,
                         new Font("Courier New", Font.PLAIN, 15),
                         new Color(180, 180, 180));
        }

        // Show game over or win screen if needed
        if (gameOver) drawGameOver(g);
        if (youWon)   drawYouWon(g);
    }

    // Draws the score and lives at the top of the screen
    void drawHUD(Graphics g) {
        g.setFont(new Font("Courier New", Font.BOLD, 18));
        g.setColor(new Color(200, 200, 200));
        g.drawString("Score:" + score, 20, 30);

        // Draw circles as lives indicator
        g.setColor(new Color(220, 60, 80));
        for (int i = 0; i < lives; i++) {
            g.fillOval(WIDTH - 35 - i * 28, 12, 18, 18);
        }
        g.setColor(new Color(200, 200, 200));
        g.setFont(new Font("Courier New", Font.PLAIN, 14));
        g.drawString("Lives:", WIDTH - 120, 27);
    }

    // Draws the game over screen with a dark overlay
    void drawGameOver(Graphics g) {
        // Semi transparent dark overlay
        g.setColor(new Color(0, 0, 0, 160));
        g.fillRect(0, 0, WIDTH, HEIGHT);

        // "GAME OVER" title
        drawCentered(g, "GAME OVER",
                     WIDTH / 2, HEIGHT / 2 - 30,
                     new Font("Courier New", Font.BOLD, 64),
                     new Color(220, 60, 60));

        // Final score
        drawCentered(g, "Score: " + score,
                     WIDTH / 2, HEIGHT / 2 + 30,
                     new Font("Courier New", Font.PLAIN, 22),
                     new Color(200, 200, 200));

        // Restart prompt
        drawCentered(g, "Press SPACE to play again",
                     WIDTH / 2, HEIGHT / 2 + 65,
                     new Font("Courier New", Font.PLAIN, 22),
                     new Color(200, 200, 200));
    }

    // Draws the you win screen with a dark overlay
    void drawYouWon(Graphics g) {
        // Semi-transparent dark overlay
        g.setColor(new Color(0, 0, 0, 160));
        g.fillRect(0, 0, WIDTH, HEIGHT);

        // "YOU WIN!" title
        drawCentered(g, "YOU WIN!",
                     WIDTH / 2, HEIGHT / 2 - 30,
                     new Font("Courier New", Font.BOLD, 64),
                     new Color(100, 230, 100));

        // Final score
        drawCentered(g, "Score: " + score,
                     WIDTH / 2, HEIGHT / 2 + 30,
                     new Font("Courier New", Font.PLAIN, 22),
                     new Color(200, 200, 200));

        // Restart prompt
        drawCentered(g, "Press SPACE to play again",
                     WIDTH / 2, HEIGHT / 2 + 65,
                     new Font("Courier New", Font.PLAIN, 22),
                     new Color(200, 200, 200));
    }

    // Helper: draws text centered horizontally at position (x, y)
    void drawCentered(Graphics g, String text, int cx, int y, Font font, Color color) {
        g.setFont(font);
        g.setColor(color);
        FontMetrics fm = g.getFontMetrics();
     // Calculate the x position to center the text
        int x = cx - fm.stringWidth(text) / 2;  
        g.drawString(text, x, y);
    }

    // Keyboard input
    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();

        // Move paddle left or right
        if (key == KeyEvent.VK_LEFT)  paddle.movingLeft  = true;
        
        if (key == KeyEvent.VK_RIGHT) paddle.movingRight = true;

        if (key == KeyEvent.VK_SPACE) {
            if (gameOver || youWon) {
                // Restart the game if it's over
                restartGame();
            } else {
                // Launch the ball
                ball.launched = true;
            }
        }
    }
    // Stop moving the paddle when the key is released
    @Override
    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();
        if (key == KeyEvent.VK_LEFT)  paddle.movingLeft  = false;
        if (key == KeyEvent.VK_RIGHT) paddle.movingRight = false;
    }

    @Override
    public void keyTyped(KeyEvent e) {} // Not used but required by KeyListener

   
    // Resets everything back to the starting state
    void restartGame() {
        score    = 0;
        lives    = 3;
        gameOver = false;
        youWon   = false;
        paddle.x = 330;     // Reset paddle position
        bricks.resetAll();  // Bring all bricks back
        ball.reset();
        ball.sitOnPaddle(paddle);
    }
}