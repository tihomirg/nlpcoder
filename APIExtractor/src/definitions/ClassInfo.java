package definitions;

public class ClassInfo {

	private String name;
	private Declaration[] declarations;
	private TypeParameter[] typeArgs;
	
	private ClassInfo superclass;
	private TypeParameter[] superTypeArgs;

	public ClassInfo(String name, Declaration[] declarations,
			TypeParameter[] typeArgs, ClassInfo superclass) {
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
	public ClassInfo getSuperclass() {
		return superclass;
	}
	public void setSuperclass(ClassInfo superclass) {
		this.superclass = superclass;
	}	
}
