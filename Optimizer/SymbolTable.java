package Optimizer;

import java.util.*;

public class SymbolTable {
	// 基本属性
	private Integer					__DS, __count;
	private Map<String, Integer>	__address;

	public SymbolTable() {
		__DS = 0;
		__count = 0;
		__address = new HashMap<String, Integer>();
	}

	// Variable
    public boolean hasValue(String id) {
		return __address.containsKey(id);
    }
    public Integer addValue(String id) {
		if(hasValue(id)) {
			return null;
		} else {
			Integer addr = __count;
			__address.put(id, __count++);
			return (new Integer(addr));
		}
	}
    public Integer addValue(String id, int len) {
		if(hasValue(id)) {
			return null;
		} else {
			Integer arr_head = __count;
			__address.put(id, __count);
			for(int i = 0; i < len; ++i) {
				__address.put(id + "[" + i + "]", new Integer(__count++));
			}
			return arr_head;
		}
	}
	public Integer getValue(String var) {
		if(hasValue(var)) {
			return __address.get(var);
		} else {
			return null;
		}
	}

	// Others
	public void setDS(int DS) {
		__DS = DS;
	}
	public Integer DS() {
		return __DS;
	}
	public Integer size() {
		return __count;
	}
	public void print() {
		for(Map.Entry<String, Integer> entry: __address.entrySet()) {
			System.out.printf("%5s: %d\n", entry.getKey(), entry.getValue());
		}
	}

	public static void main(String[] args) {
		SymbolTable symbol = new SymbolTable();
		symbol.addValue("CUR");
		symbol.addValue("A", 10);
		symbol.addValue("B", 10);

		symbol.print();
	}
}
