package definitions;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import org.apache.bcel.classfile.Attribute;
import org.apache.bcel.classfile.Field;
import org.apache.bcel.classfile.FieldOrMethod;
import org.apache.bcel.classfile.JavaClass;
import org.apache.bcel.classfile.Method;
import org.eclipse.jdt.core.Signature;

import definitions.factory.InitialClassInfoFactory;
import selection.types.InitialTypeFactory;
import selection.types.ReferenceType;
import selection.types.StabileTypeFactory;
import selection.types.Substitution;
import selection.types.Type;
import selection.types.TypeFactory;
import selection.types.Unifier;

public class ClassInfo implements Serializable {

	protected static final ClassInfo[] EMPTY_CLASSES = new ClassInfo[0];
	private static final long serialVersionUID = -8473504638929013042L;	
	private static final String CONSTRUCTOR_SHORT_NAME = "<init>";
	
	private String name;
	private String simpleName;
	private String packageName;
	private boolean isClass;
	private boolean isPublic;

	private ReferenceType type;
	
	private Declaration[] methods;
	private Declaration[] fields;	
	
	private ClassInfo[] interfaces;
	private ClassInfo[] superClasses;

	private ReferenceType[] inheritedTypes;

	//null's when class is initialized
	private Declaration[] udecls;
	private Type[] allInharitedTypes;

	public ClassInfo(){}
	
	public ClassInfo(JavaClass clazz, InitialTypeFactory factory, InitialClassInfoFactory cif) {
		this.name = clazz.getClassName();
		this.packageName = clazz.getPackageName();
		this.simpleName = getShortName(this.name);

		this.isClass = clazz.isClass();
		this.isPublic = clazz.isPublic();

		String[] typeParameters = typeParametersAndInheritedTypes(clazz, factory);

		this.methods = initMethods(clazz, typeParameters, factory);
		this.fields = initFields(clazz, typeParameters, factory);
		
		try {
			this.interfaces = cif.createClassInfos(clazz.getInterfaces());
		} catch (Exception e) {
			System.out.println("*******************************************************************************************");
			this.interfaces = EMPTY_CLASSES;
		}

		try {

			this.superClasses = cif.createClassInfos(clazz.getSuperClasses());		
		} catch (Exception e) {
			System.out.println("*******************************************************************************************");
			this.superClasses = EMPTY_CLASSES;
		}
		
	}
		
	private String[] typeParametersAndInheritedTypes(JavaClass clazz, InitialTypeFactory factory) {
		Attribute[] attributes = clazz.getAttributes();
		String signature = null;
		for (Attribute attribute : attributes) {
			if (attribute instanceof org.apache.bcel.classfile.Signature){
				signature  = ((org.apache.bcel.classfile.Signature) attribute).getSignature();
				break;
			}
		}

		String[] classTypeParams = typeParameters(signature);
		this.type = getClazzType(classTypeParams, this.name, factory);
		
		this.inheritedTypes = getInheritedTypes(signature, new HashSet<String>(Arrays.asList(classTypeParams)), factory);
		return classTypeParams;
	}

	private static ReferenceType getClazzType(String[] typeParameters, String name, InitialTypeFactory factory) {
		int length = typeParameters.length;
		ReferenceType[] typeParam = new ReferenceType[length];
		for (int i = 0; i < length; i++) {
			typeParam[i] = factory.createVariable(typeParameters[i]);
		}
		
		if (length > 0) {
		    return factory.createPolymorphicType(name, typeParam);			
		} else {
		    return factory.createMonomorphicReferenceType(name);
		}
		
	}

	private static String[] typeParameters(String signature) {
		if (signature == null) return new String[0];

		String[] typeParameters = Signature.getTypeParameters(signature);
		int length = typeParameters.length;
		String[] vars = new String[length];
		for (int i = 0; i < length; i++) {
			vars[i] = Signature.getTypeVariable(typeParameters[i]);
			//String[] typeParameterBounds = Signature.getTypeParameterBounds(param);
		}
		return vars;
	}

	private static ReferenceType[] getInheritedTypes(String signature, Set<String> vars, InitialTypeFactory factory) {
		if (signature == null) return new ReferenceType[0];
		
		int firstIndex = firstIndexOfInheritance(signature);
		String inheiritanceList = signature.substring(firstIndex);		
		String[] params = Signature.getParameterTypes("("+inheiritanceList+")V");
		int length = params.length;
		ReferenceType[] types = new ReferenceType[length];
		for (int i = 0; i < length; i++) {
			types[i] = referenceType(params[i], vars, factory);
		}
		return types;
	}

	private static int firstIndexOfInheritance(String signature) {
		if (signature.length() > 0){
			int level = signature.charAt(0) == '<' ? 1:0;
			if (level > 0){
				int i=1;
				for(; level > 0; i++){
					char curr = signature.charAt(i);
					if(curr == '<') level++;
					else if (curr == '>') {
						level--;
					}
				}
				return i;
			}
		}
		return 0;
	}	

	private String getShortName(String name) {
		return name.substring(name.lastIndexOf(".")+1);
	}

	public Declaration[] getDeclarations(){
		int length = this.methods.length + this.fields.length;
		Declaration[] decls = new Declaration[length];

		System.arraycopy(this.methods, 0, decls, 0, this.methods.length);
		System.arraycopy(this.fields, 0, decls, this.methods.length, this.fields.length);	

		return decls;
	}

	private Declaration[] initMethods(JavaClass clazz, String[] classTypeParams, InitialTypeFactory factory) {
		Method[] methods = clazz.getMethods();
		String className = clazz.getClassName();
		String pkg = clazz.getPackageName();
		List<Declaration> decls = new ArrayList<Declaration>();

		for(Method method: methods){
			if(method.isPublic()){
				Declaration decl = new Declaration();
				decl.setClazz(className);
				decl.setPackageName(pkg);
				
				String name = method.getName();
				if (name.equals(CONSTRUCTOR_SHORT_NAME)){
					String clazzName = clazz.getClassName();
					decl.setName("new "+clazzName.substring(clazzName.lastIndexOf('.')+1, clazzName.length()));
					decl.setConstructor(true);
					decl.setMethod(true);		
				} else {
					decl.setName(method.getName());
					decl.setMethod(true);
				}

				decl.setStatic(method.isStatic());
				decl.setPublic(method.isPublic());		
				decl.setArgNum(method.getArgumentTypes().length);

				String signature = getSignature(method);
				String[] methodTypeParams = typeParameters(signature);
				List<Substitution> classVarSubs = getUniqueVarNames(classTypeParams, factory);
				List<Substitution> methodVarSubs = getUniqueVarNames(methodTypeParams, factory);
				
				if (!decl.isConstructor()){
					decl.setReceiverType(type.apply(classVarSubs, factory));
				}
				
				Set<String> vars = createVariables(methodTypeParams, classTypeParams);
				
				if (!decl.isConstructor()){				
					decl.setRetType(returnType(signature, classVarSubs, methodVarSubs, vars, factory));
				} else {
					decl.setRetType(type.apply(classVarSubs, factory));
				}
				
				decl.setArgType(parameterTypes(signature, classVarSubs, methodVarSubs, vars, factory));

				decls.add(decl);				
			}
		}

		return decls.toArray(new Declaration[decls.size()]);
	}

	private static Set<String> createVariables(String[] methodTypeParams, String[] clazzTypeParams) {
		Set<String> vars = new HashSet<String>();
		vars.addAll(Arrays.asList(methodTypeParams));
		vars.addAll(Arrays.asList(clazzTypeParams));
		return vars;
	}
	
	private static Type[] parameterTypes(String signature, List<Substitution> classVarSubs, List<Substitution> methodVarSubs, Set<String> vars, InitialTypeFactory factory) {
		Type[] parameterTypes = parameterTypes(signature, vars, factory);
		int length = parameterTypes.length;
		Type[] types = new Type[length];
		for (int i = 0; i < length; i++) {
			Type paramType = parameterTypes[i];
			types[i] = paramType.apply(methodVarSubs, factory).apply(classVarSubs, factory);
		}
		return types;
	}

	//Gives the priority to methodVarSubs cause they might hide some classVars.
	private static Type returnType(String signature, List<Substitution> classVarSubs, List<Substitution> methodVarSubs, Set<String> vars, InitialTypeFactory factory) {
		return returnType(signature, vars, factory).apply(methodVarSubs, factory).apply(classVarSubs, factory);
	}

	protected static List<Substitution> getUniqueVarNames(String[] typeParameters, InitialTypeFactory factory) {
		List<Substitution> list = new LinkedList<Substitution>();
		for (String param : typeParameters) {
			list.add(factory.varToNewVar(param));
		}
		return list;
	}

	private Declaration[] initFields(JavaClass clazz, String[] classTypeParams, InitialTypeFactory factory) {
		Field[] fields = clazz.getFields();
		String className = clazz.getClassName();
		String pkg = clazz.getPackageName();
		List<Declaration> decls = new ArrayList<Declaration>();

		for(Field field: fields){
			if(field.isPublic()){
				Declaration decl = new Declaration();
				decl.setName(field.getName());
				decl.setClazz(className);
				decl.setPackageName(pkg);
				decl.setField(true);
				decl.setStatic(field.isStatic());
				decl.setPublic(field.isPublic());
				
				String signature = getSignature(field);
				
				List<Substitution> classVarSubs = getUniqueVarNames(classTypeParams, factory);
				Set<String> vars = new HashSet<String>(Arrays.asList(classTypeParams));
				
				decl.setReceiverType(type.apply(classVarSubs, factory));				
				decl.setRetType(fieldType(signature, classVarSubs, vars, factory));
				
				decls.add(decl);
			}
		}
		return decls.toArray(new Declaration[decls.size()]);
	}

	private Type fieldType(String signature, List<Substitution> classVarSubs, Set<String> vars, InitialTypeFactory factory) {
		return type(signature, vars, factory).apply(classVarSubs, factory);
	}

	private static String getSignature(FieldOrMethod decl) {
		String signature = null;
		for (Attribute attribute : decl.getAttributes()) {
			if (attribute instanceof org.apache.bcel.classfile.Signature){
				signature = ((org.apache.bcel.classfile.Signature) attribute).getSignature();
				break;
			}
		}
		
		if (signature == null){
			signature = decl.getSignature();
		}
		
		return signature;
	}

	private static Type[] parameterTypes(String signature, Set<String> vars, InitialTypeFactory factory) {
		String[] parameterTypes = Signature.getParameterTypes(signature);
		int length = parameterTypes.length;
		Type[] types = new Type[length];
		for (int i = 0; i < length; i++) {
			types[i] = type(parameterTypes[i], vars, factory);
		}
		return types;
	}

	private static Type returnType(String signature, Set<String> vars, InitialTypeFactory factory) {
		String returnType = Signature.getReturnType(signature);
		return type(returnType, vars, factory);
	}
	
	private static Type type(String type, Set<String> vars, InitialTypeFactory factory){
		if (isArrayType(type)){
			int dimension = Signature.getArrayCount(type);
			String elementType = Signature.getElementType(type);
			return arrayType(elementType, dimension, vars, factory);
		} else if (isPolymorphicType(type)) {
			String[] typeParams = Signature.getTypeArguments(type);
			String typeErasure = Signature.getTypeErasure(type);
			return polyType(typeErasure, typeParams, vars, factory);
		} else {
			if (Signature.toString(type).startsWith("?")){
				//TODO: Once we introduce existential types we will change this.
				return factory.genNewVariable();
			} else {
				String dotSignature = dottedTransformation(type);
				if(vars.contains(dotSignature))
					return factory.createVariable(dotSignature);
				else
					return factory.createMonomorphicType(dotSignature);				
			}
		}		
	}

	private static ReferenceType referenceType(String type, Set<String> vars, InitialTypeFactory factory) {
		if (isArrayType(type)){
			int dimension = Signature.getArrayCount(type);
			String elementType = Signature.getElementType(type);
			return arrayType(elementType, dimension, vars, factory);
		} else if (isPolymorphicType(type)) {
			String[] typeParams = Signature.getTypeArguments(type);
			String typeErasure = Signature.getTypeErasure(type);
			return polyType(typeErasure, typeParams, vars, factory);
		} else {
			if (Signature.toString(type).startsWith("?")){
				//TODO: Once we introduce existential types we will change this.
				return factory.genNewVariable();
			} else {
				String dotSignature = dottedTransformation(type);
				if(vars.contains(dotSignature))
					return factory.createVariable(dotSignature);
				else
					return factory.createMonomorphicReferenceType(dotSignature);				
			}
		}
	}
	
	private static ReferenceType referenceAndPrimitiveToBoxedType(String type, Set<String> vars, InitialTypeFactory factory) {
		if (isArrayType(type)){
			int dimension = Signature.getArrayCount(type);
			String elementType = Signature.getElementType(type);
			return arrayType(elementType, dimension, vars, factory);
		} else if (isPolymorphicType(type)) {
			String[] typeParams = Signature.getTypeArguments(type);
			String typeErasure = Signature.getTypeErasure(type);
			return polyType(typeErasure, typeParams, vars, factory);
		} else {
			if (Signature.toString(type).startsWith("?")){
				//TODO: Once we introduce existential types we will change this.
				return factory.genNewVariable();
			} else {
				String dotSignature = dottedTransformation(type);
				if(vars.contains(dotSignature))
					return factory.createVariable(dotSignature);
				else
					return factory.createMonomorphicReferenceAndPrimitiveToBoxedType(dotSignature);				
			}
		}
	}	

	protected static String dottedTransformation(String type) {
		return dottedName(Signature.toString(type));
	}

	private static String dottedName(String string) {
		return string.replace(".", "$").replace("/", ".");
	}

	private static ReferenceType polyType(String typeErasure, String[] typeParams, Set<String> vars, InitialTypeFactory factory) {
		String name = Signature.toString(typeErasure);
		return factory.createPolymorphicType(dottedName(name), types(typeParams, vars, factory));
	}

	private static ReferenceType[] types(String[] signatures, Set<String> vars, InitialTypeFactory factory) {
		int length = signatures.length;
		ReferenceType[] types = new ReferenceType[length];
		for (int i = 0; i < length; i++) {
			types[i] = referenceType(signatures[i], vars, factory);
		}
		return types;
	}

	private static ReferenceType arrayType(String elementType, int dimension, Set<String> vars, InitialTypeFactory factory) {
		if (dimension > 0){
			return factory.createPolymorphicType("java.lang.Array", new ReferenceType[]{arrayType(elementType, dimension - 1, vars, factory)});	
		} else {
			return referenceAndPrimitiveToBoxedType(elementType, vars, factory);
		}
	}

	private static boolean isPolymorphicType(String type) {
		return Signature.getTypeArguments(type).length > 0;
	}

	private static boolean isArrayType(String type) {
		return Signature.getArrayCount(type) > 0;
	}	
		
	public List<Declaration> getInstantiatedDeclarations(Type instType, StabileTypeFactory factory) {
		List<Declaration> decls = new LinkedList<Declaration>();
		
		Declaration[] uDecls = getUniqueInstantiatedDeclarations(instType, factory);
		decls.addAll(Arrays.asList(uDecls));
		
		Unifier unify = this.type.unify(instType, factory);
		
		for (ReferenceType type: this.inheritedTypes) {
			ClassInfo classInfo = type.getClassInfo();
			Type iType = type.apply(unify.getSubs(), factory);
			decls.addAll(classInfo.getInstantiatedDeclarations(iType, factory));
		}
		
		return decls;
	}

	public List<Type> getInstantiatedInheritedTypes(Type instType, StabileTypeFactory factory) {
		Unifier unify = instType.unify(type, factory);
		
		List<Substitution> subs = unify.getSubs();
		
		int length = this.inheritedTypes.length;
		List<Type> uInhTypes = new LinkedList<Type>();
		for (int i = 0; i < length; i++) {
			uInhTypes.add(this.inheritedTypes[i].apply(subs, factory));
		}
		
		return uInhTypes;
	}
	
	public Declaration[] getUniqueInstantiatedDeclarations(Type instType, StabileTypeFactory factory){
		
		Declaration[] uDecls = getUniqueDeclarations();
		int length = uDecls.length;
		Declaration[] clones = new Declaration[length];
		for (int i = 0; i < length; i++) {
			Declaration clone = uDecls[i].clone();
			Unifier unify = null;
			if(clone.isConstructor()){
				Type retType = clone.getRetType();
				unify = instType.unify(retType, factory);
				clone.setRetType(instType);
			} else {
				Type receiverType = clone.getReceiverType();
				unify = instType.unify(receiverType, factory);
				clone.setReceiverType(instType);
				
				Type retType = clone.getRetType();
				clone.setRetType(retType.apply(unify.getSubs(), factory));
			}
			
			Type[] argTypes = clone.getArgTypes();
			List<Substitution> subs = unify.getSubs();
			for(int j=0; j < argTypes.length; j++){
				argTypes[j] = argTypes[j].apply(subs, factory);
			}
			
			clones[i] = clone;
		}
		
		return clones;
	}	
	
	public Declaration[] getUniqueDeclarations() {
		if(this.udecls == null){
			Declaration[] decls = getDeclarations();
			List<Declaration> list = new LinkedList<Declaration>();
			for (Declaration decl : decls) {
				if(!isOverriden(decl, getInheritedClasses())){
					list.add(decl);
				}
			}
			return this.udecls = list.toArray(new Declaration[list.size()]);
		} else return this.udecls;
	}
	
	private ClassInfo[] getInheritedClasses(){
		int length = this.superClasses.length + this.interfaces.length;
		ClassInfo[] types = new ClassInfo[length];

		System.arraycopy(this.superClasses, 0, types, 0, this.superClasses.length);
		System.arraycopy(this.interfaces, 0, types, this.superClasses.length, this.interfaces.length);

		return types;
	}	

	public static boolean isOverriden(Declaration decl, ClassInfo[] classes) {
		for (ClassInfo clazz : classes) {
			if(clazz.isOverriden(decl)){
				return true;
			}
		}
		return false;
	}

	public boolean isOverriden(Declaration decl){
		if (decl.isMethod()) {
			return isOverridenMethod(decl);
		} else {
			return isOverridenField(decl);
		}
	}

	private boolean isOverridenField(Declaration decl) {
		for (Declaration field: fields) {
			if (decl.overrides(field)) return true;
		}
		return false;
	}

	private boolean isOverridenMethod(Declaration decl) {
		for (Declaration method: methods) {
			if (decl.overrides(method)) return true;
		}
		return false;
	}	
	
	public Declaration[] getMethods() {
		return methods;
	}

	public void setMethods(Declaration[] methods) {
		this.methods = methods;
	}

	public Declaration[] getFields() {
		return fields;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public ClassInfo[] getInterfaces() {
		return interfaces;
	}

	public void setInterfaces(ClassInfo[] interfaces) {
		this.interfaces = interfaces;
	}

	public ClassInfo[] getSuperClasses() {
		return superClasses;
	}

	public void setSuperClasses(ClassInfo[] superClasses) {
		this.superClasses = superClasses;
	}

	public boolean isClass() {
		return isClass;
	}

	public void setClass(boolean isClass) {
		this.isClass = isClass;
	}

	public boolean isPublic() {
		return isPublic;
	}

	public void setPublic(boolean isPublic) {
		this.isPublic = isPublic;
	}

	public void setFields(Declaration[] fields) {
		this.fields = fields;
	}

	public String getPackageName() {
		return packageName;
	}

	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}

	public void setSimpleName(String simpleName) {
		this.simpleName = simpleName;
	}	

	public String getSimpleName(){
		return simpleName;
	}

	public void setType(ReferenceType type) {
		this.type = type;
	}
	
	public ReferenceType getType() {
		return this.type;
	}	

	public void setInheritedTypes(ReferenceType[] inheritedTypes) {
		this.inheritedTypes = inheritedTypes;
	}	

	public ReferenceType[] getInheritedTypes() {
		return this.inheritedTypes;
	}
	
	public Type[] getAllInharitedTypes(TypeFactory factory){
		if (this.allInharitedTypes == null){
			List<Type> types = new LinkedList<Type>();
			for (ReferenceType iType : this.inheritedTypes) {
				ClassInfo clazz = iType.getClassInfo();
				ReferenceType oType = clazz.getType();
				Unifier unify = oType.unify(iType, factory);
				types.add(iType);
				types.addAll(instantiate(unify, clazz.getAllInharitedTypes(factory), factory));
			}
			this.allInharitedTypes = types.toArray(new Type[types.size()]);
		}
		return this.allInharitedTypes;
	}
	
	private List<Type> instantiate(Unifier unifier, Type[] types, TypeFactory factory) {
		List<Type> list = new LinkedList<Type>();
		
		List<Substitution> subs = unifier.getSubs();
		
		for (Type type : types) {
			list.add(type.apply(subs, factory));
		}
		
		return list;
	}


	private String interfacesToString(){
		String s="";
		for (ClassInfo clazz: interfaces) {
			s+=" "+clazz.getName();
		}
		return s;
	}

	private String superClassesToString(){
		String s="";
		for (ClassInfo clazz: superClasses) {
			s+=" "+clazz.getName();
		}
		return s;
	}	
	
	@Override
	public String toString() {
		return "ClassInfo [name=" + name + 
//				", superClasses=["+ superClassesToString() + "]"+
//				", interfaces=["+interfacesToString()+"],"+
				", type="+this.type +
				", ["+Arrays.toString(this.inheritedTypes)+"],"+
				"isClass=" + isClass+ 
				", isPublic=" + isPublic + 
				"\ndeclarations=\n"+ Arrays.toString(getDeclarations())+
				"]\n\n";
	}

}
