package statistics.posttrees;

import java.util.List;

import statistics.Names;
import synthesis.handlers.Handler;
import synthesis.handlers.HandlerFactory;
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

	@Override
	public List<Expr> getArgs() {
		throw new UnsupportedOperationException();
	}

	@Override
	public synthesis.trees.Expr createRep(List<Integer> ids) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Handler getHandler(HandlerFactory hf) {
		throw new UnsupportedOperationException();
	}
}
