package Statement;

// 语法分析
import Compiler.TokenScanner;
// 基本表达式
import Statement.Expression.*;
// 错误异常处理
import Error.ResolveError;

public class AssignStmt extends BaseStmt {
	private IdExp		identifier;
	private ComplexExp  expr;

	public AssignStmt(TokenScanner scanner) throws ResolveError {
		identifier	= new IdExp(scanner);
		expr		= null;

		String token = scanner.getToken();
		if(token.equals(":=")) {
			scanner.match(":=");

			expr = new ComplexExp(scanner);
		} else {
			throw (new ResolveError("AssignStmt: " + token + "is not ':='"));
		}
	}

	public void print(int deep) {
		identifier.print(deep);

		super.print(deep);
		System.out.println(":=");

		expr.print(deep);
	}

	public static void main(String[] args) {
		try {
			TokenScanner scanner = new TokenScanner("Test/Statement/AssignStmt.txt");

			String token = scanner.getToken();
			while(!token.equals("")) {
				AssignStmt stmt = new AssignStmt(scanner);
				stmt.print(0);

				token = scanner.getToken();
			}
		} catch (ResolveError re) {
			System.err.println(re.getMessage());
		}
	}
}
