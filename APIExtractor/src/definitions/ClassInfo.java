package definitions;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
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

import api.InitialAPI;

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
	protected static final Type[] EMPTY_TYPE_ARRAY = new Type[0];

	private String name;
	private String simpleName;
	private String packageName;
	private boolean isClass;
	private boolean isPublic;

	private ReferenceType type;

	private Declaration[] constructors;	
	private Declaration[] methods;
	private Declaration[] fields;	

	private ClassInfo[] interfaces;
	private ClassInfo[] superClasses;

	private ReferenceType[] inheritedTypes;

	//null's when class is initialized
	private Declaration[] udecls;
	private Set<Type> allInharitedTypes;

	public ClassInfo(){}

	public ClassInfo(JavaClass clazz, InitialTypeFactory factory, InitialAPI cif) {
		this.name = clazz.getClassName();
		this.packageName = clazz.getPackageName();
		this.simpleName = getShortName(this.name);

		this.isClass = clazz.isClass();
		this.isPublic = clazz.isPublic();

		String[] typeParameters = typeParametersAndInheritedTypes(clazz, factory);

		initMethods(clazz, typeParameters, factory);
		initFields(clazz, typeParameters, factory);

		//After we set 'methods' and 'fields' we can change variables in the type. This is due to the receiver.		
		renameTypeVars(factory, typeParameters);

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

	private void renameTypeVars(InitialTypeFactory factory, String[] typeParameters) {
		List<Substitution> uniqueVarParams = getUniqueVarNames(typeParameters, factory);
		this.type = (ReferenceType) this.type.apply(uniqueVarParams, factory);

		for (int i = 0; i < this.inheritedTypes.length; i++) {
			this.inheritedTypes[i] = (ReferenceType) this.inheritedTypes[i].apply(uniqueVarParams, factory);
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

		this.inheritedTypes = getInheritedTypes(signature, clazz, new HashSet<String>(Arrays.asList(classTypeParams)), factory);
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

	private static ReferenceType[] getInheritedTypes(String signature, JavaClass clazz, Set<String> vars, InitialTypeFactory factory) {
		if (clazz.getClassName().equals(java.lang.Object.class.getName())) return new ReferenceType[0];

		if (signature == null) {

			String[] interfaceNames = clazz.getInterfaceNames();
			int length = interfaceNames.length;
			String[] names = new String[length + 1];
			names[0] = clazz.getSuperclassName();
			System.arraycopy(interfaceNames, 0, names, 1, length);

			ReferenceType[] types = new ReferenceType[length + 1];
			for (int i = 0; i < length + 1; i++) {
				types[i] = factory.createMonomorphicReferenceType(names[i]);
			}

			return types;
		} else {

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
		int length = this.constructors.length 
				+ this.methods.length + 
				this.fields.length;
		Declaration[] decls = new Declaration[length];

		System.arraycopy(this.constructors, 0, decls, 0, this.constructors.length);
		System.arraycopy(this.methods, 0, decls, this.constructors.length, this.methods.length);
		System.arraycopy(this.fields, 0, decls, this.constructors.length + this.methods.length, this.fields.length);	

		return decls;
	}

	private void initMethods(JavaClass clazz, String[] classTypeParams, InitialTypeFactory factory) {
		Method[] javaDecls = clazz.getMethods();
		String className = clazz.getClassName();
		String pkg = clazz.getPackageName();
		List<Declaration> methods = new LinkedList<Declaration>();
		List<Declaration> consts = new LinkedList<Declaration>();

		for(Method javaDecl: javaDecls){
			if(javaDecl.isPublic()){
				Declaration decl = new Declaration();
				decl.setClazz(className);
				decl.setPackageName(pkg);

				String name = javaDecl.getName();
				if (name.equals(CONSTRUCTOR_SHORT_NAME)){
					String clazzName = clazz.getClassName();
					decl.setName("new "+clazzName.substring(clazzName.lastIndexOf('.')+1, clazzName.length()));
					decl.setConstructor(true);
					decl.setMethod(true);		
				} else {
					decl.setName(javaDecl.getName());
					decl.setMethod(true);
				}

				decl.setStatic(javaDecl.isStatic());
				decl.setPublic(javaDecl.isPublic());		
				decl.setArgNum(javaDecl.getArgumentTypes().length);

				String signature = getSignature(javaDecl);
				String[] methodTypeParams = typeParameters(signature);
				List<Substitution> classVarSubs = getUniqueVarNames(classTypeParams, factory);
				List<Substitution> methodVarSubs = getUniqueVarNames(methodTypeParams, factory);

				if (!decl.isConstructor() && !javaDecl.isStatic()){
					decl.setReceiverType(type.apply(classVarSubs, factory));
				}

				Set<String> vars = createVariables(methodTypeParams, classTypeParams);

				if (!decl.isConstructor()){				
					decl.setRetType(returnType(signature, classVarSubs, methodVarSubs, vars, factory));
				} else {
					decl.setRetType(type.apply(classVarSubs, factory));
				}

				decl.setArgTypes(parameterTypes(signature, classVarSubs, methodVarSubs, vars, factory));


				if (decl.isConstructor()){
					consts.add(decl);
				} else {
					methods.add(decl);
				}
			}
		}

		this.constructors = consts.toArray(new Declaration[consts.size()]);
		this.methods = methods.toArray(new Declaration[methods.size()]);

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
		Type returnType = returnType(signature, vars, factory);
		if (returnType.isVoidType()) return returnType;
		return returnType.apply(methodVarSubs, factory).apply(classVarSubs, factory);
	}

	protected static List<Substitution> getUniqueVarNames(String[] typeParameters, InitialTypeFactory factory) {
		List<Substitution> list = new LinkedList<Substitution>();
		for (String param : typeParameters) {
			list.add(factory.varToNewVar(param));
		}
		return list;
	}

	private void initFields(JavaClass clazz, String[] classTypeParams, InitialTypeFactory factory) {
		Field[] javaDecls = clazz.getFields();
		String className = clazz.getClassName();
		String pkg = clazz.getPackageName();
		List<Declaration> decls = new ArrayList<Declaration>();

		for(Field javaDecl: javaDecls){
			if(javaDecl.isPublic()){
				Declaration decl = new Declaration();
				decl.setName(javaDecl.getName());
				decl.setClazz(className);
				decl.setPackageName(pkg);
				decl.setField(true);
				decl.setStatic(javaDecl.isStatic());
				decl.setPublic(javaDecl.isPublic());

				String signature = getSignature(javaDecl);

				List<Substitution> classVarSubs = getUniqueVarNames(classTypeParams, factory);
				Set<String> vars = new HashSet<String>(Arrays.asList(classTypeParams));

				if(!javaDecl.isStatic()) decl.setReceiverType(type.apply(classVarSubs, factory));

				decl.setRetType(fieldType(signature, classVarSubs, vars, factory));
				decl.setArgTypes(EMPTY_TYPE_ARRAY);

				decls.add(decl);
			}
		}

		this.fields = decls.toArray(new Declaration[decls.size()]);
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
				return factory.createNewVariable();
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
				return factory.createNewVariable();
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
				return factory.createNewVariable();
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
			List<Declaration> list = new LinkedList<Declaration>();
			list.addAll(Arrays.asList(this.constructors));
			ClassInfo[] inheritedClasses = getInheritedClasses();
			list.addAll(getUniqueFields(inheritedClasses));
			list.addAll(getUniqueMethods(inheritedClasses));
			return this.udecls = list.toArray(new Declaration[list.size()]);
		} else return this.udecls;
	}

	private List<Declaration> getUniqueFields(ClassInfo[] inheritedClasses) {
		List<Declaration> list = new LinkedList<Declaration>();

		for (Declaration field : this.fields) {
			if(!containsEquivalentField(inheritedClasses, field)) list.add(field);
		}
		return list;
	}

	private List<Declaration> getUniqueMethods(ClassInfo[] inheritedClasses) {
		List<Declaration> list = new LinkedList<Declaration>();

		for (Declaration method : this.methods) {
			if (!containsEquivalentMethod(inheritedClasses, method)) list.add(method);
		}
		return list;
	}

	private boolean containsEquivalentMethod(ClassInfo[] inheritedClasses, Declaration method) {
		for (ClassInfo clazz : inheritedClasses) {
			if (clazz.containsEquivalentMethod(method)){
				return true;
			}
		}

		return false;
	}

	private boolean containsEquivalentField(ClassInfo[] inheritedClasses, Declaration field) {
		for (ClassInfo clazz : inheritedClasses) {
			if (clazz.containsEquivalentField(field)){
				return true;
			}
		}

		return false;
	}	

	private ClassInfo[] getInheritedClasses(){
		int length = this.superClasses.length + this.interfaces.length;
		ClassInfo[] types = new ClassInfo[length];

		System.arraycopy(this.superClasses, 0, types, 0, this.superClasses.length);
		System.arraycopy(this.interfaces, 0, types, this.superClasses.length, this.interfaces.length);

		return types;
	}	

	private boolean containsEquivalentField(Declaration decl) {
		for (Declaration field: fields) {
			if (field.equivalent(decl)) return true;
		}
		return false;
	}

	private boolean containsEquivalentMethod(Declaration decl) {
		for (Declaration method: methods) {
			if (method.equivalent(decl)) return true;
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

	public Set<Type> getAllInharitedTypes(TypeFactory factory){
		if (this.allInharitedTypes == null){
			Set<Type> types = new HashSet<Type>();
			for (ReferenceType iType : this.inheritedTypes) {
				ClassInfo clazz = iType.getClassInfo();
				ReferenceType oType = clazz.getType();
				Unifier unify = oType.unify(iType, factory);
				types.add(iType);
				types.addAll(instantiate(unify, clazz.getAllInharitedTypes(factory), factory));
			}
			this.allInharitedTypes = types;
		}
		return this.allInharitedTypes;
	}

	public Set<Type> getAllInstantiatedInheritedType(Type instType, TypeFactory factory) {
		//Should put more general type always as the argument of unify
		Unifier unify = type.unify(instType, factory);
		List<Substitution> subs = unify.getSubs();

		Set<Type> inharited = this.getAllInharitedTypes(factory);
		Set<Type> uInhTypes = new HashSet<Type>();
		for (Type type : inharited) {
			uInhTypes.add(type.apply(subs, factory));
		}

		return uInhTypes;
	}

	private Set<Type> instantiate(Unifier unifier, Set<Type> types, TypeFactory factory) {
		Set<Type> set = new HashSet<Type>();

		List<Substitution> subs = unifier.getSubs();

		for (Type type : types) {
			set.add(type.apply(subs, factory));
		}

		return set;
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

	public Declaration[] getConstructors() {
		return constructors;
	}

	public void setConstructors(Declaration[] constructors) {
		this.constructors = constructors;
	}	

	@Override
	public String toString() {
		return "ClassInfo [name=" + name + 
				//				", superClasses=["+ superClassesToString() + "]"+
				//				", interfaces=["+interfacesToString()+"],"+
				", type="+this.type +
				", inherited="+Arrays.toString(this.inheritedTypes)+
				", isClass=" + isClass+ 
				", isPublic=" + isPublic + 
				"\ndeclarations=\n"+ Arrays.toString(getDeclarations())+
				"]\n\n";
	}
}