package statistics.posttrees;

import java.util.Arrays;
import java.util.List;
import statistics.Names;
import synthesis.handlers.Handler;
import synthesis.handlers.HandlerFactory;
import types.Type;

import definitions.Declaration;

public class ConstructorInvocation extends Expr {

	private Declaration decl;
	private List<Expr> args;

	public ConstructorInvocation(Declaration decl) {
		this.decl = decl;
	}

	@Override
	public void addArgs(List<Expr> args) {		
		this.args = args;
	}

	@Override
	protected String shortReadableRep() {
		return Names.ConstructorInvocation+"("+decl.getLongName()+")";
	}
	
	@Override
	protected String shortRep() {
		return Names.ConstructorInvocation+"("+decl.getId()+")";
	}	

	@Override
	public String getPrefix() {
		return Names.ConstructorInvocation;
	}
	
	public Declaration getDecl() {
		return decl;
	}

	@Override
	public List<Type> getArgTypes() {
		return Arrays.asList(decl.getArgTypes());
	}

	@Override
	public Type getReturnType() {
		return decl.getRetType();
	}

	@Override
	public List<Expr> getArgs() {
		return args;
	}

	@Override
	public synthesis.trees.Expr createRep(List<Integer> ids) {
		return new synthesis.trees.ConstructorInvocation(decl, ids);
	}

	@Override
	public Handler getHandler(HandlerFactory hf) {
		return hf.getConstructorInvocationHandler();
	}

}
