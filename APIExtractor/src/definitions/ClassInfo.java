package definitions;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.bcel.classfile.Field;
import org.apache.bcel.classfile.JavaClass;
import org.apache.bcel.classfile.Method;
import org.apache.bcel.generic.Type;

import selection.IWordExtractor;

public class ClassInfo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8473504638929013042L;	
	private static final Map<String, ClassInfo> classes = new HashMap<String, ClassInfo>();

	private String name;
	private ClassInfo[] interfaces;
	private ClassInfo[] superClasses;
	private boolean isClass;
	private boolean isPublic;
	
	private Declaration[] methods;
	private Declaration[] fields;
	private String simpleName;
	private String packageName;
	private Declaration[] udecls;
	
	public ClassInfo(){}
	
	public ClassInfo(JavaClass clazz, IWordExtractor extractor) {
		this.name = clazz.getClassName();
		this.packageName = clazz.getPackageName();
		this.simpleName = getShortName(this.name);
		classes.put(this.name, this);
		
		this.isClass = clazz.isClass();
		this.isPublic = clazz.isPublic();
		
		this.methods = initMethods(clazz, extractor);
		this.fields = initFields(clazz, extractor);

		try {
			this.interfaces = makeInterfaces(clazz.getInterfaces(), extractor);
		} catch (Exception e) {
			System.out.println("*******************************************************************************************");
			this.interfaces = new ClassInfo[0];
		}

		try {

			this.superClasses = makeSuperClasses(clazz.getSuperClasses(), extractor);		
		} catch (Exception e) {
			System.out.println("*******************************************************************************************");
			this.superClasses = new ClassInfo[0];
		}
	}

	private String getShortName(String name) {
		return name.substring(name.lastIndexOf(".")+1);
	}

	private ClassInfo[] makeSuperClasses(JavaClass[] superClasses2, IWordExtractor extractor) {
		List<ClassInfo> list = new LinkedList<ClassInfo>();
		for (JavaClass superClass: superClasses2) {
			getClass(superClass, list, extractor);
		}
		return list.toArray(new ClassInfo[list.size()]);
	}

	private void getClass(JavaClass javaClass, List<ClassInfo> list, IWordExtractor extractor) {
		String className = javaClass.getClassName();
		if (className != null) {
			ClassInfo clazz;
			if (classes.containsKey(className)){
				clazz = classes.get(className);
			} else {
				clazz = new ClassInfo(javaClass, extractor);
				classes.put(className, clazz);
			}
			list.add(clazz);
		}
	}

	private ClassInfo[] makeInterfaces(JavaClass[] interfaces, IWordExtractor extractor) {
		List<ClassInfo> list = new LinkedList<ClassInfo>();
		for (JavaClass interfaceClass: interfaces) {
			getClass(interfaceClass, list, extractor);
		}
		return list.toArray(new ClassInfo[list.size()]);
	}

	public Declaration[] getDeclarations(){
		int length = this.methods.length + this.fields.length;
		Declaration[] decls = new Declaration[length];
	
		System.arraycopy(this.methods, 0, decls, 0, this.methods.length);
		System.arraycopy(this.fields, 0, decls, this.methods.length, this.fields.length);	
		
		return decls;
	}
	
	private ClassInfo[] getInheritedTypes(){
		int length = this.superClasses.length + this.interfaces.length;
		ClassInfo[] types = new ClassInfo[length];
	
		System.arraycopy(this.superClasses, 0, types, 0, this.superClasses.length);
		System.arraycopy(this.interfaces, 0, types, this.superClasses.length, this.interfaces.length);
		
		return types;
	}	
	
	public Declaration[] getUniqueDeclarations() {
		if(this.udecls == null){
			Declaration[] decls = getDeclarations();
			List<Declaration> list = new LinkedList<Declaration>();
			for (Declaration decl : decls) {
				if(!isOverriden(decl, getInheritedTypes())){
					list.add(decl);
				}
			}
			return this.udecls = list.toArray(new Declaration[list.size()]);
		} else return this.udecls;
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

	private Declaration[] initMethods(JavaClass clazz, IWordExtractor extractor) {
		Method[] methods = clazz.getMethods();
		
		List<Declaration> decls = new ArrayList<Declaration>();
		
		for(Method method: methods){
			if(method.isPublic()){
				Declaration decl = new Declaration();
				decl.setClazz(clazz.getClassName());
				
				String name = method.getName();
				if (name.equals("<init>")){
					String clazzName = clazz.getClassName();
					decl.setName(clazzName.substring(clazzName.lastIndexOf('.')+1, clazzName.length()));
					decl.setConstructor(true);
					decl.setMethod(true);					
				} else {
					decl.setName(method.getName());
					decl.setMethod(true);
				}
				
				decl.setArgNum(method.getArgumentTypes().length);
				
				setArgTypes(method, decl);
				decl.setRetType(method.getReturnType().toString());
				decl.setStatic(method.isStatic());			
				decl.setWords(extractor.getWords(decl));
				
				decls.add(decl);				
			}
		}
		
		return decls.toArray(new Declaration[decls.size()]);
	}

	private void setArgTypes(Method method, Declaration decl) {
		String[] argTypes = new String[decl.getArgNum()];
		
		Type[] argumentTypes = method.getArgumentTypes();
		
		for (int i = 0; i < argTypes.length; i++) {
			Type type = argumentTypes[i];
			argTypes[i] = type.toString();
		}
		
		decl.setArgType(argTypes);
	}
	
	private Declaration[] initFields(JavaClass clazz, IWordExtractor extractor) {
		Field[] fields = clazz.getFields();
		
		List<Declaration> decls = new ArrayList<Declaration>();
		
		for(Field field: fields){
			if(field.isPublic()){
				Declaration decl = new Declaration();
				decl.setName(field.getName());
				decl.setField(true);
				decl.setStatic(field.isStatic());
				decl.setRetType(field.getType().toString());
				
				decl.setWords(extractor.getWords(decl));
				
				decls.add(decl);
			}
		}
		return decls.toArray(new Declaration[decls.size()]);
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

	public static Map<String, ClassInfo> getClasses() {
		return classes;
	}
	
	private String interfacesToString(){
		String s="";
		for (ClassInfo clazz: superClasses) {
			s+=" "+clazz.getName();
		}
		return s;
	}
	
	private String superClassesToString(){
		String s="";
		for (ClassInfo clazz: interfaces) {
			s+=" "+clazz.getName();
		}
		return s;		
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

	@Override
	public String toString() {
		return "ClassInfo [name=" + name + 
				", superClasses=["+ superClassesToString() + "]"+
				", interfaces=["+interfacesToString()+"],"+
				"isClass=" + isClass+ 
				", isPublic=" + isPublic + 
				"\ndeclarations=\n"+ Arrays.toString(getDeclarations())+
				"]\n";
	}
	
}
