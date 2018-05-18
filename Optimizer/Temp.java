package Optimizer;

public class Temp {
	private static int	count = 1;
	private int			__id;

	public Temp(SymbolTable symbol) {
		__id = count++;
		symbol.addValue(toString());
	}

	public String toString() {
		return (__id + "T");
	}
}
