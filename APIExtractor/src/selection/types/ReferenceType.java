package selection.types;

import java.util.LinkedList;
import java.util.List;

import definitions.ClassInfo;

public abstract class ReferenceType extends Type {

	private static final long serialVersionUID = -1016449819536737614L;

	protected ClassInfo clazz;
	protected List<ReferenceType> inheritedTypes;
	protected List<Type> compatibleTypes;	

	public ReferenceType(String name) {
		super(name);
	}
	
	public ReferenceType(String name, ClassInfo clazz) {
		super(name);
		this.clazz = clazz;
	}	
	
	public ClassInfo getClassInfo() {
		return clazz;
	}

	public void setClassInfo(ClassInfo clazz) {
		this.clazz = clazz;
	}
	
	public boolean hasClassInfo(){
		return this.clazz != null;
	}
	
	@Override
	protected List<ReferenceType> getInheritedTypes(StabileTypeFactory factory) {
		if(this.inheritedTypes == null){
			this.inheritedTypes = factory.getInheritedTypes(this);
		}
		return this.inheritedTypes;
	}
	
	@Override
	public List<Type> getCompatibleTypes(StabileTypeFactory factory) {
		if (this.compatibleTypes == null) {
			this.compatibleTypes = new LinkedList<Type>();
			this.compatibleTypes.add(this);
			this.compatibleTypes.addAll(getInheritedTypes(factory));
		}
		return compatibleTypes;
	}
	
	@Override
	public Unifier isCompatible(Type type, StabileTypeFactory factory) {
		if(type.isNoType() || type.isNullType()) return Unifier.True();
		else {
			List<Type> cTypes = type.getCompatibleTypes(factory);
			for (Type cType : cTypes) {
				Unifier unify = this.unify(cType, factory);
				if(unify.isSuccess()){
					return unify;
				}
			}
		}
		return Unifier.False();
	}	
	
	@Override
	public boolean isReferenceType() {
		return true;
	}
	
	@Override
	public boolean isPrimitiveType() {
		return false;
	}
	
	@Override
	public boolean isNoType() {
		return false;
	}

	@Override	
	public boolean isNullType(){
		return false;
	}

}
