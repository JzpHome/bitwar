package Frontend.Statement.Expression;

import Error.BaseError;
import Error.Frontend.*;
import Frontend.TokenScanner;

public class IdExp extends BaseExp {
	private String		__id;
	private ComplexExp	__index;

	public IdExp(TokenScanner scanner) throws BaseError {
		__id	= null;
		__index	= null;

		scan(scanner);
	}

	public IdExp(IdExp expr) {
		__id	= new String(expr.__id);
		if(expr.__index != null) {
			__index = new ComplexExp(expr.__index);
		} else {
			__index = null;
		}
	}

	public String ID() {
		return (new String(__id));
	}

	public ComplexExp Index() {
		if(__index != null) {
			return (new ComplexExp(__index));
		} else {
			return null;
		}
	}

	public String toString() {
		if(__index != null) {
			return (__id + "[" + __index.toString() + "]");
		} else {
			return (new String(__id));
		}
	}

	public void print(int deep) {
		super.print(deep);
		System.out.println(__id);

		if(__index != null) {
			super.print(deep);
			System.out.println('[');

			__index.print(deep);

			super.print(deep);
			System.out.println(']');
		}
	}

	public void scan(TokenScanner scanner) throws BaseError {
		String token = scanner.getToken();
		if(token.matches("[a-zA-Z][a-zA-Z0-9_]*")) {
			scanner.match(token);
			__id = new String(token);

			token = scanner.getToken();
			if(token.equals("[")) {
				scanner.match("[");

				__index = new ComplexExp(scanner);

				token = scanner.getToken();
				if(token.equals("]")) {
					scanner.match("]");
				} else {
			throw (new ExprError("IdExp", scanner.fpath(), scanner.lineno(), 
						scanner.linepos(), "'" + token + "' is not a ']'"));
				}
			}
		} else {
			throw (new ExprError("IdExp", scanner.fpath(), scanner.lineno(), 
						scanner.linepos(), "'" + token + "' is not a id"));
		}
	}

	public static void main(String[] args) {
		try {
			TokenScanner scanner = new TokenScanner("Test/Frontend/Statement/Expression/IdExp.txt");

			String token = scanner.getToken();
			while(!token.equals("")) {
				IdExp id = new IdExp(scanner);
				System.out.println("ID: " + id.toString());

				id.print(1);
				System.out.println();

				token = scanner.getToken();
			}
		} catch (BaseError e) {
			System.err.println(e.getMessage());
		}
	}
}
