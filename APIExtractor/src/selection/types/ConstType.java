package selection.types;

import java.util.List;

import definitions.ClassInfo;

public class ConstType extends ReferenceType {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1898401502500691232L;

	public ConstType(String name) {
		super(name);
	}
	
	public ConstType(ClassInfo clazz) {
		super(clazz);
	}	
	
	public Type apply(Substitution sub, TypeFactory factory) {
		return this;
	}

	public Unifier unify(Type type, TypeFactory factory) {
		if (this.equals(type)) return Unifier.True();
		else if (type.isVariable()){
			return type.unify(this, factory);
		}
		
		return Unifier.False();
	}	

	public boolean contains(Type type) {
		return this.equals(type);
	}

	@Override
	public boolean isPolymorphicType() {
		return false;
	}

	@Override
	public boolean isConstantType() {
		return true;
	}

	@Override
	public boolean isBoxedType() {
		return false;
	}	
}
