package Frontend.Statement.Expression;

// 前端
import Frontend.TokenScanner;
// 错误异常
import MyExecption.*;

public class TermExp extends BaseExp {
	private FactorExp __expr_01;
	private String	  __option;
	private FactorExp __expr_02;

	public TermExp(TokenScanner scanner) throws FrontendExecption {
		__expr_01	= null;
		__option	= null;
		__expr_02	= null;

		scan(scanner);
	}

	public TermExp(TermExp expr) {
		__expr_01 = new FactorExp(expr.__expr_01);
		__option  = new String(__option);
		__expr_02 = new FactorExp(expr.__expr_02);
	}

	public FactorExp Expr01() {
		if(__expr_01 != null) {
			return (new FactorExp(__expr_01));
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

	public FactorExp Expr02() {
		if(__expr_02 != null) {
			return (new FactorExp(__expr_02));
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
		__expr_01 = new FactorExp(scanner);

		String token = scanner.getToken();
		if(token.equals("*")) {
			scanner.match("*");

			__option = new String(token);
			__expr_02 = new FactorExp(scanner);
		} else if(token.equals("/")) {
			scanner.match("/");

			__option = new String(token);
			__expr_02 = new FactorExp(scanner);
		}
	}

	public static void main(String[] args) {
		try {
			TokenScanner scanner = new TokenScanner("Test/Frontend/Statement/Expression/TermExp.txt");

			String token = scanner.getToken();
			while(!token.equals("")) {
				TermExp expr = new TermExp(scanner);
				System.out.println("TermExp: " + expr.toString());

				token = scanner.getToken();
			}
		} catch (FrontendExecption re) {
			System.err.println(re.getMessage());
		}
	}
}
