package brickArkanoid;

import javax.swing.JFrame;

public class Main {

	public static void main(String[] args) {
		
		//set up frame with features to display game
		JFrame frame = new JFrame();
		
		Gameplay gameObj = new Gameplay();
		frame.add(gameObj); //add this class obj to the frame panel of Main class.
		
		frame.setBounds(10,10,700,600);
		frame.setTitle("Arkanoidzz");
		frame.setResizable(false);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		
		
		
	}
	
	
	

}
