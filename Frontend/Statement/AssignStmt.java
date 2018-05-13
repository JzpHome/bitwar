package Frontend.Statement;

// 前端
import Frontend.TokenScanner;
import Frontend.Statement.Expression.*;
// 错误异常
import MyExecption.*;

public class AssignStmt extends BaseStmt {
	private IdExp		__id;
	private ComplexExp  __expr;

	public AssignStmt(TokenScanner scanner) throws FrontendExecption {
		__id	= null;
		__expr	= null;

		scan(scanner);
	}

	public AssignStmt(AssignStmt stmt) {
		__id = new IdExp(stmt.__id);
		__expr = new ComplexExp(stmt.__expr);
	}

	public IdExp ID() {
		return (new IdExp(__id));
	}

	public ComplexExp Expr() {
		return (new ComplexExp(__expr));
	}

	public String toString() {
		String result = "";

		result += __id.toString();

		result += " := ";

		result += __expr.toString();

		return result;
	}

	public void scan(TokenScanner scanner) throws FrontendExecption {
		__id	= new IdExp(scanner);

		String token = scanner.getToken();
		if(token.equals(":=")) {
			scanner.match(":=");

			__expr = new ComplexExp(scanner);
		} else {
			throw (new FrontendExecption("AssignStmt: " + token + " is not ':='"));
		}
	}

	public static void main(String[] args) {
		try {
			TokenScanner scanner = new TokenScanner("Test/Frontend/Statement/AssignStmt.txt");

			String token = scanner.getToken();
			while(!token.equals("")) {
				AssignStmt stmt = new AssignStmt(scanner);
				token = scanner.getToken();

				System.out.println(stmt.toString());
				System.out.println("------------------------------------");
			}
		} catch (FrontendExecption re) {
			System.err.println(re.getMessage());
		}
	}
}
