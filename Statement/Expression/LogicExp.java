package Statement.Expression;

// 语法分析
import Compiler.TokenScanner;
// 错误异常
import Error.ResolveError;

public class LogicExp extends BaseExp {
	private SimpleExp expr_01;
	private String	  option;
	private SimpleExp expr_02;

	public LogicExp(TokenScanner scanner) throws ResolveError {
		expr_01 = new SimpleExp(scanner);
		expr_02 = null;

		String token = scanner.getToken();
		if(token.equals(">")) {
			scanner.match(">");

			option = new String(token);
			expr_02 = new SimpleExp(scanner);
		} else if(token.equals("<")) {
			scanner.match("<");

			option = new String(token);
			expr_02 = new SimpleExp(scanner);
		} else if(token.equals("=")) {
			scanner.match("=");

			option = new String(token);
			expr_02 = new SimpleExp(scanner);
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
			TokenScanner scanner = new TokenScanner("Test/Statement/Expression/LogicExp.txt");

			String token = scanner.getToken();
			while(!token.equals("")) {
				LogicExp expr = new LogicExp(scanner);
				expr.print(0);

				token = scanner.getToken();
			}
		} catch (ResolveError re) {
			System.err.println(re.getMessage());
		}
	}
}
