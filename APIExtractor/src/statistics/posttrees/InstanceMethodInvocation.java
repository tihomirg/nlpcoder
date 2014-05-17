package statistics.posttrees;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import statistics.Names;
import statistics.handlers.Handler;
import statistics.handlers.HandlerFactory;
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
	protected String shortRep() {
		return Names.InstanceMethodInvocation+"("+decl.getLongName()+")";
	}

	@Override
	protected String argsRep() {
		Expr[] exprs = null;
		if (decl.isStatic()){
			exprs = args.toArray(new Expr[args.size()]);
		} else {
			exprs = new Expr[args.size()+1];
			exprs[0] = exp;
			System.arraycopy(args.toArray(new Expr[args.size()]), 0, exprs, 1, args.size());			
		}
		return shortReps(exprs);
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
		return new synthesis.trees.InstanceMethodInvocation(decl, ids);
	}

	@Override
	public Handler getHandler(HandlerFactory hf) {
		return hf.getMethodInvocationHandler();
	}	
}
