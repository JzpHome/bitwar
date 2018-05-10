package Statement.Expression;

// 语法分析
import Compiler.TokenScanner;
// 常量表达式
import Statement.Expression.Const.*;
// 错误异常
import Error.ResolveError;

public class ConstExp extends BaseExp {
	private BaseConst expr;

	public ConstExp(TokenScanner scanner) throws ResolveError {
		String token = scanner.getToken();
		if(token.equals("CUR")) {
			expr = new CUR(scanner);
		} else if(token.equals("A")) {
			expr = new A(scanner);
		} else if(token.equals("B")) {
			expr = new B(scanner);
		} else {
			throw (new ResolveError("ConstExp: " + token + " is not 'CUR' or 'A' or 'B'"));
		}
	}

	public void print(int deep) {
		expr.print(deep);
	}

	public static void main(String[] args) {
		try {
			TokenScanner scanner = new TokenScanner("Test/Statement/Expression/ConstExp.txt");

			String token = scanner.getToken();
			while(!token.equals("")) {
				ConstExp expr = new ConstExp(scanner);
				expr.print(0);

				token = scanner.getToken();
			}
		} catch (ResolveError re) {
			System.err.println(re.getMessage());
		}
	}
}
