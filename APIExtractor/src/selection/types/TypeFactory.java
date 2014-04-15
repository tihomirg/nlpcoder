package selection.types;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import definitions.ClassInfo;

public class TypeFactory {

	private NameGenerator nameGen;
	
	private final Map<String, ReferenceType> cons = new HashMap<String, ReferenceType>();
	private final Map<String, BoxedType> boxed = new HashMap<String, BoxedType>();
	private final Map<String, PrimitiveType> primitive = new HashMap<String, PrimitiveType>();
	private final Set<ReferenceType> reference = new HashSet<ReferenceType>();
	
	private final Type noType = new NoType();
	private final Type nullType = new NullType();
	
	public TypeFactory(NameGenerator nameGen) {
		this.nameGen = nameGen;
		ClassInfo.setFactory(this);
	}

	public Variable createVariable(String name) {
		return new Variable(name);
	}
	
	public Type createBoxedType0(String name) {
		if(!boxed.containsKey(name)){
			BoxedType type = new BoxedType(name);
			this.reference.add(type);
			boxed.put(name, type);
		}
		return boxed.get(name);
	}
	
	public Type createBoxedType(String name) {
		if(!boxed.containsKey(name)){
			BoxedType type = new BoxedType(name);
			boxed.put(name, type);
		}
		return boxed.get(name);
	}	
	
	public Type createConstType0(String name) {
		if(!cons.containsKey(name)){
			ConstType type = new ConstType(name);
			this.reference.add(type);
			cons.put(name, type);
		}
		return cons.get(name);
	}
	
	public Type createConstType(String name) {
		if(!cons.containsKey(name)){
			ConstType type = new ConstType(name);
			cons.put(name, type);
		}
		return cons.get(name);
	}	
	
	public Type createConstType(ClassInfo clazz) {
		String name = clazz.getName();
		if(!cons.containsKey(name)){
			ConstType type = new ConstType(clazz);
			cons.put(name, type);
		}
		return cons.get(name);
	}	
	
	public Type createPrimitiveType(String name) {
		if(!primitive.containsKey(name)){
			primitive.put(name, new PrimitiveType(name));
		}
		return cons.get(name);
	}

	
	public PolymorphicType createPolymorphicType0(String name, Type[] params) {
		PolymorphicType type = new PolymorphicType(name, params);
	    this.reference.add(type);
	    return type;
	}	
	
	public PolymorphicType createPolymorphicType(String name, Type[] params) {
		return new PolymorphicType(name, params);
	}

	public PolymorphicType createPolymorphicType(ClassInfo clazz, Type[] params) {
		return new PolymorphicType(clazz, params);
	}		
	
	@Override
	public String toString() {
		return    "\nprimitive: " + primitive.values()
				+ "\nreference: "+reference;
	}
	
	public Substitution varToNewVar(String oldName){
		return new Substitution(createVariable(oldName), genNewVariable());
	}
	
	public Variable genNewVariable(){
		return createVariable(nameGen.genNewName());
	}
	
	public NameGenerator getVarGen() {
		return nameGen;
	}

	public Type getNoType() {
		return noType;
	}
 
	public Type createNullType() {
		return nullType;
	}

	public Type getBoxedType(PrimitiveType primitiveType) {
		return null;
	}
	
	public void setClassInfo(Map<String, ClassInfo> map){
		for (ReferenceType type : reference) {
			String name = type.getPrefix();
			if(map.containsKey(name)){
				type.setClassInfo(map.get(name));
			}
		}
	}

	public List<Type> getInheritedTypes(ReferenceType referenceType) {
		// TODO Auto-generated method stub
		return null;
	}

}
