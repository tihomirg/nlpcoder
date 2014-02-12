package definitions;

import java.util.List;

public abstract class Type {
	private ClassInfo name;

	public Type(ClassInfo name) {
		super();
		this.setName(name);
	}

	public ClassInfo getName() {
		return name;
	}

	public void setName(ClassInfo name) {
		this.name = name;
	}
}

class SimpleType extends Type {

	public SimpleType(ClassInfo name) {
		super(name);
		// TODO Auto-generated constructor stub
	}
	
}

class ComplexType extends Type {

	private List<Type> args;

	public ComplexType(ClassInfo name, List<Type> args) {
		super(name);
		this.args = args;
	}

	public List<Type> getArgs() {
		return args;
	}

	public void setArgs(List<Type> args) {
		this.args = args;
	}

}

class TypeParameter extends Type {
	private Type bounds;

	public TypeParameter(ClassInfo name, Type bounds) {
		super(name);
		this.bounds = bounds;
	}

	public Type getBounds() {
		return bounds;
	}

	public void setBounds(Type bounds) {
		this.bounds = bounds;
	}
}