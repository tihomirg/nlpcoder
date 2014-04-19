package selection.types;

import java.util.LinkedList;
import java.util.List;

public class PrimitiveType extends Type {

	private static final long serialVersionUID = 5449804471090807580L;
	private List<Type> compatibleTypes;
	private BoxedType boxedType;

	protected PrimitiveType(String name) {
		super(name);
	}
	
	public Type apply(Substitution sub, TypeFactory factory) {
		return this;
	}

	public Unifier unify(Type type, TypeFactory factory) {
		if (this.equals(type)) return Unifier.True();
		else if (type.isVariable()){
			return boxedType(factory).unify(type, factory);
		}
		
		return Unifier.False();
	}

	private BoxedType boxedType(TypeFactory factory) {
		if (this.boxedType == null){
			this.boxedType = factory.getBoxedType(this);
		}
		return boxedType;
	}	

	public boolean contains(Type type) {
		return this.equals(type);
	}

	@Override
	protected List<Type> getCompatibleTypes(StabileTypeFactory factory) {
		if (this.compatibleTypes == null) {
			this.compatibleTypes = new LinkedList<Type>();
			this.compatibleTypes.add(this);
			this.compatibleTypes.addAll(boxedType(factory).getInheritedTypes(factory));
		}
		return compatibleTypes;
	}

	@Override
	public Unifier isCompatible(Type type, StabileTypeFactory factory) {
		if (type.isNoType()) return Unifier.True();
		else {
			if (type.isNullType()) return Unifier.False();
			
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
	protected List<Type> getInheritedTypes(StabileTypeFactory factory) {
		return EMPTY_TYPE_LIST;
	}
}
