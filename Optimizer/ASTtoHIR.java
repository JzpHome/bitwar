package Optimizer;

// 前端
import java.util.*;
import Frontend.Parser;
import Frontend.Statement.*;
import Frontend.Statement.Expression.*;
// 错误异常
import MyExecption.*;

public class ASTtoHIR {
	public static ArrayList<Quadruple> translator(Stategy stategy) {
		ArrayList<Quadruple> list = new ArrayList<Quadruple>();

		list.add(new Quadruple("Label", (new Label()).toString()));

		list.addAll(translator(stategy.Stmts()));

		return list;
	}

	public static ArrayList<Quadruple> translator(StmtSequence stmts) {
		ArrayList<Quadruple> list = new ArrayList<Quadruple>();

		list.addAll(translator(stmts.Stmt()));

		if(stmts.Stmts() != null) {
			list.addAll(translator(stmts.Stmts()));
		}

		return list;
	}

	public static ArrayList<Quadruple> translator(SingleStmt stmt) {
		String type = stmt.Type();
		if(type.equals("IfStmt")) {
			return translator((IfStmt)stmt.Stmt());
		} else if(type.equals("WhileStmt")) {
			return translator((WhileStmt)stmt.Stmt());
		} else if(type.equals("ReturnStmt")) {
			return translator((ReturnStmt)stmt.Stmt());
		} else if(type.equals("RandomStmt")) {
			return translator((RandomStmt)stmt.Stmt());
		} else {
			return translator((AssignStmt)stmt.Stmt());
		}
	}

	public static ArrayList<Quadruple> translator(IfStmt stmt) {
		Label label_01 = new Label();

		ArrayList<Quadruple> condi = translator(stmt.Condition());
		ArrayList<Quadruple> list = new ArrayList<Quadruple>();
		String temp = condi.get(condi.size()-1).address_01;


		list.addAll(condi);

		list.add(new Quadruple("if_false", temp, label_01.toString()));

		list.addAll(translator(stmt.Then()));

		if(stmt.Else() != null) {
			Label label_02 = new Label();
			list.add(new Quadruple("jmp", label_02.toString()));

			list.add(new Quadruple("Label", label_01.toString()));

			list.addAll(translator(stmt.Else()));

			list.add(new Quadruple("Label", label_02.toString()));
		} else {
			list.add(new Quadruple("Label", label_01.toString()));
		}

		return list;
	}

	public static ArrayList<Quadruple> translator(WhileStmt stmt) {
		Label label_01 = new Label();
		Label label_02 = new Label();

		ArrayList<Quadruple> condi = translator(stmt.Condition());
		ArrayList<Quadruple> list = new ArrayList<Quadruple>();
		String temp = condi.get(condi.size()-1).address_01;

		list.addAll(condi);

		list.add(new Quadruple("Label", label_01.toString()));
		list.add(new Quadruple("if_false", temp, label_02.toString()));

		list.addAll(translator(stmt.Stmts()));

		list.addAll(condi);
		list.add(new Quadruple("if_false", temp, label_01.toString()));

		list.add(new Quadruple("Label", label_02.toString()));

		return list;
	}

	public static ArrayList<Quadruple> translator(AssignStmt stmt) {
		ArrayList<Quadruple> list = new ArrayList<Quadruple>();

		list.addAll(translator(stmt.ID()));
		String identifier = (list.remove(list.size()-1)).address_02;

		list.addAll(translator(stmt.Expr()));
		String result = list.get(list.size()-1).address_01;

		list.add(new Quadruple(":=", identifier, result));

		return list;
	}

	public static ArrayList<Quadruple> translator(ReturnStmt stmt) {
		ArrayList<Quadruple> list = new ArrayList<Quadruple>();

		list.addAll(translator(stmt.Expr()));

		if(!list.isEmpty()) {
			String result = list.get(list.size()-1).address_01;
			list.add(new Quadruple("return", result));
		} else {
			list.add(new Quadruple("return", "0"));
		}

		return list;
	}

	public static ArrayList<Quadruple> translator(RandomStmt stmt) {
		ArrayList<Quadruple> list = new ArrayList<Quadruple>();

		list.addAll(translator(stmt.Expr()));

		Temp temp = new Temp();
		if(!list.isEmpty()) {
			String max = list.get(list.size()-1).address_01;
			list.add(new Quadruple("RANDOM", temp.toString(), "0", max));
		} else {
			list.add(new Quadruple("RANDOM", temp.toString(), "0", "0"));
		}

		return list;
	}

	public static ArrayList<Quadruple> translator(ComplexExp expr) {
		ArrayList<Quadruple> list = new ArrayList<Quadruple>();

		list.addAll(translator(expr.Expr01()));

		String option = expr.Option();
		if(option != null) {
			String temp_01 = list.get(list.size()-1).address_01;

			list.addAll(translator(expr.Expr02()));
			String temp_02 = list.get(list.size()-1).address_01;

			Temp temp = new Temp();
			list.add(new Quadruple(option, temp.toString(), temp_01, temp_02));
		}

		return list;
	}

	public static ArrayList<Quadruple> translator(LogicExp expr) {
		ArrayList<Quadruple> list = new ArrayList<Quadruple>();

		list.addAll(translator(expr.Expr01()));

		String option = expr.Option();
		if(option != null) {
			String temp_01 = list.get(list.size()-1).address_01;

			list.addAll(translator(expr.Expr02()));
			String temp_02 = list.get(list.size()-1).address_01;

			Temp temp = new Temp();
			list.add(new Quadruple(option, temp.toString(), temp_01, temp_02));
		}

		return list;
	}

	public static ArrayList<Quadruple> translator(SimpleExp expr) {
		ArrayList<Quadruple> list = new ArrayList<Quadruple>();

		list.addAll(translator(expr.Expr01()));

		String option = expr.Option();
		if(option != null) {
			String temp_01 = list.get(list.size()-1).address_01;

			list.addAll(translator(expr.Expr02()));
			String temp_02 = list.get(list.size()-1).address_01;

			Temp temp = new Temp();
			list.add(new Quadruple(option, temp.toString(), temp_01, temp_02));
		}

		return list;
	}

	public static ArrayList<Quadruple> translator(TermExp expr) {
		ArrayList<Quadruple> list = new ArrayList<Quadruple>();

		list.addAll(translator(expr.Expr01()));

		String option = expr.Option();
		if(option != null) {
			String temp_01 = list.get(list.size()-1).address_01;

			list.addAll(translator(expr.Expr02()));
			String temp_02 = list.get(list.size()-1).address_01;

			Temp temp = new Temp();
			list.add(new Quadruple(option, temp.toString(), temp_01, temp_02));
		}

		return list;
	}

	public static ArrayList<Quadruple> translator(FactorExp expr) {
		ArrayList<Quadruple> list = new ArrayList<Quadruple>();

		String type = expr.Type();
		BaseExp temp_expr = expr.Expr();
		if(type.equals("ComplexExp")) {
			list.addAll(translator((ComplexExp)temp_expr));
		} else if(type.equals("NumExp")) {
			list.addAll(translator((NumExp)temp_expr));
		} else {
			list.addAll(translator((IdExp)temp_expr));
		}

		return list;
	}

	public static ArrayList<Quadruple> translator(NumExp expr) {
		ArrayList<Quadruple> list = new ArrayList<Quadruple>();

		Temp temp = new Temp();
		list.add(new Quadruple(":=", temp.toString(), expr.Number()));

		return list;
	}

	public static ArrayList<Quadruple> translator(IdExp expr) {
		String id = expr.ID();
		ComplexExp index = expr.Index();
		ArrayList<Quadruple> list = new ArrayList<Quadruple>();

		if(index != null) {
			list.addAll(translator(index));

			Temp temp = new Temp();
			String arr_index = list.get(list.size()-1).address_01;

			list.add(new Quadruple(":=", temp.toString(), id + "[" + arr_index + "]"));
		} else {
			Temp temp = new Temp();
			list.add(new Quadruple(":=", temp.toString(), id));
		}

		return list;
	}

	public static void main(String[] args) {
		try {
			Stategy stategy = Parser.parse("Test/Optimizer/ASTtoHIR_01.txt");
			for(Quadruple temp: translator(stategy)) {
				System.out.print(temp.option + " ");
				System.out.print(temp.address_01 + " ");
				System.out.print(temp.address_02 + " ");
				System.out.print(temp.address_03 + "\n");
			}

			stategy = Parser.parse("Test/Optimizer/ASTtoHIR_02.txt");
			for(Quadruple temp: translator(stategy)) {
				System.out.print(temp.option + " ");
				System.out.print(temp.address_01 + " ");
				System.out.print(temp.address_02 + " ");
				System.out.print(temp.address_03 + "\n");
			}

			stategy = Parser.parse("Test/Optimizer/ASTtoHIR_03.txt");
			for(Quadruple temp: translator(stategy)) {
				System.out.print(temp.option + " ");
				System.out.print(temp.address_01 + " ");
				System.out.print(temp.address_02 + " ");
				System.out.print(temp.address_03 + "\n");
			}
		} catch (FrontendExecption e) {
			System.err.println(e.getMessage());
		}
	}
}
