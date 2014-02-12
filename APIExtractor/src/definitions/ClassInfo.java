package definitions;

import java.util.Arrays;

public class ClassInfo {

	private String name;
	private Declaration[] declarations;
	private TypeParameter[] typeArgs;
	
	@Override
	public String toString() {
		return "ClassInfo [name=" + name + ", declarations="
				+ Arrays.toString(declarations) + ", typeArgs="
				+ Arrays.toString(typeArgs) + ", superclass=" + superclass
				+ ", superTypeArgs=" + Arrays.toString(superTypeArgs) + "]";
	}

	private String superclass;
	private TypeParameter[] superTypeArgs;

	public ClassInfo(){}
	
	public ClassInfo(String name, Declaration[] declarations,
			TypeParameter[] typeArgs, String superclass) {
		this.name = name;
		this.declarations = declarations;
		this.typeArgs = typeArgs;
		this.superclass = superclass;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Declaration[] getDeclarations() {
		return declarations;
	}
	public void setDeclarations(Declaration[] declarations) {
		this.declarations = declarations;
	}
	public Type[] getTypeArgs() {
		return typeArgs;
	}
	public void setTypeArgs(TypeParameter[] typeArgs) {
		this.typeArgs = typeArgs;
	}
	public String getSuperclass() {
		return superclass;
	}
	public void setSuperclass(String superclass) {
		this.superclass = superclass;
	}

	public TypeParameter[] getSuperTypeArgs() {
		return superTypeArgs;
	}

	public void setSuperTypeArgs(TypeParameter[] superTypeArgs) {
		this.superTypeArgs = superTypeArgs;
	}	
}
