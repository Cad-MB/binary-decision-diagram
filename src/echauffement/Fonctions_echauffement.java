package echauffement;

import java.math.BigInteger;

public class Fonctions_echauffement
{
	//Question 1.2
	public static boolean[] decomposition(BigInteger n) 
	{
		int binary_length = n.toString(2).length();
		boolean[] binary_flags = new boolean[binary_length];
		for (int i = 0; i < binary_length; ++i)
		{
			if (n.toString(2).charAt(binary_length-i-1)=='0')
				binary_flags[i]=false;
			else
				binary_flags[i]=true;
		}
		return binary_flags;
	}
	
	//Question 1.3
	public static boolean[] completion(boolean[] list, int n) 
	{
		boolean[] completed_list = new boolean[n];
		if (n<list.length)
			for (int i = 0; i < n; ++i)
				completed_list[i] = list[i];
		else
			for (int i = 0; i < list.length; ++i)
				completed_list[i] = list[i];	
		return completed_list;
	}
	
	//Question 1.4
	public static boolean[] table(BigInteger x, int n) 
	{
		return completion(decomposition(x), n);
	}
}