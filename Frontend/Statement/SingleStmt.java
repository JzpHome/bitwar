package Frontend.Statement;

// 语法分析
import Frontend.TokenScanner;
import Frontend.Statement.Expression.*;
// 错误异常处理
import MyExecption.*;

public class SingleStmt extends BaseStmt {
	private String	 __type;
	private BaseStmt __stmt;

	public SingleStmt(TokenScanner scanner) throws FrontendExecption {
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
		} else if(__type.equals("RandomStmt")) {
			__stmt = new RandomStmt((RandomStmt)(stmt.__stmt));
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
		} else if(__type.equals("RandomStmt")) {
			return (new RandomStmt((RandomStmt)__stmt));
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
		} else if(__type.equals("RandomStmt")) {
			return ((RandomStmt)__stmt).toString();
		} else {
			return ((AssignStmt)__stmt).toString();
		}
	}

	public void scan(TokenScanner scanner) throws FrontendExecption {
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
		} else if(token.equals("RANDOM")) {
			__type = new String("RandomStmt");
			__stmt = new RandomStmt(scanner);
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
				System.out.println("------------------------------------");
			}
		} catch (FrontendExecption re) {
			System.err.println(re.getMessage());
		}
	}
}
