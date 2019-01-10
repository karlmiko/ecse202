
// Assignment 3 - ECSE202
// Karl Michel Koerich
// 260870321
// Fall 2018

package main;

// Class taken from class example and modified.
// (Original unused methods removed).

public class bTree {
		
	bNode root=null; // Default constructor
	
	// addNode method - wrapper for rNode
	public void addNode(gBall randomBall)
	{
		root=rNode(root, randomBall);
	}									

	// rNode method - recursively adds a new entry into the B-Tree
	private bNode rNode(bNode root, gBall randomBall) 
	{
		// Termination condition for recursion.  We have descended to a leaf
		// node of the tree (or the tree may initially be empty).  In either case,
		// create a new node --> copy over data, set successor nodes to null.
		
		if (root==null)
		{
			bNode node = new bNode();	 // Create a new node
			node.randomBall = randomBall;// Copy data
			node.left = null;			 // Successors to null.
			node.right = null;
			root=node;					 // Node created is root
			return root;				 // of new (sub) tree.
		}
		
		//Not at a leaf node, so traverse to the left if data being
		// added is strictly less than data at current node.
		
		else if (randomBall.bSize < root.randomBall.bSize) {
			root.left = rNode(root.left,randomBall);
		}
		
		// If greater than or equal, then traverse to the right.
		// Keep recursing until a node is found with no successors.
		else {
			root.right = rNode(root.right,randomBall);
		}
		return root;
	}
	
	// Nested class example
	
	public class bNode {
		gBall randomBall;
		bNode left;
		bNode right;
	}
	
	/*------------------------------------------------------------------*/
	
	// See if the balls' loops are still running.
	
	public int balls_running; // Number of balls still running
	public boolean isRunning() {
		balls_running = 0;
		t_inorder_running(root);
		if (balls_running != 0) return true; //return false only if 0 balls running
		else return false;
	}
	
	// Traverse Left-Root-Right and count how many loops are still running.
	private void t_inorder_running(bNode root) {
		if (root.left != null) t_inorder_running(root.left);
		if (root.randomBall.running == true) balls_running += 1;
		if (root.right != null) t_inorder_running(root.right);
	}
	
	/*------------------------------------------------------------------*/
	
	public double old_X; // Global to know position of next ball
	public void moveSort()
	{
		old_X = 0; // Initial position X is zero
		put_in_order(root);
	}
	
	// Traverse Left-Root-Right to put balls in size order.
	private void put_in_order(bNode root) 
	{
		if (root.left != null) put_in_order(root.left);
		root.randomBall.moveTo(old_X, 2*root.randomBall.bSize); // Moves ball to new position X 
		old_X = (root.randomBall.bSize)*2 + old_X; // Updates old_X
		if (root.right != null) put_in_order(root.right);
	}
}
	

