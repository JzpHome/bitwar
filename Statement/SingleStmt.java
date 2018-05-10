package Statement;

// 语法分析
import Compiler.TokenScanner;
// 基本表达式
import Statement.Expression.*;
// 错误异常处理
import Error.ResolveError;

public class SingleStmt extends BaseStmt {
	private BaseStmt stmt;

	public SingleStmt(TokenScanner scanner) throws ResolveError {
		String token = scanner.getToken();
		if(token.equals("if")) {
			stmt = new IfStmt(scanner);
		} else if(token.equals("while")) {
			stmt = new WhileStmt(scanner);
		} else if(token.equals("return")) {
			stmt = new ReturnStmt(scanner);
		} else {
			stmt = new AssignStmt(scanner);
		}
	}

	public void print(int deep) {
		stmt.print(deep);
	}

	public static void main(String[] args) {
		try {
			TokenScanner scanner = new TokenScanner("Test/Statement/SingleStmt.txt");

			String token = scanner.getToken();
			while(!token.equals("")) {
				SingleStmt stmt = new SingleStmt(scanner);
				stmt.print(0);

				token = scanner.getToken();
			}
		} catch (ResolveError re) {
			System.err.println(re.getMessage());
		}
	}
}
