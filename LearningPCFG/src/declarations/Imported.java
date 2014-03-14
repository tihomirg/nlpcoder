package declarations;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import definitions.ClassInfo;
import definitions.Declaration;

public class Imported {
	
	private Map<String, Set<ClassInfo>> classes;
	private Map<String, Set<Declaration>> fields;
	private Map<String, Set<Declaration>> methods;
	private Map<String, Set<Declaration>> cons;
	
	public Imported() {
		this.classes = new HashMap<String, Set<ClassInfo>>();
		this.fields = new HashMap<String, Set<Declaration>>();
		this.methods = new HashMap<String, Set<Declaration>>();
		this.cons = new HashMap<String, Set<Declaration>>();
	}
	
	public boolean isImportedClass(String type) {
		return classes.containsKey(type);
	}

	public boolean isImportedField(String name) {
		return fields.containsKey(name);
	}

	public boolean isImportedMethod(String name) {
		return methods.containsKey(name);
	}

	public void addClasses(Set<ClassInfo> classes) {
		for (ClassInfo clazz : classes) {
			addClass(clazz);
		}
	}

	public void addClass(ClassInfo clazz) {
		getClasses(clazz.getSimpleName()).add(clazz);
	}

	public Set<ClassInfo> getClasses(String name) {
		return get(name, classes);
	}

	public void addDecls(Set<Declaration> decls) {
		for (Declaration decl : decls) {
			addDecl(decl);
		}		
	}

	public void addDecl(Declaration decl) {
		String name = decl.getSimpleName();
		if (decl.isMethod()){
			if (decl.isConstructor()){
				getConstructors(name).add(decl);
			} else {
				getMethods(name).add(decl);				
			}
		} else if (decl.isField()) {
			getFields(name).add(decl);
		}
	}

	public Set<Declaration> getConstructors(String name) {
		return get(name, cons);
	}

	public Set<Declaration> getMethods(String name) {
		return get(name, methods);
	}

	public Set<Declaration> getFields(String name) {
		return get(name, fields);
	}

	private <T> Set<T> get(String name, Map<String, Set<T>> cons) {
		if(!cons.containsKey(name)){
			HashSet<T> set = new HashSet<T>();
			cons.put(name, set);
		}
		return cons.get(name);
	}

	public Map<String, Set<Declaration>> getMethods() {
		return methods;
	}

	public Map<String, Set<ClassInfo>> getClasses() {
		return classes;
	}
}
