
// Assignment 3 - ECSE202
// Karl Michel Koerich
// 260870321
// Fall 2018

package main;

import java.awt.Color;
import acm.graphics.GLabel;
import acm.graphics.GRect;
import acm.program.GraphicsProgram;
import acm.util.RandomGenerator;

public class bSim extends GraphicsProgram
{
	public static void main(String[] args) 
	{
		new bSim().start(args);
	}
	
	private static final int WIDTH = 1200;
	private static final int HEIGHT = 600;
	private static final int OFFSET = 200;
	private static final int NUMBALLS = 15;
	private static final double MINSIZE = 1;
	private static final double MAXSIZE = 8;
	private static final double XMIN = 10;
	private static final double XMAX = 50;
	private static final double YMIN = 50;
	private static final double YMAX = 100;
	private static final double EMIN = 0.4;
	private static final double EMAX = 0.9;
	private static final double VMIN = 0.5;
	private static final double VMAX = 3.0;

	public void run()
	{
		this.resize(WIDTH, HEIGHT+OFFSET);
		
		// Set up line on the screen
	    GRect horizon = new GRect(0, HEIGHT, WIDTH, 3);
	    horizon.setColor(Color.BLACK);
	    horizon.setFilled(true);
	    add(horizon);
	    
	    //Random number generator
	    RandomGenerator rgen = new RandomGenerator();
	    
	    //Create B-Tree
	    bTree myTree = new bTree();
	    
	    for (int i=0; i < NUMBALLS; i++)
	    {
	    	// Creating Random values with boundaries
			double bLoss = rgen.nextDouble(EMIN,EMAX);
			double bSize = rgen.nextDouble(MINSIZE, MAXSIZE);
			double Xi = rgen.nextDouble(XMIN, XMAX);
			double Yi = rgen.nextDouble(YMIN, YMAX);
			double bVel = rgen.nextDouble(VMIN, VMAX);
			Color bColor = rgen.nextColor();
			
			// Creating Ball
			gBall randomBall = new gBall(Xi, Yi, bSize, bColor, bLoss, bVel);
			add(randomBall.myBall);
			myTree.addNode(randomBall);
			randomBall.start();
			
	    }
	    
	    while (myTree.isRunning());
	    // All balls stopped
	    
	    //Bonus part
	    GLabel label = new GLabel("Click mouse to continue!", 1000, 500);
	    label.setColor(Color.RED);
	    this.add(label); // Adds label to the screen
	    this.waitForClick(); // Waits for the user to click
	    myTree.moveSort();
	    label.setLabel("All sorted!");
	}
}
