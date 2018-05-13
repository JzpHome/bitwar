package Frontend.Statement;

// 前端
import Frontend.TokenScanner;
import Frontend.Statement.Expression.*;
// 错误异常
import MyExecption.*;

public class IfStmt extends BaseStmt {
	private ComplexExp 	 __condi;
	private StmtSequence __stmt_01;
	private StmtSequence __stmt_02;

	public IfStmt(TokenScanner scanner) throws FrontendExecption {
		__condi	= null;
		__stmt_01		= null;
		__stmt_02		= null;

		scan(scanner);
	}

	public IfStmt(IfStmt stmt) {
		__condi = new ComplexExp(stmt.__condi);
		__stmt_01 = new StmtSequence(stmt.__stmt_01);

		if(stmt.__stmt_02 != null) {
			__stmt_02 = new StmtSequence(stmt.__stmt_02);
		} else {
			__stmt_02 = null;
		}
	}

	public ComplexExp Condition() {
		return (new ComplexExp(__condi));
	}

	public StmtSequence Then() {
		return (new StmtSequence(__stmt_01));
	}

	public StmtSequence Else() {
		if(__stmt_02 != null) {
			return (new StmtSequence(__stmt_02));
		} else {
			return null;
		}
	}

	public String toString() {
		String result = "if ";

		result += __condi.toString();

		result += " then " + __stmt_01.toString();

		if(__stmt_02 != null) {
			result += " else " + __stmt_02.toString();
		}

		return (result + " end");
	}

	public void scan(TokenScanner scanner) throws FrontendExecption {
		String token = scanner.getToken();
		if(token.equals("if")) {
			scanner.match("if");
			__condi = new ComplexExp(scanner);

			token = scanner.getToken();
			if(token.equals("then")) {
				scanner.match("then");
				__stmt_01 = new StmtSequence(scanner);

				// 是否有else语句
				token = scanner.getToken();
				if(token.equals("else")) {
					scanner.match("else");
					__stmt_02 = new StmtSequence(scanner);
				}

				token = scanner.getToken();
				if(token.equals("end")) {
					scanner.match("end");
				} else {
					throw (new FrontendExecption("IfStmt: " + token + " is not 'end'"));
				}
			} else {
				throw (new FrontendExecption("IfStmt: " + token + " is not 'then'"));
			}
		} else {
			throw (new FrontendExecption("IfStmt: " + token + " is not 'if'"));
		}
	}

	public static void main(String[] args) {
		try {
			TokenScanner scanner = new TokenScanner("Test/Frontend/Statement/IfStmt.txt");

			String token = scanner.getToken();
			while(!token.equals("")) {
				IfStmt stmt = new IfStmt(scanner);
				token = scanner.getToken();

				System.out.println(stmt.toString());
				System.out.println("------------------------------------");
			}
		} catch (FrontendExecption re) {
			System.err.println(re.getMessage());
		}
	}
}
