package selection.types;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import definitions.ClassInfo;

public abstract class ReferenceType extends Type {

	private static final long serialVersionUID = -1016449819536737614L;

	protected ClassInfo clazz;
	protected Set<Type> inheritedTypes;
	protected Set<Type> compatibleTypes;	

	public ReferenceType(){}

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
	protected Set<Type> getInheritedTypes(StabileTypeFactory factory) {
		if(this.inheritedTypes == null){
			this.inheritedTypes = factory.getInheritedTypes(this);
		}
		return this.inheritedTypes;
	}

	@Override
	public Set<Type> getCompatibleTypes(StabileTypeFactory factory) {
		if (this.compatibleTypes == null) {
			this.compatibleTypes = new HashSet<Type>();
			this.compatibleTypes.add(this);
			this.compatibleTypes.addAll(getInheritedTypes(factory));
		}
		return compatibleTypes;
	}

	@Override
	public Unifier checkCompatible(Type type, StabileTypeFactory factory) {
		if(type.isVoidType()) return Unifier.False();

		if(type.isNoType() || type.isNullType()) return Unifier.True();
		
		Set<Type> cTypes = type.getCompatibleTypes(factory);
		for (Type cType : cTypes) {
			Unifier unifier = this.unify(cType, factory);
			if(unifier.isSuccess()){
				return unifier;
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

	@Override
	public boolean isVoidType() {
		return false;
	}

	public String referenceToString(){
		return "R("+(clazz != null ? clazz.getName(): null)+")";
	}

	@Override
	public String toString() {
		return super.toString()+" "+referenceToString();
	}

}
