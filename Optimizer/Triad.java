package Optimizer;

public class Triad {
	public	String	option;
	public	String	addr_01, addr_02;

	public Triad(String option) {
		this.option = new String(option);
		this.addr_01 = null;
		this.addr_02 = null;
	}

	public Triad(String option, String addr_01) {
		this.option = new String(option);
		this.addr_01 = new String(addr_01);
		this.addr_02 = null;
	}

	public Triad(String option, String addr_01, String addr_02) {
		this.option = new String(option);
		this.addr_01 = new String(addr_01);
		this.addr_02 = new String(addr_02);
	}

	public Triad(Triad triad) {
		option = new String(triad.option);
		if(triad.addr_01 != null) {
			addr_01 = new String(triad.addr_01);
		} else {
			addr_01 = null;
		}
		if(triad.addr_02 != null) {
			addr_02 = new String(triad.addr_02);
		} else {
			addr_02 = null;
		}
	}
}
