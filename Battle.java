import java.util.*;
import Optimizer.*;
import Error.BaseError;
import Frontend.Parser;
import Frontend.ASTtoHIR;
import Backend.LIRtoExec;
import BitwarVM.VirtualMachine;
import Frontend.Statement.Stategy;

public class Battle {
	// private Stategy		__T1, __T2;
	private SymbolTable		__symbol;
	private String[]		__exec;
	private VirtualMachine	__vm;

    public Battle(Stategy T1, Stategy T2, int N) throws BaseError {
		__symbol = new SymbolTable();
		// 添加基本变量
		__symbol.addValue("1AScore");
		__symbol.addValue("1BScore");
		__symbol.addValue("CUR");
		__symbol.addValue("N");
		__symbol.addValue("A", N);
		__symbol.addValue("B", N);
		// 用于返回策略选择值
		__symbol.addValue("1RET");

		// 策略T1, T2的HIR
		HIR hir_T1	= ASTtoHIR.translator(T1, __symbol);
		HIR hir_T2	= ASTtoHIR.translator(T2, __symbol);

		// to HIR
		HIR hir	= new HIR();
		hir.add(new Quadruple("MOV", "CUR", "0"));		// 初始化CUR
		hir.add(new Quadruple("MOV", "N", "" + N));		// 初始化N
		hir.addAll(connectHIR(hir_T1, hir_T2, __symbol));	// 

		// to LIR
		LIR lir	= HIRtoLIR.translator(hir, __symbol);

		// to Exec
		__exec	= LIRtoExec.translator(lir, __symbol);
	}

	public Integer[] Run() throws BaseError {
		__vm = new VirtualMachine(__exec.length+10);
		__vm.Load(__exec, 0);
		__vm.Run();

		Integer AScore_addr = __symbol.getValue("1AScore") + __symbol.DS();
		Integer BScore_addr = __symbol.getValue("1BScore") + __symbol.DS();
		Integer AScore = Integer.valueOf(__vm.getMemory(AScore_addr));
		Integer BScore = Integer.valueOf(__vm.getMemory(BScore_addr));

		Integer[] Score = new Integer[2];
		Score[0] = AScore;
		Score[1] = BScore;

		return Score;
	}

	private HIR connectHIR(HIR hir_T1, HIR hir_T2, 
							SymbolTable symbol) throws BaseError {
		HIR hir = new HIR();
		String label_01 = (new Label()).toString();
		String label_02 = (new Label()).toString();
		String label_03 = (new Label()).toString();
		String label_04 = (new Label()).toString();
		String condi	= (new Temp(symbol)).toString();
		String temp_01	= (new Temp(symbol)).toString();
		String temp_02	= (new Temp(symbol)).toString();

		// 循环条件判断(CUR < N)
		hir.add(new Quadruple("LABEL", label_01));
		hir.add(new Quadruple("LESS", condi, "CUR", "N"));
		hir.add(new Quadruple("IFF", condi, label_02));
		// 循环体
		// A[CUR] := T1
		hir.add(new Quadruple("MOV", temp_01, "[A]"));
		hir.add(new Quadruple("ADD", temp_02, "CUR", temp_01));
		hir.add(new Quadruple("JMP", hir_T1.get(0).addr_01));
		hir.add(new Quadruple("LABEL", label_03));
		hir.add(new Quadruple("MOV", "[" + temp_02 +"]", "1RET"));
		// B[CUR] := T2
		hir.add(new Quadruple("MOV", temp_01, "[B]"));
		hir.add(new Quadruple("ADD", temp_02, "CUR", temp_01));
		hir.add(new Quadruple("JMP", hir_T2.get(0).addr_01));
		hir.add(new Quadruple("LABEL", label_04));
		hir.add(new Quadruple("MOV", "[" + temp_02 + "]", "1RET"));
		// 计算得分
		hir.addAll(CalcScore(symbol));
		// CUR := CUR + 1
		hir.add(new Quadruple("MOV", temp_01, "1"));
		hir.add(new Quadruple("ADD", temp_02, "CUR", temp_01));
		hir.add(new Quadruple("MOV", "CUR", temp_02));
		hir.add(new Quadruple("JMP", label_01));
		// 添加策略T1的HIR
		hir.addAll(IDReplace(hir_T1, "A", "B"));
		hir.add(new Quadruple("JMP", label_03));
		// 添加策略T2的HIR
		hir.addAll(IDReplace(hir_T2, "B", "A"));
		hir.add(new Quadruple("JMP", label_04));
		// 循环结束
		hir.add(new Quadruple("LABEL", label_02));

		return hir;
	}

	private HIR CalcScore(SymbolTable symbol) {
		HIR hir = new HIR();
		String label_01 = (new Label()).toString();
		String label_02 = (new Label()).toString();
		String label_03 = (new Label()).toString();
		String label_04 = (new Label()).toString();
		String temp_01	= (new Temp(symbol)).toString();
		String temp_02	= (new Temp(symbol)).toString();

		hir.add(new Quadruple("MOV", temp_01, "[A]"));
		hir.add(new Quadruple("ADD", temp_02, "CUR", temp_01));
		hir.add(new Quadruple("MOV", temp_02, "[[" + temp_02 + "]]"));
		hir.add(new Quadruple("IFF", temp_02, label_01));
		// A[CUR] := 1
		hir.add(new Quadruple("MOV", temp_01, "[B]"));
		hir.add(new Quadruple("ADD", temp_02, "CUR", temp_01));
		hir.add(new Quadruple("MOV", temp_02, "[[" + temp_02 + "]]"));
		hir.add(new Quadruple("IFF", temp_02, label_02));
		// B[CUR] := 1
		hir.addAll(addScore(3, 3, symbol));
		hir.add(new Quadruple("JMP", label_04));
		// B[CUR] := 0
		hir.add(new Quadruple("LABEL", label_02));
		hir.addAll(addScore(0, 5, symbol));
		hir.add(new Quadruple("JMP", label_04));
		
		// A[CUR] := 0
		hir.add(new Quadruple("LABEL", label_01));
		hir.add(new Quadruple("MOV", temp_01, "[B]"));
		hir.add(new Quadruple("ADD", temp_02, "CUR", temp_01));
		hir.add(new Quadruple("MOV", temp_02, "[[" + temp_02 + "]]"));
		hir.add(new Quadruple("IFF", temp_02, label_03));
		// B[CUR] := 1
		hir.addAll(addScore(5, 0, symbol));
		hir.add(new Quadruple("JMP", label_04));
		// B[CUR] := 0
		hir.add(new Quadruple("LABEL", label_03));
		hir.addAll(addScore(1, 1, symbol));
		hir.add(new Quadruple("LABEL", label_04));

		return hir;
	}

	private HIR addScore(int AScore, int BScore, SymbolTable symbol) {
		HIR hir = new HIR();
		String temp_01 = (new Temp(symbol)).toString();
		String temp_02 = (new Temp(symbol)).toString();

		hir.add(new Quadruple("MOV", temp_01, "" + AScore));
		hir.add(new Quadruple("ADD", temp_02, "1AScore", temp_01));
		hir.add(new Quadruple("MOV", "1AScore", temp_02));

		hir.add(new Quadruple("MOV", temp_01, "" + BScore));
		hir.add(new Quadruple("ADD", temp_02, "1BScore", temp_01));
		hir.add(new Quadruple("MOV", "1BScore", temp_02));

		return hir;
	}

	private HIR IDReplace(HIR hir, String my, String opponent) {
		HIR			replace = new HIR();
		Quadruple	temp	= null;

		for(int i = 0; i < hir.size(); ++i) {
			temp = new Quadruple(hir.get(i));
			// A || A[index]	==> my || my[index]
			// B || B[index]	==> opponent || opponent[index]
			if(temp.addr_01 != null) {
				if(temp.addr_01.charAt(0) == 'A' && 
					((temp.addr_01.length() == 1) || (temp.addr_01.charAt(1) == '['))) {
					temp.addr_01 = my + temp.addr_01.substring(1);
				} else if(temp.addr_01.charAt(0) == 'B' && 
					((temp.addr_01.length() == 1) || (temp.addr_01.charAt(1) == '['))) {
					temp.addr_01 = opponent + temp.addr_01.substring(1);
				}
			}
			if(temp.addr_02 != null) {
				if(temp.addr_02.charAt(0) == 'A' && 
					((temp.addr_02.length() == 1) || (temp.addr_02.charAt(1) == '['))) {
					temp.addr_02 = my + temp.addr_02.substring(1);
				} else if(temp.addr_02.charAt(0) == 'B' && 
					((temp.addr_02.length() == 1) || (temp.addr_02.charAt(1) == '['))) {
					temp.addr_02 = opponent + temp.addr_02.substring(1);
				}
			}
			if(temp.addr_03 != null) {
				if(temp.addr_03.charAt(0) == 'A' && 
					((temp.addr_03.length() == 1) || (temp.addr_03.charAt(1) == '['))) {
					temp.addr_03 = my + temp.addr_03.substring(1);
				} else if(temp.addr_03.charAt(0) == 'B' && 
					((temp.addr_03.length() == 1) || (temp.addr_03.charAt(1) == '['))) {
					temp.addr_03 = opponent + temp.addr_03.substring(1);
				}
			}
			replace.add(temp);
		}

		return replace;
	}

    public static void main(String args[]){
		try {
			if(args.length < 3 || !args[args.length-1].matches("[0-9]*")) {
				System.err.println("java Main <fpath fpath...> num");
			} else {
				Map<String, Integer> score = new HashMap<String, Integer>();
				for(int i = 0; i < args.length-1; ++i) {
					score.put(args[i], 0);
				}

				Integer[] Score = null;
				for(int i = 0; i < args.length-1; ++i) {
					for(int j = i+1; j < args.length-1; ++j) {
						Stategy	T1 = Parser.parse(args[i]);
						Stategy T2 = Parser.parse(args[j]);
						Integer N	= Integer.valueOf(args[args.length-1]);

						Battle battle = new Battle(T1, T2, N);

						Score = battle.Run();
						score.put(args[i], score.get(args[i]) + Score[0]);
						score.put(args[j], score.get(args[j]) + Score[1]);
					}
				}
				List<Map.Entry<String, Integer>> list = new ArrayList<Map.Entry<String, Integer>>(score.entrySet());
				Collections.sort(list, new Comparator<Map.Entry<String, Integer>>() {
					public int compare(Map.Entry<String, Integer> A, Map.Entry<String, Integer> B) {
						return (B.getValue().compareTo(A.getValue()));
					}
				});
 
				for(Map.Entry<String, Integer> mapping: list){
					System.out.println(mapping.getKey() + ": " + mapping.getValue()); 
				} 

			}
		} catch (BaseError e) {
			System.err.println(e.getMessage());
		}
    }

}
