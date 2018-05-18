package Frontend.Statement;

import Error.BaseError;
import Frontend.TokenScanner;

public class StmtSequence extends BaseStmt {
	private SingleStmt	 __stmt;
	private StmtSequence __stmts;

	public StmtSequence(TokenScanner scanner) throws BaseError {
		__stmt	= null;
		__stmts = null;

		scan(scanner);
	}

	public StmtSequence(StmtSequence stmts) {
		__stmt = new SingleStmt(stmts.__stmt);
		if(stmts.__stmts != null) {
			__stmts = new StmtSequence(stmts.__stmts);
		} else {
			__stmts = null;
		}
	}

	public SingleStmt Stmt() {
		return (new SingleStmt(__stmt));
	}

	public StmtSequence Stmts() {
		if(__stmts != null) {
			return (new StmtSequence(__stmts));
		} else {
			return null;
		}
	}

	public String toString() {
		String result = "";

		result += __stmt.toString();

		if(__stmts != null) {
			result += "; " + __stmts.toString();
		}

		return result;
	}

	public void print(int deep) {
		__stmt.print(deep);

		if(__stmts != null) {
			super.print(deep);
			System.out.println(";");

			__stmts.print(deep);
		}
	}

	public void scan(TokenScanner scanner) throws BaseError {
		__stmt = new SingleStmt(scanner);

		String token = scanner.getToken();
		if(token.equals(";")) {
			scanner.match(";");
			__stmts = new StmtSequence(scanner);
		}
	}

	public static void main(String[] args) {
		try {
			TokenScanner scanner = new TokenScanner("Test/Frontend/Statement/StmtSequence.txt");

			String token = scanner.getToken();
			while(!token.equals("")) {
				StmtSequence stmt = new StmtSequence(scanner);
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
