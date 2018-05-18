package Frontend;

import Optimizer.*;
import Error.BaseError;
import Frontend.Statement.*;
import Frontend.Statement.Expression.*;

/*
 */
public class ASTtoHIR {
	public static HIR translator(Stategy stategy, SymbolTable symbol) {
		HIR hir = new HIR();
		String label_01 = (new Label()).toString();

		hir.add(new Quadruple("LABEL", label_01));

		hir.addAll(translator(stategy.Stmts(), symbol));

		return hir;
	}

	public static HIR translator(StmtSequence stmts, SymbolTable symbol) {
		HIR hir = new HIR();

		hir.addAll(translator(stmts.Stmt(), symbol));

		if(stmts.Stmts() != null) {
			hir.addAll(translator(stmts.Stmts(), symbol));
		}

		return hir;
	}

	public static HIR translator(SingleStmt stmt, SymbolTable symbol) {
		String type = stmt.Type();
		if(type.equals("IfStmt")) {
			return translator((IfStmt)stmt.Stmt(), symbol);
		} else if(type.equals("WhileStmt")) {
			return translator((WhileStmt)stmt.Stmt(), symbol);
		} else if(type.equals("ReturnStmt")) {
			return translator((ReturnStmt)stmt.Stmt(), symbol);
		} else {
			return translator((AssignStmt)stmt.Stmt(), symbol);
		}
	}

	public static HIR translator(IfStmt stmt, SymbolTable symbol) {
		String label_01 = (new Label()).toString();

		HIR condi = translator(stmt.Condition(), symbol);
		HIR hir = new HIR();
		String temp = condi.get(condi.size()-1).addr_01;


		hir.addAll(condi);

		hir.add(new Quadruple("IFF", temp, label_01));

		hir.addAll(translator(stmt.Then(), symbol));

		if(stmt.Else() != null) {
			String label_02 = (new Label()).toString();
			hir.add(new Quadruple("JMP", label_02));

			hir.add(new Quadruple("LABEL", label_01));

			hir.addAll(translator(stmt.Else(), symbol));

			hir.add(new Quadruple("LABEL", label_02));
		} else {
			hir.add(new Quadruple("LABEL", label_01));
		}

		return hir;
	}

	public static HIR translator(WhileStmt stmt, SymbolTable symbol) {
		String label_01 = (new Label()).toString();
		String label_02 = (new Label()).toString();

		HIR condi = translator(stmt.Condition(), symbol);
		HIR hir = new HIR();
		String temp = condi.get(condi.size()-1).addr_01;

		// 循环条件判断
		hir.add(new Quadruple("LABEL", label_01));
		hir.addAll(condi);
		hir.add(new Quadruple("IFF", temp, label_02));
		// 循环体
		hir.addAll(translator(stmt.Stmts(), symbol));
		hir.add(new Quadruple("JMP", label_01));
		// 循环结束
		hir.add(new Quadruple("LABEL", label_02));

		return hir;
	}

	public static HIR translator(AssignStmt stmt, SymbolTable symbol) {
		HIR hir = new HIR();

		// 把标识符添加到SymbolTable
		symbol.addValue(stmt.ID().ID());

		// AssignStmt to HIR
		hir.addAll(translator(stmt.ID(), symbol));
		String identifier = (hir.remove(hir.size()-1)).addr_02;

		hir.addAll(translator(stmt.Expr(), symbol));
		String result = hir.get(hir.size()-1).addr_01;

		hir.add(new Quadruple("MOV", identifier, result));

		return hir;
	}

	public static HIR translator(ReturnStmt stmt, SymbolTable symbol) {
		HIR hir = new HIR();

		hir.addAll(translator(stmt.Expr(), symbol));

		String result = hir.get(hir.size()-1).addr_01;
		hir.add(new Quadruple("RETURN", result));

		return hir;
	}

	public static HIR translator(ComplexExp expr, SymbolTable symbol) {
		HIR hir = new HIR();

		hir.addAll(translator(expr.Expr01(), symbol));

		String option = expr.Option();
		if(option != null) {
			String temp_01 = hir.get(hir.size()-1).addr_01;

			hir.addAll(translator(expr.Expr02(), symbol));
			String temp_02 = hir.get(hir.size()-1).addr_01;

			String temp = (new Temp(symbol)).toString();
			if(option.equals("or")) {
				hir.add(new Quadruple("OR", temp, temp_01, temp_02));
			} else {
				hir.add(new Quadruple("AND", temp, temp_01, temp_02));
			}
		}

		return hir;
	}

	public static HIR translator(LogicExp expr, SymbolTable symbol) {
		HIR hir = new HIR();

		hir.addAll(translator(expr.Expr01(), symbol));

		String option = expr.Option();
		if(option != null) {
			String temp_01 = hir.get(hir.size()-1).addr_01;

			hir.addAll(translator(expr.Expr02(), symbol));
			String temp_02 = hir.get(hir.size()-1).addr_01;

			String temp = (new Temp(symbol)).toString();
			if(option.equals(">")) {
				hir.add(new Quadruple("GREAT", temp, temp_01, temp_02));
			} else if(option.equals("=")) {
				hir.add(new Quadruple("EQUAL", temp, temp_01, temp_02));
			} else {
				hir.add(new Quadruple("LESS", temp, temp_01, temp_02));
			}
		}

		return hir;
	}

	public static HIR translator(SimpleExp expr, SymbolTable symbol) {
		HIR hir = new HIR();

		hir.addAll(translator(expr.Expr01(), symbol));

		String option = expr.Option();
		if(option != null) {
			String temp_01 = hir.get(hir.size()-1).addr_01;

			hir.addAll(translator(expr.Expr02(), symbol));
			String temp_02 = hir.get(hir.size()-1).addr_01;

			String temp = (new Temp(symbol)).toString();
			if(option.equals("+")) {
				hir.add(new Quadruple("ADD", temp, temp_01, temp_02));
			} else {
				hir.add(new Quadruple("SUB", temp, temp_01, temp_02));
			}
		}

		return hir;
	}

	public static HIR translator(TermExp expr, SymbolTable symbol) {
		HIR hir = new HIR();

		hir.addAll(translator(expr.Expr01(), symbol));

		String option = expr.Option();
		if(option != null) {
			String temp_01 = hir.get(hir.size()-1).addr_01;

			hir.addAll(translator(expr.Expr02(), symbol));
			String temp_02 = hir.get(hir.size()-1).addr_01;

			String temp = (new Temp(symbol)).toString();
			if(option.equals("+")) {
				hir.add(new Quadruple("MUL", temp, temp_01, temp_02));
			} else {
				hir.add(new Quadruple("DIV", temp, temp_01, temp_02));
			}
		}

		return hir;
	}

	public static HIR translator(FactorExp expr, SymbolTable symbol) {
		HIR hir = new HIR();

		String type = expr.Type();
		BaseExp temp_expr = expr.Expr();
		if(type.equals("ComplexExp")) {
			hir.addAll(translator((ComplexExp)temp_expr, symbol));
		} else if(type.equals("NumExp")) {
			hir.addAll(translator((NumExp)temp_expr, symbol));
		} else if(type.equals("RandomExp")) {
			hir.addAll(translator((RandomExp)temp_expr, symbol));
		} else {
			hir.addAll(translator((IdExp)temp_expr, symbol));
		}

		return hir;
	}

	public static HIR translator(RandomExp expr, SymbolTable symbol) {
		HIR hir = new HIR();

		hir.addAll(translator(expr.Expr(), symbol));
		String max = hir.get(hir.size()-1).addr_01;

		String min = (new Temp(symbol)).toString();
		hir.add(new Quadruple("MOV", min, "0"));

		String temp = (new Temp(symbol)).toString();
		hir.add(new Quadruple("RANDOM", temp, min, max));

		return hir;
	}

	public static HIR translator(NumExp expr, SymbolTable symbol) {
		HIR hir = new HIR();

		String temp = (new Temp(symbol)).toString();
		hir.add(new Quadruple("MOV", temp, expr.Number()));

		return hir;
	}

	public static HIR translator(IdExp expr, SymbolTable symbol) {
		HIR hir = new HIR();
		String id = expr.ID();
		ComplexExp index = expr.Index();

		String temp = (new Temp(symbol)).toString();
		if(index != null) {
			hir.addAll(translator(index, symbol));
			String arr_index = hir.get(hir.size()-1).addr_01;

			String temp_01 = (new Temp(symbol)).toString();
			hir.add(new Quadruple("ADD", temp_01, arr_index, id));
			hir.add(new Quadruple("MOV", temp, "[" + temp_01 + "]"));
		} else {
			hir.add(new Quadruple("MOV", temp, id));
		}

		return hir;
	}

	public static void main(String[] args) {
		try {
			SymbolTable symbol = new SymbolTable();
			Stategy stategy = Parser.parse("Test/Frontend/ASTtoHIR_01.txt");
			HIR hir = translator(stategy, symbol);
			for(int i = 0; i < hir.size(); ++i) {
				Quadruple temp = hir.get(i);
				System.out.printf("%10s", temp.option + " ");
				System.out.printf("%10s", temp.addr_01 + " ");
				System.out.printf("%10s", temp.addr_02 + " ");
				System.out.printf("%10s", temp.addr_03 + "\n");
			}
			System.out.println("-------------------------------------------------------");

			stategy = Parser.parse("Test/Frontend/ASTtoHIR_02.txt");
			hir = translator(stategy, symbol);
			for(int i = 0; i < hir.size(); ++i) {
				Quadruple temp = hir.get(i);
				System.out.printf("%10s", temp.option + " ");
				System.out.printf("%10s", temp.addr_01 + " ");
				System.out.printf("%10s", temp.addr_02 + " ");
				System.out.printf("%10s", temp.addr_03 + "\n");
			}
			System.out.println("-------------------------------------------------------");

			stategy = Parser.parse("Test/Frontend/ASTtoHIR_03.txt");
			hir = translator(stategy, symbol);
			for(int i = 0; i < hir.size(); ++i) {
				Quadruple temp = hir.get(i);
				System.out.printf("%10s", temp.option + " ");
				System.out.printf("%10s", temp.addr_01 + " ");
				System.out.printf("%10s", temp.addr_02 + " ");
				System.out.printf("%10s", temp.addr_03 + "\n");
			}
			System.out.println("-------------------------------------------------------");
		} catch (BaseError e) {
			System.err.println(e.getMessage());
		}
	}
}
