import java.util.Scanner;

/**
 * Simple demo for the B-Tree class
 * @author ferrie
 *
 */
public class demo {
	public static void main(String args[]) {
		bTree myTree = new bTree();
		Scanner sc = new Scanner(System.in);
		System.out.println("Enter a list of integers to sort (blank line to end):");
		while (true) {
			System.out.print("--> ");
			String resp = sc.nextLine();
			if (resp.equals("")) break;
			int x=Integer.parseInt(resp);
			myTree.addNode(x);
		}
		System.out.println("Preorder Traversal:");
		myTree.preorder();
		System.out.println("Inorder Traversal:");
		myTree.inorder();
		System.out.println("Postorder Traversal");
		myTree.postorder();
	}	
}