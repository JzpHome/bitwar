package Statement;

// 语法分析
import Compiler.TokenScanner;
// 基本表达式
import Statement.Expression.*;
// 错误异常处理
import Error.ResolveError;

public class StmtSequence extends BaseStmt {
	private SingleStmt	 single;
	private StmtSequence sequence;

	public StmtSequence(TokenScanner scanner) throws ResolveError {
		single = new SingleStmt(scanner);

		String token = scanner.getToken();
		if(token.equals(";")) {
			scanner.match(";");

			sequence = new StmtSequence(scanner);
		} else {
			sequence = null;
		}
	}

	public void print(int deep) {
		single.print(deep);

		if(sequence != null) {
			sequence.print(deep);
		}
	}

	public static void main(String[] args) {
		try {
			TokenScanner scanner = new TokenScanner("Test/Statement/StmtSequence.txt");

			String token = scanner.getToken();
			while(!token.equals("")) {
				StmtSequence stmt = new StmtSequence(scanner);
				stmt.print(0);

				token = scanner.getToken();
			}
		} catch (ResolveError re) {
			System.err.println(re.getMessage());
		}
	}
}
