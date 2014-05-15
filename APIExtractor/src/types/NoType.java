package types;

import java.util.List;
import java.util.Set;


public class NoType extends Type {

	private static final long serialVersionUID = 8943437847372860249L;

	private static final String NO_TYPE = "NoType";

	public NoType() {
		super(NO_TYPE);
	}
	
	@Override
	protected Set<Type> getInheritedTypes(StabileTypeFactory factory) {
		return EMPTY_TYPE_SET;
	}	
	
	@Override
	public Set<Type> getCompatibleTypes(StabileTypeFactory factory) {
		return EMPTY_TYPE_SET;
	}

	@Override
	public Unifier checkCompatible(Type type, StabileTypeFactory factory) {
		if(type.isVoidType()) return Unifier.False();
		
		return Unifier.True();
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
		return true;
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
	public List<Variable> getVariables() {
		return EMPTY_VAR_LIST;
	}

	@Override
	public boolean isVoidType() {
		return false;
	}
}
