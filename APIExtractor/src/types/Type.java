package types;

import java.io.Serializable;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public abstract class Type implements Serializable {

	private static final long serialVersionUID = -900979897015622962L;

	protected static final List<String> EMPTY_STRING_LIST = new LinkedList<String>();
	protected static final Set<Type> EMPTY_TYPE_SET = new HashSet<Type>();
	public static final List<Type> EMPTY_TYPE_LIST = new LinkedList<Type>();	
	//protected static final List<ReferenceType> EMPTY_REFERENCE_TYPE_LIST = new LinkedList<ReferenceType>();		

	protected static final List<Variable> EMPTY_VAR_LIST = new LinkedList<Variable>();
	
	protected String name;
	
	protected Type(){}
	
	protected Type(String name) {
		this.name = name;
	}

	//Compatible
	protected abstract Set<Type> getInheritedTypes(StabileTypeFactory factory);
	protected abstract Set<Type> getCompatibleTypes(StabileTypeFactory factory);
	public abstract Unifier checkCompatible(Type type, StabileTypeFactory factory);
	
	public boolean isCompatible(Type type, StabileTypeFactory factory) {
		return checkCompatible(type, factory).isSuccess();
	}
	
	//Type kind
	public abstract boolean isPrimitiveType();
	public abstract boolean isNullType();
	public abstract boolean isNoType();
	public abstract boolean isVoidType();
	public abstract boolean isVariable();
	public abstract boolean isReferenceType();	
	public abstract boolean isPolymorphicType();
	public abstract boolean isConstantType();
	public abstract boolean isBoxedType();
	
	
	//Unification
	public abstract Type apply(Substitution sub, TypeFactory factory);
	public abstract Unifier unify(Type type, TypeFactory factory);
	public abstract boolean contains(Type type);	
	
	public abstract List<Variable> getVariables();	
	
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
