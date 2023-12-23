package arbre_de_decision_et_compression;

import java.math.BigInteger;
import arbre_de_decision_et_compression.Fonctions_arbre_de_decision_et_compression;
import arbre_de_decision_et_compression.Fonctions_arbre_de_decision_et_compression.Node;

public class Tests_arbre_de_decision_et_compression {
	
	//static Fonctions_arbre_de_decision_et_compression Fonctions_arbre_de_decision_et_compression = new Fonctions_arbre_de_decision_et_compression();
	
    public static void treePrint(String prefix, Node n, boolean isLeft) 
    {
        if (n != null) 
        {
            treePrint(prefix + "                                    ", n.right, false);
            System.out.println (prefix + ("|-- ") + n.content);
            treePrint(prefix + "                                    ", n.left, true);
        }
    }
    
    public static void treePrintPointers(String prefix, Node n, boolean isLeft) 
    {
        if (n != null) 
        {
            treePrintPointers(prefix + "                                                                                                  ", n.right, false);
            System.out.println (prefix + ("|-- ") + n);
            treePrintPointers(prefix + "                                                                                                  ", n.left, true);
        }
    }
	
    //Test Question 2.6
	public static void testCons_arbre()
	{

		boolean[] table_de_verite = echauffement.Fonctions_echauffement.table(BigInteger.valueOf(38),8);
		Fonctions_arbre_de_decision_et_compression.BinaryDecisionTree BDT =  Fonctions_arbre_de_decision_et_compression.cons_arbre(table_de_verite);
	   	System.out.println("fonction [cons_arbre] --> \033[93mCONFIRMATION-VISUELLE\u001B[0m :");
	   	treePrint("", BDT.root, false);
	}
	
	//Test Question 2.7
	public static void testLuke()
	{
		boolean[] table_de_verite = echauffement.Fonctions_echauffement.table(BigInteger.valueOf(38),8);
		Fonctions_arbre_de_decision_et_compression.BinaryDecisionTree BDT =  Fonctions_arbre_de_decision_et_compression.cons_arbre(table_de_verite);
		Fonctions_arbre_de_decision_et_compression.luka(BDT);
		System.out.println("fonction [luka] --> \033[93mCONFIRMATION-VISUELLE\u001B[0m :");
	   	treePrint("", BDT.root, false);
	}
	
	//Test Question 2.8
	public static void testCompression()
	{
		boolean[] table_de_verite = echauffement.Fonctions_echauffement.table(BigInteger.valueOf(38),8);
		Fonctions_arbre_de_decision_et_compression.BinaryDecisionTree BDT =  Fonctions_arbre_de_decision_et_compression.cons_arbre(table_de_verite);
		Fonctions_arbre_de_decision_et_compression.luka(BDT);
		System.out.println("fonction [compression] --> \033[93mCONFIRMATION-VISUELLE\u001B[0m :");
		System.out.println("BEFORE");
		System.out.println(System.lineSeparator());
		treePrintPointers("", BDT.root, false);
		Fonctions_arbre_de_decision_et_compression.compression(BDT);
		System.out.println("AFTER");
		System.out.println(System.lineSeparator());
	   	treePrintPointers("", BDT.root, false);
	}
	
    //Test Question 2.9
    public static void testDot()
    {
		boolean[] table_de_verite = echauffement.Fonctions_echauffement.table(BigInteger.valueOf(38),8);
		Fonctions_arbre_de_decision_et_compression.BinaryDecisionTree BDT =  Fonctions_arbre_de_decision_et_compression.cons_arbre(table_de_verite);
		Fonctions_arbre_de_decision_et_compression.luka(BDT);
		Digraph graph1 = new Digraph("test_graph_non_compresse");
		graph1.addNode(""+BDT.root.toString().substring(81), /*"x"+ (arbre_de_decision_et_compression.Fonctions_arbre_de_decision_et_compression.getExposantOfPowBaseTwo(table_de_verite.length))+*/ BDT.root.content);
		Fonctions_arbre_de_decision_et_compression.dot(BDT.root, arbre_de_decision_et_compression.Fonctions_arbre_de_decision_et_compression.getExposantOfPowBaseTwo(table_de_verite.length), graph1);
		graph1.generate("graph_non_compresse.dot");
		graph1 = null;
		
		
		Fonctions_arbre_de_decision_et_compression.BinaryDecisionTree BDT2 =  Fonctions_arbre_de_decision_et_compression.cons_arbre(table_de_verite);
		Fonctions_arbre_de_decision_et_compression.luka(BDT2);
		Fonctions_arbre_de_decision_et_compression.compression(BDT2);
		Digraph graph2 = new Digraph("test_graph_compresse");
		graph2.addNode(""+BDT2.root.toString().substring(81), /*"x"+arbre_de_decision_et_compression.Fonctions_arbre_de_decision_et_compression.getExposantOfPowBaseTwo(table_de_verite.length)+*/BDT2.root.content);
		Fonctions_arbre_de_decision_et_compression.dot(BDT2.root, arbre_de_decision_et_compression.Fonctions_arbre_de_decision_et_compression.getExposantOfPowBaseTwo(table_de_verite.length), graph2);
		graph2.generate("graph_compresse.dot");   
    }
	
    public static void main(String[] args) 
    {	
    	testCons_arbre();
        System.out.println(System.lineSeparator()+"___________________________________________"+System.lineSeparator());
    	testLuke();
        System.out.println(System.lineSeparator()+"___________________________________________"+System.lineSeparator());
    	testCompression();
        System.out.println(System.lineSeparator()+"___________________________________________"+System.lineSeparator());
    	testDot();
    }
}