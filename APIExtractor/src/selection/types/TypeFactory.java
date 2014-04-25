package selection.types;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;

import org.eclipse.jdt.core.dom.TypeParameter;

import definitions.ClassInfo;

public abstract class TypeFactory {

	private static final String NO_VAR_CONST = "noVarConst";

	private static final String VOID = "void";

	private NameGenerator nameGen;
	
	protected Map<String, ConstType> cons = new HashMap<String, ConstType>();
	protected Map<String, BoxedType> boxed = new HashMap<String, BoxedType>();
	protected Map<String, PrimitiveType> primitive = new HashMap<String, PrimitiveType>();
	
	private NoType noType = new NoType();
	private NullType nullType = new NullType();
	private VoidType voidType = new VoidType();

	private ConstType noVarConst = new ConstType(NO_VAR_CONST);

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

	private static Map<String, String> boxedToPrimitive = new HashMap<String, String>(){
		{
			put(java.lang.Byte.class.getName(), "byte");
			put(java.lang.Short.class.getName(), "short");
			put(java.lang.Integer.class.getName(), "int");
			put(java.lang.Long.class.getName(), "long");
			put(java.lang.Float.class.getName(), "float");
			put(java.lang.Double.class.getName(), "double");
			put(java.lang.Boolean.class.getName(), "boolean");
			put(java.lang.Character.class.getName(), "char");
		
		}
	};	
	
	public TypeFactory() {}	
	
	public TypeFactory(NameGenerator nameGen) {
		this.nameGen = nameGen;
	}

	protected abstract void addReferenceType(ReferenceType type);
	
	public BoxedType createBoxedType(String name) {
		return createBoxedType(name, null);
	}
	
	public BoxedType createBoxedType(Class<?> type) {
		return createBoxedType(type.getName());
	}	
	
	public ConstType createConstType(String name) {
		return createConstType(name, null);
	}
	
	public ConstType createConstType(Class<?> type) {
		return createConstType(type.getName());
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
	
	public PolymorphicType createPolymorphicType(ClassInfo clazz, Type[] params) {
	    return createPolymorphicType(clazz.getName(), clazz, params);
	}	
	
	public PolymorphicType createPolymorphicType(String name, ClassInfo clazz, Type[] params) {
		PolymorphicType type = new PolymorphicType(name, clazz, params);
		addReferenceType(type);
		return type;
	}
	
	public PolymorphicType createPolymorphicType(Class<?> type, ClassInfo classInfo, Type[] params) {
		return createPolymorphicType(type.getName(), classInfo, params);
	}	

	public Type createMonomorphicType(String name) {
		if (isPrimitiveType(name))
			return createPrimitiveType(name);
		else if(isVoidType(name))
			return voidType;
		else 
			return createMonomorphicReferenceType(name);	
	}
	
	private boolean isVoidType(String name) {
		return name.equals(VOID);
	}

	public ReferenceType createMonomorphicReferenceType(String name) {
		if(isBoxedType(name)){
			return createBoxedType(name);
		} else {
			return createConstType(name);
		}		
	}	

	public ReferenceType createMonomorphicReferenceAndPrimitiveToBoxedType(String name) {
		if (isPrimitiveType(name)){
			return createBoxedType(primitiveToBoxed.get(name));
		} else if(isBoxedType(name)){
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
		return new Substitution(createVariable(oldName), createNewVariable());
	}
	
	public Variable createNewVariable(){
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

	public PrimitiveType getPrimitiveType(BoxedType boxedType) {
		return createPrimitiveType(boxedToPrimitive.get(boxedType.getPrefix()));
	}
	
	@Override
	public String toString() {
		return "primitive: " + primitive.values();
	}

	public Map<String, ConstType> getCons() {
		return cons;
	}

	public void setCons(Map<String, ConstType> cons) {
		this.cons = cons;
	}

	public Map<String, BoxedType> getBoxed() {
		return boxed;
	}

	public void setBoxed(Map<String, BoxedType> boxed) {
		this.boxed = boxed;
	}

	public Map<String, PrimitiveType> getPrimitive() {
		return primitive;
	}

	public void setPrimitive(Map<String, PrimitiveType> primitive) {
		this.primitive = primitive;
	}

	public NoType getNoType() {
		return noType;
	}

	public void setNoType(NoType noType) {
		this.noType = noType;
	}

	public NullType getNullType() {
		return nullType;
	}

	public void setNullType(NullType nullType) {
		this.nullType = nullType;
	}

	public VoidType getVoidType() {
		return voidType;
	}

	public void setVoidType(VoidType voidType) {
		this.voidType = voidType;
	}

	public ConstType createNoVariableType() {
		return this.noVarConst;
	}
		
}
