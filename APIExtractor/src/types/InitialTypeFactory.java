package types;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import definitions.ClassInfo;

public class InitialTypeFactory extends TypeFactory {

	private final List<ReferenceType> reference = new LinkedList<ReferenceType>();
	
	public InitialTypeFactory() {
		super();
	}
	
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
