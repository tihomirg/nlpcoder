package definitions;

public abstract class Declaration {
	
	private String name;
	private Type type;
	
	private boolean method;
	private boolean constructor;
	private boolean field;

	public Declaration(String name, Type type, boolean method,
			boolean constructor, boolean field) {
		this.name = name;
		this.type = type;
		this.method = method;
		this.constructor = constructor;
		this.field = field;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Type getType() {
		return type;
	}
	public void setType(Type type) {
		this.type = type;
	}
	public boolean isMethod() {
		return method;
	}
	public void setMethod(boolean method) {
		this.method = method;
	}
	public boolean isConstructor() {
		return constructor;
	}
	public void setConstructor(boolean constructor) {
		this.constructor = constructor;
	}
	public boolean isField() {
		return field;
	}
	public void setField(boolean field) {
		this.field = field;
	}	
}

abstract class AMethod extends Declaration {
	private Type[] args;
	private Type retType;
	
	public AMethod(String name, Type type, boolean method, boolean constructor,
			boolean field, Type[] args, Type retType) {
		super(name, type, method, constructor, field);
		this.args = args;
		this.retType = retType;
	}

	public Type[] getArgs() {
		return args;
	}

	public void setArgs(Type[] args) {
		this.args = args;
	}

	public Type getRetType() {
		return retType;
	}

	public void setRetType(Type retType) {
		this.retType = retType;
	}
}


class Method extends AMethod {
	private TypeParameter[] typeArgs;

	public Method(String name, Type type, boolean method, boolean constructor,
			boolean field, Type[] args, Type retType, TypeParameter[] typeArgs) {
		super(name, type, method, constructor, field, args, retType);
		this.typeArgs = typeArgs;
	}

	public TypeParameter[] getTypeArgs() {
		return typeArgs;
	}

	public void setTypeArgs(TypeParameter[] typeArgs) {
		this.typeArgs = typeArgs;
	}
}

class Field extends Declaration {

	public Field(String name, Type type, boolean method, boolean constructor,
			boolean field) {
		super(name, type, method, constructor, field);
		// TODO Auto-generated constructor stub
	}
	
}

class Constructor extends AMethod {

	public Constructor(String name, Type type, boolean method,
			boolean constructor, boolean field, Type[] args, Type retType) {
		super(name, type, method, constructor, field, args, retType);
		// TODO Auto-generated constructor stub
	}
}
