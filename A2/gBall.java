
// Assignment 2 - ECSE202
// Karl Michel Koerich
// 260870321
// Fall 2018

package main;

import java.awt.Color;
import acm.graphics.GOval;

public class gBall extends Thread 
{ 
	private static final int HEIGHT = 600;
	private static final double LOW_VT = 0.5; // Boundary for vt
	private static final double INTERVAL_TIME = .1; // Seconds
	private static final double G = 9.8;
	private static final double SCALE_FACTOR = 10; // 10 pixels = 1 meter 
	private Color bColor;
	private double bSize;
	private double Yi;
	private double Xi;
	private double bLoss;
	private double bVel;
	public GOval myBall;
	
	public gBall(double Xi, double Yi, double bSize, Color bColor, double bLoss, double bVel)
	{
		this.Xi = Xi;
		this.Yi = Yi;
		this.bSize = bSize;
		this.bColor = bColor;
		this.bLoss = bLoss;
		this.bVel = bVel;
		
		//Create instance of GOval with parameters
		myBall = new GOval(Xi, Yi, 2*bSize, 2*bSize); //radius times 2 = diameter (in pixels)
		myBall.setFilled(true);
		myBall.setFillColor(this.bColor);
	}
	
	public void run()
	{			
	    // Initialize variables    
	    boolean goingdown = true;
	    double totalt = 0;
	    double t = 0;
	    double h = Yi;
	    double initialUpPosition = 0;
	    double vt = Math.sqrt(2*G*Yi);
	    double elSqRt = Math.sqrt((1 - bLoss));
	    double Y = HEIGHT - Yi*SCALE_FACTOR;
	    double X = Xi;
	    
	    // When vt is lower than 0.5 the ball doesn't
	    // bounce significantly anymore
	    while (vt > LOW_VT)  
	    {
	    	if (goingdown)
			{
			    h = Yi-(0.5 * G * (t*t));
			    	
			    if (h <= 0) // Ground impact
			    {
			    	h = 0;
			    	Yi = h; // New h0
			    	initialUpPosition = h; // Keep track of the actual h
			    	goingdown = false;
			    	t = 0; // Resetting time for upward arc
			    	vt = vt * elSqRt; // New impact energy loss
			    }
			}
			else
			{
			    h = initialUpPosition + (vt*t) - (0.5*G*(t*t));
			    if (h > Yi)
			    {
			    	Yi = h;
			    }
			    else // Top of the arc
			    {
			    	goingdown = true;
			    	t = 0; // Resetting time for going down
			    }
			}
	    	
	    	Y = HEIGHT - h*SCALE_FACTOR - (2*bSize); // Y-motion
	    	X = Xi + (bVel*SCALE_FACTOR*totalt); // X-motion
	    	t += INTERVAL_TIME;
	    	totalt += INTERVAL_TIME;
	    	
	    	this.myBall.setLocation(X, Y);
	    	
	    	try {
	    		// pause for 50 milliseconds
	    		Thread.sleep(50);
	    		} catch (InterruptedException e) {
	    		e.printStackTrace(); }
	    }
	}	
}
