
// Assignment 1 - ECSE202
// Karl Michel Koerich
// 260870321
// Fall 2018

package main;

import java.awt.Color;
import acm.graphics.*;
import acm.program.*;

public class Bounce extends GraphicsProgram 
{
	private static final int WIDTH = 600; // Pixels
	private static final int HEIGHT = 800; // Pixels
    private static final double G = 9.8;  // m/s^2
    private static final double TIME_OUT = 30; // Seconds
    private static final double INTERVAL_TIME = .1; // Seconds
    private static final double Vx = 10; // 1m/s
	
	public void run()
	{
		this.resize(WIDTH, HEIGHT);
		
		// Read simulation parameters from user.
	    double h0 = readDouble ("Enter the height of the ball in meters [0,60]: ");
	    double l = readDouble ("Energy loss [0,1]: ");
	    
	    // Initialize variables    
	    boolean goingdown = true;
	    double totalt = 0;
	    double t = 0;
	    double h = h0;
	    double initialUpPosition = 0;
	    double vt = Math.sqrt(2*G*h0);
	    double elSqRt = Math.sqrt((1 - l));
	    double Y = 600 - h0*10;
	    double X = 0;
	    
	    // Set up images on the screen
	    GOval ball = new GOval(X, Y, 60, 60);
	    GLine horizon = new GLine (0, 660, 600, 660);
	    add(ball);
	    add(horizon);
	    ball.setFilled(true);
	    ball.setColor(Color.RED);
	    
	    while (totalt < TIME_OUT)
	    {
	    	if (goingdown)
			{
			    h = h0-(0.5 * G * (t*t));
			    	
			    if (h <= 0) // Ground impact
			    {
			    	h0 = h; // New h0
			    	initialUpPosition = h; // Keep track of the actual h
			    	goingdown = false;
			    	t = 0; // Resetting time for upward arc
			    	if (vt >= 0)
			    	{
			    		vt = vt * elSqRt; // New impact energy loss
			    	}
			    }
			}
			else
			{
			    h = initialUpPosition + (vt*t) - (0.5*G*(t*t));
			    if (h > h0)
			    {
			    	h0 = h;
			    }
			    else // Top of the arc
			    {
			    	goingdown = true;
			    	t = 0; // Resetting time for going down
			    }
			}
	    	
	    	Y = 600 - h*10; // Y-motion
	    	if (Y>600)
	    	{
	    		Y=600; // To avoid ball crossing the ground line
	    	}
	    	X = Vx*totalt; // X-motion
	    	System.out.println("Time: " + totalt + " X: "+ X + " Y: " + h + "vt: " + vt);
	    	t += INTERVAL_TIME;
	    	totalt += INTERVAL_TIME;
	    	
	    	// Dots to keep track of motion
	    	add(new GOval(X+30, Y+30, 1, 1));
	    	ball.setLocation(X, Y);
	    	
	    	pause(INTERVAL_TIME*1000); // Units are ms
	    }
	}
}