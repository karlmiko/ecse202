
// Assignment 4 - ECSE202
// Karl Michel Koerich
// 260870321
// Fall 2018

package main;

import java.awt.Color;
import java.awt.Component;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import acm.graphics.GLabel;
import acm.graphics.GRect;
import acm.program.GraphicsProgram;
import acm.util.RandomGenerator;
import main.bTree.bNode;

public class bSim extends GraphicsProgram implements ChangeListener, ActionListener
{
	public static void main(String[] args) 
	{
		new bSim().start(args);
	}
	
	private static final int WIDTH = 1200; //Screen coordinates
	private static final int HEIGHT = 600;
	private static final int OFFSET = 200;
	private static final int MINBALLS = 1;  //Min and Max # of Balls to simulate
	private static final int MAXBALLS = 25;
	private static final int MINSIZE = 1; //Min and Max size of Balls
	private static final int MAXSIZE = 8;
	private static final int XMIN = 10; //Min and Max Xi
	private static final int XMAX = 50;
	private static final int YMIN = 50; //Min and Max Yi
	private static final int YMAX = 100;
	private static final int EMIN = 4; //Min and Max loss
	private static final int EMAX = 9;
	private static final int VMIN = 5; //Min and Max Vx
	private static final int VMAX = 30;
	private static final int FACTOR = 10; //To scale some values from the sliders
	private static final int RUN = 0; //Constants for JBomboBox index
	private static final int CLEAR = 1;
	private static final int SORT = 2;
	private static final int QUIT = 3;
	
	//Slider Boxes
	sliderBox numBallsSlider = new sliderBox("NUMBALLS:", MINBALLS, MINBALLS, MAXBALLS);
    sliderBox minSizeSlider = new sliderBox("MIN SIZE:", MINSIZE, MINSIZE, MAXSIZE);
    sliderBox maxSizeSlider = new sliderBox("MAX SIZE:", MINSIZE, MINSIZE, MAXSIZE);
    sliderBox xMinSlider = new sliderBox("X MIN:", XMIN, XMIN, XMAX);
    sliderBox xMaxSlider = new sliderBox("X MAX:", XMIN, XMIN, XMAX);
    sliderBox yMinSlider = new sliderBox("Y MIN:", YMIN, YMIN, YMAX);
    sliderBox yMaxSlider = new sliderBox("Y MAX:", YMIN, YMIN, YMAX);
    sliderBox lossMinSlider = new sliderBox("LOSS MIN:", EMIN, EMIN, EMAX);
    sliderBox lossMaxSlider = new sliderBox("LOSS MAX:", EMIN, EMIN, EMAX);
    sliderBox xVelMinSlider = new sliderBox("X VEL MIN:", VMIN, VMIN, VMAX);
    sliderBox xVelMaxSlider = new sliderBox("X VEL MAX:", VMIN, VMIN, VMAX);
    
    //Values from the Slider Boxes
    int numBallsVal = numBallsSlider.getISlider();
    int minSizeVal = minSizeSlider.getISlider();
    int maxSizeVal = maxSizeSlider.getISlider();
    int xMinVal = xMinSlider.getISlider();
    int xMaxVal = xMaxSlider.getISlider();
    int yMinVal = yMinSlider.getISlider();
    int yMaxVal = yMaxSlider.getISlider();
    int lossMinVal = lossMinSlider.getISlider();
    int lossMaxVal = lossMaxSlider.getISlider();
    int xVelMinVal = xVelMinSlider.getISlider();
    int xVelMaxVal = xVelMaxSlider.getISlider();
    
    //JComboBox with 4 options: "Run", "Clear", "Sort", "Quit"
    JComboBox<String> box;
    
    //Declaring myTree
    bTree myTree = null;
    
    
    //Sliders action
	public void stateChanged(ChangeEvent e) { 
		JSlider source = (JSlider)e.getSource();
		
		if (source == numBallsSlider.mySlider)
		{
			numBallsVal = numBallsSlider.getISlider();
			numBallsSlider.setISlider(numBallsVal);
		}
		else if (source == minSizeSlider.mySlider)
		{
			minSizeVal = minSizeSlider.getISlider();
			minSizeSlider.setISlider(minSizeVal);
		}
		else if (source == maxSizeSlider.mySlider)
		{
			maxSizeVal = maxSizeSlider.getISlider();
			maxSizeSlider.setISlider(maxSizeVal);
		}
		else if (source == xMinSlider.mySlider)
		{
			xMinVal = xMinSlider.getISlider();
			xMinSlider.setISlider(xMinVal);
		}
		else if (source == xMaxSlider.mySlider)
		{
			xMaxVal = xMaxSlider.getISlider();
			xMaxSlider.setISlider(xMaxVal);
		}
		else if (source == yMinSlider.mySlider)
		{
			yMinVal = yMinSlider.getISlider();
			yMinSlider.setISlider(yMinVal);
		}
		else if (source == yMaxSlider.mySlider)
		{
			yMaxVal = yMaxSlider.getISlider();
			yMaxSlider.setISlider(yMaxVal);
		}
		else if (source == lossMinSlider.mySlider)
		{
			lossMinVal = lossMinSlider.getISlider();
			lossMinSlider.setISlider(lossMinVal);
		}
		else if (source == lossMaxSlider.mySlider)
		{
			lossMaxVal = lossMaxSlider.getISlider();
			lossMaxSlider.setISlider(lossMaxVal);
		}
		else if (source == xVelMinSlider.mySlider)
		{
			xVelMinVal = xVelMinSlider.getISlider();
			xVelMinSlider.setISlider(xVelMinVal);
		}
		else if (source == xVelMaxSlider.mySlider)
		{
			xVelMaxVal = xVelMaxSlider.getISlider();
			xVelMaxSlider.setISlider(xVelMaxVal);
		}
	}
	
	//Button action
	public void actionPerformed(ActionEvent ae)
	{
		String action = ae.getActionCommand();
		if (action.equals("GO")) {
			
			int i_box = box.getSelectedIndex(); //Index of the option from box
			
			if (i_box == RUN || i_box == CLEAR)
			{
				// Clear if there is already a bTree
				if (myTree != null)
				{
					removeAll(myTree.root);
				}
				if (i_box == RUN) // Run new bTree
				{
					myTree = runBall();
					myTree.startBalls(myTree.root);
				}
			}
			else if (i_box == SORT) //Sorting the bTree and displaying
			{
				myTree.moveSort();
			}
			else if (i_box == QUIT) //Exiting program
			{
				this.exit();
			}	
		}
	}
	
	//Remove balls from the simulation -> left-root-right
	private void removeAll(bNode root) {
		if (root.left != null) removeAll(root.left);
		remove(root.randomBall.myBall);
		root.randomBall.interrupt(); // Interrupt individual gBall Thread
		if (root.right != null) removeAll(root.right);
	}
	
	//Creates a bTree with the parameters from the sliders
	public bTree runBall()
	{
		//Random number generator
	    RandomGenerator rgen = new RandomGenerator();
	    
	    //Creates new bTree
	    myTree = new bTree();
	    
	    //Creates number of desired nodes to myTree
	    for (int i=0; i < numBallsVal; i++)
	    {
			double bLoss = rgen.nextDouble(lossMinVal, Math.max(lossMinVal, lossMaxVal));
			double bSize = rgen.nextDouble(minSizeVal, Math.max(minSizeVal, maxSizeVal));
			double Xi = rgen.nextDouble(xMinVal, Math.max(xMinVal, xMaxVal));
			double Yi = rgen.nextDouble(yMinVal, Math.max(yMinVal, yMaxVal));
			double bVel = rgen.nextDouble(xVelMinVal, Math.max(xVelMinVal, xVelMaxVal));
			Color bColor = rgen.nextColor();
			
			// Creating gBall
			gBall randomBall = new gBall(Xi, Yi, bSize, bColor, (bLoss/FACTOR), (bVel/FACTOR));
			add(randomBall.myBall);
			myTree.addNode(randomBall);
	    }
	    
	    return myTree;
	}

	public void run()
	{	
		//JLabel
		JLabel top = new JLabel("Simulation Parameters");
		add(top, EAST);
		
	    //JPanel
		JPanel jp = new JPanel();
		jp.setLayout(new GridLayout(11,1));
	    jp.add(numBallsSlider.myPanel, EAST);
	    jp.add(minSizeSlider.myPanel, EAST);
	    jp.add(maxSizeSlider.myPanel, EAST);
	    jp.add(xMinSlider.myPanel, EAST);
	    jp.add(xMaxSlider.myPanel, EAST);
	    jp.add(yMinSlider.myPanel, EAST);
	    jp.add(yMaxSlider.myPanel, EAST);
	    jp.add(lossMinSlider.myPanel, EAST);
	    jp.add(lossMaxSlider.myPanel, EAST);
	    jp.add(xVelMinSlider.myPanel, EAST);
	    jp.add(xVelMaxSlider.myPanel, EAST);
	    add(jp, EAST);
	    
		// Set up line on the screen
	    GRect horizon = new GRect(0, HEIGHT, WIDTH, 3);
	    horizon.setColor(Color.BLACK);
	    horizon.setFilled(true);
	    add(horizon);
	    
	    //JLabel
	  	JLabel ins = new JLabel("Choose an option and click on GO");
	  	add(ins, EAST);
	    
	    // JComboBox
	    String[] options = new String[] {"Run", "Clear", "Sort", "Quit"};
	    box = new JComboBox<String>(options);
	    add(box, EAST);
	    
	    //JButton
	    JButton go = new JButton("GO");
	    go.setActionCommand("GO");
	    add(go, EAST);
	    go.addActionListener(this);
	    
	    //Add listeners to sliders
	    numBallsSlider.mySlider.addChangeListener((ChangeListener) this);
	    minSizeSlider.mySlider.addChangeListener((ChangeListener) this);
	    maxSizeSlider.mySlider.addChangeListener((ChangeListener) this);
	    xMinSlider.mySlider.addChangeListener((ChangeListener) this);
	    xMaxSlider.mySlider.addChangeListener((ChangeListener) this);
	    yMinSlider.mySlider.addChangeListener((ChangeListener) this);
	    yMaxSlider.mySlider.addChangeListener((ChangeListener) this);
	    lossMinSlider.mySlider.addChangeListener((ChangeListener) this);
	    lossMaxSlider.mySlider.addChangeListener((ChangeListener) this);
	    xVelMinSlider.mySlider.addChangeListener((ChangeListener) this);
	    xVelMaxSlider.mySlider.addChangeListener((ChangeListener) this);
	    
	    //Defines screen size
	    this.resize(WIDTH, HEIGHT+OFFSET);
	    
	}
}
