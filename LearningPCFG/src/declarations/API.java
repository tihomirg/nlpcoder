package declarations;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import definitions.ClassInfo;
import definitions.Declaration;

public class API {

	private static final String JAVA_LANG = "java.lang";
	private Map<String, Set<ClassInfo>> packages = new HashMap<String, Set<ClassInfo>>();
	private Map<String, ClassInfo> classes = new HashMap<String, ClassInfo>();	
	
	public Imported createImported() {
		Imported imported = new Imported();
		load(imported, JAVA_LANG, false);
		return imported;
	}

	public void load(Imported imported, String imp){
		
	}
	
	public void load(Imported imported, String imp, boolean isPkg) {
		Set<ClassInfo> classes = getClasses(imp, isPkg);
		Set<ClassInfo>  superClasses = getSuperClasses(classes);
		classes.addAll(superClasses);
        Set<Declaration> decls = getDecls(classes);
		
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

}
