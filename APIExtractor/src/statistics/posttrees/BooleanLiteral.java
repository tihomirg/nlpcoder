package statistics.posttrees;

import java.util.List;

import statistics.Names;

public class BooleanLiteral extends Expr {

	private boolean value;
	
	@Override
	public void addArgs(List<Expr> args) {
		String string = args.get(0).getString();
		value = Boolean.parseBoolean(string);
	}

	@Override
	protected String shortRep() {
		return Names.BooleanLiteral;
	}

	@Override
	protected String argsRep() {
		return Boolean.toString(value);
	}

	@Override
	public String getPrefix() {
		return Names.BooleanLiteral;
	}
	
	@Override
	public boolean isLiteral() {
		return true;
	}
}
