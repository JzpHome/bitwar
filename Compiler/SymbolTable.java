package Compiler;

import java.util.Map;
import java.util.HashMap;

public class SymbolTable {
    private Map<String, Object> values;

	public SymbolTable() {
		values = new HashMap<String, Object>();
	}

    public boolean hasValue(String id) {
		return values.containsKey(id);
    }

    public Object getValue(String id) {
		return values.get(id);
    }

    public Object addValue(String id, Object value) {
		if(hasValue(id)) {
			return null;
		} else {
			return values.put(id, value);
		}
	}

    public Object setValue(String id, Object value) {
		if(hasValue(id)){
			return values.put(id, value);
		} else {
			return null;
		}
    }

    public Object delValue(String id) {
		return values.remove(id);
    }

	public void print() {
		for(Map.Entry<String, Object> entry: values.entrySet()) {
			System.out.println(entry.getKey() + ": " + entry.getValue());
		}
	}

	public static void main(String[] args) {
		SymbolTable values = new SymbolTable();
		values.addValue("CUR", 1);
		for(int i = 0; i < 10; ++i) {
			values.addValue("A[" + i + "]", i);
			values.addValue("B[" + i + "]", i);
		}
		values.print();
	}
}
