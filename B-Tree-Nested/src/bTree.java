
/**
 * Implements a B-Tree class.
 * @author ferrie
 *
 */

public class bTree {
		
	bNode root=null;					// Default constructor
	
/**
 * addNode method - wrapper for rNode
 */	
	public void addNode(int data) {		// This is a wrapper to hide
		root=rNode(root,data);			// having to deal with root node.
	}									// Calls actual method below.
/**
 * rNode method - recursively adds a new entry into the B-Tree
 */	
	private bNode rNode(bNode root, int data) {
//
//  Termination condition for recursion.  We have descended to a leaf
//  node of the tree (or the tree may initially be empty).  In either case,
//	create a new node --> copy over data, set successor nodes to null.
//
		if (root==null) {
			bNode node = new bNode();	// Create a new node
			node.data = data;			// Copy data
			node.left = null;			// Successors to null.
			node.right = null;
			root=node;					// Node created is root
			return root;				// of new (sub) tree.
		}
//
//	Not at a leaf node, so traverse to the left if data being
//  added is strictly less than data at current node.
//
		else if (data < root.data) {
			root.left = rNode(root.left,data);
		}
//
//  If greater than or equal, then traverse to the right.  Keep
//  recursing until a node is found with no successors.
		else {
			root.right = rNode(root.right,data);
		}
		return root;
	}
	
/**
 * inorder method - inorder traversal via call to recursive method
 */
	
	public void inorder() {			// This is a wrapper for traverse_
		traverse_inorder(root);		// inorder to hide details of the
	}								// root node.
	
/**
 * traverse_inorder method - recursively traverses tree in order and prints each node.
 * Order: Descend following left successor links, returning back to the current
 *        root node (where a specific action takes place, e.g., printing data),
 *        and then repeat the descent along right successor links.
 */
	
	private void traverse_inorder(bNode root) {
		if (root.left != null) traverse_inorder(root.left);
		System.out.println(root.data);
		if (root.right != null) traverse_inorder(root.right);
	}
	
/**
 * preorder method - preorder traversal via call to recursive method
 * 
 */
	
	public void preorder() {		// As above, a wrapper for traverse_
		traverse_preorder(root);	// pre-order.
	}
	
/**
 * traverse_preorder method - recursively traverses tree in preorder and prints each node.
 * Order: Similar approach to traverse_inorder, except that the pattern is now
 *        do at root, then traverse left followed by traverse right.
 */

	public void traverse_preorder(bNode root) {
		System.out.println(root.data);
		if (root.left != null) traverse_preorder(root.left);
		if (root.right != null) traverse_preorder(root.right);
	}
	
/**
 * postorder method - postorder traversal via call to recursive method
 */
	
	public void postorder() {		// As above, a wrapper for traverse_
		traverse_postorder(root);	// post-order.
	}
	
/**
 * traverse_postorder method - recursively traverses tree in postorder and prints each node.
 * Order: Similar approach to traverse_inorder, except that the pattern is now
 *        traverse left, then traverse right, followed by do at root.
 
 */
	
	public void traverse_postorder(bNode root) {
		if (root.left != null) traverse_postorder(root.left);
		if (root.right != null) traverse_postorder(root.right);
		System.out.println(root.data);
	}
	
// Example of a nested class //
	
	public class bNode {
		int data;
		bNode left;
		bNode right;
	}
}

