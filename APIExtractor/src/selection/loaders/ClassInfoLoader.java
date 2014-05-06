package selection.loaders;

import java.util.Collection;
import java.util.List;

import api.InitialAPI;

import definitions.ClassInfo;

public abstract class ClassInfoLoader {
	
	private InitialAPI cif;
	
	public ClassInfoLoader(){}
	
	public ClassInfoLoader(InitialAPI cif) {
		this.cif = cif;
	}

	public abstract void load(List<String> jarFiles);

	public Collection<ClassInfo> getClasses() {
		return cif.getClasses();
	}

	public InitialAPI getCif() {
		return cif;
	}

	public void setCif(InitialAPI cif) {
		this.cif = cif;
	}

	public void loadArrayClassInfo() {
		cif.createArrayClassInfo();
	}
	
	public void connectTypesAndClassInfos(){
		cif.connectTypesAndClassInfos();
	}
}
