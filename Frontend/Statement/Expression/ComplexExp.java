package Frontend.Statement.Expression;

// 前端
import Frontend.TokenScanner;
// 错误异常
import MyExecption.*;

public class ComplexExp extends BaseExp {
	private LogicExp __expr_01;
	private String	 __option;
	private LogicExp __expr_02;

	public ComplexExp(TokenScanner scanner) throws FrontendExecption {
		__expr_01	= null;
		__option	= null;
		__expr_02	= null;

		scan(scanner);
	}

	public ComplexExp(ComplexExp expr) {
		__expr_01 = new LogicExp(expr.__expr_01);

		if(expr.__expr_02 != null) {
			__option = new String(expr.__option);
			__expr_02 = new LogicExp(expr.__expr_02);
		} else {
			__option = null;
			__expr_02 = null;
		}
	}

	public LogicExp Expr01() {
		if(__expr_01 != null) {
			return (new LogicExp(__expr_01));
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

	public LogicExp Expr02() {
		if(__expr_02 != null) {
			return (new LogicExp(__expr_02));
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
		__expr_01 = new LogicExp(scanner);

		String token = scanner.getToken();
		if(token.equals("and")) {
			scanner.match("and");

			__option = new String(token);
			__expr_02 = new LogicExp(scanner);
		} else if(token.equals("or")) {
			scanner.match("or");

			__option = new String(token);
			__expr_02 = new LogicExp(scanner);
		}
	}

	public static void main(String[] args) {
		try {
			TokenScanner scanner = new TokenScanner("Test/Frontend/Statement/Expression/ComplexExp.txt");

			String token = scanner.getToken();
			while(!token.equals("")) {
				ComplexExp expr = new ComplexExp(scanner);
				System.out.println("SimpleExp: " + expr.toString());

				token = scanner.getToken();
			}
		} catch (FrontendExecption re) {
			System.err.println(re.getMessage());
		}
	}
}
