package statistics.posttrees;

import java.util.LinkedList;
import java.util.List;

import statistics.Names;
import synthesis.handlers.Handler;
import synthesis.handlers.HandlerFactory;
import types.Type;

import definitions.Declaration;

public class InstanceFieldAccess extends Expr {

	private Declaration decl;
	private Expr exp;

	public InstanceFieldAccess(Declaration decl) {
		this.decl = decl;
	}

	@Override
	public void addArgs(List<Expr> args) {
		this.exp = decl.isStatic() ? null : args.remove(0);
	}

	@Override
	protected String shortReadableRep() {
		return Names.InstanceFieldAccess+"("+decl.getLongName()+")";
	}
	
	@Override
	protected String shortRep() {
		return Names.InstanceFieldAccess+"("+decl.getId()+")";
	}	

	@Override
	public String getPrefix() {
		return Names.InstanceFieldAccess;
	}
	
	public Declaration getDecl() {
		return decl;
	}

	@Override
	public List<Type> getArgTypes() {
		return decl.isStatic() ? Type.EMPTY_TYPE_LIST : new LinkedList<Type>(){{add(decl.getReceiverType());}};
	}

	@Override
	public Type getReturnType() {
		return decl.getRetType();
	}

	@Override
	public List<Expr> getArgs() {
		return decl.isStatic() ? Expr.EMPTY_EXPR_LIST : new LinkedList<Expr>(){{add(exp);}};
	}

	@Override
	public synthesis.trees.Expr createRep(List<Integer> ids) {
		return new synthesis.trees.InstanceFieldAccess(decl, decl.isStatic() ? 0 : ids.get(0));
	}

	@Override
	public Handler getHandler(HandlerFactory hf) {
		return hf.getFieldAccessHandler();
	}
}
