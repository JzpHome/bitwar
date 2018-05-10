package Statement.Expression;

// 语法分析
import Compiler.TokenScanner;
// 错误异常
import Error.ResolveError;

public class IdExp extends BaseExp {
	private String		identifier;
	private ComplexExp	index;

	public IdExp(TokenScanner scanner) throws ResolveError {
		index = null;

		String token = scanner.getToken();
		if(token.matches("[a-zA-Z][a-zA-Z0-9_]*")) {
			scanner.match(token);
			identifier = new String(token);

			token = scanner.getToken();
			if(token.equals("[")) {
				scanner.match("[");

				index = new ComplexExp(scanner);

				token = scanner.getToken();
				if(token.equals("]")) {
					scanner.match("]");
				} else {
					throw (new ResolveError("IdExp: " + token + " is not a ']'"));
				}
			}
		} else {
			throw (new ResolveError("IdExp: '" + token + "' is not a id"));
		}
	}

	public void print(int deep) {
		super.print(deep);
		System.out.println("ID: " + identifier);

		if(index != null) {
			super.print(deep);
			System.out.println("[");
			
			index.print(deep+1);

			super.print(deep);
			System.out.println("]");
		}
	}

	public static void main(String[] args) {
		try {
			TokenScanner scanner = new TokenScanner("Test/Statement/Expression/IdExp.txt");

			String token = scanner.getToken();
			while(token != null) {
				IdExp id = new IdExp(scanner);
				id.print(0);

				token = scanner.getToken();
			}
		} catch (ResolveError re) {
			System.err.println(re.getMessage());
		}
	}
}
