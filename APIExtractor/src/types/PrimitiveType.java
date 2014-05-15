package types;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class PrimitiveType extends Type {

	private static final long serialVersionUID = 5449804471090807580L;
	private Set<Type> compatibleTypes;
	private BoxedType boxedType;

	public PrimitiveType(){}
	
	public PrimitiveType(String name) {
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
	public List<Variable> getVariables() {
		return EMPTY_VAR_LIST;
	}

	@Override
	public Set<Type> getCompatibleTypes(StabileTypeFactory factory) {
		if (this.compatibleTypes == null) {
			this.compatibleTypes = new HashSet<Type>();
			this.compatibleTypes.add(this);
			BoxedType boxedType = boxedType(factory);
			this.compatibleTypes.add(boxedType);
			this.compatibleTypes.addAll(boxedType.getInheritedTypes(factory));
		}
		return compatibleTypes;
	}

	@Override
	public Unifier checkCompatible(Type type, StabileTypeFactory factory) {
		if (type.isNullType() || type.isVoidType()) return Unifier.False();
		
		if (type.isNoType()) return Unifier.True();
		
	    if (type.isBoxedType() && type.getCompatibleTypes(factory).contains(this)) return Unifier.True();
			
	    return this.unify(type, factory);
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
	protected Set<Type> getInheritedTypes(StabileTypeFactory factory) {
		return EMPTY_TYPE_SET;
	}

	@Override
	public boolean isVoidType() {
		return false;
	}
}
