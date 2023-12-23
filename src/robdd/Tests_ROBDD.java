package robdd;

import arbre_de_decision_et_compression.Digraph;
import arbre_de_decision_et_compression.Fonctions_arbre_de_decision_et_compression;
import echauffement.Fonctions_echauffement;
import java.math.BigInteger;

public class Tests_ROBDD {

    public static void test_bdd() {
        boolean[] table_de_verite = Fonctions_echauffement.table(BigInteger.valueOf(38), 8);
        Fonctions_arbre_de_decision_et_compression.BinaryDecisionTree BDT = Fonctions_arbre_de_decision_et_compression.cons_arbre(table_de_verite);
        Fonctions_ROBDD.compression_bdd(BDT);
        Digraph graph1 = new Digraph("test_graph_bdd");
        graph1.addNode("" + BDT.getRoot().toString().substring(81), BDT.getRoot().getContent());
        Fonctions_arbre_de_decision_et_compression.dot(BDT.getRoot(), Fonctions_arbre_de_decision_et_compression.getExposantOfPowBaseTwo(table_de_verite.length), graph1);
        graph1.generate("graph_bdd.dot");
        graph1 = null;
    }

    public static void main(String[] args) {
        test_bdd();
    }
}
