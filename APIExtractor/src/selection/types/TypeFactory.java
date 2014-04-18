package selection.types;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import definitions.ClassInfo;

public abstract class TypeFactory {

	private NameGenerator nameGen;
	
	protected final Map<String, ConstType> cons = new HashMap<String, ConstType>();
	protected final Map<String, BoxedType> boxed = new HashMap<String, BoxedType>();
	protected final Map<String, PrimitiveType> primitive = new HashMap<String, PrimitiveType>();
	
	private final NoType noType = new NoType();
	private final NullType nullType = new NullType();

	private static final Set<String> primitiveNames = new HashSet<String>(Arrays.asList(new String[]{"byte", "short", "int", "long", "float", "double", "boolean","char"}));

	private static final Set<String> boxedNames = 
		new HashSet<String>(Arrays.asList(new String[]{
			java.lang.Byte.class.getName(), 
			java.lang.Short.class.getName(),
			java.lang.Integer.class.getName(),
			java.lang.Long.class.getName(),
			java.lang.Float.class.getName(),
			java.lang.Double.class.getName(),
			java.lang.Boolean.class.getName(),
			java.lang.Character.class.getName()}));;
			
	private static final Map<String, String> primitiveToBoxed = new HashMap<String, String>(){
		{
			put("byte", java.lang.Byte.class.getName());
			put("short", java.lang.Short.class.getName());
			put("int", java.lang.Integer.class.getName());
			put("long", java.lang.Long.class.getName());
			put("float", java.lang.Float.class.getName());
			put("double", java.lang.Double.class.getName());
			put("boolean", java.lang.Boolean.class.getName());
			put("char", java.lang.Character.class.getName());
		
		}
	};
	
	private final Map<String, ClassInfo> polyNameToClass = new HashMap<String, ClassInfo>();
			
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

	public Type createMonomorphicType(String name) {
		if (isPrimitiveType(name))
			return createPrimitiveType(name);
		else 
			return createMonomorphicReferenceType(name);	
	}
	
	public ReferenceType createMonomorphicReferenceType(String name) {
		if(isBoxedType(name)){
			return createBoxedType(name);
		} else {
			return createConstType(name);
		}		
	}	

	public static boolean isBoxedType(String name) {
		return boxedNames.contains(name);
	}

	public static boolean isPrimitiveType(String name) {
		return primitiveNames.contains(name);
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
	
	public BoxedType getBoxedType(PrimitiveType primitiveType) {
		return createBoxedType(primitiveToBoxed.get(primitiveType.getPrefix()));
	}	

	@Override
	public String toString() {
		return "primitive: " + primitive.values();
	}
}
