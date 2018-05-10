package Statement.Expression.Const;

// 语法分析
import Compiler.TokenScanner;
// 基本表达式
import Statement.Expression.ComplexExp;
// 错误异常
import Error.ResolveError;

public class B extends BaseConst {
	private ComplexExp index;

	public B(TokenScanner scanner) throws ResolveError {
		String token = scanner.getToken();
		if(token.equals("B")) {
			scanner.match(token);
			
			token = scanner.getToken();
			if(token.equals("[")) {
				scanner.match("[");

				index = new ComplexExp(scanner);

				token = scanner.getToken();
				if(token.equals("]")) {
					scanner.match("]");
				} else {
					throw (new ResolveError("Token: " + token + " is not a ']'"));
				}
			} else {
				throw (new ResolveError("Token: " + token + " is not a '['"));
			}
		} else {
			throw (new ResolveError("Token: " + token + " is not a 'B'"));
		}
	}

	public void print(int deep) {
		super.print(deep);
		System.out.println("B[");
		
		index.print(deep+1);

		super.print(deep);
		System.out.println("]");
	}

	public static void main(String[] args) {
		try {
			TokenScanner scanner = new TokenScanner("Test/Statement/Expression/Const/B.txt");

			String token = scanner.getToken();
			while(!token.equals("")) {
				B b = new B(scanner);
				b.print(0);

				token = scanner.getToken();
			}
		} catch (ResolveError re) {
			System.err.println(re.getMessage());
		}
	}
}
