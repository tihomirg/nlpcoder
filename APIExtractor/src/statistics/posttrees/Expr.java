package statistics.posttrees;

import java.util.LinkedList;
import java.util.List;
import definitions.Declaration;
import synthesis.handlers.Handler;
import synthesis.handlers.HandlerFactory;
import types.Type;

public abstract class Expr {

	public static final List<Expr> EMPTY_EXPR_LIST = new LinkedList<Expr>();
	private double logProbability;

	public abstract void addArgs(List<Expr> args);

	public abstract List<Type> getArgTypes();
	public abstract Type getReturnType();
	
	public List<Type> getAllTypes(){
		LinkedList<Type> list = new LinkedList<Type>();
		list.addAll(getArgTypes());
		list.add(getReturnType());
		return list;
	}
	
	public String getString() {
		throw new UnsupportedOperationException();
	}

	public void setLogProbability(double logProbability) {
		this.logProbability = logProbability;
	}

	public double getLogProbability() {
		return logProbability;
	}

	public String toString(){
		return logProbability+" : "+shortRep();
	}

	public static String shortReps(Expr... args){
		String s = "";
		if (args.length > 0){
			s += args[0].shortRep();
			for (int i = 1; i < args.length; i++) {
				s+=","+args[i].shortRep();
			}
		}
		return s;
	}

	protected abstract String shortRep();

	protected abstract String argsRep();

	public abstract String getPrefix();

	public String getOperator() {
		throw new UnsupportedOperationException();
	}

	public boolean isLiteral(){
		return false;
	}

	public Declaration getDecl(){
		throw new UnsupportedOperationException();
	}

	public abstract List<Expr> getArgs();

	public abstract synthesis.trees.Expr createRep(List<Integer> ids);

	public abstract Handler getHandler(HandlerFactory hf);
}
