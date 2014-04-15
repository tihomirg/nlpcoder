package selection.types;

import java.util.LinkedList;
import java.util.List;

public class Variable extends Type {

	private static final long serialVersionUID = 2312467056934914968L;	

	protected Variable(String name) {
		super(name);
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
	
	@Override
	public List<String> getWords() {
		return EMPTY_STRING_LIST;
	}

	@Override
	protected List<Type> getInheritedTypes(TypeFactory factory) {
		return EMPTY_TYPE_LIST;
	}	
	
	@Override
	public List<Type> getCompatibleTypes(TypeFactory factory) {
		return EMPTY_TYPE_LIST;
	}

	@Override
	public Unifier isCompatible(Type type, TypeFactory factory) {
		if (type.isNoType() || type.isNullType()) return Unifier.True();
		else return this.unify(type, factory);
	}

	@Override
	public String getPrefix() {
		return this.name;
	}

	@Override
	public boolean isPrimitiveType() {
		return false;
	}

	@Override
	public boolean isNullType() {
		return false;
	}

	@Override
	public boolean isNoType() {
		return false;
	}

	@Override
	public boolean isVariable() {
		return true;
	}

	@Override
	public boolean isReferenceType() {
		return false;
	}

	@Override
	public boolean isPolymorphicType() {
		return false;
	}

	@Override
	public boolean isConstantType() {
		return false;
	}

	@Override
	public boolean isBoxedType() {
		return false;
	}

}
