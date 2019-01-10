
// Assignment 3 - ECSE202
// Karl Michel Koerich
// 260870321
// Fall 2018

package main;

public class gUtil 
{
	private static final int HEIGHT = 600;
	private static final double SCALE = HEIGHT/100; // Pixels/meter
	
	static int XtoSCreen(double X) {
		return (int) (X*SCALE);
	}
	
	static int YtoSCreen(double Y) {
		return (int) (HEIGHT - Y*SCALE);
	}
	
	static int LtoScreen(double length) {
		return (int) (length*SCALE);
	}

}
