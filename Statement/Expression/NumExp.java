package Statement.Expression;

// 语法分析
import Compiler.TokenScanner;
// 错误异常
import Error.ResolveError;

public class NumExp extends BaseExp {
	private String  number;

	public NumExp(TokenScanner scanner) throws ResolveError {
		String token = scanner.getToken();
		if(token.matches("[0-9]+")) {
			scanner.match(token);
			number = new String(token);
		} else {
			throw (new ResolveError("NumExp: '" + token + "' is not num"));
		}
	}

	public void print(int deep) {
		super.print(deep);
		System.out.println("NUM: " + number);
	}

	public static void main(String[] args) {
		try {
			TokenScanner scanner = new TokenScanner("Test/Statement/Expression/NumExp.txt");

			String token = scanner.getToken();
			while(token != null) {
				NumExp num = new NumExp(scanner);
				num.print(0);

				token = scanner.getToken();
			}
		} catch (ResolveError re) {
			System.err.println(re.getMessage());
		}
	}
}
