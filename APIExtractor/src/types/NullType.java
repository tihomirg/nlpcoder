package types;

import java.util.List;
import java.util.Set;

public class NullType extends Type {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7584472897250915114L;	
	private static final String NULL_TYPE = "NullType";

	public NullType() {
		super(NULL_TYPE);
	}

	@Override
	protected Set<Type> getInheritedTypes(StabileTypeFactory factory) {
		return EMPTY_TYPE_SET;
	}	
	
	@Override
	protected Set<Type> getCompatibleTypes(StabileTypeFactory factory) {
		return EMPTY_TYPE_SET;
	}

	@Override
	public Unifier checkCompatible(Type type, StabileTypeFactory factory) {
		if(type.isReferenceType() || type.isNullType() || type.isNoType()) return Unifier.True();
		
		return Unifier.False();
	}

	@Override
	public boolean isPrimitiveType() {
		return false;
	}

	@Override
	public boolean isNullType() {
		return true;
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
	public Type apply(Substitution sub, TypeFactory factory) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Unifier unify(Type type, TypeFactory factory) {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean contains(Type type) {
		return false;
	}

	@Override
	public boolean isVoidType() {
		return false;
	}
}