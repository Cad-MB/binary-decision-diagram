package arbre_de_decision_et_compression;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

public class Digraph {
    private static String graphname;
    private static ArrayList<Node> nodes = new ArrayList<>();

    public Digraph(String graphname) {
        this.graphname = graphname;
        this.nodes = new ArrayList<>();
    }

    public Digraph addNode(String nodeID) {
        if (exists(nodeID)) {
            System.out.println("Error: Node " + nodeID + " already exists.");
            System.exit(0);
        }
        Node n = new Node(nodeID);
        n.graph = this;
        nodes.add(n);
        return this;
    }

    public static boolean exists(String nodeID) {
        for (Node n : nodes) {
            if (n.nodeID.equals(nodeID)) {
                return true;
            }
        }
        return false;
    }

    public Node getNode(String nodeID) {
        for (Node n : nodes) {
            if (n.nodeID.equals(nodeID)) {
                return n;
            }
        }
        return null;
    }

    public Node addNode(String nodeID, String nodeName) {
        if (exists(nodeID)) {
            System.out.println("Error: Node " + nodeID + " already exists.");
            System.exit(0);
        }
        Node n = new Node(nodeID, nodeName);
        n.graph = this;
        nodes.add(n);
        return n;
    }

    public MyPair link(String parentNodeID, String childNodeID, boolean isLeft) {
        Node parent = getNode(parentNodeID);
        if (parent == null) {
            System.out.print("JavaGraph: Node " + parentNodeID + " does not exist.");
            System.exit(0);
        }
        Node child = getNode(childNodeID);
        if (child == null) {
            System.out.print("JavaGraph: Node " + childNodeID + " does not exist.");
            System.exit(0);
        }
        MyPair pair= new MyPair(child, isLeft);
        parent.addChild(pair);
        return pair;
    }



    public static void generate(String filename) {
        try {
        	List<String> linkedR = new ArrayList<>();
        	List<String> linkedL = new ArrayList<>();
            PrintWriter writer = new PrintWriter(filename, "UTF-8");
            writer.println("digraph " + graphname + " {");
            for (Node n : nodes) {
                if (n.hasName())
                    writer.println("ID" + n.nodeID + " [label=\"" + n.nodeName + "\"];");
                else
                    writer.println("ID" + n.nodeID + " [label=\"" + n.nodeID + "\"];");
            }
            for (Node n : nodes) {
                if (n.children.size() > 0) {
                    for (MyPair c : n.children) {
                    	if (c.isLeft) {
                    		if (!linkedL.contains(n.nodeID+c.node.nodeID)) {
                                if (c.node.hasLabel())
                                {
                                    writer.println("ID" + n.nodeID + " -> ID" + c.node.nodeID + " [label=\"" + c.node.linkLabel + "\"],[style=dashed];");
                                    linkedL.add(n.nodeID+c.node.nodeID);}
                                else {
                                    writer.println("ID" + n.nodeID + " -> ID" + c.node.nodeID + "[style=dashed];");
                                    linkedL.add(n.nodeID+c.node.nodeID);}

                    		}
                    	}
                    	else {
                    		if (!linkedR.contains(n.nodeID+c.node.nodeID)) {
                                if (c.node.hasLabel()) {
                                    writer.println("ID" + n.nodeID + " -> ID" + c.node.nodeID + " [label=\"" + c.node.linkLabel + "\"];");
                                    linkedR.add(n.nodeID+c.node.nodeID);}
                                    else {
                                    writer.println("ID" + n.nodeID + " -> ID" + c.node.nodeID + ";");
                                    linkedR.add(n.nodeID+c.node.nodeID);}

                    		}
                    	}

                    }
                }
            }
            writer.println("}");
            writer.close();
            System.out.println("Tree generated");
            writer = null;
        } catch (FileNotFoundException e) {
            System.out.println("JavaGraph: " + filename + " could not be written to.");
        } catch (UnsupportedEncodingException e) {
            System.out.print("JavaGraph: " + e.getMessage());
        }
    }

    public int getNodeCount() {
        return nodes.size();
    }
    
    class Node {
        private String nodeID;
        private String nodeName;
        private String linkLabel;
        private ArrayList<MyPair> children = new ArrayList<>();
        private Digraph graph;

        public Node(String nodeID) {
            this.nodeID = nodeID;
        }

        public void setLabel(String label) {
            this.linkLabel = label;
        }

        public Node(String nodeID, String nodeName) {
            this.nodeID = nodeID;
            this.nodeName = nodeName;
        }

        public Node setName(String nodeName) {
            this.nodeName = nodeName;
            return this;
        }
        
        public Node setIsLeft(boolean isLeft) {
            return this;
        }

        public MyPair addChild(MyPair newChild) {
            if (newChild == null) {
                return null;
            }
            this.children.add(newChild);
            return newChild;
        }

        public MyPair addChild(MyPair newChild, String linkLabel) {
            if (newChild == null) {
                return null;
            }
            newChild.node.linkLabel = linkLabel;
            this.children.add(newChild);
            return newChild;
        }

        public boolean hasName() {
            return (nodeName != null);
        }

        public boolean hasLabel() {
            return (linkLabel != null);
        }
    }
    
    class MyPair
    {
        private final Node node;
        private final boolean isLeft;

        public MyPair(Node node, boolean isLeft)
        {
            this.node   = node;
            this.isLeft = isLeft;
        }
    }
}
