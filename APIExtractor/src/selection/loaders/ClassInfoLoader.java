package selection.loaders;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import definitions.ClassInfo;
import selection.types.InitialTypeFactory;


public abstract class ClassInfoLoader {
	
	private final Map<String, ClassInfo> classesMap = new HashMap<String, ClassInfo>();
	
	public abstract void load(List<String> jarFiles, InitialTypeFactory factory);

	public Collection<ClassInfo> getClasses() {
		return classesMap.values();
	}
	
	public Map<String, ClassInfo> getClassesMap() {
		return classesMap;
	}
}
