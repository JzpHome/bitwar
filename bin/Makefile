default: 
	# Error
	javac -d . -cp . ../Error/*.java
	javac -d . -cp . ../Error/Frontend/*.java
	javac -d . -cp . ../Error/Optimizer/*.java
	javac -d . -cp . ../Error/Backend/*.java
	javac -d . -cp . ../Error/BitwarVM/*.java
	# Frontend
	javac -d . -cp . ../Frontend/TokenScanner.java
	javac -d . -cp . ../Frontend/Statement/Expression/*.java
	javac -d . -cp . ../Frontend/Statement/*.java 
	javac -d . -cp . ../Frontend/*.java 
	# Optimizer
	javac -d . -cp . ../Optimizer/*.java
	# Backend
	javac -d . -cp . ../Backend/*.java
	# BitwarVM
	javac -d . -cp . ../BitwarVM/*.java
	# 
	javac -d . -cp . ../*.java

# 测试前端
test_TokenScanner:
	# 测试语法分析
	java Frontend.TokenScanner
test_Expression:
	# 测试基本表达式
	java Frontend.Statement.Expression.IdExp
	java Frontend.Statement.Expression.NumExp
	java Frontend.Statement.Expression.RandomExp
	java Frontend.Statement.Expression.FactorExp
	java Frontend.Statement.Expression.TermExp
	java Frontend.Statement.Expression.SimpleExp
	java Frontend.Statement.Expression.LogicExp
	java Frontend.Statement.Expression.ComplexExp
test_Statement:
	# 测试基本语句
	java Frontend.Statement.ReturnStmt
	java Frontend.Statement.AssignStmt
	java Frontend.Statement.WhileStmt
	java Frontend.Statement.IfStmt
	java Frontend.Statement.SingleStmt
	java Frontend.Statement.StmtSequence
	java Frontend.Statement.Stategy
test_Parser:
	# 测试语义分析
	java  Frontend.Parser



# 测试Optimizer
test_SymbolTable:
	# 测试符号表
	java Optimizer.SymbolTable
test_ASTtoHIR:
	# 测试ASTtoHIR
	java Frontend.ASTtoHIR
test_HIRtoLIR:
	# 测试HIRtoLIR
	java Optimizer.HIRtoLIR
test_LIRtoExec:
	# 测试LIRtoExec
	java Backend.LIRtoExec



# 测试Backend



# 测试BitwarVM
test_BitwarVM:
	# 测试BitwarVM
	java BitwarVM.VirtualMachine



# 测试
test:
	java AST Test/T1.txt Test/T2.txt Test/T3.txt Test/T4.txt Test/T5.txt
	java Battle Test/T1.txt Test/T2.txt Test/T3.txt Test/T4.txt Test/T5.txt 10
