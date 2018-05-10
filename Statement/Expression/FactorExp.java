package Statement.Expression;

// 语法分析
import Compiler.TokenScanner;
// 错误异常
import Error.ResolveError;

public class FactorExp extends BaseExp {
	private BaseExp expr;

	public FactorExp(TokenScanner scanner) throws ResolveError {
		expr = null;

		String token = scanner.getToken();
		if(token.equals("RANDOM")) {
			expr = new RandomExp(scanner);
		} else if(token.matches("[0-9]+")) {
			expr = new NumExp(scanner);
		} else if(token.matches("[a-zA-Z][a-zA-Z0-9_]*")) {
			expr = new IdExp(scanner);
		} else if(token.equals("(")) {
			scanner.match("(");
			expr = new ComplexExp(scanner);

			token = scanner.getToken();
			if(token.equals(")")) {
				scanner.match(")");
			} else {
				throw (new ResolveError("FactorExp: 缺少右括号"));
			}
		} else {
			throw (new ResolveError("FactorExp: 缺少左括号"));
		}
	}

	public void print(int deep) {
		expr.print(deep);
	}

	public static void main(String[] args) {
		try {
			TokenScanner scanner = new TokenScanner("Test/Statement/Expression/FactorExp.txt");

			String token = scanner.getToken();
			while(!token.equals("")) {
				FactorExp expr = new FactorExp(scanner);
				expr.print(0);

				token = scanner.getToken();
			}
		} catch (ResolveError re) {
			System.err.println(re.getMessage());
		}
	}
}
