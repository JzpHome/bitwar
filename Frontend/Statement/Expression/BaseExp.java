package Frontend.Statement.Expression;

public class BaseExp {
	public String toString() {
		return (new String("null"));
	}

	public void print(int deep) {
		for(int i = 0; i < deep; ++i) {
			System.out.print("    ");
		}
	}
}
