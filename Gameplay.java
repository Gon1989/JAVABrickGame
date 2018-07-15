package brickArkanoid;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.lang.invoke.ConstantCallSite;
import javax.swing.Timer;
import javax.swing.JFrame;
import javax.swing.JPanel;


public class Gameplay extends JPanel implements KeyListener, ActionListener {

	//variables
	private boolean play = false;
	private int score = 0; 
	private int totalBricks = 21;
	private Timer timer;
	private int delay = 4;
	
	//player vars
	private int playerX = 300;
	private int ballPosX = 200;
	private int ballPosY = 200;
	private int ballDirectionX = -1;
	private int ballDirectionY = -2;
	
	//obj to access stage class
	private StageGenerator mapObj;
	
	
	public Gameplay() { //constructor
		
		addKeyListener(this); //to work with key listeners add <--
		setFocusable(true);
		setFocusTraversalKeysEnabled(false);
		
		mapObj = new StageGenerator(3, 7); //create new obj and pass row, col
		
		timer = new Timer(delay, this); //create a new time obj
		timer.start(); //start timer
		
	}
	
	//method to add graphics (to draw shapes)
	public void paint(Graphics g) {
		//background
		g.setColor(Color.BLACK);
		g.fillRect(1, 1, 692, 592);
		
		//draw stage
		mapObj.draw((Graphics2D)g);
		
		//borders
		g.setColor(Color.cyan);
		g.fillRect(0, 0, 3, 592);
		g.fillRect(0, 0, 692, 3);
		g.fillRect(691, 0, 3, 592);
		
		//scores
		g.setColor(Color.RED);
		g.setFont(new Font("serif", Font.BOLD, 25));
		g.drawString("" + score, 590, 30);
		
		//player
		g.setColor(Color.GREEN);
		g.fillRect(playerX, 550, 100, 16);
		
		//ball
		g.setColor(Color.yellow);
		g.fillOval(ballPosX, ballPosY, 20, 20);
	
		//check for GAME OVER when ball is going down
		if (ballPosY > 570) {
			gameOver(g);
		}
		
		//if there is no more breaks
		if (totalBricks < 0) {
			win(g);
		}
		
		g.dispose();
		
	}
	
	//Method to Get Input for different actions
	@Override
	public void actionPerformed(ActionEvent e) {
		timer.start(); //start timer
		
		//make ball move
		if (play) { //check if game is on
		
			//Collission
			//create rectangle mask on ball that is rectangle shape and check colliding.
			//if rectangle on ball collides with player mask
			if (new Rectangle(ballPosX,ballPosY,20,20).intersects(new Rectangle(playerX, 550, 100, 16))) {
				//System.out.println("Collission!!!!"); //debug only
				ballDirectionY = -ballDirectionY; // make it bounce up again
			}
			
			//collision ball with bricks
			A: for(int i = 0; i < mapObj.map.length; i++) {
				for(int j = 0; j < mapObj.map[0].length; j++) {
					if (mapObj.map[i][j] > 0) {
						//vars
						int brickX = j * mapObj.brickWidth + 80;
						int brickY = i * mapObj.brickHeight + 50;
						int brickWidth = mapObj.brickWidth;
						int brickHeight = mapObj.brickHeight;
					
						Rectangle rect = new Rectangle(brickX, brickY, brickWidth, brickHeight);
						
						Rectangle ballMask = new Rectangle(ballPosX, ballPosY, 20, 20); //mask collider for the ball
						
						Rectangle brickMask = rect; //mask collider for the brick
						
						//Collision
						if(ballMask.intersects(brickMask)) { //if the mask of the ball colliders with mask of bricks
							//actions 
							mapObj.setBrickHp(0, i, j); //set brick hp
							totalBricks -= 1; //subtract bricks
							score += 10; //add 10 points to the score
							
							// if we collide with one of these bricks
							if (ballPosX + 19 <= brickMask.x || ballPosX + 1 >= brickMask.x + brickMask.width) { 
								ballDirectionX = -ballDirectionX;
							} else {
								ballDirectionY = -ballDirectionY;
							}
							
						break A;
					}
				}
			}
		}
			
			
			
			
			ballPosX += ballDirectionX;
			ballPosY += ballDirectionY;
			
			//left border
			if (ballPosX < 0) {
				ballDirectionX = -ballDirectionX;
			}
			
			//top
			if (ballPosY < 0) {
				ballDirectionY = -ballDirectionY;
			}
			
			//right border
			if (ballPosX > 670) {
				ballDirectionX = -ballDirectionX;
			}
			
		}
		
		repaint(); //recall graphics method each step to painting every frame
	}

	//method to control player movement and INPUTS
	@Override
	public void keyPressed(KeyEvent e) {
		
		//left
		if (e.getKeyCode() == KeyEvent.VK_LEFT) {
			//check for the border on the left and stop it there
			if (playerX < 10) {
				playerX = 10;
			} else {
				moveLeft();
			}
		}
		
		//right
		if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
			//check for the border on the right and stop it there
			if (playerX >= 600) {
				playerX = 600;
			} else {
				moveRight();
			}
		}
		
		//Enter to continue game
		if (e.getKeyCode() == KeyEvent.VK_ENTER) {
			//check if is game over first
			if (!play) {
				play = true; //set game to play
				
				//reset ball position
				ballPosX = 120;
				ballPosY = 350;
				
				//reset ball direction      
				ballDirectionX = -1;
				ballDirectionY = -2;
				
				//reset player position
				playerX = 310;
				
				//reset score
				score = 0;
				
				totalBricks = 21;
				
				mapObj = new StageGenerator(3, 7);
				
				repaint();
			}
		}
		
	}

	//method to display game over
	public void gameOver(Graphics g) {
		play = false;
		ballDirectionX = 0;
		ballDirectionY = 0;
		
		//display game over message in red
		g.setColor(Color.RED);
		g.setFont(new Font("serif", Font.BOLD, 30));
		g.drawString("Game Over , Score: " , 190, 300);
		
		g.setFont(new Font("serif", Font.BOLD, 20));
		g.drawString("Press enter to restart... " , 230, 350);
		
	}
	
	//method to display win stage
	public void win(Graphics g) {
		play = false;
		ballDirectionX = 0;
		ballDirectionY = 0;
		
		//display game over message in red
		g.setColor(Color.RED);
		g.setFont(new Font("serif", Font.BOLD, 30));
		g.drawString("WINNEER!!! , Score: " , 190, 300);
		
		g.setFont(new Font("serif", Font.BOLD, 20));
		g.drawString("Press enter to restart... " , 230, 350);
		
		
	}
	
	//method to move right
	public void moveRight() {
		play = true;
		playerX += 30;
	}
	
	//method to move left
	public void moveLeft() {
		play = true;
		playerX -= 30;
	}
	
	
	@Override
	public void keyReleased(KeyEvent e) {}
	@Override
	public void keyTyped(KeyEvent e) {} 

	
	
	

	
}
