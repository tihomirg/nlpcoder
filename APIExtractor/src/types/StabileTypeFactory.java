package types;

import java.util.Set;

import definitions.ClassInfo;

public class StabileTypeFactory extends TypeFactory {
	
	public StabileTypeFactory(NameGenerator nameGen) {
		super(nameGen);
	}

	protected void addReferenceType(ReferenceType type){}

	public Set<Type> getInheritedTypes(ReferenceType referenceType) {
		ClassInfo clazz = referenceType.getClassInfo();
		
		if (clazz != null) return clazz.getAllInstantiatedInheritedType(referenceType, this);
		else return Type.EMPTY_TYPE_SET;
	}
	
	@Override
	public String toString() {
		return "primitive: " + primitive.values();
	}

}
