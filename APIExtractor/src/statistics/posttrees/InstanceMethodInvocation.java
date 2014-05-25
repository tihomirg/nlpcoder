package statistics.posttrees;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import statistics.Names;
import synthesis.handlers.Handler;
import synthesis.handlers.HandlerFactory;
import types.Type;
import definitions.Declaration;

public class InstanceMethodInvocation extends Expr {

	private Declaration decl;
	private Expr exp;
	private List<Expr> args;	
	
	public InstanceMethodInvocation(Declaration decl) {
		this.decl = decl;
	}

	@Override
	public void addArgs(List<Expr> args) {
		this.exp = decl.isStatic() ? null : args.remove(0);
		this.args = args;
	}

	@Override
	public String shortReadableRep() {
		return Names.InstanceMethodInvocation+"("+decl.getLongName()+")";
	}
	
	@Override
	protected String shortRep() {
		return Names.InstanceMethodInvocation+"("+decl.getId()+")";
	}	
	
	@Override
	public String getPrefix() {
		return Names.InstanceMethodInvocation;
	}
	
	public Declaration getDecl() {
		return decl;
	}

	@Override
	public List<Type> getArgTypes() {
		List<Type> list = new LinkedList<Type>();
		if (!decl.isStatic()) list.add(decl.getReceiverType());
		list.addAll(Arrays.asList(decl.getArgTypes()));
		return list;
	}

	@Override
	public Type getReturnType() {
		return decl.getRetType();
	}

	@Override
	public List<Expr> getArgs() {
		List<Expr> list = new LinkedList<Expr>();
		if (!decl.isStatic()) list.add(exp);
		list.addAll(args);
		return list;
	}

	@Override
	public synthesis.trees.Expr createRep(List<Integer> ids) {
		if (decl.isStatic()){
			return new synthesis.trees.InstanceMethodInvocation(decl, 0 , ids);
		} else {
			LinkedList<Integer> indexes = new LinkedList<Integer>();
			indexes.addAll(ids);
			return new synthesis.trees.InstanceMethodInvocation(decl, indexes.remove(0), indexes);
		}
	}

	@Override
	public Handler getHandler(HandlerFactory hf) {
		return hf.getMethodInvocationHandler();
	}	
}
