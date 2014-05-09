package statistics.posttrees;

import java.util.List;

public class Assignment extends Expr {

	public String op;
	private Expr lexp;
	private Expr rexp;
	
	public Assignment(String op) {
		this.op = op;
	}

	public String getOp() {
		return op;
	}

	public void setOp(String op) {
		this.op = op;
	}

	@Override
	public void addArgs(List<Expr> args) {
		this.lexp = args.get(0);
		this.rexp = args.get(1);
	}
	
}
