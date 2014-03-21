package selection.types;

import java.util.LinkedList;
import java.util.List;

public class Const extends Type {
	/**
	 * 
	 */
	private static final long serialVersionUID = 2178934969598789226L;
	
	protected Const(String name) {
		super(name);
	}

	@Override
	public int hashCode() {
		return head.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		return this == obj;
	}

	@Override
	public String toString() {
		return "Const ("+ head + ")";
	}

	@Override
	public Type apply(Substitution sub, TypeFactory factory) {
		return this;
	}
	
	public Unifier unify(Type type, TypeFactory factory) {
		if (this.equals(type)) return Unifier.True();
		else if (type instanceof Variable){
			Variable var = (Variable) type;
			return var.unify(this, factory);
		}
		
		return Unifier.False();
	}

	@Override
	public boolean contains(Type type) {
		return this.equals(type);
	}

	@Override
	public List<String> caracteristicWords() {
		return new LinkedList<String>(){{add(shortName(head));}};
	}
	
	private static String shortName(String name) {
		return name.substring(name.lastIndexOf(".")+1);
	}
}
