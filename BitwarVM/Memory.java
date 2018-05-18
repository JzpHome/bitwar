package BitwarVM;

import Error.BitwarVM.MemoryError;

public class Memory {
	// 主存
	private String[] __memory;

	public Memory(int memsize) {
		__memory = new String[memsize];
		for(int i = 0; i < __memory.length; ++i) {
			__memory[i] = "0";
		}
	}

	public String indexOf(Integer index) throws MemoryError {
		if(index < size()) {
			return __memory[index];
		} else {
			throw (new MemoryError("Memory", "out of Memory"));
		}
	}

	public String getMemory(Integer addr) throws MemoryError {
		return indexOf(addr);
	}

	public String setMemory(Integer addr, String var) throws MemoryError {
		if(addr < size()) {
			if(var != null) {
				__memory[addr] = new String(var);
			} else {
				__memory[addr] = new String("0");
			}
			return __memory[addr];
		} else {
			throw (new MemoryError("Memory", "out of Memory"));
		}
	}

	public int size() {
		return __memory.length;
	}

	public void print() {
		for(int i = 0; i < __memory.length; ++i) {
			if(i%6 == 0) {
				System.out.println();
			}
			System.out.printf("%-12s |", i + ": " + __memory[i]);
		}
	}
}
