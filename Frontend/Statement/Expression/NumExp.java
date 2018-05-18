package Frontend.Statement.Expression;

import Error.BaseError;
import Error.Frontend.*;
import Frontend.TokenScanner;

public class NumExp extends BaseExp {
	private String  __number;

	public NumExp(TokenScanner scanner) throws BaseError {
		__number = null;
		
		scan(scanner);
	}

	public NumExp(NumExp num) {
		__number = new String(num.__number);
	}

	public String Number() {
		return (new String(__number));
	}

	public String toString() {
		return (new String(__number));
	}

	public void print(int deep) {
		super.print(deep);
		System.out.println(__number);
	}

	public void scan(TokenScanner scanner) throws BaseError {
		String token = scanner.getToken();
		if(token.matches("[1-9][0-9]*|0")) {
			scanner.match(token);
			__number = new String(token);
		} else {
			throw (new ExprError("NumExp", scanner.fpath(), scanner.lineno(), 
						scanner.linepos(), "'" + token + "' is not num"));
		}
	}

	public static void main(String[] args) {
		try {
			TokenScanner scanner = new TokenScanner("Test/Frontend/Statement/Expression/NumExp.txt");

			String token = scanner.getToken();
			while(!token.equals("")) {
				NumExp num = new NumExp(scanner);
				System.out.println("NUM: " + num.toString());

				num.print(1);
				System.out.println();

				token = scanner.getToken();
			}
		} catch (BaseError e) {
			System.err.println(e.getMessage());
		}
	}
}
