package api;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import selection.types.InitialTypeFactory;
import selection.types.NameGenerator;
import selection.types.StabileTypeFactory;
import definitions.ClassInfo;
import definitions.Declaration;

public class StabileAPI {
	
	private static final String JAVA_LANG = "java.lang";
	private Map<String, Set<ClassInfo>> packages = new HashMap<String, Set<ClassInfo>>();	
	private Map<String, ClassInfo> classes;
	private StabileTypeFactory stf;
	
	public StabileAPI(InitialAPI icif, NameGenerator nameGen) {
		this.classes = icif.getClassesMap();
		setStf(icif.getFactory(), nameGen);
		putIntoPackages(classes.values());
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
		
		imported.addClasses(classes);
		imported.addDecls(decls);
	}

	private void putIntoPackages(Collection<ClassInfo> classes){
		for (ClassInfo clazz : classes) {
			putIntoPackages(clazz);
		}
	}
	
	private void putIntoPackages(ClassInfo clazz) {
		Set<ClassInfo> pkg = getPackage(clazz);
		pkg.add(clazz);
	}

	private Set<ClassInfo> getPackage(ClassInfo clazz) {
		String pkg = clazz.getPackageName();
		return getPackage(pkg);
	}

	private Set<ClassInfo> getPackage(String pkg) {
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

	public Map<String, ClassInfo> getClassesMap() {
		return classes;
	}

	public void setClassesMap(Map<String, ClassInfo> classesMap) {
		this.classes = classesMap;
	}

	public StabileTypeFactory getStf() {
		return stf;
	}

	public void setStf(StabileTypeFactory stf) {
		this.stf = stf;
	}
	
	public void setStf(InitialTypeFactory itf, NameGenerator nameGen) {
		this.stf = new StabileTypeFactory(nameGen);
		this.stf.setBoxed(itf.getBoxed());
		this.stf.setCons(itf.getCons());
		this.stf.setNoType(itf.getNoType());
		this.stf.setNullType(itf.getNullType());
		this.stf.setPrimitive(itf.getPrimitive());
	}
}