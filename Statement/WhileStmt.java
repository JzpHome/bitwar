package Statement;

// 语法分析
import Compiler.TokenScanner;
// 基本表达式
import Statement.Expression.*;
// 错误异常处理
import Error.ResolveError;

public class WhileStmt extends BaseStmt {
	private ComplexExp	 condition;
	private StmtSequence sequence;

	public WhileStmt(TokenScanner scanner) throws ResolveError {
		String token = scanner.getToken();

		if(token.equals("while")) {
			scanner.match("while");
			condition = new ComplexExp(scanner);

			token = scanner.getToken();
			if(token.equals("do")) {
				scanner.match("do");
				sequence = new StmtSequence(scanner);

				token = scanner.getToken();
				if(token.equals("end")) {
					scanner.match("end");
				} else {
					throw (new ResolveError("WhileStmt: " + token + " is not 'end'"));
				}
			} else {
				throw (new ResolveError("WhileStmt: " + token + " is not 'do'"));
			}
		} else {
			throw (new ResolveError("WhileStmt: " + token + " is not 'while'"));
		}
	}

	public void print(int deep) {
		super.print(deep);
		System.out.println("while");

		condition.print(deep+1);

		super.print(deep);
		System.out.println("do");

		sequence.print(deep+1);

		super.print(deep);
		System.out.println("end");
	}

	public static void main(String[] args) {
		try {
			TokenScanner scanner = new TokenScanner("Test/Statement/WhileStmt.txt");

			String token = scanner.getToken();
			while(!token.equals("")) {
				WhileStmt stmt = new WhileStmt(scanner);
				stmt.print(0);

				token = scanner.getToken();
			}
		} catch (ResolveError re) {
			System.err.println(re.getMessage());
		}
	}
}
