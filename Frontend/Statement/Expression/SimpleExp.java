package Frontend.Statement.Expression;

// 前端
import Frontend.TokenScanner;
// 错误异常
import MyExecption.*;

public class SimpleExp extends BaseExp {
	private TermExp	__expr_01;
	private String	__option;
	private TermExp	__expr_02;

	public SimpleExp(TokenScanner scanner) throws FrontendExecption {
		__expr_01	= null;
		__option	= null;
		__expr_02	= null;

		scan(scanner);
	}

	public SimpleExp(SimpleExp expr) {
		__expr_01 = new TermExp(expr.__expr_01);

		if(expr.__expr_02 != null) {
			__option = new String(expr.__option);
			__expr_02 = new TermExp(expr.__expr_02);
		} else {
			__option = null;
			__expr_02 = null;
		}
	}

	public TermExp Expr01() {
		if(__expr_01 != null) {
			return (new TermExp(__expr_01));
		} else {
			return null;
		}
	}

	public String Option() {
		if(__option != null) {
			return (new String(__option));
		} else {
			return null;
		}
	}

	public TermExp Expr02() {
		if(__expr_02 != null) {
			return (new TermExp(__expr_02));
		} else {
			return null;
		}
	}

	public String toString() {
		if(__expr_02 != null) {
			return (__expr_01.toString() + " " + __option + " " + __expr_02.toString());
		} else {
			return __expr_01.toString();
		}
	}

	public void scan(TokenScanner scanner) throws FrontendExecption {
		__expr_01 = new TermExp(scanner);

		String token = scanner.getToken();
		if(token.equals("+")) {
			scanner.match("+");

			__option = new String(token);
			__expr_02 = new TermExp(scanner);
		} else if(token.equals("-")) {
			scanner.match("-");

			__option = new String(token);
			__expr_02 = new TermExp(scanner);
		}
	}

	public static void main(String[] args) {
		try {
			TokenScanner scanner = new TokenScanner("Test/Frontend/Statement/Expression/SimpleExp.txt");

			String token = scanner.getToken();
			while(!token.equals("")) {
				SimpleExp expr = new SimpleExp(scanner);
				System.out.println("SimpleExp: " + expr.toString());

				token = scanner.getToken();
			}
		} catch (FrontendExecption re) {
			System.err.println(re.getMessage());
		}
	}
}
