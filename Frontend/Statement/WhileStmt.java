package Frontend.Statement;

import Error.BaseError;
import Error.Frontend.*;
import Frontend.TokenScanner;
import Frontend.Statement.Expression.ComplexExp;

public class WhileStmt extends BaseStmt {
	private ComplexExp	 __condi;
	private StmtSequence __stmts;

	public WhileStmt(TokenScanner scanner) throws BaseError {
		__condi = null;
		__stmts = null;

		scan(scanner);
	}

	public WhileStmt(WhileStmt stmt) {
		__condi = new ComplexExp(stmt.__condi);
		__stmts = new StmtSequence(stmt.__stmts);
	}

	public ComplexExp Condition() {
		return (new ComplexExp(__condi));
	}

	public StmtSequence Stmts() {
		return (new StmtSequence(__stmts));
	}

	public String toString() {
		String result = "while ";

		result += __condi.toString();
		result += " do ";
		result += __stmts.toString();

		return (result + " end");
	}

	public void print(int deep) {
		super.print(deep);
		System.out.println("while");

		__condi.print(deep+1);

		super.print(deep);
		System.out.println("do");

		__stmts.print(deep+1);

		super.print(deep);
		System.out.println("end");
	}

	public void scan(TokenScanner scanner) throws BaseError {
		String token = scanner.getToken();
		if(token.equals("while")) {
			scanner.match("while");
			__condi = new ComplexExp(scanner);

			token = scanner.getToken();
			if(token.equals("do")) {
				scanner.match("do");
				__stmts = new StmtSequence(scanner);

				token = scanner.getToken();
				if(token.equals("end")) {
					scanner.match("end");
				} else {
					throw (new StmtError("WhileStmt", scanner.fpath(), scanner.lineno(), 
								scanner.linepos(), "'" + token + "' is not 'end'"));
				}
			} else {
				throw (new StmtError("WhileStmt", scanner.fpath(), scanner.lineno(), 
							scanner.linepos(), "'" + token + "' is not 'do'"));
			}
		} else {
			throw (new StmtError("WhileStmt", scanner.fpath(), scanner.lineno(), 
						scanner.linepos(), "'" + token + "' is not 'while'"));
		}
	}

	public static void main(String[] args) {
		try {
			TokenScanner scanner = new TokenScanner("Test/Frontend/Statement/WhileStmt.txt");

			String token = scanner.getToken();
			while(!token.equals("")) {
				WhileStmt stmt = new WhileStmt(scanner);
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
