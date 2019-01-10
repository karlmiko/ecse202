
// Assignment 2 - ECSE202
// Karl Michel Koerich
// 260870321
// Fall 2018

package main;

import java.awt.Color;

import acm.graphics.GLine;
import acm.program.GraphicsProgram;
import acm.util.RandomGenerator;

public class bSim extends GraphicsProgram
{
	private static final int WIDTH = 1200; // Screen coords
	private static final int HEIGHT = 600;
	private static final int OFFSET = 200; 
	private static final int NUMBALLS = 100; // # balls to sim
	private static final double MINSIZE = 3; // Min ball size PIXELS
	private static final double MAXSIZE = 20; // Max ball size PIXELS
	private static final double XMIN = 10; // Min X start loc METERS
	private static final double XMAX = 50;  // Max X start loc METERS
	private static final double YMIN = 50; // Min Y start loc METERS
	private static final double YMAX = 100; // Max Y start loc METERS
	private static final double EMIN = 0.1; // Min loss coeff
	private static final double EMAX = 0.3; // Max loss coeff
	private static final double VMIN = 0.5; // Min X velocity METERS/SEC
	private static final double VMAX = 3.0; // Max X velocity METERS/SEC
	
	public void run()
	{
		this.resize(WIDTH, HEIGHT+OFFSET);
		
		// Set up line on the screen
	    GLine horizon = new GLine (0, HEIGHT, WIDTH, HEIGHT);
	    add(horizon);
	    
	    for (int i=0; i < NUMBALLS; i++)
	    {
	    	// Creating Random values with boundaries
	    	RandomGenerator rgen = new RandomGenerator();
			double bLoss = rgen.nextDouble(EMIN,EMAX);
			double bSize = rgen.nextDouble(MINSIZE, MAXSIZE);
			double Xi = rgen.nextDouble(XMIN, XMAX);
			double Yi = rgen.nextDouble(YMIN, YMAX);
			double bVel = rgen.nextDouble(VMIN, VMAX);
			Color bColor = rgen.nextColor();
			
			// Creating Ball
			gBall randomBall = new gBall(Xi, Yi, bSize, bColor, bLoss, bVel);
			add(randomBall.myBall);
			randomBall.start();
			
	    }
	}
}
