package statistics.posttrees;

import java.util.List;

import statistics.Names;

public class Hole extends Expr {

	@Override
	public void addArgs(List<Expr> args) {
		// TODO Auto-generated method stub

	}

	@Override
	protected String shortRep() {
		return Names.Hole;
	}

	@Override
	protected String argsRep() {
		return "";
	}
	
	@Override
	public String getPrefix() {
		return Names.Hole;
	}

}
