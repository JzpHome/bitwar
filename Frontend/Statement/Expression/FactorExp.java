package Frontend.Statement.Expression;

// 前端
import Frontend.TokenScanner;
// 错误异常
import MyExecption.*;

public class FactorExp extends BaseExp {
	private String	__type;
	private BaseExp __expr;

	public FactorExp(TokenScanner scanner) throws FrontendExecption {
		__type = null;
		__expr = null;

		scan(scanner);
	}

	public FactorExp(FactorExp expr) {
		__type = expr.Type();
		__expr = expr.Expr();
	}

	public String Type() {
		return (new String(__type));
	}

	public BaseExp Expr() {
		if(__type.equals("ComplexExp")) {
			return (new ComplexExp((ComplexExp)__expr));
		} else if(__type.equals("NumExp")) {
			return (new NumExp((NumExp)__expr));
		} else {
			return (new IdExp((IdExp)__expr));
		}
	
	}

	public String toString() {
		if(__type.equals("ComplexExp")) {
			return ("(" + ((ComplexExp)__expr).toString() + ")");
		} else if(__type.equals("NumExp")) {
			return ((NumExp)__expr).toString();
		} else {
			return ((IdExp)__expr).toString();
		}
	}

	public void scan(TokenScanner scanner) throws FrontendExecption {
		String token = scanner.getToken();
		if(token.equals("(")) {
			scanner.match("(");
			__type = new String("ComplexExp");
			__expr = new ComplexExp(scanner);

			token = scanner.getToken();
			if(token.equals(")")) {
				scanner.match(")");
			} else {
				throw (new FrontendExecption("FactorExp: 缺少右括号"));
			}
		} else if(token.matches("[1-9][0-9]*|0")) {
			__type = new String("NumExp");
			__expr = new NumExp(scanner);
		} else if(token.matches("[a-zA-Z][a-zA-Z0-9_]*")) {
			__type = new String("IdExp");
			__expr = new IdExp(scanner);
		} else {
			throw (new FrontendExecption("FactorExp: 缺少左括号"));
		}
	}

	public static void main(String[] args) {
		try {
			TokenScanner scanner = new TokenScanner("Test/Frontend/Statement/Expression/FactorExp.txt");

			String token = new String(scanner.getToken());
			while(!token.equals("")) {
				FactorExp expr = new FactorExp(scanner);
				System.out.println("FactorExp: " + expr.toString());

				// scanner.match(token);
				token = new String(scanner.getToken());
			}
		} catch (FrontendExecption re) {
			System.err.println(re.getMessage());
		}
	}
}
