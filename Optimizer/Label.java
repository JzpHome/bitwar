package Optimizer;

public class Label {
	private static int	count = 1;
	private int			__id;

	public Label() {
		__id = count++;
	}

	public String toString() {
		return (__id + "L");
	}
}
