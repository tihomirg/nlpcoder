package selection.types;

import java.util.List;

public class StabileTypeFactory extends TypeFactory {
	
	public StabileTypeFactory(NameGenerator nameGen) {
		super(nameGen);
	}

	protected void addReferenceType(ReferenceType type){}

	public List<Type> getInheritedTypes(ReferenceType referenceType) {
		return null;
	}
	
	@Override
	public String toString() {
		return "primitive: " + primitive.values();
	}	

}
