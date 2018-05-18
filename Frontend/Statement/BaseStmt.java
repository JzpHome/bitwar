package Frontend.Statement;

public class BaseStmt {
	public String toString() {
		return (new String("null"));
	}

	public void print(int deep) {
		for(int i = 0; i < deep; ++i) {
			System.out.print("    ");
		}
	}
}
