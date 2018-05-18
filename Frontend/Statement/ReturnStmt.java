package Frontend.Statement;

import Error.BaseError;
import Error.Frontend.*;
import Frontend.TokenScanner;
import Frontend.Statement.Expression.ComplexExp;

public class ReturnStmt extends BaseStmt {
	private ComplexExp __expr;

	public ReturnStmt(TokenScanner scanner) throws BaseError {
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

	public void print(int deep) {
		super.print(deep);
		System.out.println("return");

		__expr.print(deep+1);
	}

	public void scan(TokenScanner scanner) throws BaseError {
		String token = scanner.getToken();
		if(token.equals("return")) {
			scanner.match("return");

			__expr = new ComplexExp(scanner);
		} else {
			throw (new StmtError("ReturnStmt", scanner.fpath(), scanner.lineno(), 
						scanner.linepos(), "'" + token + "' is not 'return'"));
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
				stmt.print(0);
				System.out.println("------------------------------------");
			}
		} catch (BaseError e) {
			System.err.println(e.getMessage());
		}
	}
}
