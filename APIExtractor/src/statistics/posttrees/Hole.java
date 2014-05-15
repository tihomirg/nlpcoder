package statistics.posttrees;

import java.util.List;

import statistics.Names;
import types.Type;

public class Hole extends Expr {

	private Type type;

	public Hole(Type type){
		this.type = type;
	}
	
	@Override
	public void addArgs(List<Expr> args) {
		// TODO Auto-generated method stub

	}

	@Override
	protected String shortRep() {
		return Names.Hole;
	}

	@Override
	protected String argsRep() {
		return "";
	}
	
	@Override
	public String getPrefix() {
		return Names.Hole;
	}

	@Override
	public List<Type> getArgTypes() {
		return Type.EMPTY_TYPE_LIST;
	}

	@Override
	public Type getReturnType() {
		return type;
	}

}
