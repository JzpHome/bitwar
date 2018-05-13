package Frontend.Statement;

// 前端
import Frontend.TokenScanner;
import Frontend.Statement.Expression.*;
// 错误异常
import MyExecption.*;

public class RandomStmt extends BaseStmt {
	private ComplexExp __expr;

	public RandomStmt(TokenScanner scanner) throws FrontendExecption {
		__expr = null;

		scan(scanner);
	}

	public RandomStmt(RandomStmt stmt) {
		__expr = new ComplexExp(stmt.__expr);
	}

	public ComplexExp Expr() {
		return (new ComplexExp(__expr));
	}

	public String toString() {
		String result = "RANDOM(";

		result += __expr.toString() + ")";

		return result;
	}

	public void scan(TokenScanner scanner) throws FrontendExecption {
		String token =	scanner.getToken();
		if(token.equals("RANDOM")) {
			scanner.match("RANDOM");

			token = scanner.getToken();
			if(token.equals("(")) {
				scanner.match("(");
				__expr = new ComplexExp(scanner);

				token = scanner.getToken();
				if(token.equals(")")) {
					scanner.match(")");
				} else {
					throw (new FrontendExecption("RandomExp: 缺少右括号')'"));
				}
			} else {
				throw (new FrontendExecption("RandomExp: 缺少左括号'('"));
			}
		} else {
			throw (new FrontendExecption("RandomExp: " + token + " is not 'RANDOM'"));
		}
	}

	public static void main(String[] args) {
		try {
			TokenScanner scanner = new TokenScanner("Test/Frontend/Statement/RandomStmt.txt");

			String token = scanner.getToken();
			while(!token.equals("")) {
				RandomStmt stmt = new RandomStmt(scanner);
				token = scanner.getToken();

				System.out.println(stmt.toString());
				System.out.println("------------------------------------");
			}
		} catch (FrontendExecption re) {
			System.err.println(re.getMessage());
		}
	}
}
