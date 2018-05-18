package Frontend.Statement;

import Error.BaseError;
import Error.Frontend.*;
import Frontend.TokenScanner;
import Frontend.Statement.Expression.IdExp;
import Frontend.Statement.Expression.ComplexExp;

public class AssignStmt extends BaseStmt {
	private IdExp		__id;
	private ComplexExp  __expr;

	public AssignStmt(TokenScanner scanner) throws BaseError {
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

	public void print(int deep) {
		__id.print(deep);

		super.print(deep);
		System.out.println(":=");

		__expr.print(deep);
	}

	public void scan(TokenScanner scanner) throws BaseError {
		__id	= new IdExp(scanner);

		String token = scanner.getToken();
		if(token.equals(":=")) {
			scanner.match(":=");

			__expr = new ComplexExp(scanner);
		} else {
			throw (new StmtError("AssignStmt", scanner.fpath(), scanner.lineno(), 
						scanner.linepos(), token + " is not ':='"));
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
				stmt.print(0);
				System.out.println("------------------------------------");
			}
		} catch (BaseError e) {
			System.err.println(e.getMessage());
		}
	}
}
