package selection.types;

import java.util.List;
import java.util.Set;

public class VoidType extends Type {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1235629510779552691L;

	public VoidType(){
		super("voidType");
	}
	
	@Override
	protected Set<Type> getInheritedTypes(StabileTypeFactory factory) {
		return EMPTY_TYPE_LIST;
	}	
	
	@Override
	protected Set<Type> getCompatibleTypes(StabileTypeFactory factory) {
		return EMPTY_TYPE_LIST;
	}

	@Override
	public Unifier checkCompatible(Type type, StabileTypeFactory factory) {
		return Unifier.False();
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
		return true;
	}

}
