package Statement.Expression.Const;

// 语法分析
import Compiler.TokenScanner;
// 错误异常
import Error.ResolveError;

public class CUR extends BaseConst {
	public CUR(TokenScanner scanner) throws ResolveError {
		String token = scanner.getToken();
		if(token.equals("CUR")) {
			scanner.match(token);
		} else {
			throw (new ResolveError("Token: '" + token + "' is not 'CUR'"));
		}
	}

	public void print(int deep) {
		super.print(deep);
		System.out.println("CUR");
	}

	public static void main(String[] args) {
		try {
			TokenScanner scanner = new TokenScanner("Test/Statement/Expression/Const/CUR.txt");

			String token = scanner.getToken();
			while(!token.equals("")) {
				CUR cur = new CUR(scanner);
				cur.print(0);

				token = scanner.getToken();
			}
		} catch (ResolveError re) {
			System.err.println(re.getMessage());
		}
	}
}
