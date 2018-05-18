package Frontend.Statement;

import Error.BaseError;
import Frontend.TokenScanner;

public class SingleStmt extends BaseStmt {
	private String	 __type;
	private BaseStmt __stmt;

	public SingleStmt(TokenScanner scanner) throws BaseError {
		__type = null;
		__stmt = null;

		scan(scanner);
	}

	public SingleStmt(SingleStmt stmt) {
		__type = new String(stmt.__type);
		if(__type.equals("IfStmt")) {
			__stmt = new IfStmt((IfStmt)(stmt.__stmt));
		} else if(__type.equals("WhileStmt")) {
			__stmt = new WhileStmt((WhileStmt)(stmt.__stmt));
		} else if(__type.equals("ReturnStmt")) {
			__stmt = new ReturnStmt((ReturnStmt)(stmt.__stmt));
		} else {
			__stmt = new AssignStmt((AssignStmt)(stmt.__stmt));
		}
	}

	public String Type() {
		return (new String(__type));
	}

	public BaseStmt Stmt() {
		if(__type.equals("IfStmt")) {
			return (new IfStmt((IfStmt)__stmt));
		} else if(__type.equals("WhileStmt")) {
			return (new WhileStmt((WhileStmt)__stmt));
		} else if(__type.equals("ReturnStmt")) {
			return (new ReturnStmt((ReturnStmt)__stmt));
		} else {
			return (new AssignStmt((AssignStmt)__stmt));
		}
	}

	public String toString() {
		if(__type.equals("IfStmt")) {
			return ((IfStmt)__stmt).toString();
		} else if(__type.equals("WhileStmt")) {
			return ((WhileStmt)__stmt).toString();
		} else if(__type.equals("ReturnStmt")) {
			return ((ReturnStmt)__stmt).toString();
		} else {
			return ((AssignStmt)__stmt).toString();
		}
	}

	public void print(int deep) {
		__stmt.print(deep);
	}

	public void scan(TokenScanner scanner) throws BaseError {
		String token = scanner.getToken();
		if(token.equals("if")) {
			__type = new String("IfStmt");
			__stmt = new IfStmt(scanner);
		} else if(token.equals("while")) {
			__type = new String("WhileStmt");
			__stmt = new WhileStmt(scanner);
		} else if(token.equals("return")) {
			__type = new String("ReturnStmt");
			__stmt = new ReturnStmt(scanner);
		} else {
			__type = new String("AssignStmt");
			__stmt = new AssignStmt(scanner);
		}
	}

	public static void main(String[] args) {
		try {
			TokenScanner scanner = new TokenScanner("Test/Frontend/Statement/SingleStmt.txt");

			String token = scanner.getToken();
			while(!token.equals("")) {
				SingleStmt stmt = new SingleStmt(scanner);
				token = scanner.getToken();

				System.out.println(stmt.toString());
				stmt.print(0);
				System.out.println("------------------------------------");
			}
		} catch (BaseError e) {
			System.err.println(e.getMessage());
		}
	}
}
