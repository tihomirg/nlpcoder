package statistics.posttrees;

import java.util.List;

import statistics.Names;

public class StringExpr extends Expr {

	private String string;
	
	public StringExpr(String string) {
		this.string = string;
	}
	
	public String getString() {
		return string;
	}

	public void setString(String string) {
		this.string = string;
	}

	@Override
	public void addArgs(List<Expr> args) {

	}

	@Override
	protected String shortRep() {
		return string;
	}

	@Override
	protected String argsRep() {
		return "";
	}

}
