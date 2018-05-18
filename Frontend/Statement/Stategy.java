package Frontend.Statement;

import Error.BaseError;
import Error.Frontend.*;
import Frontend.TokenScanner;
import Frontend.Statement.Expression.*;

public class Stategy extends BaseStmt {
	private String		 __name;
	private StmtSequence __stmts;

	public Stategy(TokenScanner scanner) throws BaseError {
		__name	= null;
		__stmts = null;

		scan(scanner);
	}

	public StmtSequence Stmts() {
		return (new StmtSequence(__stmts));
	}

	public String toString() {
		String result = "Stategy ";

		result += __name + ": ";
		result += __stmts.toString();

		result += " end";

		return result;
	}

	public void print(int deep) {
		super.print(deep);
		System.out.println("Stategy " + __name + ": ");

		__stmts.print(deep+1);

		super.print(deep);
		System.out.println("end");
	}

	public void scan(TokenScanner scanner) throws BaseError {
		String token = scanner.getToken();
		if(token.equals("Stategy")) {
			scanner.match("Stategy");

			__name = new String(scanner.getToken());
			scanner.match(__name);

			token = scanner.getToken();
			if(token.equals(":")) {
				scanner.match(":");
				__stmts = new StmtSequence(scanner);

				token = scanner.getToken();
				if(token.equals("end")) {
					scanner.match("end");
				} else {
					throw (new StmtError("Stategy", scanner.fpath(), scanner.lineno(), 
								scanner.linepos(), "'" + token + "' is not 'end'"));
				}
			} else {
				throw (new StmtError("Stategy", scanner.fpath(), scanner.lineno(), 
							scanner.linepos(), "'" + token + "' is not ':'"));
			}
		} else {
			throw (new StmtError("Stategy", scanner.fpath(), scanner.lineno(), 
						scanner.linepos(), "'" + token + "' is not 'Stategy'"));
		}
	}

	public static void main(String[] args) {
		try {
			TokenScanner scanner = new TokenScanner("Test/Frontend/Statement/Stategy.txt");

			String token = scanner.getToken();
			while(!token.equals("")) {
				Stategy stategy = new Stategy(scanner);
				token = scanner.getToken();

				System.out.println(stategy.toString());
				stategy.print(0);
				System.out.println("------------------------------------");
			}
		} catch (BaseError e) {
			System.err.println(e.getMessage());
		}
	}
}
