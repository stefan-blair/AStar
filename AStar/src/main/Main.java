package main;

import java.awt.BorderLayout;

import javax.swing.JFrame;

public class Main {
	
	public static void main(String[] args){
		JFrame frame = new JFrame("Pathfinding Algorithm");
		GUI gui = new GUI();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		frame.setResizable(false);
		frame.setLayout(new BorderLayout());
		frame.add(gui, BorderLayout.CENTER);
		frame.pack();
		frame.setLocationRelativeTo(null);
		
	}
	
}
