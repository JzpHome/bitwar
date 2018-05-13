package Opitimizer;

import java.util.Map;
import java.util.HashMap;

public class SymbolTable {
	private static SymbolTable	_symbol = null;
    private Map<String, Object> __symbol;

	public static SymbolTable symbol() {
		if(_symbol == null) {
			_symbol = new SymbolTable();
		}
		return _symbol;
	}

    public boolean hasValue(String id) {
		return __symbol.containsKey(id);
    }

    public Object getValue(String id) {
		return __symbol.get(id);
    }

    public Object addValue(String id, Object value) {
		if(hasValue(id)) {
			return null;
		} else {
			return __symbol.put(id, value);
		}
	}

    public Object setValue(String id, Object value) {
		if(hasValue(id)){
			return __symbol.put(id, value);
		} else {
			return null;
		}
    }

    public Object delValue(String id) {
		return __symbol.remove(id);
    }

	public void print() {
		for(Map.Entry<String, Object> entry: __symbol.entrySet()) {
			System.out.println(entry.getKey() + ": " + entry.getValue());
		}
	}

	private SymbolTable() {
		__symbol = new HashMap<String, Object>();
	}

	public static void main(String[] args) {
		SymbolTable symbol = SymbolTable.symbol();
		symbol.addValue("CUR", 1);
		for(int i = 0; i < 10; ++i) {
			symbol.addValue("A[" + i + "]", i);
			symbol.addValue("B[" + i + "]", i);
		}
		symbol.print();
	}
}
