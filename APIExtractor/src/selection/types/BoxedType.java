package selection.types;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import definitions.ClassInfo;

public class BoxedType extends ConstType {

	private static final long serialVersionUID = -1048533350084331176L;

	private PrimitiveType primitiveType;
	
	public BoxedType() {}
	
	public BoxedType(String name) {
		super(name);
	}

	public BoxedType(String name, ClassInfo clazz) {
		super(name, clazz);
	}
	
	private PrimitiveType primitiveType(TypeFactory factory){
		if (this.primitiveType == null){
			this.primitiveType = factory.getPrimitiveType(this);
		}
		return this.primitiveType;
	}
	
	@Override
	public Set<Type> getCompatibleTypes(StabileTypeFactory factory) {
		if (this.compatibleTypes == null) {
			this.compatibleTypes = new HashSet<Type>();
			this.compatibleTypes.add(this);
			this.compatibleTypes.add(primitiveType(factory));
			this.compatibleTypes.addAll(this.getInheritedTypes(factory));
		}
		return compatibleTypes;
	}	
	
	@Override
	public boolean isBoxedType() {
		return true;
	}		
}
