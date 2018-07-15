package brickArkanoid;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;

public class StageGenerator {

	int map[][];
	int brickWidth;
	int brickHeight;
	
	
	//method to generate map
	public StageGenerator(int row, int col) {
		map = new int [row][col];
		
		for (int i = 0; i < map.length; i++) {
			for(int j = 0; j < map[0].length; j++) {
				map[i][j] = 1; //this one is to check that the brick is not collide with the ball
			}
		}
			
		brickWidth = 540/col; //set brick width
		brickHeight = 150/row; //set brick height
	}
	
	//method to draw all the bricks
	public void draw(Graphics2D g) {
		for(int i = 0; i < map.length; i++) {
			for (int j = 0; j < map[0].length; j++) {
				if (map[i][j] > 0) {//check if value is greater than 0, means the brick is alive
					g.setColor(Color.WHITE); //set brick color white and draw the rectangle
					g.fillRect(j * brickWidth + 80, i * brickHeight + 50 , brickWidth, brickHeight);
					
				}	
				
				g.setStroke(new BasicStroke(10)); //draw a border to separate bricks
				g.setColor(Color.black);
				g.drawRect(j * brickWidth + 80, i * brickHeight + 50 , brickWidth, brickHeight);
			}
		}
	}
	
	//method to control hp of bricks
	public void setBrickHp(int value, int row, int col) {
		map[row][col] = value;
		
	}
	
	
	
	
	
	
	
	
}






