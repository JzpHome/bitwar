default: 
	# 错误异常
	javac -d . -cp . ../MyExecption/*.java
	# 关键字和符号表
	javac -d . -cp . ../Frontend/Keywords.java
	# 语法分析
	javac -d . -cp . ../Frontend/TokenScanner.java
	# 基本表达式
	javac -d . -cp . ../Frontend/Statement/Expression/*.java
	# 基本语句
	javac -d . -cp . ../Frontend/Statement/*.java
	# 语义分析
	javac -d . -cp . ../Frontend/Parser.java
	# 中间代码
	javac -d . -cp . ../Optimizer/*.java

test_scan:
	# 测试语法分析
	java Frontend.TokenScanner

test_symbol:
	# 测试符号表
	java Optimizer.SymbolTable

test_expr:
	# 测试基本表达式
	java Frontend.Statement.Expression.IdExp
	java Frontend.Statement.Expression.NumExp
	java Frontend.Statement.Expression.FactorExp
	java Frontend.Statement.Expression.TermExp
	java Frontend.Statement.Expression.SimpleExp
	java Frontend.Statement.Expression.LogicExp
	java Frontend.Statement.Expression.ComplexExp

test_stmt:
	# 测试基本语句
	java Frontend.Statement.RandomStmt
	java Frontend.Statement.ReturnStmt
	java Frontend.Statement.AssignStmt
	java Frontend.Statement.WhileStmt
	java Frontend.Statement.IfStmt
	java Frontend.Statement.SingleStmt
	java Frontend.Statement.StmtSequence
	java Frontend.Statement.Stategy

test_parser:
	# 测试语义分析
	java  Frontend.Parser

test_optimizer:
	# 测试HIR
	java Optimizer.ASTtoHIR
	# 测试LIR
	java Optimizer.HIRtoLIR
