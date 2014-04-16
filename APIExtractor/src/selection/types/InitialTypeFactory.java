package selection.types;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;


import definitions.ClassInfo;

public class InitialTypeFactory extends TypeFactory {

	private final Set<ReferenceType> reference = new HashSet<ReferenceType>();
	
	public InitialTypeFactory(NameGenerator nameGen) {
		super(nameGen);
	}

	public Variable createVariable(String name) {
		return new Variable(name);
	}

	@Override
	protected void addReferenceType(ReferenceType type) {
		this.reference.add(type);
	}

	public void connectTypesAndClassInfos(Map<String, ClassInfo> map){
		for (ReferenceType type : reference) {
			String name = type.getPrefix();
			if(map.containsKey(name)){
				type.setClassInfo(map.get(name));
			}
		}
	}
	
}
