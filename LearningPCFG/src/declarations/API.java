package declarations;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import selection.DeclMap;
import selection.types.Type;
import selection.types.TypeFactory;

import definitions.ClassInfo;
import definitions.Declaration;

public class API {

	private static final String JAVA_LANG = "java.lang";
	private Map<String, Set<ClassInfo>> packages = new HashMap<String, Set<ClassInfo>>();
	private Map<String, ClassInfo> classes = new HashMap<String, ClassInfo>();
	private TypeFactory factory;
	private DeclMap declMap;
	private static ClassInfo JAVA_LANG_STRING = null;
	
	public API(TypeFactory factory) {
		this.factory = factory;
		this.declMap = new DeclMap();
	}

	public Imported createImported() {
		Imported imported = new Imported();
		load(imported, JAVA_LANG, false);
		return imported;
	}
	
	public void load(Imported imported, String imp, boolean isPkg) {
		Set<ClassInfo> classes = getClasses(imp, isPkg);
		Set<ClassInfo>  superClasses = getSuperClasses(classes);
		classes.addAll(superClasses);
        Set<Declaration> decls = getDecls(classes);
		
        this.declMap.add(decls);
        
		imported.addClasses(classes);
		imported.addDecls(decls);
	}

	public void addClasses(ClassInfo[] classes){
		for (ClassInfo clazz : classes) {
			addClass(clazz);
		}
	}
	
	private void addClass(ClassInfo clazz) {
		putIntoPackages(clazz);
		putIntoClasses(clazz);
	}

	private void putIntoClasses(ClassInfo clazz) {
		classes.put(clazz.getName(), clazz);
	}

	private void putIntoPackages(ClassInfo clazz) {
		Set<ClassInfo> pkg = getPackage(clazz);
		pkg.add(clazz);
	}

	public Set<ClassInfo> getPackage(ClassInfo clazz) {
		String pkg = clazz.getPackageName();
		return getPackage(pkg);
	}

	public Set<ClassInfo> getPackage(String pkg) {
		if (!packages.containsKey(pkg)) {
			Set<ClassInfo> set = new HashSet<ClassInfo>();
			packages.put(pkg, set);
		}
		return packages.get(pkg);
	}

	//TODO: Include interfaces.
	private static Set<ClassInfo> getSuperClasses(Set<ClassInfo> classes) {
		Set<ClassInfo> set = new HashSet<ClassInfo>();
		for (ClassInfo clazz : classes) {
			set.addAll(Arrays.asList(clazz.getSuperClasses()));
			//set.addAll(Arrays.asList(clazz.getInterfaces()));
		}
		return set;
	}

	private static Set<Declaration> getDecls(Set<ClassInfo> classes2) {
		Set<Declaration> set = new HashSet<Declaration>();
		for (ClassInfo clazz : classes2) {
			set.addAll(getDecls(clazz));
		}
		return set;
	}

	private static Set<Declaration> getDecls(ClassInfo clazz) {
		return new HashSet<Declaration>(Arrays.asList(clazz.getUniqueDeclarations()));
	}
	
	private ClassInfo getClass(String className){
		Set<ClassInfo> classes = getClasses(className, false);
		if(classes.isEmpty()) return null;
		else return classes.toArray(new ClassInfo[1])[0];
	}

	private Set<ClassInfo> getClasses(String imp, boolean isPkg) {
		if (isPkg){
			Set<ClassInfo> classes = packages.get(imp);
			if (classes != null) return classes;
		} else {
			final ClassInfo classInfo = classes.get(imp);
			if (classInfo != null) return new HashSet<ClassInfo>(){{add(classInfo);}};
		}
		
		return new HashSet<ClassInfo>();
	}

	public Declaration getTrueBooleanLiteral() {
		return new Declaration("true", factory.createPrimitiveType("boolean"), true);
	}

	private ClassInfo getJavaLangString() {
		if (JAVA_LANG_STRING == null) {
			JAVA_LANG_STRING = getClasses("java.lang.String", true).toArray(new ClassInfo[1])[0];
		}
		return JAVA_LANG_STRING;
	}

	public Declaration getFalseBooleanLiteral() {
		return new Declaration("false", factory.createPrimitiveType("boolean"), true);
	}

	public Declaration getStringLiteral() {
		return new Declaration("string", getJavaLangString().getType(), true);
	}

	public Declaration getCharacterLiteral() {
		return new Declaration("char", factory.createPrimitiveType("char"), true);
	}

	public Declaration getNumberLiteral() {
		return new Declaration("number", factory.createNoType(), true);
	}

	public boolean canBeReceiver(Declaration head, Declaration receiver) {
		return compatableTypes(head.getReceiverType(), receiver.getReceiverType());
	}

	private boolean compatableTypes(Type recParamType, Type recArgType) {
		return getCompatableTypes(recArgType).contains(recParamType);
	}

	private Set<Type> getCompatableTypes(Type type) {
		Set<Type> types = new HashSet<Type>();
		types.add(type);
		
		String head = type.getPrefix();
		ClassInfo clazz = getClass(head);
		if(clazz != null){
			List<ClassInfo> list = new LinkedList<ClassInfo>();
			list.addAll(Arrays.asList(clazz.getSuperClasses()));
			list.addAll(Arrays.asList(clazz.getInterfaces()));
			types.addAll(getTypes(list));
		}
		return types;
	}

	private Set<Type> getTypes(List<ClassInfo> list) {
		Set<Type> types = new HashSet<Type>();
		for (ClassInfo clazz : list) {
			types.add(clazz.getType());
		}
		return types;
	}

	public boolean canBeArgument(Declaration head, int i, Declaration arg) {
		return compatableTypes(head.getArgTypes()[i], arg.getReceiverType());
	}

	public DeclMap getDeclMap() {
		return declMap;
	}
}
