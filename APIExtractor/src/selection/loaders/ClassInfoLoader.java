package selection.loaders;

import java.util.Collection;
import java.util.List;

import definitions.ClassInfo;
import definitions.factory.InitialClassInfoFactory;

public abstract class ClassInfoLoader {
	
	private InitialClassInfoFactory cif;
	
	public ClassInfoLoader(){}
	
	public ClassInfoLoader(InitialClassInfoFactory cif) {
		this.cif = cif;
	}

	public abstract void load(List<String> jarFiles);

	public Collection<ClassInfo> getClasses() {
		return cif.getClasses();
	}

	public InitialClassInfoFactory getCif() {
		return cif;
	}

	public void setCif(InitialClassInfoFactory cif) {
		this.cif = cif;
	}

	public void loadArrayClassInfo() {
		cif.createArrayClassInfo();
	}
	
	public void connectTypesAndClassInfos(){
		cif.connectTypesAndClassInfos();
	}

	public void setArrayClassInfoSuperClasses() {
		cif.setArrayClassInfoSuperClasses();
	}
}
