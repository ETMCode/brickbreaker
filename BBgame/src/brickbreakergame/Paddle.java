//Ethan Martinez BrickBreaker Paddle(s) Portion

package brickbreakergame;
import javax.swing.Keystroke; 
import java.awt.event.KeyEvent;
//allows for VK(s) virtual keys
import java.awt.event.KeyListener;
import java.awt.*;
//allows for the coloring in gamepannel

//the guidlines for what happens with the keyboard (we can use it for bricks if we wanted/will talk in class) *powerups??!?*
//I think keystroke is already imported could be wrong though
//(w800, h600) <-Jframes size limits



//Adding implements KeyListener will tell our main this is the section that ultilizes those inputs later in JFrame
public class Paddle implements KeyListener {
//This is one of two paddles and Paddle class may change to Paddle1 *4/13/26
  

private int x;
private int y;
private int paddlelength;
private int paddlewidth;
//Decleration of the int(s)

  
public Paddle() {
//[Consructor] Starting PaddlePoint and Size  
   int x = 400;
   int y = 500;
   int paddlelength = 20;
   int paddlewidth = 60;
}
  public void draw(Graphics g) {
    g.setColor(Color.WHITE);
    g.fillRect(x, y, paddlewidth, paddlelength);
  }
  // ^ the data for when game panel calls to my paddle (method is draw,utlity is the java* import)
  
  public void keyPressed(KeyEvent Input1)
  {
    int button = Input1.getKeyCode();
  //vk is for virtual key
  //add hold down button options
  if (button == KeyEvent.VK_A) { x = x -10; }
 //moves paddle1 to the left 
  if (button == KeyEvent.VK_D) { x = x +10; }
 //moves paddle1 to the right
  }
    
  public void keyReleased(KeyEvent Input1) {}
  public void keyTyped(KeyEvent Input1) {}
 //Thses two are required becasue of the Keylistner tool (its dumb but it wont run without them)
  
//[TODO] 
//Create a second paddle on the inverse so like -
//Ask if we want paddle to be able to turn (tilt on an axis)
//If Brick or Bricks wants to add effects that could invert paddles control code (ie: make it use VK_W or VK_S when (blankbrick) hit)
//Move ball collison to paddle folder  
//Need setters and getters 
//If keys calls goes to me then goes to him then back to me?

  
}
