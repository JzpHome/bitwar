package Statement;

// 语法分析
import Compiler.TokenScanner;
// 基本表达式
import Statement.Expression.*;
// 错误异常处理
import Error.ResolveError;

public class BaseStmt {
	public void print(int deep) {
		for(int i = 0; i < deep; ++i) {
			System.out.print("   ");
		}
	}
}
