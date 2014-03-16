package selection.types;

import java.io.Serializable;
import java.util.List;

public abstract class Type implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -900979897015622962L;

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

	public abstract List<String> caracteristicWords();
}
