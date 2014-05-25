package statistics.posttrees;

import java.util.List;

import statistics.Names;
import synthesis.handlers.Handler;
import synthesis.handlers.HandlerFactory;
import types.Type;

//Fix bugs with StringExpr!

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
	public String shortReadableRep() {
		return string;
	}
	
	@Override
	protected String shortRep() {
		//throw new UnsupportedOperationException();
		return string;
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
		return Expr.EMPTY_EXPR_LIST;
	}

	@Override
	public synthesis.trees.Expr createRep(List<Integer> ids) {
		return new synthesis.trees.Hole(null);
	}

	@Override
	public Handler getHandler(HandlerFactory hf) {
		return hf.getHoleHandler();
	}
}
