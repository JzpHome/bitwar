package Frontend.Statement.Expression;

// 前端
import Frontend.TokenScanner;
// 错误异常
import MyExecption.*;

public class NumExp extends BaseExp {
	private String  __number;

	public NumExp(TokenScanner scanner) throws FrontendExecption {
		__number = null;
		
		scan(scanner);
	}

	public String Number() {
		return (new String(__number));
	}

	public NumExp(NumExp num) {
		__number = new String(num.__number);
	}

	public String toString() {
		return (new String(__number));
	}

	public void scan(TokenScanner scanner) throws FrontendExecption {
		String token = scanner.getToken();
		if(token.matches("[1-9][0-9]*|0")) {
			scanner.match(token);
			__number = new String(token);
		} else {
			throw (new FrontendExecption("NumExp: '" + token + "' is not num"));
		}
	}

	public static void main(String[] args) {
		try {
			TokenScanner scanner = new TokenScanner("Test/Frontend/Statement/Expression/NumExp.txt");

			String token = new String(scanner.getToken());
			while(!token.equals("")) {
				NumExp num = new NumExp(scanner);
				System.out.println("NUM: " + num.toString());

				scanner.match(token);
				token = new String(scanner.getToken());
			}
		} catch (FrontendExecption e) {
			System.err.println(e.getMessage());
		}
	}
}
