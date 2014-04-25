package definitions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.bcel.classfile.Field;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.internal.core.CreateMethodOperation;

import selection.types.ConstType;
import selection.types.InitialTypeFactory;
import selection.types.ReferenceType;
import selection.types.Substitution;
import selection.types.Type;
import selection.types.Variable;

public class ArrayClassInfo extends ClassInfo {

	//	private String name;
	//	private ClassInfo[] interfaces;
	//	private ClassInfo[] superClasses;
	//	private boolean isClass;
	//	private boolean isPublic;
	//
	//	private Declaration[] methods;
	//	private Declaration[] fields;
	//	private String simpleName;
	//	private String packageName;
	//	private Declaration[] udecls;
	//	private ReferenceType type;
	//	private ReferenceType[] inheritedTypes;


	public static final String ACCESS = "access";
	public static final String SHORT_NAME = "Array";
	public static final String PKG_NAME = "java.lang";
	public static final String LONG_NAME = PKG_NAME+"."+SHORT_NAME;

	public ArrayClassInfo(){}

	public ArrayClassInfo(InitialTypeFactory tf) {
		String name = LONG_NAME;
		this.setName(name);
		this.setClass(true);
		this.setPublic(true);
		this.setSimpleName(SHORT_NAME);
		this.setPackageName(PKG_NAME);
		ConstType iType = tf.createConstType(java.lang.Object.class);

		this.setInheritedTypes(new ReferenceType[]{iType});
		this.setInterfaces(EMPTY_CLASSES);

		String paramName = "VArr";
		Variable var = tf.createVariable(paramName);

		this.setType(tf.createPolymorphicType(name, this, new ReferenceType[]{var}));

		this.setConstructors(createConstructors(paramName, tf));
		this.setFields(createFields(paramName, tf));
		this.setMethods(createMethods(paramName, tf));
	}

	private Declaration[] createMethods(String paramName, InitialTypeFactory tf) {
		return new Declaration[]{createAccessMethod(paramName, tf)};
	}
	
	private Declaration[] createConstructors(String paramName, InitialTypeFactory tf) {
		return new Declaration[]{createIntConstructor(paramName, tf), createArrayIntConstructor(paramName, tf)};
	}	

	public void setSuperClasses() {
		setSuperClasses(new ClassInfo[]{this.getInheritedTypes()[0].getClassInfo()});
	}	

	private Declaration createIntConstructor(String paramName, InitialTypeFactory tf) {
		Declaration decl = new Declaration();

		String clazzName = this.getName();
		decl.setClazz(clazzName);
		decl.setPackageName(this.getPackageName());
		decl.setName("new "+SHORT_NAME);
		decl.setConstructor(true);
		decl.setMethod(true);

		decl.setStatic(false);
		decl.setPublic(true);		

		String[] classTypeParams = new String[]{paramName};
		List<Substitution> classVarSubs = getUniqueVarNames(classTypeParams, tf);
		decl.setRetType(this.getType().apply(classVarSubs, tf));
		Type[] args = new Type[]{tf.createPrimitiveType("int")};
		decl.setArgTypes(args);
		decl.setArgNum(args.length);		

		return decl;
	}

	private Declaration createArrayIntConstructor(String paramName, InitialTypeFactory tf) {
		Declaration decl = new Declaration();

		String clazzName = this.getName();
		decl.setClazz(clazzName);
		decl.setPackageName(this.getPackageName());
		decl.setName("new "+SHORT_NAME);
		decl.setConstructor(true);
		decl.setMethod(true);

		decl.setStatic(false);
		decl.setPublic(true);		

		String[] classTypeParams = new String[]{paramName};
		List<Substitution> classVarSubs = getUniqueVarNames(classTypeParams, tf);
		decl.setRetType(this.getType().apply(classVarSubs, tf));
		Type[] args = new Type[]{tf.createPolymorphicType(clazzName, this, new Type[]{tf.createBoxedType(java.lang.Integer.class)})};
		decl.setArgTypes(args);
		decl.setArgNum(args.length);		

		return decl;
	}	

	private Declaration createAccessMethod(String paramName, InitialTypeFactory tf) {
		Declaration decl = new Declaration();

		String clazzName = this.getName();
		decl.setClazz(clazzName);
		decl.setPackageName(this.getPackageName());
		decl.setName(ACCESS);
		decl.setConstructor(false);
		decl.setMethod(true);

		decl.setStatic(false);
		decl.setPublic(true);	

		String[] classTypeParams = new String[]{paramName};
		List<Substitution> classVarSubs = getUniqueVarNames(classTypeParams, tf);
		decl.setReceiverType(this.getType().apply(classVarSubs, tf));
		decl.setRetType(classVarSubs.get(0).getType());

		Type[] args = new Type[]{tf.createPrimitiveType("int")};
		decl.setArgTypes(args);
		decl.setArgNum(args.length);		

		return decl;
	}

	private Declaration[] createFields(String paramName, InitialTypeFactory tf) {
		return new Declaration[]{createLengthField(paramName, tf)};
	}

	private Declaration createLengthField(String paramName, InitialTypeFactory tf) {
		Declaration decl = new Declaration();

		decl.setClazz(this.getName());
		decl.setName("length");
		decl.setPackageName(this.getPackageName());
		decl.setField(true);
		decl.setStatic(false);
		decl.setPublic(true);

		String[] classTypeParams = new String[]{paramName};
		List<Substitution> classVarSubs = getUniqueVarNames(classTypeParams, tf);

		decl.setReceiverType(this.getType().apply(classVarSubs, tf));				
		decl.setRetType(tf.createPrimitiveType("int"));
		decl.setArgTypes(EMPTY_TYPE_ARRAY);
		
		return decl;
	}

}
