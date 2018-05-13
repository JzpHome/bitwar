package Optimizer;

public class Temp {
	private static int	count = 1;
	private int			__id;

	public Temp() {
		__id = count++;
	}

	public String toString() {
		return (__id + "t");
	}
}
