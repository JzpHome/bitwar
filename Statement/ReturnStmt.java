package Statement;

// 语法分析
import Compiler.TokenScanner;
// 基本表达式
import Statement.Expression.*;
// 错误异常处理
import Error.ResolveError;

public class ReturnStmt extends BaseStmt {
	private ComplexExp expr;

	public ReturnStmt(TokenScanner scanner) throws ResolveError {
		String token = scanner.getToken();

		if(token.equals("return")) {
			scanner.match("return");

			expr = new ComplexExp(scanner);
		} else {
			throw (new ResolveError("ReturnStmt: " + token + "is not 'return'"));
		}
	}

	public void print(int deep) {
		expr.print(deep);
	}

	public static void main(String[] args) {
		try {
			TokenScanner scanner = new TokenScanner("Test/Statement/ReturnStmt.txt");

			String token = scanner.getToken();
			while(!token.equals("")) {
				ReturnStmt stmt = new ReturnStmt(scanner);
				stmt.print(0);

				token = scanner.getToken();
			}
		} catch (ResolveError re) {
			System.err.println(re.getMessage());
		}
	}
}
