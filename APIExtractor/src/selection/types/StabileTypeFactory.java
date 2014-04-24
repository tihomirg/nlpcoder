package selection.types;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import definitions.ClassInfo;

public class StabileTypeFactory extends TypeFactory {
	
	public StabileTypeFactory(NameGenerator nameGen) {
		super(nameGen);
	}

	protected void addReferenceType(ReferenceType type){}

	public Set<Type> getInheritedTypes(ReferenceType referenceType) {
		ClassInfo clazz = referenceType.getClassInfo();
		return clazz.getAllInstantiatedInheritedType(referenceType, this);
	}
	
	@Override
	public String toString() {
		return "primitive: " + primitive.values();
	}

}
