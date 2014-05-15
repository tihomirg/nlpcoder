package statistics.posttrees;

import java.util.LinkedList;
import java.util.List;

import statistics.Names;
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
	protected String shortRep() {
		return Names.InstanceFieldAccess+"("+decl.getLongName()+")";
	}

	@Override
	protected String argsRep() {
		return decl.isStatic() ? "" : shortReps(exp);
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
}
