
// Assignment 3 - ECSE202
// Karl Michel Koerich
// 260870321
// Fall 2018

package main;

import java.awt.Color;
import acm.graphics.GOval;

public class gBall extends Thread 
{ 
	private static final double INTERVAL_TIME = .1; // Seconds
	private static final double G = 9.8; 
	private Color bColor;
	public double bSize;
	private double Yi;
	private double Xi;
	private double bLoss;
	private double bVel;
	public GOval myBall;
	public boolean running;
	
	public gBall(double Xi, double Yi, double bSize, Color bColor, double bLoss, double bVel)
	{
		this.Xi = Xi;
		this.Yi = Yi;
		this.bSize = bSize;
		this.bColor = bColor;
		this.bLoss = bLoss;
		this.bVel = bVel;
		this.running = true;
		
		myBall = new GOval(gUtil.XtoSCreen(Xi-bSize), gUtil.YtoSCreen(Yi+bSize),
				gUtil.LtoScreen(2*bSize), gUtil.LtoScreen(2*bSize));
		
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
	    double last_stop = Yi;
	    double vt = Math.sqrt(2*G*Yi);
	    double elSqRt = Math.sqrt((1.0 - bLoss));
	    double Y;
    	double X;
	    
	    while (true)  
	    {
	    	if (goingdown)
			{
			    h = last_stop-(0.5 * G * (t*t));
			    	
			    if (h <= bSize) // Ground impact
			    {
			    	goingdown = false;
			    	last_stop = bSize;
			    	t = 0; // Resetting time for upward arc
			    	vt = vt * elSqRt; // New impact energy loss
			    }
			}
			else
			{
			    h = bSize + (vt*t) - (0.5*G*(t*t));
			    if (h < last_stop)
			    {
			    	if (h <= bSize) break;
			    	goingdown = true;
			    	t = 0; // Resetting time for going down
			    }
			    last_stop = h;
			}
	    	
	    	Y = Math.max(bSize, h); // To stay above ground
	    	X = Xi + (bVel*totalt);
	    	moveTo(X-bSize, Y+bSize);
	    	
	    	try {
	    		// pause for 50 milliseconds
	    		Thread.sleep(50);
	    	} catch (InterruptedException e) {
	    		e.printStackTrace();
	    	}
	    	t += INTERVAL_TIME;
	    	totalt += INTERVAL_TIME;
	    }
	    
	    this.running = false;
	}
	
	public void moveTo(double X, double Y)
	{
		this.myBall.setLocation(gUtil.XtoSCreen(X), gUtil.YtoSCreen(Y));
	}
}
