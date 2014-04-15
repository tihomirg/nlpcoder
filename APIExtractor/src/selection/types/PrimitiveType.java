package selection.types;

import java.util.LinkedList;
import java.util.List;

public class PrimitiveType extends Type {

	private static final long serialVersionUID = 5449804471090807580L;
	private List<Type> compatibleTypes;

	protected PrimitiveType(String name) {
		super(name);
	}
	
	public Type apply(Substitution sub, TypeFactory factory) {
		return this;
	}

	public Unifier unify(Type type, TypeFactory factory) {
		if (this.equals(type)) return Unifier.True();
		else if (type.isVariable()){
			return factory.getBoxedType(this).unify(this, factory);
		}
		
		return Unifier.False();
	}	

	public boolean contains(Type type) {
		return this.equals(type);
	}

	@Override
	protected List<Type> getCompatibleTypes(TypeFactory factory) {
		if (this.compatibleTypes == null) {
			this.compatibleTypes = new LinkedList<Type>();
			this.compatibleTypes.add(this);
			Type boxedType = factory.getBoxedType(this);
			this.compatibleTypes.addAll(boxedType.getInheritedTypes(factory));
		}
		return compatibleTypes;
	}

	@Override
	public Unifier isCompatible(Type type, TypeFactory factory) {
		if (type.isNoType()) return Unifier.True();
		else {
			if (type.isBoxedType() && type.getCompatibleTypes(factory).contains(this)) return Unifier.True();
			else {
			  return this.unify(type, factory);
			}
		}
	}

	@Override
	public boolean isPrimitiveType() {
		return true;
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
		return false;
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

	@Override
	protected List<Type> getInheritedTypes(TypeFactory factory) {
		return EMPTY_TYPE_LIST;
	}
}
