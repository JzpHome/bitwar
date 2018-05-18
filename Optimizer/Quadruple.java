package Optimizer;

public class Quadruple {
	public	String	option;
	public	String	addr_01, addr_02, addr_03;

	public Quadruple(String option) {
		this.option = new String(option);
		this.addr_01 = null;
		this.addr_02 = null;
		this.addr_03 = null;
	}

	public Quadruple(String option, String addr_01) {
		this.option = new String(option);
		this.addr_01 = new String(addr_01);
		this.addr_02 = null;
		this.addr_03 = null;
	}

	public Quadruple(String option, String addr_01, String addr_02) {
		this.option = new String(option);
		this.addr_01 = new String(addr_01);
		this.addr_02 = new String(addr_02);
		this.addr_03 = null;
	}

	public Quadruple(String option, String addr_01, String addr_02, String addr_03) {
		this.option = new String(option);
		this.addr_01 = new String(addr_01);
		this.addr_02 = new String(addr_02);
		this.addr_03 = new String(addr_03);
	}

	public Quadruple(Quadruple quadruple) {
		option = new String(quadruple.option);
		if(quadruple.addr_01 != null) {
			addr_01 = new String(quadruple.addr_01);
		} else {
			addr_01 = null;
		}
		if(quadruple.addr_02 != null) {
			addr_02 = new String(quadruple.addr_02);
		} else {
			addr_02 = null;
		}
		if(quadruple.addr_03 != null) {
			addr_03 = new String(quadruple.addr_03);
		} else {
			addr_03 = null;
		}
	}
}
