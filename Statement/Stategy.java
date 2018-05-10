package Statement;

// 语法分析
import Compiler.TokenScanner;
// 基本表达式
import Statement.Expression.*;
// 错误异常处理
import Error.ResolveError;

public class Stategy {
	private StmtSequence sequence;

	public Stategy(TokenScanner scanner) throws ResolveError {
		sequence = new StmtSequence(scanner);
	}

	public void print() {
		System.out.println("Stategy: ");
		
		sequence.print(1);
	}

	public static void main(String[] args) {
		try {
			TokenScanner scanner = new TokenScanner("Test/Statement/Stategy.txt");

			String token = scanner.getToken();
			while(!token.equals("")) {
				Stategy stategy = new Stategy(scanner);
				stategy.print();

				token = scanner.getToken();
			}
		} catch (ResolveError re) {
			System.err.println(re.getMessage());
		}
	}
}
