package api;

import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.bcel.classfile.JavaClass;

import definitions.ArrayClassInfo;
import definitions.ClassInfo;

import selection.types.InitialTypeFactory;

public class InitialAPI {
	
	private Map<String, ClassInfo> classesMap = new HashMap<String, ClassInfo>();
	private InitialTypeFactory factory;
	private ArrayClassInfo aci;
	
	public InitialAPI() {}
	
	public InitialAPI(InitialTypeFactory factory) {
		this.factory = factory;
	}

	public ClassInfo createClassInfo(JavaClass javaClass){
		if (!javaClass.isPublic()) return null;
		
		ClassInfo clazz = null;
		String className = javaClass.getClassName();		
		if (classesMap.containsKey(className)){
			clazz = classesMap.get(className);
		} else {
			clazz = new ClassInfo(javaClass, factory, this);
			classesMap.put(className, clazz);
		}
		
		return clazz;
	}

	public ClassInfo[] createClassInfos(JavaClass[] javaClassses) {
		List<ClassInfo> list = new LinkedList<ClassInfo>();
		for (JavaClass javaClass: javaClassses) {
			ClassInfo clazz = createClassInfo(javaClass);
			if (clazz != null) list.add(clazz);
		}
		return list.toArray(new ClassInfo[list.size()]);
	}

	public void createArrayClassInfo(){ 
		aci = new ArrayClassInfo(factory);
		classesMap.put(aci.getName(), aci);
	}
	
	public Collection<ClassInfo> getClasses() {
		return classesMap.values();
	}
	
	public void connectTypesAndClassInfos() {
	    factory.connectTypesAndClassInfos(classesMap);
	}

	public void setArrayClassInfoSuperClasses() {
		aci.setSuperClasses();
	}

	public Map<String, ClassInfo> getClassesMap() {
		return classesMap;
	}

	public void setClassesMap(Map<String, ClassInfo> classesMap) {
		this.classesMap = classesMap;
	}

	public InitialTypeFactory getFactory() {
		return factory;
	}

	public void setFactory(InitialTypeFactory factory) {
		this.factory = factory;
	}

	public ArrayClassInfo getAci() {
		return aci;
	}

	public void setAci(ArrayClassInfo aci) {
		this.aci = aci;
	}
	
	
	
}
