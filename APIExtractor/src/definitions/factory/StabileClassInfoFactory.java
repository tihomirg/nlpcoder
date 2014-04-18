package definitions.factory;

import java.util.HashMap;
import java.util.Map;

import definitions.ClassInfo;

public class StabileClassInfoFactory {
	private final Map<String, ClassInfo> classesMap = new HashMap<String, ClassInfo>();
	
	public ClassInfo createClassInfo(ClassInfo clazz){
		if (clazz == null) return null;
		String name = clazz.getName();
		if (!classesMap.containsKey(name)){
			classesMap.put(name, clazz);
		}
		return classesMap.get(name);
	}
}
