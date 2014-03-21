package selection.types;

import java.util.LinkedList;
import java.util.List;

public class Variable extends Type {
	/**
	 * 
	 */
	private static final long serialVersionUID = 2312467056934914968L;

	private static final List<String> EMPTY_LIST = new LinkedList<String>();	
	
	protected Variable(String name) {
		super(name);
	}

	public String getName() {
		return head;
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
		return "Variable (" + head + ")";
	}

	@Override
	public Type apply(Substitution sub, TypeFactory factory) {
		if (this.equals(sub.getVariable())) {
			return sub.getType();
		} else return this;
	}

	@Override
	public Unifier unify(Type type, TypeFactory factory) {
		if (!type.contains(this)){
			List<Substitution> subs = new LinkedList<Substitution>();
			subs.add(new Substitution(this, type));
			return new Unifier(true, subs);
		} else return Unifier.False();
	}

	@Override
	public boolean contains(Type type) {
		return this.equals(type);
	}
	
	@Override
	public List<String> caracteristicWords() {
		return EMPTY_LIST;
	}
}
