package Statement;

// 语法分析
import Compiler.TokenScanner;
// 基本表达式
import Statement.Expression.*;
// 错误异常处理
import Error.ResolveError;

public class IfStmt extends BaseStmt {
	private ComplexExp 	 condition;
	private StmtSequence then_stmt;
	private StmtSequence else_stmt;

	public IfStmt(TokenScanner scanner) throws ResolveError {
		condition	= null;
		then_stmt	= null;
		else_stmt	= null;

		String token = scanner.getToken();
		if(token.equals("if")) {
			scanner.match("if");
			condition = new ComplexExp(scanner);

			token = scanner.getToken();
			if(token.equals("then")) {
				scanner.match("then");
				then_stmt = new StmtSequence(scanner);

				// 是否有else语句
				token = scanner.getToken();
				if(token.equals("else")) {
					scanner.match("else");
					else_stmt = new StmtSequence(scanner);
				}

				token = scanner.getToken();
				if(token.equals("end")) {
					scanner.match("end");
				} else {
					throw (new ResolveError("IfStmt: " + token + " is not 'end'"));
				}
			} else {
				throw (new ResolveError("IfStmt: " + token + " is not 'then'"));
			}
		} else {
			throw (new ResolveError("IfStmt: " + token + " is not 'if'"));
		}
	}

	public void print(int deep) {
		super.print(deep);
		System.out.println("if");

		condition.print(deep);

		super.print(deep);
		System.out.println("then");

		then_stmt.print(deep+1);

		if(else_stmt != null) {
			super.print(deep);
			System.out.println("else");

			else_stmt.print(deep+1);
		}

		super.print(deep);
		System.out.println("end");
	}

	public static void main(String[] args) {
		try {
			TokenScanner scanner = new TokenScanner("Test/Statement/IfStmt.txt");

			String token = scanner.getToken();
			while(!token.equals("")) {
				IfStmt stmt = new IfStmt(scanner);
				stmt.print(0);

				token = scanner.getToken();
			}
		} catch (ResolveError re) {
			System.err.println(re.getMessage());
		}
	}
}
