package Compiler;

import java.util.Arrays;
import java.util.HashSet;

public class Keywords {
    private static final HashSet<String> keyword
		= new HashSet<String>(Arrays.asList("stategy",
					"if", "then", "else", 
					"while", "do", "end", 
					"return", 
					"and", "or", 
					"RANDOM", 
					"CUR", "A", "B"));

	public static boolean match(String token) {
		return keyword.contains(token);
	}
}
