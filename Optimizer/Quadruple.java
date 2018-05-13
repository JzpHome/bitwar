package Optimizer;

// 前端
import Frontend.Statement.*;
import Frontend.Statement.Expression.*;

public class Quadruple {
	public	String		option;
	public	String		address_01, address_02, address_03;

	public Quadruple(String op) {
		option = new String(op);
		address_01 = null;
		address_02 = null;
		address_03 = null;
	}

	public Quadruple(String op, String addr_01) {
		option = new String(op);
		address_01 = new String(addr_01);
		address_02 = null;
		address_03 = null;
	}

	public Quadruple(String op, String addr_01, String addr_02) {
		option = new String(op);
		address_01 = new String(addr_01);
		address_02 = new String(addr_02);
		address_03 = null;
	}

	public Quadruple(String op, String addr_01, String addr_02, String addr_03) {
		option = new String(op);
		address_01 = new String(addr_01);
		address_02 = new String(addr_02);
		address_03 = new String(addr_03);
	}
}
