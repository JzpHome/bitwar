package Frontend.Statement.Expression;

import Error.BaseError;
import Error.Frontend.*;
import Frontend.TokenScanner;

public class RandomExp extends BaseExp {
	private ComplexExp __expr;

	public RandomExp(TokenScanner scanner) throws BaseError {
		__expr = null;

		scan(scanner);
	}

	public RandomExp(RandomExp expr) {
		__expr = new ComplexExp(expr.__expr);
	}

	public ComplexExp Expr() {
		return (new ComplexExp(__expr));
	}

	public String toString() {
		String result = "RANDOM(";
		result += __expr.toString() + ")";

		return result;
	}

	public void print(int deep) {
		super.print(deep);
		System.out.println("RANDOM(");

		__expr.print(deep);

		super.print(deep);
		System.out.println(")");
	}

	public void scan(TokenScanner scanner) throws BaseError {
		String token =	scanner.getToken();
		if(token.equals("RANDOM")) {
			scanner.match("RANDOM");

			token = scanner.getToken();
			if(token.equals("(")) {
				scanner.match("(");
				__expr = new ComplexExp(scanner);

				token = scanner.getToken();
				if(token.equals(")")) {
					scanner.match(")");
				} else {
					throw (new ExprError("RandomExp", scanner.fpath(), scanner.lineno(), 
								scanner.linepos(), "缺少右括号')'"));
				}
			} else {
				throw (new ExprError("RandomExp", scanner.fpath(), scanner.lineno(), 
							scanner.linepos(), "缺少左括号'('"));
			}
		} else {
			throw (new ExprError("RandomExp", scanner.fpath(), scanner.lineno(), 
						scanner.linepos(),  "'" + token + "' is not 'RANDOM'"));
		}
	}

	public static void main(String[] args) {
		try {
			TokenScanner scanner = new TokenScanner("Test/Frontend/Statement/Expression/RandomExp.txt");

			String token = scanner.getToken();
			while(!token.equals("")) {
				RandomExp expr = new RandomExp(scanner);
				System.out.println(expr.toString());
				
				expr.print(1);
				System.out.println();

				token = scanner.getToken();
			}
		} catch (BaseError e) {
			System.err.println(e.getMessage());
		}
	}
}
