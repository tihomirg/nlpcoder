package api;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import definitions.ClassInfo;
import definitions.Declaration;

public class Imported {
	
	private Map<String, Set<ClassInfo>> classes;
	private Map<String, ClassInfo> types;	
	
	private Map<String, Set<Declaration>> fields;
	private Map<String, Map<Integer, Set<Declaration>>> methods;
	private Map<String, Map<Integer, Set<Declaration>>> cons;
	
	public Imported() {
		this.classes = new HashMap<String, Set<ClassInfo>>();
		this.fields = new HashMap<String, Set<Declaration>>();
		this.methods = new HashMap<String, Map<Integer,Set<Declaration>>>();
		this.cons = new HashMap<String, Map<Integer,Set<Declaration>>>();
		this.types = new HashMap<String, ClassInfo>();
	}
	
	public boolean isImportedClass(String type) {
		return classes.containsKey(type);
	}

	public boolean isImportedType(String type) {
		return types.containsKey(type);
	}
	
	public boolean isImportedField(String name) {
		return fields.containsKey(name);
	}
	
	public boolean isImporteddConstructor(String name, int argNum){
		return isImported(name, argNum, cons);
	}
	
	public boolean isImportedMethod(String name, int argNum) {
		return isImported(name, argNum, methods);
	}

	private static <K,A,T> boolean isImported(K name, A argNum, Map<K, Map<A, Set<T>>> map) {
		if (map.containsKey(name)){
			return map.get(name).containsKey(argNum);
		} else return false;
	}

	public void addClasses(Set<ClassInfo> classes) {
		for (ClassInfo clazz : classes) {
			addClass(clazz);
		}
	}
	
	public void addTypes(Set<ClassInfo> classes) {
		for (ClassInfo clazz : classes) {
			addType(clazz);
		}
	}

	private void addType(ClassInfo clazz) {
		types.put(clazz.getSimpleName(), clazz);
	}

	public void addClass(ClassInfo clazz) {
		getClasses(clazz.getSimpleName()).add(clazz);
	}

	public Set<ClassInfo> getClasses(String name) {
		return getSet(name, classes);
	}
	
	public ClassInfo getFirstType(String name) {
		return types.get(name);
	}

	public Set<Declaration> getFields(String name) {
		return getSet(name, fields);
	}
	
	public Map<String, Map<Integer, Set<Declaration>>> getMethods() {
		return methods;
	}

	public Map<String, Set<ClassInfo>> getClasses() {
		return classes;
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
				getConstructors(name, decl.getArgNum()).add(decl);
			} else {
				getMethods(name, decl.getArgNum()).add(decl);				
			}
		} else if (decl.isField()) {
			getFields(name).add(decl);
		}
	}
	
	public Map<Integer, Set<Declaration>> getConstructors(String name) {
		return getMap(name, cons);
	}
	
	private static <K,A,T> Map<A, Set<T>> getMap(K name, Map<K, Map<A, Set<T>>> decls) {
		if (!decls.containsKey(name)) {
			decls.put(name, new HashMap<A, Set<T>>());
		}
		return decls.get(name);
	}

	public Map<Integer, Set<Declaration>> getMethods(String name) {
		return getMap(name, methods);
	}

	public Set<Declaration> getConstructors(String name, int argNum) {
		return getSet(name, argNum, cons);
	}

	public Set<Declaration> getMethods(String name, int argNum) {
		return getSet(name, argNum, methods);
	}

	private static <K,A,T> Set<T> getSet(K name, A argNum, Map<K, Map<A, Set<T>>> decls) {
		return getSet(argNum, getMap(name, decls));
	}
	
	private static <K, T> Set<T> getSet(K name, Map<K, Set<T>> cons) {
		if(!cons.containsKey(name)){
			cons.put(name, new HashSet<T>());
		}
		return cons.get(name);
	}	
}
