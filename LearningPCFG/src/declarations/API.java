package declarations;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import definitions.ClassInfo;
import definitions.Declaration;

public class API {

	private Map<String, Set<ClassInfo>> fullPackages = new HashMap<String, Set<ClassInfo>>();
	private Map<String, ClassInfo> classes = new HashMap<String, ClassInfo>();	
	
	public Imported createImported() {
		return null;
	}

	public void load(Imported imported, String imp, boolean single) {
		Set<ClassInfo>  classes = getSuperClasses(getTypes(imp, single));
        Set<Declaration> decls = getDecls(classes);
		
		imported.addTypes(classes);
		imported.addDecls(decls);
	}

	private Set<ClassInfo> getSuperClasses(Set<ClassInfo> classes) {
		Set<ClassInfo> set = new HashSet<ClassInfo>();
		for (ClassInfo clazz : classes) {
			set.addAll(Arrays.asList(clazz.getSuperClasses()));
			//set.addAll(Arrays.asList(clazz.getInterfaces()));
		}
		return set;
	}

	private Set<Declaration> getDecls(Set<ClassInfo> classes2) {
		Set<Declaration> set = new HashSet<Declaration>();
		for (ClassInfo clazz : classes2) {
			set.addAll(getDecls(clazz));	
		}
		return set;
	}

	private Set<Declaration> getDecls(ClassInfo clazz) {
		return new HashSet<Declaration>(Arrays.asList(clazz.getUniquDeclarations()));
	}

	private Set<ClassInfo> getTypes(String imp, boolean single) {
		if (!single){
			Set<ClassInfo> classes = fullPackages.get(imp);
			if (classes != null) return classes;
		} else {
			final ClassInfo classInfo = classes.get(imp);
			if (classInfo != null) return new HashSet<ClassInfo>(){{add(classInfo);}};
		}
		
		return new HashSet<ClassInfo>();
	}

}
