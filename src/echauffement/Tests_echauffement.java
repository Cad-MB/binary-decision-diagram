package echauffement;

import java.math.BigInteger;
import java.util.Arrays;

public class Tests_echauffement {
	
	static Fonctions_echauffement Fonctions_echauffement = new Fonctions_echauffement();
	
	//Test Question 1.2
	public static void testDecomposition()
	{
		boolean[] binary_flags_test = echauffement.Fonctions_echauffement.decomposition(BigInteger.valueOf(38));
		boolean[] binary_flags_expected = {false, true, true, false, false, true};
		if(Arrays.equals(binary_flags_test, binary_flags_expected))
			System.out.println("fonction [decomposition] --> \033[32mWORKING\u001B[0m");
		else
			System.out.println("fonction [decomposition] --> \033[31mNOT-WORKING");
	}
	
	//Test Question 1.3
	public static void testCompletion()
	{
		boolean[] t = {false, true, true, false, false, true};
		boolean[] completed_list_test1 = echauffement.Fonctions_echauffement.completion(t, 4);
		boolean[] completed_list_test2 = echauffement.Fonctions_echauffement.completion(t, 8);
		boolean[] completed_list_expected1 = {false, true, true, false};
		boolean[] completed_list_expected2 = {false, true, true, false, false, true, false, false};
		if((Arrays.equals(completed_list_test1, completed_list_expected1)) && (Arrays.equals(completed_list_test2, completed_list_expected2)))
			System.out.println("fonction [completion] --> \033[32mWORKING\u001B[0m");
		else
			System.out.println("fonction [completion] --> \033[31mNOT-WORKING\u001B[0m");
	}
	
	//Test Question 1.4
	public static void testTable()
	{
		boolean[] table_de_verite_test = echauffement.Fonctions_echauffement.table(BigInteger.valueOf(38),8);
		boolean[] table_de_verite_expected = {false, true, true, false, false, true, false, false};
		if((Arrays.equals(table_de_verite_test, table_de_verite_expected)))
			System.out.println("fonction [table] --> \033[32mWORKING\u001B[0m");
		else
			System.out.println("fonction [table] --> \033[31mNOT-WORKING\u001B[0m");
	}
	
    public static void main(String[] args) 
    {	
    	testDecomposition();
    	testCompletion();
    	testTable();
    }
	
}