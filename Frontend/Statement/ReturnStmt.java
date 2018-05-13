package Frontend.Statement;

// 前端
import Frontend.TokenScanner;
import Frontend.Statement.Expression.*;
// 错误异常
import MyExecption.*;

public class ReturnStmt extends BaseStmt {
	private ComplexExp __expr;

	public ReturnStmt(TokenScanner scanner) throws FrontendExecption {
		__expr = null;

		scan(scanner);
	}

	public ReturnStmt(ReturnStmt stmt) {
		__expr = new ComplexExp(stmt.__expr);
	}

	public ComplexExp Expr() {
		return (new ComplexExp(__expr));
	}

	public String toString() {
		String result = "return ";
		
		result += __expr.toString();

		return result;
	}

	public void scan(TokenScanner scanner) throws FrontendExecption {
		String token = scanner.getToken();
		if(token.equals("return")) {
			scanner.match("return");

			__expr = new ComplexExp(scanner);
		} else {
			throw (new FrontendExecption("ReturnStmt: " + token + " is not 'return'"));
		}
	}

	public static void main(String[] args) {
		try {
			TokenScanner scanner = new TokenScanner("Test/Frontend/Statement/ReturnStmt.txt");

			String token = scanner.getToken();
			while(!token.equals("")) {
				ReturnStmt stmt = new ReturnStmt(scanner);
				token = scanner.getToken();

				System.out.println(stmt.toString());
				System.out.println("------------------------------------");
			}
		} catch (FrontendExecption re) {
			System.err.println(re.getMessage());
		}
	}
}
