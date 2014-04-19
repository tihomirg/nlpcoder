package selection.types;

import java.util.List;


public class NoType extends Type {

	private static final long serialVersionUID = 8943437847372860249L;

	private static final String NO_TYPE = "NoType";

	protected NoType() {
		super(NO_TYPE);
	}

	@Override
	public String toString() {
		return NO_TYPE;
	}

	@Override
	protected List<Type> getInheritedTypes(StabileTypeFactory factory) {
		return EMPTY_TYPE_LIST;
	}	
	
	@Override
	public List<Type> getCompatibleTypes(StabileTypeFactory factory) {
		return EMPTY_TYPE_LIST;
	}

	@Override
	public Unifier isCompatible(Type type, StabileTypeFactory factory) {
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
}
