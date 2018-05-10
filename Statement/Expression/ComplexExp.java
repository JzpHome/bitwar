package Statement.Expression;

// 语法分析
import Compiler.TokenScanner;
// 错误异常
import Error.ResolveError;

public class ComplexExp extends BaseExp {
	private LogicExp	expr_01;
	private String		option;
	private ComplexExp	expr_02;

	public ComplexExp(TokenScanner scanner) throws ResolveError {
		expr_01 = new LogicExp(scanner);
		expr_02 = null;

		String token = scanner.getToken();
		if(token.equals("and")) {
			scanner.match("and");

			option = new String(token);
			expr_02 = new ComplexExp(scanner);
		} else if(token.equals("or")) {
			scanner.match("or");

			option = new String(token);
			expr_02 = new ComplexExp(scanner);
		}
	}

	public void print(int deep) {
		expr_01.print(deep);

		if(expr_02 != null) {
			super.print(deep);
			System.out.println(option);

			expr_02.print(deep);
		}
	}

	public static void main(String[] args) {
		try {
			TokenScanner scanner = new TokenScanner("Test/Statement/Expression/ComplexExp.txt");

			String token = scanner.getToken();
			while(!token.equals("")) {
				ComplexExp expr = new ComplexExp(scanner);
				expr.print(0);

				token = scanner.getToken();
			}
		} catch (ResolveError re) {
			System.err.println(re.getMessage());
		}
	}
}
