package BitwarVM;

import Error.*;
import java.util.*;
import Error.BitwarVM.*;

/*
 * VirtualMachine说明:
 *	2. 支持多重间接寻址
 */
public class VirtualMachine {
	// 指令指针寄存器
	private int IP;
	// 主存
	private Memory __memory;

	public VirtualMachine() {
		__memory = new Memory(64*1024);
	}

	public VirtualMachine(int memsize) {
		__memory = new Memory(memsize);
	}

	public String getMemory(int index) throws BaseError {
		return __memory.getMemory(index);
	}

	public void Load(String[] program, int IP) throws BaseError {
		this.IP = IP;
		for(int i = 0; i < program.length; ++i) {
			__memory.setMemory(IP+i, program[i]);
		}
	}

	public void Run() throws BaseError {
		/*__memory.print();
		System.out.println("\nIP: " + IP);*/
		while(!__memory.indexOf(IP).equals("HALT")) {
			if(__memory.indexOf(IP).equals("MOV")) {
				MOV(__memory.indexOf(IP+1), __memory.indexOf(IP+2));
				IP += 3;
			} else if(__memory.indexOf(IP).equals("ADD")) {
				ADD(__memory.indexOf(IP+1), __memory.indexOf(IP+2));
				IP += 3;
			} else if(__memory.indexOf(IP).equals("SUB")) {
				SUB(__memory.indexOf(IP+1), __memory.indexOf(IP+2));
				IP += 3;
			} else if(__memory.indexOf(IP).equals("MUL")) {
				MUL(__memory.indexOf(IP+1), __memory.indexOf(IP+2));
				IP += 3;
			} else if(__memory.indexOf(IP).equals("DIV")) {
				DIV(__memory.indexOf(IP+1), __memory.indexOf(IP+2));
				IP += 3;
			} else if(__memory.indexOf(IP).equals("RANDOM")) {
				RANDOM(__memory.indexOf(IP+1), __memory.indexOf(IP+2));
				IP += 3;
			} else if(__memory.indexOf(IP).equals("JGZ")) {
				JGZ(__memory.indexOf(IP+1), __memory.indexOf(IP+2));
			} else if(__memory.indexOf(IP).equals("JEZ")) {
				JEZ(__memory.indexOf(IP+1), __memory.indexOf(IP+2));
			} else if(__memory.indexOf(IP).equals("JLZ")) {
				JLZ(__memory.indexOf(IP+1), __memory.indexOf(IP+2));
			} else {
				throw (new VMError("指令错误",  "'" + __memory.indexOf(IP) + "'" + ": 未定义的指令"));
			}
			/*__memory.print();
			System.out.printf("\nIP: %d --> %d\n", IP-3, IP);*/
			/*Scanner input = new Scanner(System.in);
			input.nextLine();*/
		}
	}

	/*
	 * 数据传送指令(支持多重间接寻址);
	 *	1. MOV NUM1	NUM2		==> 把NUM2赋值到
	 *								地址为NUM1的主存
	 *
	 *	2. MOV [NUM1] NUM2		==> 把NUM2赋值到
	 *								地址为(地址为NUM1的主存的值)的主存
	 *
	 *	3. MOV NUM1 [NUM2]		==> 地址为NUM2的主存的值
	 *								赋值到地址为NUM1的主存
	 *
	 *	4. MOV [NUM1] [NUM2]	==> 地址为NUM2的主存的值
	 *								赋值到地址为(地址为NUM1的主存的值)的主存
	 */
	private void MOV(String target, String source) throws BaseError {
		// target
		Integer addr_01 = CalcAddress(target);
		// source
		String var = null;
		if(source.charAt(0) == '[') {
			// MOV 主存 主存
			Integer addr_02 = CalcAddress(source);
			var = __memory.getMemory(addr_02);
		} else {
			// MOV 主存 NUM
			var = source;
		}
		__memory.setMemory(addr_01, var);
	}

	/*
	 * ADD指令(支持多重间接寻址):
	 *	1. ADD [NUM1] [NUM2]	==> 地址为NUM1的主存的值
	 *								+= 地址为NUM2的主存的值
	 */
	private void ADD(String target, String source) throws BaseError {
		// <ADD> 主存 主存
		Integer addr_01 = CalcAddress(target);
		Integer addr_02 = CalcAddress(source);
		// target += source
		Integer var = Integer.valueOf(__memory.getMemory(addr_01));
		var += Integer.valueOf(__memory.getMemory(addr_02));
		__memory.setMemory(addr_01, String.valueOf(var));
	}

	/*
	 * SUB指令(支持多重间接寻址):
	 *	1. SUB [NUM1] [NUM2]	==> 地址为NUM1的主存的值
	 *								-= 地址为NUM2的主存的值
	 */
	private void SUB(String target, String source) throws BaseError {
		// <SUB> 主存 主存
		Integer addr_01 = CalcAddress(target);
		Integer addr_02 = CalcAddress(source);
		// target -= source
		Integer var = Integer.valueOf(__memory.getMemory(addr_01));
		var -= Integer.valueOf(__memory.getMemory(addr_02));
		__memory.setMemory(addr_01, String.valueOf(var));
	}

	/*
	 * MUL指令(支持多重间接寻址):
	 *	1. MUL [NUM1] [NUM2]	==> 地址为NUM1的主存的值
	 *								*= 地址为NUM2的主存的值
	 */
	private void MUL(String target, String source) throws BaseError {
		// <MUL> 主存 主存
		Integer addr_01 = CalcAddress(target);
		Integer addr_02 = CalcAddress(source);
		// target *= source
		Integer var = Integer.valueOf(__memory.getMemory(addr_01));
		var *= Integer.valueOf(__memory.getMemory(addr_02));
		__memory.setMemory(addr_01, String.valueOf(var));
	}

	/*
	 * DIV指令(支持多重间接寻址):
	 *	1. DIV [NUM1] [NUM2]	==> 地址为NUM1的主存的值
	 *								/= 地址为NUM2的主存的值
	 */
	private void DIV(String target, String source) throws BaseError {
		// <DIV> 主存 主存
		Integer addr_01 = CalcAddress(target);
		Integer addr_02 = CalcAddress(source);
		// target /= source
		Integer var = Integer.valueOf(__memory.getMemory(addr_01));
		var /= Integer.valueOf(__memory.getMemory(addr_02));
		__memory.setMemory(addr_01, String.valueOf(var));
	}

	/*
	 * JGZ指令(支持多重间接寻址):
	 *	1. JGZ [NUM1] NUM2		==> 地址为NUM1的主存的值 > 0 
	 *									--> IP = NUM2
	 *
	 *	2. JGZ [NUM1] [NUM2]	==> 地址为NUM1的主存的值 > 0 
	 *									--> IP = 地址为NUM2的内存的值
	 */
	private void JGZ(String target, String addr) throws BaseError {
		// target
		Integer var = null;
		if(target.matches("[0-9]*")) {
			var = Integer.valueOf(target);
		} else {
			Integer addr_01 = CalcAddress(target);
			var = Integer.valueOf(__memory.getMemory(addr_01));
		}
		// addr
		Integer addr_02 = null;
		if(addr.matches("[0-9]*")) {
			addr_02 = Integer.valueOf(addr);
		} else {
			addr_02 = CalcAddress(addr);
			addr_02 = Integer.valueOf(__memory.getMemory(addr_02));
		}

		if(var > 0) {
			IP = addr_02;
		} else {
			IP += 3;
		}
	}

	/*
	 * JEZ指令(支持多重间接寻址):
	 *	1. JEZ [NUM1] NUM2		==> 地址为NUM1的主存的值 = 0 
	 *									--> IP = NUM2
	 *
	 *	2. JEZ [NUM1] [NUM2]	==> 地址为NUM1的主存的值 = 0 
	 *									--> IP = 地址为NUM2的内存的值
	 */
	private void JEZ(String target, String addr) throws BaseError {
		// target
		Integer var = null;
		if(target.matches("[0-9]*")) {
			var = Integer.valueOf(target);
		} else {
			Integer addr_01 = CalcAddress(target);
			var = Integer.valueOf(__memory.getMemory(addr_01));
		}
		// addr
		Integer addr_02 = null;
		if(addr.matches("[0-9]*")) {
			addr_02 = Integer.valueOf(addr);
		} else {
			addr_02 = CalcAddress(addr);
			addr_02 = Integer.valueOf(__memory.getMemory(addr_02));
		}

		if(var == 0) {
			IP = addr_02;
		} else {
			IP += 3;
		}
	}

	/*
	 * JLZ指令(支持多重间接寻址):
	 *	1. JLZ [NUM1] NUM2		==> 地址为NUM1的主存的值 < 0 
	 *									--> IP = NUM2
	 *
	 *	2. JLZ [NUM1] [NUM2]	==> 地址为NUM1的主存的值 < 0 
	 *									--> IP = 地址为NUM2的内存的值
	 */
	private void JLZ(String target, String addr) throws BaseError {
		// target
		Integer var = null;
		if(target.matches("[0-9]*")) {
			var = Integer.valueOf(target);
		} else {
			Integer addr_01 = CalcAddress(target);
			var = Integer.valueOf(__memory.getMemory(addr_01));
		}
		// addr
		Integer addr_02 = null;
		if(addr.matches("[0-9]*")) {
			addr_02 = Integer.valueOf(addr);
		} else {
			addr_02 = CalcAddress(addr);
			addr_02 = Integer.valueOf(__memory.getMemory(addr_02));
		}

		if(var < 0) {
			IP = addr_02;
		} else {
			IP += 3;
		}
	}

	/*
	 * RANDOM指令(支持多重间接寻址):
	 *	4. RANDOM [NUM1] [NUM2]		==> 随机生成0到地址为NUM2的主存的值的数
	 *									赋值到地址为NUM1的主存
	 */
	private void RANDOM(String target, String max) throws BaseError {
		// NUM1 || [NUM1]
		Integer addr_01 = CalcAddress(target);
		Integer addr_02 = CalcAddress(max);
		// 
		Integer num = Integer.valueOf(__memory.getMemory(addr_02));
		Integer variable = (new Random()).nextInt(num);
		__memory.setMemory(addr_01, String.valueOf(variable));
	}

	/*
	 * 计算target的地址(直接寻址, 多重间接寻址):
	 *	1. [NUM]	==> 地址为(地址为NUM的主存的值)
	 *	
	 *	2. NUM		==> 地址为NUM
	 */
	private Integer CalcAddress(String target) throws BaseError {
		if(target.matches("\\[[0-9]+\\]")) {
			return Integer.valueOf(target.substring(1, target.length()-1));
		} else if(target.charAt(1) == '['){
			Integer addr = CalcAddress(target.substring(1, target.length()-1));
			return Integer.valueOf(__memory.getMemory(addr));
		} else {
			throw (new VMError("Address", target + ": 非法地址表达式"));
		}
	}

	public static void main(String[] args) {
		String[] exec = new String[51];
		// 测试程序
		int CS = 0;
		exec[CS+0] = "MOV";		// MOV [46] 39
		exec[CS+1] = "[46]";
		exec[CS+2] = "36";
		exec[CS+3] = "MOV";		// MOV [41] 999
		exec[CS+4] = "[41]";
		exec[CS+5] = "999";
		exec[CS+6] = "MOV";		// MOV [42] [41]
		exec[CS+7] = "[42]";
		exec[CS+8] = "[41]";
		exec[CS+9] = "ADD";		// ADD [41] [42]
		exec[CS+10] = "[41]";
		exec[CS+11] = "[42]";
		exec[CS+12] = "SUB";	// SUB [41] [42]
		exec[CS+13] = "[41]";
		exec[CS+14] = "[42]";		
		exec[CS+15] = "MUL";	// MUL [42] [41]
		exec[CS+16] = "[42]";
		exec[CS+17] = "[41]";
		exec[CS+18] = "DIV";	// DIV [42] [41]
		exec[CS+19] = "[42]";
		exec[CS+20] = "[41]";
		exec[CS+21] = "JGZ";	// JGZ [43] [46]
		exec[CS+22] = "[43]";
		exec[CS+23] = "[46]";
		exec[CS+24] = "JEZ";	// JEZ [43] [46]
		exec[CS+25] = "[43]";
		exec[CS+26] = "[46]";
		exec[CS+27] = "JLZ";	// JLZ [43] [46]
		exec[CS+28] = "[43]";
		exec[CS+29] = "[46]";
		exec[CS+30] = "RANDOM";	// RANDOM [43] [41]
		exec[CS+31] = "[43]";
		exec[CS+32] = "[41]";
		exec[CS+33] = "HALT";

		try {
			VirtualMachine vm = new VirtualMachine();

			for(int i = 0; i < 51; i += 3) {
				System.out.printf("%10s", exec[i] + " | ");
				System.out.printf("%10s", exec[i+1] + " | ");
				System.out.printf("%10s\n", exec[i+2]);
			}
			System.out.println();

			vm.Load(exec, 6);
			vm.Run();
		} catch (BaseError e) {
			System.err.println(e.getMessage());
		}
	}
}
