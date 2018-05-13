package Frontend.Statement;

// 前端
import Frontend.TokenScanner;
import Frontend.Statement.Expression.*;
// 错误异常
import MyExecption.*;

public class Stategy {
	private String		 __name;
	private StmtSequence __stmts;

	public Stategy(TokenScanner scanner) throws FrontendExecption {
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

	public void scan(TokenScanner scanner) throws FrontendExecption {
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
					throw (new FrontendExecption("Stategy: " + token + " is not 'end'"));
				}
			} else {
				throw (new FrontendExecption("Stategy: " + token + " is not ':'"));
			}
		} else {
			throw (new FrontendExecption("Stategy: " + token + " is not 'Stategy'"));
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
				System.out.println("------------------------------------");
			}
		} catch (FrontendExecption re) {
			System.err.println(re.getMessage());
		}
	}
}
