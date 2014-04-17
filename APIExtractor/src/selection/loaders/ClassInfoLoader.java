package selection.loaders;

import java.util.Collection;
import java.util.List;

import definitions.ClassInfo;
import definitions.ClassInfoFactory;

public abstract class ClassInfoLoader {
	
	protected final ClassInfoFactory cif;
	
	public ClassInfoLoader(ClassInfoFactory cif) {
		this.cif = cif;
	}

	public abstract void load(List<String> jarFiles);

	public Collection<ClassInfo> getClasses() {
		return cif.getClasses();
	}	
}
