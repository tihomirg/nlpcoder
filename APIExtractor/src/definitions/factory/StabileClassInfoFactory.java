package definitions.factory;

import java.util.Map;
import selection.types.InitialTypeFactory;
import selection.types.NameGenerator;
import selection.types.StabileTypeFactory;

import definitions.ClassInfo;

public class StabileClassInfoFactory {
	private Map<String, ClassInfo> classesMap;
	private StabileTypeFactory stf;
	
	public StabileClassInfoFactory(InitialClassInfoFactory icif, NameGenerator nameGen) {
		this.classesMap = icif.getClassesMap();
		setStf(icif.getFactory(), nameGen);
	}

	public ClassInfo createClassInfo(ClassInfo clazz){
		if (clazz == null) return null;
		String name = clazz.getName();
		if (!classesMap.containsKey(name)){
			classesMap.put(name, clazz);
		}
		return classesMap.get(name);
	}

	public Map<String, ClassInfo> getClassesMap() {
		return classesMap;
	}

	public void setClassesMap(Map<String, ClassInfo> classesMap) {
		this.classesMap = classesMap;
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
