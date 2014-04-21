package selection.types;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

public abstract class Type implements Serializable {

	private static final long serialVersionUID = -900979897015622962L;

	protected static final List<String> EMPTY_STRING_LIST = new LinkedList<String>();
	protected static final List<Type> EMPTY_TYPE_LIST = new LinkedList<Type>();	
	protected static final List<ReferenceType> EMPTY_REFERENCE_TYPE_LIST = new LinkedList<ReferenceType>();	
	
	
	protected String name;
	
	protected Type(String name) {
		this.name = name;
	}

	//Compatible
	protected abstract List<Type> getInheritedTypes(StabileTypeFactory factory);
	protected abstract List<Type> getCompatibleTypes(StabileTypeFactory factory);
	public abstract Unifier isCompatible(Type type, StabileTypeFactory factory);
	
	//Type kind
	public abstract boolean isPrimitiveType();
	public abstract boolean isNullType();
	public abstract boolean isNoType();
	public abstract boolean isVariable();
	public abstract boolean isReferenceType();	
	public abstract boolean isPolymorphicType();
	public abstract boolean isConstantType();
	public abstract boolean isBoxedType();
	
	
	//Unification
	public abstract Type apply(Substitution sub, TypeFactory factory);
	public abstract Unifier unify(Type type, TypeFactory factory);
	public abstract boolean contains(Type type);	
	
	public Type apply(List<Substitution> subs, TypeFactory factory){
		Type curr = this;
		for (Substitution sub : subs) {
			curr = curr.apply(sub, factory);
		}
		return curr;
	}	
	
	@Override
	public int hashCode() {
		return this.name.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		return this.hashCode() == obj.hashCode();
	}

	public List<String> getWords() {
		return new LinkedList<String>(){{add(shortName(name));}};
	}

	protected static String shortName(String name) {
		return name.substring(name.lastIndexOf(".")+1);
	}	
	
	public String getPrefix() {
		return this.name;
	}

	@Override
	public String toString() {
		return name;
	}
}
