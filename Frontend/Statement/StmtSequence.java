package Frontend.Statement;

// 语法分析
import Frontend.TokenScanner;
import Frontend.Statement.Expression.*;
// 错误异常处理
import MyExecption.*;

public class StmtSequence extends BaseStmt {
	private SingleStmt	 __stmt;
	private StmtSequence __stmts;

	public StmtSequence(TokenScanner scanner) throws FrontendExecption {
		__stmt	= null;
		__stmts = null;

		scan(scanner);
	}

	public StmtSequence(StmtSequence stmts) {
		__stmt = new SingleStmt(stmts.__stmt);
		__stmts = new StmtSequence(stmts.__stmts);
	}

	public SingleStmt Stmt() {
		return (new SingleStmt(__stmt));
	}

	public StmtSequence Stmts() {
		return (new StmtSequence(__stmts));
	}

	public String toString() {
		String result = "";

		result += __stmt.toString();

		if(__stmts != null) {
			result += "; " + __stmts.toString();
		}

		return result;
	}

	public void scan(TokenScanner scanner) throws FrontendExecption {
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
				System.out.println("------------------------------------");
			}
		} catch (FrontendExecption re) {
			System.err.println(re.getMessage());
		}
	}
}
