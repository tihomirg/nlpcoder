package selection.loaders;

import java.util.Collection;
import java.util.List;

import definitions.ClassInfo;
import definitions.factory.InitialClassInfoFactory;

public abstract class ClassInfoLoader {
	
	protected final InitialClassInfoFactory cif;
	
	public ClassInfoLoader(InitialClassInfoFactory cif) {
		this.cif = cif;
	}

	public abstract void load(List<String> jarFiles);

	public Collection<ClassInfo> getClasses() {
		return cif.getClasses();
	}	
}
