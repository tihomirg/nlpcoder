package statistics.posttrees;

import java.util.List;

import statistics.Names;
import types.Type;

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
	
	@Override
	public String getPrefix() {
		throw new UnsupportedOperationException();
	}

	@Override
	public List<Type> getArgTypes() {
		throw new UnsupportedOperationException();
	}

	@Override
	public Type getReturnType() {
		throw new UnsupportedOperationException();
	}
}
