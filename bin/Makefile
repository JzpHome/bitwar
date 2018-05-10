default: 
	# 错误异常
	javac -d . -cp . ../Error/*.java
	# 关键字和符号表
	javac -d . -cp . ../Compiler/Keywords.java
	javac -d . -cp . ../Compiler/SymbolTable.java
	# 语法分析
	javac -d . -cp . ../Compiler/TokenScanner.java
	# 基本表达式
	javac -d . -cp . ../Statement/Expression/*.java
	# 基本语句
	javac -d . -cp . ../Statement/*.java
	# 语义分析
	javac -d . -cp . ../Compiler/Parser.java

test_scan:
	# 测试语法分析
	java Compiler.TokenScanner

test_symbol:
	# 测试符号表
	java Compiler.SymbolTable

test_expr:
	# 测试基本表达式
	java Statement.Expression.IdExp
	java Statement.Expression.NumExp
	java Statement.Expression.RandomExp
	java Statement.Expression.ConstExp
	java Statement.Expression.FactorExp
	java Statement.Expression.TermExp
	java Statement.Expression.SimpleExp
	java Statement.Expression.LogicExp
	java Statement.Expression.ComplexExp

test_stmt:
	# 测试基本语句
	java  Statement.ReturnStmt
	java  Statement.AssignStmt
	java  Statement.WhileStmt
	java  Statement.IfStmt
	java  Statement.SingleStmt
	java  Statement.StmtSequence
	java  Statement.Stategy

test_parser:
	# 测试语义分析
	java  Compiler.Parser
