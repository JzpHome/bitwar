package Backend;

import Optimizer.*;
import Error.Backend.TransError;

public class LIRtoExec {
	public static String[] translator(LIR lir, 
										SymbolTable symbol) throws TransError {
		symbol.setDS(lir.size()*3 + 1);
		int size = lir.size()*3 + 1 + symbol.size();

		String[] exec = new String[size+1];
		for(int i = 0, j = 0; i < lir.size(); ++i, j += 3) {
			exec[j] = new String(lir.get(i).option);
			exec[j+1] = CalcAddress(lir.get(i).addr_01, symbol);
			exec[j+2] = CalcAddress2(lir.get(i).addr_02, symbol);
		}
		exec[lir.size()*3] = new String("HALT");

		return exec;
	}

	private static String CalcAddress(String value, 
										SymbolTable symbol) throws TransError {
		if(value == null) {
			throw (new TransError("CalcAddress", value + " is a NullPointer"));
		} else if(value.matches("[0-9]+")) {
			throw (new TransError("CalcAddress", value + " is a number"));
		} else if(value.charAt(0) == '[') {
			value = value.substring(1, value.length()-1);
			value = CalcAddress(value, symbol);
			return ("[" + value + "]");
		} else {
			Integer addr = symbol.getValue(value) + symbol.DS();
			return ("[" + addr + "]");
		}
	}

	private static String CalcAddress2(String value, 
										SymbolTable symbol) throws TransError {
		if(value == null) {
			throw (new TransError("CalcAddress2", value + " is a NullPointer"));
		} else if(value.matches("[0-9]+")) {
			return (new String(value));
		} else if(value.charAt(0) == '[') {
			if(value.charAt(1) == '[') {
				value = value.substring(2, value.length()-2);
				Integer addr = symbol.getValue(value) + symbol.DS();
				return ("[[" + addr + "]]");
			} else {
				value = value.substring(1, value.length()-1);
				Integer addr = symbol.getValue(value) + symbol.DS();
				return ("" + addr);
			}
		} else {
			Integer addr = symbol.getValue(value) + symbol.DS();
			return ("[" + addr + "]");
		}
	}

	public static void main(String[] args) {
		try {
			// 添加必要变量
			SymbolTable symbol = new SymbolTable();
			symbol.addValue("A");
			symbol.addValue("B");

			// 测试LIRtoExec
			LIR lir = new LIR();
			// MOV || ADD || SUB || MUL || DIV
			lir.add(new Triad("MOV", "A", "1"));
			lir.add(new Triad("MOV", "B", "A"));
			lir.add(new Triad("ADD", "A", "B"));
			lir.add(new Triad("SUB", "B", "[A]"));
			lir.add(new Triad("MUL", "A", "B"));
			lir.add(new Triad("DIV", "B", "A"));
			// JGZ || JEZ || JLZ || RANDOM
			lir.add(new Triad("JGZ", "B", "A"));
			lir.add(new Triad("JEZ", "B", "A"));
			lir.add(new Triad("JLZ", "B", "A"));
			lir.add(new Triad("RANDOM", "A", "10"));

			String[] exec = translator(lir, symbol);
			for(int i = 0; i < exec.length; ++i) {
				System.out.println(exec[i]);
			}
		} catch (TransError e) {
			System.err.println(e.getMessage());
		}
	}
}
