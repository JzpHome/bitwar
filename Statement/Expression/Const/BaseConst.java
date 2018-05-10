package Statement.Expression.Const;

// 语法分析
import Compiler.TokenScanner;
// 错误异常
import Error.ResolveError;

public class BaseConst {
	public void print(int deep) {
		for(int i = 0; i < deep; ++i) {
			System.out.print("    ");
		}
	}
}
