package robdd;

import arbre_de_decision_et_compression.Fonctions_arbre_de_decision_et_compression;

import java.util.ArrayList;
import java.util.List;

public class Fonctions_ROBDD {


    //Question 3.10

    static void terminal(Fonctions_arbre_de_decision_et_compression.Node node, List<Fonctions_arbre_de_decision_et_compression.Node> theList) {
        if (node != null) {
            Fonctions_arbre_de_decision_et_compression.Node unique_true = Fonctions_arbre_de_decision_et_compression.getPointersFromPointersList(theList, "true");
            Fonctions_arbre_de_decision_et_compression.Node unique_false = Fonctions_arbre_de_decision_et_compression.getPointersFromPointersList(theList, "false");
            if (node.getLeft().getContent().equals("true")) {
                node.setLeft(unique_true);
            } else if (node.getLeft().getContent().equals("false")) {
                node.setLeft(unique_false);
            } else {
                terminal(node.getLeft(), theList);
            }
            if (node.getRight().getContent().equals("true")) {
                node.setRight(unique_true);
            } else if (node.getRight().getContent().equals("false")) {
                node.setRight(unique_false);
            } else {
                terminal(node.getRight(), theList);
            }
        }
    }

    static void deletion_node(Fonctions_arbre_de_decision_et_compression.BinaryDecisionTree tree, Fonctions_arbre_de_decision_et_compression.Node node) {
        Fonctions_arbre_de_decision_et_compression.Node treeRoot = tree.getRoot();
        Fonctions_arbre_de_decision_et_compression.Node currNode = node;
        if (treeRoot.equals(currNode)) {
            if (treeRoot.getLeft() != null) {
                if (treeRoot.getLeft().equals(treeRoot.getRight())) {
                    currNode = treeRoot.getLeft();
                    tree.setRoot(treeRoot.getLeft());
                }
                //deletion_node(tree,currNode);
            }
        }
        if (currNode != null && currNode.getLeft() != null) {
            deletion_node(tree, currNode.getLeft());
            if (currNode.getLeft().getLeft() != null && currNode.getLeft().getLeft().equals(currNode.getLeft().getRight())) {
                currNode.setLeft(currNode.getLeft().getLeft());
            }
            deletion_node(tree, currNode.getRight());
            if (currNode.getRight().getLeft() != null && currNode.getRight().getLeft().equals(currNode.getRight().getRight())) {
                currNode.setRight(currNode.getRight().getLeft());
            }
        }
    }

    static void deletion(Fonctions_arbre_de_decision_et_compression.BinaryDecisionTree tree) {
        Fonctions_arbre_de_decision_et_compression.Node treeRoot = tree.getRoot();
        deletion_node(tree, treeRoot);
    }

    static void merging(Fonctions_arbre_de_decision_et_compression.Node node, List<Fonctions_arbre_de_decision_et_compression.Node> theList) {
        Fonctions_arbre_de_decision_et_compression.compressionOnNode(node.getLeft(), node, theList);
        Fonctions_arbre_de_decision_et_compression.compressionOnNode(node.getRight(), node, theList);
    }

    public static void compression_bdd(Fonctions_arbre_de_decision_et_compression.BinaryDecisionTree tree) {
        List<Fonctions_arbre_de_decision_et_compression.Node> theList = new ArrayList<>();
        List<Fonctions_arbre_de_decision_et_compression.Node> mergeList = new ArrayList<>();
        Fonctions_arbre_de_decision_et_compression.createListOfPointers(tree.getRoot(), theList);
        terminal(tree.getRoot(), theList);
        deletion(tree);
        Fonctions_arbre_de_decision_et_compression.lukaOnNode(tree.getRoot());
        Fonctions_arbre_de_decision_et_compression.createListOfPointers(tree.getRoot(), mergeList);
        merging(tree.getRoot(), mergeList);
        deletion(tree);
    }


}
