package definitions;

import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.bcel.classfile.JavaClass;

import selection.types.InitialTypeFactory;

public class InitialClassInfoFactory {
	
	private final Map<String, ClassInfo> classesMap = new HashMap<String, ClassInfo>();
	private InitialTypeFactory factory;
	
	public InitialClassInfoFactory(InitialTypeFactory factory) {
		this.factory = factory;
	}

	public ClassInfo createClassInfo(JavaClass javaClass){
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
			list.add(createClassInfo(javaClass));
		}
		return list.toArray(new ClassInfo[list.size()]);
	}

	public Collection<ClassInfo> getClasses() {
		return classesMap.values();
	}
	
	public void connectTypesAndClassInfos() {
	    factory.connectTypesAndClassInfos(classesMap);
	}
}
