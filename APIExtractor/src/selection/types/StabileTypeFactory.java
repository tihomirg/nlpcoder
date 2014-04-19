package selection.types;

import java.util.List;

import definitions.ClassInfo;

public class StabileTypeFactory extends TypeFactory {
	
	public StabileTypeFactory(NameGenerator nameGen) {
		super(nameGen);
	}

	protected void addReferenceType(ReferenceType type){}

	public List<Type> getInheritedTypes(ReferenceType referenceType) {
		ClassInfo clazz = referenceType.getClassInfo();
		return clazz.getInstantiatedInheritedTypes(referenceType, this);
	}
	
	@Override
	public String toString() {
		return "primitive: " + primitive.values();
	}	

}
