package selection.types;

import java.util.List;

public abstract class Type {
	
	public Type apply(List<Substitution> subs, TypeFactory factory){
		Type curr = this;
		for (Substitution sub : subs) {
			curr = curr.apply(sub, factory);
		}
		return curr;
	}
	
	public abstract Type apply(Substitution sub, TypeFactory factory);
	public abstract boolean contains(Type type);
	public abstract Unifier unify(Type type, TypeFactory factory);
}
