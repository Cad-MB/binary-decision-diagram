package arbre_de_decision_et_compression;

import java.lang.Math;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import arbre_de_decision_et_compression.Digraph.Node;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

public class Fonctions_arbre_de_decision_et_compression
{
	//Question 2.5
	public static class Node
	{
		String content;
	    Node left;
	    Node right;

	    public Node(String content)
	    {
	        this.content = content;
	        this.right = null;
	        this.left = null;
	    }

	    public String getContent()
	    {
	        return content;
	    }

	    public Node getLeft()
	    {
	    	return left;
	    }

	    public Node getRight()
	    {
	    	return right;
	    }

	    public void setContent(String content)
	    {
	        this.content = content;
	    }

	    public void setLeft(Node left)
	    {
	        this.left = left;
	    }

	    public void setRight(Node right)
	    {
	        this.right = right;
	    }
	}

	public static class BinaryDecisionTree
	{
	    Node root;

	    public BinaryDecisionTree(Node root)
	    {
	        this.root = root;
	    }

		public Node getRoot() {
			return root;
		}

		public void setRoot(Node root) {
			this.root = root;
		}
	}

	//Question 2.6
	public static int getExposantOfPowBaseTwo (int length)
	{
		int exposant = 0;
		while (Math.pow(2,exposant)<length)
			exposant++;
		return exposant;
	}

	static void insertIntoNode(Node tree, boolean[] table)
	{
		int h = getExposantOfPowBaseTwo(table.length);
		tree.setContent("x"+h);
		if(h!=0)
		{
		tree.setLeft(new Node(null));
		insertIntoNode(tree.left, Arrays.copyOfRange(table, 0, table.length/2));
		tree.setRight(new Node(null));
		insertIntoNode(tree.right, Arrays.copyOfRange(table, table.length/2, table.length));
		}
		else tree.setContent(Boolean.toString(table[0]));
	}

	public static BinaryDecisionTree cons_arbre(boolean[] table_de_verite)
	{
		Node root = new Node(null);
		BinaryDecisionTree BDT = new BinaryDecisionTree(root);
		insertIntoNode(BDT.root , table_de_verite);
		return BDT;
	}

	//Question 2.7
	public static void lukaOnNode(Node root)
	{
		if (root.left != null)
		{
			lukaOnNode(root.left);
			lukaOnNode(root.right);
			root.setContent(root.getContent()+"("+root.left.getContent()+")("+root.right.getContent()+")");
		}
	}

	public static void luka(BinaryDecisionTree tree)
	{
		lukaOnNode(tree.root);
	}

	//Test Question 2.8
	public static Node getPointersFromPointersList (List<Node> theList, String content)
	{
		for (int i=0; i<theList.size(); i++)
			if (theList.get(i).getContent().equals(content)) return theList.get(i);
		return null;
	}

	public static boolean existInPointersList(List<Node> theList, Node root)
	{
		boolean result = false;
		for (int i=0; i<theList.size(); i++) {
			if (theList.get(i).getContent().equals(root.getContent())) {result = true;}}
		return result;
	}

	public static void createListOfPointers(Node root, List<Node> theList)
	{
		if (root != null)
        {
			createListOfPointers(root.left, theList);
            if (!existInPointersList(theList, root))
            	theList.add(root);
            createListOfPointers(root.right, theList);
            if (!existInPointersList(theList, root))
            	theList.add(root);
        }
	}

	public static void compressionOnNode (Node node ,Node nodeParent, List<Node> theList)
	{
	    if (node != null)
	    {
	    	compressionOnNode(node.left, node, theList); //invert order to gain time?
	    	if (existInPointersList(theList, nodeParent.left)) //always true?
	    		nodeParent.setLeft(getPointersFromPointersList(theList, nodeParent.left.content));
        // if we do a redirect, we know the subtrees are equal: no need to re-analyze?
	    	compressionOnNode(node.right, node, theList);
	    	if (existInPointersList(theList, nodeParent.right)) //always true?
	    		nodeParent.setRight(getPointersFromPointersList(theList, nodeParent.right.content));
	    }
	}

	static void compression(BinaryDecisionTree tree)
	{
		List<Node> theList = new ArrayList<>();
		createListOfPointers(tree.root, theList);
		compressionOnNode(tree.root.left,tree.root, theList);
		compressionOnNode(tree.root.right,tree.root, theList);

	}

	//Test Question 2.9
	public static void dot(Node tree, int h, arbre_de_decision_et_compression.Digraph graph)
	{
        if(tree.left !=null) {
        if (h != 1) {
            if (!graph.exists(tree.left.toString().substring(81)))
                graph.addNode("" + tree.left.toString().substring(81), /*("x"+(h-1)) +*/ tree.left.content);
            graph.link("" + tree.toString().substring(81), "" + tree.left.toString().substring(81), true);
            if (!graph.exists(tree.right.toString().substring(81)))
                graph.addNode("" + tree.right.toString().substring(81), /*("x"+(h-1)) +*/ tree.right.content);
            graph.link("" + tree.toString().substring(81), "" + tree.right.toString().substring(81), false);
            dot(tree.left, h - 1, graph);
            dot(tree.right, h - 1, graph);
        } else {
            if (!graph.exists(tree.left.toString().substring(81)))
                graph.addNode("" + tree.left.toString().substring(81), tree.left.content);
            graph.link("" + tree.toString().substring(81), "" + tree.left.toString().substring(81), true);
            if (!graph.exists(tree.right.toString().substring(81)))
                graph.addNode("" + tree.right.toString().substring(81), tree.right.content);
            graph.link("" + tree.toString().substring(81), "" + tree.right.toString().substring(81), false);
        }
    }
	}
}