package Optimizer;

import java.util.*;

/**
 * LIR设计:
 * ----------------------------------------------------------------------------
 *	Option	| addr		| addr		| 说明
 * ---------+-----------+-----------+------------------------------------------
 *	MOV		| target	| source	| target = source
 * ---------+-----------+-----------+------------------------------------------
 *	ADD 	| target	| source	| target += source
 * ---------+-----------+-----------+------------------------------------------
 *	SUB 	| target	| source	| target -= source
 * ---------+-----------+-----------+------------------------------------------
 *	MUL 	| target	| source	| target *= source
 * ---------+-----------+-----------+------------------------------------------
 *	DIV 	| target	| source	| target /= source
 * ---------+-----------+-----------+------------------------------------------
 *	JGZ 	| target	| addr		| 如果target大于0, 则跳转到addr
 * ---------+-----------+-----------+------------------------------------------
 *	JEZ 	| target	| addr		| 如果target等于0, 则跳转到addr
 * ---------+-----------+-----------+------------------------------------------
 *	JLZ 	| target	| addr		| 如果target小于0, 则跳转到addr
 * ---------+-----------+-----------+------------------------------------------
 *	RANDOM	| target	| max		| 产生一个0到max的一个随机数, 
 *			|			|			| 并复制到target
 * ----------------------------------------------------------------------------
 *
 * LIR说明:
 *	1. addr的计算方式:
 *		a. 数字			==> 立即数
 *		b. 标识符		==> 标识符的地址
 *		c. [标识符]		==> [标识符的内容]
 *
 */
public class LIR {
	private ArrayList<Triad> __ir;

	public LIR() {
		__ir = new ArrayList<Triad>();
	}

	public LIR(LIR lir) {
		__ir = new ArrayList<Triad>(lir.__ir);
	}

	public boolean isEmpty() {
		return __ir.isEmpty();
	}

	public int size() {
		return __ir.size();
	}

	public Triad get(int index) {
		return __ir.get(index);
	}

	public Triad remove(int index) {
		return __ir.remove(index);
	}

	public void add(Triad ir) {
		__ir.add(ir);
	}

	public void addAll(LIR lir) {
		__ir.addAll(lir.__ir);
	}
}
