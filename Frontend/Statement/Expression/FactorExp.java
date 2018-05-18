package Frontend.Statement.Expression;

import Error.BaseError;
import Error.Frontend.*;
import Frontend.TokenScanner;

public class FactorExp extends BaseExp {
	private String	__type;
	private BaseExp __expr;

	public FactorExp(TokenScanner scanner) throws BaseError {
		__type = null;
		__expr = null;

		scan(scanner);
	}

	public FactorExp(FactorExp expr) {
		__type = new String(expr.__type);
		if(__type.equals("ComplexExp")) {
			__expr = new ComplexExp((ComplexExp)(expr.__expr));
		} else if(__type.equals("NumExp")) {
			__expr = new NumExp((NumExp)(expr.__expr));
		} else if(__type.equals("RandomExp")) {
			__expr = new RandomExp((RandomExp)expr.__expr);
		} else {
			__expr = new IdExp((IdExp)(expr.__expr));
		}
	}

	public String Type() {
		return (new String(__type));
	}

	public BaseExp Expr() {
		if(__type.equals("ComplexExp")) {
			return (new ComplexExp((ComplexExp)__expr));
		} else if(__type.equals("NumExp")) {
			return (new NumExp((NumExp)__expr));
		} else if(__type.equals("RandomExp")) {
			return (new RandomExp((RandomExp)__expr));
		} else {
			return (new IdExp((IdExp)__expr));
		}
	}

	public String toString() {
		if(__type.equals("ComplexExp")) {
			return ("(" + ((ComplexExp)__expr).toString() + ")");
		} else if(__type.equals("NumExp")) {
			return ((NumExp)__expr).toString();
		} else if(__type.equals("RandomExp")) {
			return ((RandomExp)__expr).toString();
		} else {
			return ((IdExp)__expr).toString();
		}
	}

	public void print(int deep) {
		__expr.print(deep);
	}

	public void scan(TokenScanner scanner) throws BaseError {
		String token = scanner.getToken();
		if(token.equals("(")) {
			scanner.match("(");
			__type = new String("ComplexExp");
			__expr = new ComplexExp(scanner);

			token = scanner.getToken();
			if(token.equals(")")) {
				scanner.match(")");
			} else {
				throw (new ExprError("FactorExp", scanner.fpath(), 
									scanner.lineno(), scanner.linepos(), "缺少右括号"));
			}
		} else if(token.equals("RANDOM")) {
			__type = new String("RandomExp");
			__expr = new RandomExp(scanner);
		} else if(token.matches("[1-9][0-9]*|0")) {
			__type = new String("NumExp");
			__expr = new NumExp(scanner);
		} else if(token.matches("[a-zA-Z][a-zA-Z0-9_]*")) {
			__type = new String("IdExp");
			__expr = new IdExp(scanner);
		} else {
			throw (new ExprError("FactorExp", scanner.fpath(), 
								scanner.lineno(), scanner.linepos(), "缺少左括号"));
		}
	}

	public static void main(String[] args) {
		try {
			TokenScanner scanner = new TokenScanner("Test/Frontend/Statement/Expression/FactorExp.txt");

			String token = scanner.getToken();
			while(!token.equals("")) {
				FactorExp expr = new FactorExp(scanner);
				System.out.println("FactorExp: " + expr.toString());

				expr.print(1);
				System.out.println();

				token = scanner.getToken();
			}
		} catch (BaseError e) {
			System.err.println(e.getMessage());
		}
	}
}
