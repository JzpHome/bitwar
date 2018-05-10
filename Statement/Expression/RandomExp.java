package Statement.Expression;

// 语法分析
import Compiler.TokenScanner;
// 错误异常
import Error.ResolveError;

public class RandomExp extends BaseExp {
	private ComplexExp expr;

	public RandomExp(TokenScanner scanner) throws ResolveError {
		expr = null;

		String token =	scanner.getToken();
		if(token.equals("RANDOM")) {
			scanner.match("RANDOM");

			token = scanner.getToken();
			if(token.equals("(")) {
				scanner.match("(");
				expr = new ComplexExp(scanner);

				token = scanner.getToken();
				if(token.equals(")")) {
					scanner.match(")");
				} else {
					throw (new ResolveError("RandomExp: 缺少右括号')'"));
				}
			} else {
				throw (new ResolveError("RandomExp: 缺少左括号'('"));
			}
		} else {
			throw (new ResolveError("RandomExp: " + token + " is not 'RANDOM'"));
		}
	}

	public void print(int deep) {
		expr.print(deep);
	}

	public static void main(String[] args) {
		try {
			TokenScanner scanner = new TokenScanner("Test/Statement/Expression/RandomExp.txt");

			String token = scanner.getToken();
			while(!token.equals("")) {
				RandomExp expr = new RandomExp(scanner);
				expr.print(0);

				token = scanner.getToken();
			}
		} catch (ResolveError re) {
			System.err.println(re.getMessage());
		}
	}
}
