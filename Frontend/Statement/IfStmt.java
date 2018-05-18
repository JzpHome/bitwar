package Frontend.Statement;

import Error.BaseError;
import Error.Frontend.*;
import Frontend.TokenScanner;
import Frontend.Statement.Expression.ComplexExp;

public class IfStmt extends BaseStmt {
	private ComplexExp 	 __condi;
	private StmtSequence __stmt_01;
	private StmtSequence __stmt_02;

	public IfStmt(TokenScanner scanner) throws BaseError {
		__condi		= null;
		__stmt_01	= null;
		__stmt_02	= null;

		scan(scanner);
	}

	public IfStmt(IfStmt stmt) {
		__condi = new ComplexExp(stmt.__condi);
		__stmt_01 = new StmtSequence(stmt.__stmt_01);

		if(stmt.__stmt_02 != null) {
			__stmt_02 = new StmtSequence(stmt.__stmt_02);
		} else {
			__stmt_02 = null;
		}
	}

	public ComplexExp Condition() {
		return (new ComplexExp(__condi));
	}

	public StmtSequence Then() {
		return (new StmtSequence(__stmt_01));
	}

	public StmtSequence Else() {
		if(__stmt_02 != null) {
			return (new StmtSequence(__stmt_02));
		} else {
			return null;
		}
	}

	public String toString() {
		String result = "if ";

		result += __condi.toString();
		result += " then " + __stmt_01.toString();

		if(__stmt_02 != null) {
			result += " else " + __stmt_02.toString();
		}

		return (result + " end");
	}

	public void print(int deep) {
		super.print(deep);
		System.out.println("if");

		__condi.print(deep+1);

		super.print(deep);
		System.out.println("then");

		__stmt_01.print(deep+1);

		if(__stmt_02 != null) {
			super.print(deep);
			System.out.println("else");

			__stmt_02.print(deep+1);
		}

		super.print(deep);
		System.out.println("end");
	}

	public void scan(TokenScanner scanner) throws BaseError {
		String token = scanner.getToken();
		if(token.equals("if")) {
			scanner.match("if");
			__condi = new ComplexExp(scanner);

			token = scanner.getToken();
			if(token.equals("then")) {
				scanner.match("then");
				__stmt_01 = new StmtSequence(scanner);

				// 是否有else语句
				token = scanner.getToken();
				if(token.equals("else")) {
					scanner.match("else");
					__stmt_02 = new StmtSequence(scanner);
				}

				token = scanner.getToken();
				if(token.equals("end")) {
					scanner.match("end");
				} else {
					throw (new StmtError("IfStmt", scanner.fpath(), scanner.lineno(), 
								scanner.linepos(), "'" + token + "' is not 'end'"));
				}
			} else {
				throw (new StmtError("IfStmt", scanner.fpath(), scanner.lineno(), 
							scanner.linepos(), "'" + token + "' is not 'then'"));
			}
		} else {
			throw (new StmtError("IfStmt", scanner.fpath(), scanner.lineno(), 
						scanner.linepos(), "'" + token + "' is not 'if'"));
		}
	}

	public static void main(String[] args) {
		try {
			TokenScanner scanner = new TokenScanner("Test/Frontend/Statement/IfStmt.txt");

			String token = scanner.getToken();
			while(!token.equals("")) {
				IfStmt stmt = new IfStmt(scanner);
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
