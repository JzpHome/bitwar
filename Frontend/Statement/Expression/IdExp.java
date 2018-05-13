package Frontend.Statement.Expression;

// 前端
import Frontend.TokenScanner;
// 错误异常
import MyExecption.FrontendExecption;

public class IdExp extends BaseExp {
	private String		__id;
	private ComplexExp	__index;

	public IdExp(TokenScanner scanner) throws FrontendExecption {
		__id	= null;
		__index	= null;

		scan(scanner);
	}

	public IdExp(IdExp expr) {
		__id	= new String(expr.__id);
		__index = new ComplexExp(expr.__index);
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

	public void scan(TokenScanner scanner) throws FrontendExecption {
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
					throw (new FrontendExecption("IdExp: " + token + " is not a ']'"));
				}
			}
		} else {
			throw (new FrontendExecption("IdExp: '" + token + "' is not a id"));
		}
	}

	public static void main(String[] args) {
		try {
			TokenScanner scanner = new TokenScanner("Test/Frontend/Statement/Expression/IdExp.txt");

			String token = new String(scanner.getToken());
			while(!token.equals("")) {
				IdExp id = new IdExp(scanner);
				System.out.println("ID: " + id.toString());

				scanner.match(token);
				token = new String(scanner.getToken());
			}
		} catch (FrontendExecption re) {
			System.err.println(re.getMessage());
		}
	}
}
