package selection.types;

import java.util.LinkedList;
import java.util.List;

public class Variable extends Type {
	protected final String name;
	
	public Variable(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	@Override
	public int hashCode() {
		return name.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		return this == obj;
	}

	@Override
	public String toString() {
		return "Variable (" + name + ")";
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
}
