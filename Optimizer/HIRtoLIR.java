package Optimizer;

import java.util.*;

public class HIRtoLIR {
	public static LIR translator(HIR hir, SymbolTable symbol) {
		LIR lir = new LIR();
		Map<String, String> label = new HashMap<String, String>();

		Quadruple quadruple = null;
		for(int i = 0; i < hir.size(); ++i) {
			quadruple = new Quadruple(hir.get(i));
			if(quadruple.option.equals("LABEL")) {
				label.put(quadruple.addr_01, String.valueOf(lir.size()*3));
			} else {
				lir.addAll(translator(quadruple, symbol));
			}
		}

		Triad traid = null;
		for(int i = 0; i < lir.size(); ++i) {
			traid = lir.get(i);
			if(traid.option.matches("J(.*)Z")) {
				if(traid.addr_02.matches("[0-9]+")) {
					Integer addr = i + Integer.valueOf(traid.addr_02);
					traid.addr_02 = String.valueOf(addr*3);
				} else {
					traid.addr_02 = label.get(traid.addr_02);
				}
			}
		}

		return lir;
	}

	private static LIR translator(Quadruple quadruple, SymbolTable symbol) {
		String addr_01 = quadruple.addr_01;
		String addr_02 = quadruple.addr_02;
		String addr_03 = quadruple.addr_03;

		if(quadruple.option.equals("MOV")) {
			// <MOV> target source
			return MOV(addr_01, addr_02, symbol);
		} else if(quadruple.option.equals("ADD")) {
			// <ADD> target source
			return ADD(addr_01, addr_02, addr_03, symbol);
		} else if(quadruple.option.equals("SUB")) {
			// <SUB> target source
			return SUB(addr_01, addr_02, addr_03, symbol);
		} else if(quadruple.option.equals("MUL")) {
			// <MUL> target source
			return MUL(addr_01, addr_02, addr_03, symbol);
		} else if(quadruple.option.equals("DIV")) {
			// <DIV> target source
			return DIV(addr_01, addr_02, addr_03, symbol); 
		} else if(quadruple.option.equals("GREAT")) {
			// <GREAT> target A B
			return GREAT(addr_01, addr_02, addr_03, symbol);
		} else if(quadruple.option.equals("EQUAL")) {
			// <EQUAL> target A B
			return EQUAL(addr_01, addr_02, addr_03, symbol);
		} else if(quadruple.option.equals("LESS")) {
			// <LESS> target A B
			return LESS(addr_01, addr_02, addr_03, symbol);
		} else if(quadruple.option.equals("AND")) {
			// <AND> target A B
			return AND(addr_01, addr_02, addr_03, symbol);
		} else if(quadruple.option.equals("OR")) {
			// <OR> target A B
			return OR(addr_01, addr_02, addr_03, symbol);
		} else if(quadruple.option.equals("JMP")) {
			// <JMP> <LABEL>
			return JMP(addr_01, symbol);
		} else if(quadruple.option.equals("RETURN")) {
			// <RETURN> result
			return RETURN(addr_01, symbol);
		} else if(quadruple.option.equals("IFF")) {
			// <IFF> condition <LABEL>
			return IFF(addr_01, addr_02, symbol);
		} else {
			// <RANDOM> target min max
			return RANDOM(addr_01, addr_02, addr_03, symbol);
		}
	}

	private static LIR MOV(String addr_01, String addr_02, 
							SymbolTable symbol) {
		LIR lir = new LIR();

		lir.add(new Triad("MOV", addr_01, addr_02));

		return lir;
	}

	private static LIR ADD(String addr_01, String addr_02, String addr_03, 
							SymbolTable symbol) {
		LIR lir = new LIR();

		lir.add(new Triad("MOV", addr_01, addr_02));
		lir.add(new Triad("ADD", addr_01, addr_03));

		return lir;
	}

	private static LIR SUB(String addr_01, String addr_02, String addr_03, 
							SymbolTable symbol) {
		LIR lir = new LIR();

		lir.add(new Triad("MOV", addr_01, addr_02));
		lir.add(new Triad("SUB", addr_01, addr_03));

		return lir;
	}

	private static LIR MUL(String addr_01, String addr_02, String addr_03, 
							SymbolTable symbol) {
		LIR lir = new LIR();

		lir.add(new Triad("MOV", addr_01, addr_02));
		lir.add(new Triad("MUL", addr_01, addr_03));

		return lir;
	}

	private static LIR DIV(String addr_01, String addr_02, String addr_03, 
							SymbolTable symbol) {
		LIR lir = new LIR();

		lir.add(new Triad("MOV", addr_01, addr_02));
		lir.add(new Triad("DIV", addr_01, addr_03));

		return lir;
	}

	private static LIR GREAT(String addr_01, String addr_02, String addr_03, 
								SymbolTable symbol) {
		LIR lir = new LIR();

		// false
		String temp = (new Temp(symbol)).toString();
		lir.add(new Triad("MOV", temp, "0"));

		// addr_02 > addr_03	==> addr_02-addr_03 > 0
		lir.add(new Triad("MOV", addr_01, addr_02));
		lir.add(new Triad("SUB", addr_01, addr_03));
		lir.add(new Triad("JGZ", addr_01, ""+3));
		// addr_02-addr_03 =< 0	==> addr_01 = 0
		lir.add(new Triad("MOV", addr_01, "0"));
		lir.add(new Triad("JEZ", temp, ""+2));
		// addr_02-addr_03 > 0	==> addr_01 = 1
		lir.add(new Triad("MOV", addr_01, "1"));

		return lir;
	}

	private static LIR EQUAL(String addr_01, String addr_02, String addr_03, 
								SymbolTable symbol) {
		LIR lir = new LIR();

		// false
		String temp = (new Temp(symbol)).toString();
		lir.add(new Triad("MOV", temp, "0"));

		// addr_02 < addr_03	==> addr_02-addr_03 < 0
		lir.add(new Triad("MOV", addr_01, addr_02));
		lir.add(new Triad("SUB", addr_01, addr_03));
		lir.add(new Triad("JEZ", addr_01, ""+3));
		// addr_02-addr_03 >= 0	==> addr_01 = 0
		lir.add(new Triad("MOV", addr_01, "0"));
		lir.add(new Triad("JEZ", temp, ""+2));
		// addr_02-addr_03 < 0	==> addr_01 = 1
		lir.add(new Triad("MOV", addr_01, "1"));

		return lir;
	}

	private static LIR LESS(String addr_01, String addr_02, String addr_03, 
								SymbolTable symbol) {
		LIR lir = new LIR();

		// false
		String temp = (new Temp(symbol)).toString();
		lir.add(new Triad("MOV", temp, "0"));

		// addr_02 < addr_03	==> addr_02-addr_03 < 0
		lir.add(new Triad("MOV", addr_01, addr_02));
		lir.add(new Triad("SUB", addr_01, addr_03));
		lir.add(new Triad("JLZ", addr_01, ""+3));
		// addr_02-addr_03 >= 0	==> addr_01 = 0
		lir.add(new Triad("MOV", addr_01, "0"));
		lir.add(new Triad("JEZ", temp, ""+2));
		// addr_02-addr_03 < 0	==> addr_01 = 1
		lir.add(new Triad("MOV", addr_01, "1"));

		return lir;
	}

	private static LIR AND(String addr_01, String addr_02, String addr_03, 
							SymbolTable symbol) {
		LIR lir = new LIR();

		// false
		String temp = (new Temp(symbol)).toString();
		lir.add(new Triad("MOV", temp, "0"));

		// A == 0 
		lir.add(new Triad("JEZ", addr_02, ""+4));
		// B == 0
		lir.add(new Triad("JEZ", addr_03, ""+3));
		lir.add(new Triad("MOV", addr_01, "1"));
		lir.add(new Triad("JEZ", temp, ""+2));
		// A == 0 && B == 0
		lir.add(new Triad("MOV", addr_01, "0"));

		return lir;
	}

	private static LIR OR(String addr_01, String addr_02, String addr_03, 
							SymbolTable symbol) {
		LIR lir = new LIR();

		// false
		String temp = (new Temp(symbol)).toString();
		lir.add(new Triad("MOV", temp, "0"));

		// A == 0
		lir.add(new Triad("JEZ", addr_02, ""+1));
		// B == 0
		lir.add(new Triad("JEZ", addr_03, ""+3));
		lir.add(new Triad("MOV", addr_01, "1"));
		lir.add(new Triad("JEZ", temp, ""+2));
		// A == 0 && B == 0
		lir.add(new Triad("MOV", addr_01, "0"));

		return lir;
	}

	private static LIR JMP(String addr_01, SymbolTable symbol) {
		LIR lir = new LIR();

		// false
		String temp = (new Temp(symbol)).toString();
		lir.add(new Triad("MOV", temp, "0"));

		lir.add(new Triad("JEZ", temp, addr_01));

		return lir;
	}

	private static LIR RETURN(String addr_01, SymbolTable symbol) {
		LIR lir = new LIR();

		lir.add(new Triad("MOV", "1RET", addr_01));

		return lir;
	}

	private static LIR IFF(String addr_01, String addr_02, SymbolTable symbol) {
		LIR lir = new LIR();

		lir.add(new Triad("JEZ", addr_01, addr_02));

		return lir;
	}

	private static LIR RANDOM(String addr_01, String addr_02, String addr_03, 
								SymbolTable symbol) {
		LIR lir = new LIR();

		// <RANDOM> addr_01 ([addr_03]-[addr_02])
		lir.add(new Triad("MOV", addr_01, addr_03));
		lir.add(new Triad("SUB", addr_01, addr_02));
		lir.add(new Triad("RANDOM", addr_01, addr_01));
		// <ADD> addr_01 addr_02
		lir.add(new Triad("ADD", addr_01, addr_02));

		return lir;
	}

	public static void main(String[] args) {
		HIR hir = new HIR();
		Random random = new Random();

		// 测试HIRtoLIR
		// MOV || ADD || SUB || MUL || DIV
		hir.add(new Quadruple("LABEL", "1L"));
		hir.add(new Quadruple("MOV", "target", "source"));
		hir.add(new Quadruple("ADD", "T" + random.nextInt(100), 
									"A" + random.nextInt(100), 
									"B" + random.nextInt(100)));
		hir.add(new Quadruple("SUB", "T" + random.nextInt(100), 
									"A" + random.nextInt(100), 
									"B" + random.nextInt(100)));
		hir.add(new Quadruple("MUL", "T" + random.nextInt(100), 
									"A" + random.nextInt(100), 
									"B" + random.nextInt(100)));
		hir.add(new Quadruple("DIV", "T" + random.nextInt(100), 
									"A" + random.nextInt(100), 
									"B" + random.nextInt(100)));
		// GREAT || EQUAL || LESS
		hir.add(new Quadruple("LABEL", "2L"));
		hir.add(new Quadruple("GREAT", "T" + random.nextInt(100), 
									"A" + random.nextInt(100), 
									"B" + random.nextInt(100)));
		hir.add(new Quadruple("EQUAL", "T" + random.nextInt(100), 
									"A" + random.nextInt(100), 
									"B" + random.nextInt(100)));
		hir.add(new Quadruple("LESS", "T" + random.nextInt(100), 
									"A" + random.nextInt(100), 
									"B" + random.nextInt(100)));
		// AND || OR
		hir.add(new Quadruple("LABEL", "3L"));
		hir.add(new Quadruple("AND", "T" + random.nextInt(100), 
									"A" + random.nextInt(100), 
									"B" + random.nextInt(100)));
		hir.add(new Quadruple("OR", "T" + random.nextInt(100), 
									"A" + random.nextInt(100), 
									"B" + random.nextInt(100)));
		// JMP || RETURN || IFF || RANDOM
		hir.add(new Quadruple("LABEL", "4L"));
		hir.add(new Quadruple("JMP", "2L"));
		hir.add(new Quadruple("RETURN", "R" + random.nextInt(100)));
		hir.add(new Quadruple("IFF", 
									"C" + random.nextInt(100), 
									"3L"));
		hir.add(new Quadruple("RANDOM", 
									"T" + random.nextInt(50), 
									"R" + random.nextInt(50),
									"R" + random.nextInt(50)));

		LIR lir = translator(hir, new SymbolTable());
		for(int i = 0; i < lir.size(); ++i) {
			Triad temp = lir.get(i);
			System.out.printf("%3d", i);
			System.out.printf("%10s", temp.option);
			System.out.printf("%10s", temp.addr_01);
			System.out.printf("%10s\n", temp.addr_02);
		}
		System.out.println("-------------------------------------------------------");
	}
}
