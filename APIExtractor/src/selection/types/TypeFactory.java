package selection.types;

import java.util.HashMap;
import java.util.Map;

import definitions.ClassInfo;

public abstract class TypeFactory {

	private NameGenerator nameGen;
	
	protected final Map<String, ConstType> cons = new HashMap<String, ConstType>();
	protected final Map<String, BoxedType> boxed = new HashMap<String, BoxedType>();
	protected final Map<String, PrimitiveType> primitive = new HashMap<String, PrimitiveType>();
	
	private final NoType noType = new NoType();
	private final NullType nullType = new NullType();
	
	public TypeFactory(NameGenerator nameGen) {
		this.nameGen = nameGen;
	}

	protected abstract void addReferenceType(ReferenceType type);

	public BoxedType createBoxedType(String name) {
		return createBoxedType(name, null);
	}
	
	public ConstType createConstType(String name) {
		return createConstType(name, null);
	}	
	
	public PrimitiveType createPrimitiveType(String name) {
		if(!primitive.containsKey(name)){
			primitive.put(name, new PrimitiveType(name));
		}
		return primitive.get(name);
	}
	
	public BoxedType createBoxedType(String name, ClassInfo clazz) {
		if(!boxed.containsKey(name)){
			BoxedType type = new BoxedType(name, clazz);
			addReferenceType(type);			
			boxed.put(name, type);
		}
		return boxed.get(name);
	}	
	
	public ConstType createConstType(String name, ClassInfo clazz) {
		if(!cons.containsKey(name)){
			ConstType type = new ConstType(name, clazz);
			addReferenceType(type);
			cons.put(name, type);
		}
		return cons.get(name);
	}	

	public PolymorphicType createPolymorphicType(String name, Type[] params) {
	    return createPolymorphicType(name, null, params);
	}
	
	public PolymorphicType createPolymorphicType(String name, ClassInfo clazz, Type[] params) {
		PolymorphicType type = new PolymorphicType(name, clazz, params);
		addReferenceType(type);	
		return type;
	}		

	public Variable createVariable(String name) {
		return new Variable(name);
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

	public NoType createNoType() {
		return noType;
	}
 
	public NullType createNullType() {
		return nullType;
	}
	
	public Type getBoxedType(PrimitiveType primitiveType) {
		return null;
	}	

	@Override
	public String toString() {
		return "primitive: " + primitive.values();
	}
}
